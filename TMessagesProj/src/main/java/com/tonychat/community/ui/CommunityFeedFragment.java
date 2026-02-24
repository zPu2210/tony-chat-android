package com.tonychat.community.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tonychat.community.CommunityBridge;
import com.tonychat.community.DeviceIdHelper;
import com.tonychat.community.model.Post;
import com.tonychat.community.repository.LocationHelper;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Community feed showing global posts (no location required).
 * Implements pagination, pull-to-refresh, visual empty state, and image posting.
 */
public class CommunityFeedFragment extends BaseFragment {
    private static final int REQUEST_LOCATION = 101;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private ImageView fabCreate;
    private LinearLayout emptyStateLayout;

    private LocationHelper locationHelper;
    private String deviceId;
    private Context safeContext;

    private List<Post> posts = new ArrayList<>();
    private boolean isLoading = false;
    private boolean hasLoadedInitial = false;
    private int pageOffset = 0;
    private Location lastLocation;
    private CreatePostBottomSheet currentSheet;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        safeContext = context;

        if (locationHelper == null) {
            locationHelper = new LocationHelper(context);
            deviceId = DeviceIdHelper.INSTANCE.getDeviceId(context);
        }

        if (getParentLayout() != null && getParentLayout().getFragmentStack().size() > 1) {
            actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        }
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Community Board");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1)
                    finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        // SwipeRefreshLayout
        swipeRefreshLayout = new SwipeRefreshLayout(context);
        swipeRefreshLayout.setColorSchemeColors(Theme.getColor(Theme.key_actionBarDefault));
        swipeRefreshLayout.setOnRefreshListener(this::refreshPosts);
        frameLayout.addView(swipeRefreshLayout, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        // RecyclerView
        recyclerView = new RecyclerView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(true);
        swipeRefreshLayout.addView(recyclerView, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        adapter = new PostAdapter(posts, this::onPostClick, this::onLikeClick);
        recyclerView.setAdapter(adapter);

        // Pagination scroll listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                if (!isLoading && dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount - 2) {
                        loadMorePosts();
                    }
                }
            }
        });

        // Empty state
        emptyStateLayout = new LinearLayout(context);
        emptyStateLayout.setOrientation(LinearLayout.VERTICAL);
        emptyStateLayout.setGravity(Gravity.CENTER);
        emptyStateLayout.setVisibility(View.GONE);
        emptyStateLayout.setPadding(AndroidUtilities.dp(32), AndroidUtilities.dp(80), AndroidUtilities.dp(32), 0);

        ImageView emptyIcon = new ImageView(context);
        emptyIcon.setImageResource(R.drawable.msg_groups);
        emptyIcon.setColorFilter(new android.graphics.PorterDuffColorFilter(
            0xFFBBBBBB, android.graphics.PorterDuff.Mode.SRC_IN));
        emptyStateLayout.addView(emptyIcon, LayoutHelper.createLinear(64, 64, Gravity.CENTER, 0, 0, 0, 16));

        TextView emptyTitle = new TextView(context);
        emptyTitle.setText("No Posts Yet");
        emptyTitle.setTextSize(20);
        emptyTitle.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        emptyTitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        emptyTitle.setGravity(Gravity.CENTER);
        emptyStateLayout.addView(emptyTitle, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        TextView emptySubtitle = new TextView(context);
        emptySubtitle.setText("Be the first to share something!");
        emptySubtitle.setTextSize(15);
        emptySubtitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        emptySubtitle.setGravity(Gravity.CENTER);
        emptyStateLayout.addView(emptySubtitle, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 24));

        TextView createBtn = new TextView(context);
        createBtn.setText("Create Post");
        createBtn.setTextSize(15);
        createBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        createBtn.setTextColor(0xFF111111);
        createBtn.setGravity(Gravity.CENTER);
        createBtn.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(12), AndroidUtilities.dp(24), AndroidUtilities.dp(12));
        createBtn.setBackground(Theme.createSimpleSelectorRoundRectDrawable(
            AndroidUtilities.dp(24), 0xFFF9E000, 0xFFE0C900));
        createBtn.setOnClickListener(v -> showCreatePostSheet());
        emptyStateLayout.addView(createBtn, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

        frameLayout.addView(emptyStateLayout, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        // FAB for create post
        fabCreate = new ImageView(context);
        fabCreate.setImageResource(R.drawable.msg_send);
        fabCreate.setScaleType(ImageView.ScaleType.CENTER);
        fabCreate.setContentDescription("Create new post");
        fabCreate.setColorFilter(
                new android.graphics.PorterDuffColorFilter(0xFF111111, android.graphics.PorterDuff.Mode.SRC_IN));
        android.graphics.drawable.Drawable circle = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56),
                0xFFF9E000, 0xFFE0C900);
        fabCreate.setBackground(circle);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            fabCreate.setElevation(AndroidUtilities.dp(4));
            fabCreate.setOutlineProvider(new android.view.ViewOutlineProvider() {
                @Override
                public void getOutline(View view, android.graphics.Outline outline) {
                    outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                }
            });
            fabCreate.setClipToOutline(true);
        }
        fabCreate.setOnClickListener(v -> showCreatePostSheet());
        frameLayout.addView(fabCreate, LayoutHelper.createFrame(
                56, 56, Gravity.END | Gravity.BOTTOM, 0, 0, 16, 106));

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasLoadedInitial) {
            hasLoadedInitial = true;
            checkLocationPermissionAndLoad();
        }
    }

    private Context ctx() {
        Activity a = getParentActivity();
        return a != null ? a : safeContext;
    }

    private void checkLocationPermissionAndLoad() {
        // Load global feed immediately — no location required
        loadInitialPosts();
        // Silently try to get location for post creation
        if (locationHelper != null && locationHelper.hasLocationPermission()) {
            CommunityBridge.getCurrentLocation(ctx(), location -> {
                if (location != null) lastLocation = location;
            });
        }
    }

    private void requestLocationPermission() {
        Activity activity = getParentActivity();
        if (activity == null) {
            loadInitialPosts();
            return;
        }
        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Got permission — get location for post creation
                CommunityBridge.getCurrentLocation(ctx(), location -> {
                    if (location != null) lastLocation = location;
                });
            }
            // Load global feed regardless of permission
            loadInitialPosts();
        }
    }

    @Override
    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (requestCode == 201 && resultCode == Activity.RESULT_OK && data != null) {
            if (currentSheet != null) {
                currentSheet.onImagePicked(data.getData());
            }
        }
    }

    private void loadInitialPosts() {
        pageOffset = 0;
        posts.clear();
        loadPosts();
    }

    private void refreshPosts() {
        pageOffset = 0;
        posts.clear();
        loadPosts();
    }

    private void loadMorePosts() {
        pageOffset += 20;
        loadPosts();
    }

    private void loadPosts() {
        if (isLoading) return;
        Context c = ctx();
        if (c == null) return;
        isLoading = true;

        CommunityBridge.getAllPosts(pageOffset, deviceId, newPosts -> {
            if (pageOffset == 0) {
                posts.clear();
            }
            posts.addAll(newPosts);
            adapter.notifyDataSetChanged();

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            isLoading = false;

            updateEmptyState();
        });
    }

    private void updateEmptyState() {
        if (emptyStateLayout == null) return;
        if (posts.isEmpty()) {
            emptyStateLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyStateLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void onPostClick(Post post) {
        PostDetailFragment fragment = PostDetailFragment.newInstance(post);
        presentFragment(fragment);
    }

    private void onLikeClick(Post post, int position) {
        boolean wasLiked = post.isLiked();
        long originalCount = post.getLikeCount();

        post.setLiked(!wasLiked);
        post.setLikeCount(originalCount + (post.isLiked() ? 1 : -1));
        adapter.notifyItemChanged(position);

        if (post.isLiked()) {
            CommunityBridge.likePost(post.getId(), deviceId, success -> {
                if (!success) {
                    post.setLiked(wasLiked);
                    post.setLikeCount(originalCount);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(ctx(), "Failed to like post. Tap to retry.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            CommunityBridge.unlikePost(post.getId(), deviceId, success -> {
                if (!success) {
                    post.setLiked(wasLiked);
                    post.setLikeCount(originalCount);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(ctx(), "Failed to unlike post. Tap to retry.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showCreatePostSheet() {
        Activity activity = getParentActivity();
        if (activity == null) return;

        // If no location, use default (0,0) — global posts don't require location
        Location loc = lastLocation;
        if (loc == null) {
            loc = new Location("default");
            loc.setLatitude(0);
            loc.setLongitude(0);
        }

        final Location finalLoc = loc;
        currentSheet = new CreatePostBottomSheet(
                activity,
                finalLoc,
                deviceId,
                post -> {
                    posts.add(0, post);
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                    updateEmptyState();
                });
        currentSheet.show();
    }

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }
}
