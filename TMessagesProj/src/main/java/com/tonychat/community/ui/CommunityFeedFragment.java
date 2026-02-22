package com.tonychat.community.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
 * Community feed showing nearby posts.
 * Implements pagination, pull-to-refresh, and location-based loading.
 */
public class CommunityFeedFragment extends BaseFragment {
    private static final int REQUEST_LOCATION = 101;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private FloatingActionButton fabCreate;

    private LocationHelper locationHelper;
    private String deviceId;
    // Cached context from createView — safe to use throughout the fragment lifecycle.
    // getParentActivity() can return null when fragment is inside a tab INavigationLayout.
    private Context safeContext;

    private List<Post> posts = new ArrayList<>();
    private boolean isLoading = false;
    private boolean hasLoadedInitial = false;
    private int pageOffset = 0;
    private Location lastLocation;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        safeContext = context;

        // Initialize helpers using the guaranteed non-null context param
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
                if (id == -1) finishFragment();
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
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT
        ));

        // RecyclerView
        recyclerView = new RecyclerView(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(true);
        swipeRefreshLayout.addView(recyclerView, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT
        ));

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

        // FAB for create post
        fabCreate = new FloatingActionButton(context);
        fabCreate.setImageResource(R.drawable.msg_send);
        fabCreate.setContentDescription("Create new post");
        fabCreate.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(0xFFF9E000)
        );
        fabCreate.setImageTintList(
            android.content.res.ColorStateList.valueOf(0xFF111111)
        );
        fabCreate.setOnClickListener(v -> showCreatePostSheet());
        // Bottom margin accounts for floating pill nav bar (66dp + 24dp margin + 16dp gap)
        frameLayout.addView(fabCreate, LayoutHelper.createFrame(
            56, 56, Gravity.END | Gravity.BOTTOM, 0, 0, 16, 106
        ));

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

    /** Return a non-null context: prefer getParentActivity(), fall back to cached context. */
    private Context ctx() {
        Activity a = getParentActivity();
        return a != null ? a : safeContext;
    }

    private void checkLocationPermissionAndLoad() {
        if (locationHelper == null) return;
        if (!locationHelper.hasLocationPermission()) {
            requestLocationPermission();
        } else {
            loadInitialPosts();
        }
    }

    private void requestLocationPermission() {
        Activity activity = getParentActivity();
        if (activity == null) {
            // Can't request permissions without an Activity — load without location
            showEmptyState();
            return;
        }
        ActivityCompat.requestPermissions(
            activity,
            new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            },
            REQUEST_LOCATION
        );
    }

    @Override
    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadInitialPosts();
            } else {
                Toast.makeText(ctx(), "Location permission required. Go to Settings to enable.", Toast.LENGTH_LONG).show();
                showEmptyState();
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

        CommunityBridge.getCurrentLocation(c, location -> {
            if (location != null) {
                lastLocation = location;
                CommunityBridge.getNearbyPosts(
                    location.getLatitude(),
                    location.getLongitude(),
                    5000,
                    pageOffset,
                    deviceId,
                    newPosts -> {
                        if (pageOffset == 0) {
                            posts.clear();
                        }
                        posts.addAll(newPosts);
                        adapter.notifyDataSetChanged();

                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        isLoading = false;

                        if (posts.isEmpty()) {
                            showEmptyState();
                        }
                    }
                );
            } else {
                Toast.makeText(ctx(), "Unable to get location. Pull down to retry.", Toast.LENGTH_LONG).show();
                isLoading = false;
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void showEmptyState() {
        Toast.makeText(ctx(), "No posts nearby. Be the first!", Toast.LENGTH_LONG).show();
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

        if (lastLocation == null) {
            Toast.makeText(activity, "Getting your location...", Toast.LENGTH_LONG).show();
            return;
        }

        CreatePostBottomSheet sheet = new CreatePostBottomSheet(
            activity,
            lastLocation,
            deviceId,
            post -> {
                posts.add(0, post);
                adapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
            }
        );
        sheet.show();
    }

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }
}
