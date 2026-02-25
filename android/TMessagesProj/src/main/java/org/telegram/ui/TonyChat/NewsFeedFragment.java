package org.telegram.ui.TonyChat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tonychat.community.NewsBridge;
import com.tonychat.community.model.NewsArticle;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Explore tab — AI-curated news feed.
 * Category tabs, news cards, pull-to-refresh, pagination, Chrome Custom Tab.
 * In-memory cache only (no Room DB).
 */
public class NewsFeedFragment extends BaseFragment {

    private static final String[] CATEGORIES = {"all", "technology", "business", "world", "entertainment", "sports"};
    private static final String[] CATEGORY_LABELS = {"All", "Tech", "Business", "World", "Entertainment", "Sports"};

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NewsCardAdapter adapter;
    private LinearLayout emptyStateLayout;
    private ProgressBar loadingSpinner;
    private LinearLayout categoryTabsContainer;

    private final List<NewsArticle> articles = new ArrayList<>();
    private final List<TextView> categoryChips = new ArrayList<>();
    private String selectedCategory = "all";
    private boolean isLoading = false;
    private boolean hasLoadedInitial = false;
    private int pageOffset = 0;
    private Context safeContext;

    /** Expose RecyclerView for scroll-to-hide bottom nav wiring. */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        safeContext = context;

        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Explore");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        // Main content container (below category tabs)
        LinearLayout mainContainer = new LinearLayout(context);
        mainContainer.setOrientation(LinearLayout.VERTICAL);
        frameLayout.addView(mainContainer, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        // Category tabs (horizontal scroll)
        buildCategoryTabs(context, mainContainer);

        // SwipeRefreshLayout
        swipeRefreshLayout = new SwipeRefreshLayout(context);
        swipeRefreshLayout.setColorSchemeColors(0xFFF9E000);
        swipeRefreshLayout.setOnRefreshListener(this::onPullToRefresh);
        mainContainer.addView(swipeRefreshLayout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        // RecyclerView
        recyclerView = new RecyclerView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(true);
        swipeRefreshLayout.addView(recyclerView, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        adapter = new NewsCardAdapter(articles, this::onArticleClick);
        recyclerView.setAdapter(adapter);

        // Pagination scroll listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                if (!isLoading && dy > 0) {
                    int visibleCount = layoutManager.getChildCount();
                    int totalCount = layoutManager.getItemCount();
                    int firstVisible = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleCount + firstVisible) >= totalCount - 2) {
                        loadMoreArticles();
                    }
                }
            }
        });

        // Loading spinner (centered)
        loadingSpinner = new ProgressBar(context);
        loadingSpinner.setVisibility(View.GONE);
        frameLayout.addView(loadingSpinner, LayoutHelper.createFrame(
                48, 48, Gravity.CENTER));

        // Empty state
        buildEmptyState(context, frameLayout);

        return fragmentView;
    }

    private void buildCategoryTabs(Context context, LinearLayout parent) {
        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        scrollView.setPadding(AndroidUtilities.dp(12), AndroidUtilities.dp(8),
                AndroidUtilities.dp(12), AndroidUtilities.dp(8));

        categoryTabsContainer = new LinearLayout(context);
        categoryTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        categoryTabsContainer.setGravity(Gravity.CENTER_VERTICAL);

        for (int i = 0; i < CATEGORIES.length; i++) {
            final String category = CATEGORIES[i];
            TextView chip = new TextView(context);
            chip.setText(CATEGORY_LABELS[i]);
            chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            chip.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            chip.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(8),
                    AndroidUtilities.dp(16), AndroidUtilities.dp(8));
            chip.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams chipLp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            chipLp.rightMargin = AndroidUtilities.dp(8);
            chip.setLayoutParams(chipLp);

            chip.setOnClickListener(v -> onCategorySelected(category));
            categoryChips.add(chip);
            categoryTabsContainer.addView(chip);
        }

        scrollView.addView(categoryTabsContainer);
        parent.addView(scrollView, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // Divider below tabs
        View divider = new View(context);
        divider.setBackgroundColor(0xFFF0F0F0);
        parent.addView(divider, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, 1));

        updateChipStyles();
    }

    private void updateChipStyles() {
        for (int i = 0; i < categoryChips.size(); i++) {
            TextView chip = categoryChips.get(i);
            boolean active = CATEGORIES[i].equals(selectedCategory);

            GradientDrawable bg = new GradientDrawable();
            bg.setShape(GradientDrawable.RECTANGLE);
            bg.setCornerRadius(AndroidUtilities.dp(16));

            if (active) {
                bg.setColor(0xFFF9E000);
                chip.setTextColor(0xFF111111);
            } else {
                bg.setColor(0xFFF5F5F5);
                chip.setTextColor(0xFF666666);
            }
            chip.setBackground(bg);
        }
    }

    private void onCategorySelected(String category) {
        if (category.equals(selectedCategory)) return;
        selectedCategory = category;
        updateChipStyles();

        // Check in-memory cache first
        NewsBridge.getCached(selectedCategory, cached -> {
            if (!cached.isEmpty()) {
                articles.clear();
                articles.addAll(cached);
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                updateEmptyState();
            } else {
                loadInitialArticles();
            }
        });
    }

    private void buildEmptyState(Context context, FrameLayout parent) {
        emptyStateLayout = new LinearLayout(context);
        emptyStateLayout.setOrientation(LinearLayout.VERTICAL);
        emptyStateLayout.setGravity(Gravity.CENTER);
        emptyStateLayout.setVisibility(View.GONE);
        emptyStateLayout.setPadding(AndroidUtilities.dp(32), AndroidUtilities.dp(80),
                AndroidUtilities.dp(32), 0);

        ImageView emptyIcon = new ImageView(context);
        emptyIcon.setImageResource(R.drawable.msg_archive);
        emptyIcon.setColorFilter(new android.graphics.PorterDuffColorFilter(
                0xFFBBBBBB, android.graphics.PorterDuff.Mode.SRC_IN));
        emptyStateLayout.addView(emptyIcon, LayoutHelper.createLinear(
                64, 64, Gravity.CENTER, 0, 0, 0, 16));

        TextView emptyTitle = new TextView(context);
        emptyTitle.setText("No articles available");
        emptyTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        emptyTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        emptyTitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        emptyTitle.setGravity(Gravity.CENTER);
        emptyStateLayout.addView(emptyTitle, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        TextView emptySubtitle = new TextView(context);
        emptySubtitle.setText("Pull down to refresh or check your connection.");
        emptySubtitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        emptySubtitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        emptySubtitle.setGravity(Gravity.CENTER);
        emptyStateLayout.addView(emptySubtitle, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 24));

        TextView retryBtn = new TextView(context);
        retryBtn.setText("Retry");
        retryBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        retryBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        retryBtn.setTextColor(0xFF111111);
        retryBtn.setGravity(Gravity.CENTER);
        retryBtn.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(12),
                AndroidUtilities.dp(24), AndroidUtilities.dp(12));
        retryBtn.setBackground(Theme.createSimpleSelectorRoundRectDrawable(
                AndroidUtilities.dp(24), 0xFFF9E000, 0xFFE0C900));
        retryBtn.setOnClickListener(v -> loadInitialArticles());
        emptyStateLayout.addView(retryBtn, LayoutHelper.createLinear(
                LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

        parent.addView(emptyStateLayout, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasLoadedInitial) {
            hasLoadedInitial = true;
            loadInitialArticles();
        } else {
            // Check staleness on resume (app foreground)
            NewsBridge.isStale(stale -> {
                if (stale) loadInitialArticles();
            });
        }
    }

    private Context ctx() {
        Activity a = getParentActivity();
        return a != null ? a : safeContext;
    }

    private void loadInitialArticles() {
        pageOffset = 0;
        articles.clear();
        adapter.notifyDataSetChanged();
        loadingSpinner.setVisibility(View.VISIBLE);
        emptyStateLayout.setVisibility(View.GONE);
        loadArticles();
    }

    private void onPullToRefresh() {
        NewsBridge.clearCache();
        pageOffset = 0;
        articles.clear();
        loadArticles();
    }

    private void loadMoreArticles() {
        pageOffset += 20;
        loadArticles();
    }

    private void loadArticles() {
        if (isLoading) return;
        isLoading = true;

        NewsBridge.getArticles(selectedCategory, pageOffset, newArticles -> {
            if (pageOffset == 0) {
                articles.clear();
            }
            articles.addAll(newArticles);
            adapter.notifyDataSetChanged();

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            loadingSpinner.setVisibility(View.GONE);
            isLoading = false;
            updateEmptyState();
        });
    }

    private void updateEmptyState() {
        if (emptyStateLayout == null) return;
        if (articles.isEmpty()) {
            emptyStateLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyStateLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void onArticleClick(NewsArticle article) {
        // Login gate: reading full article requires login
        LoginPromptSheet.checkAndRun(this, () -> openArticleUrl(article.getSourceUrl()));
    }

    private void openArticleUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        Activity activity = getParentActivity();
        if (activity == null) return;

        try {
            // Try Chrome Custom Tab
            androidx.browser.customtabs.CustomTabsIntent customTab =
                    new androidx.browser.customtabs.CustomTabsIntent.Builder()
                            .setToolbarColor(0xFFF9E000)
                            .setShowTitle(true)
                            .build();
            customTab.launchUrl(activity, Uri.parse(url));
        } catch (Exception e) {
            // Fallback: open in default browser
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent);
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }
}
