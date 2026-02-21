package com.tonychat.community.ui;

import android.Manifest;
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

    private List<Post> posts = new ArrayList<>();
    private boolean isLoading = false;
    private int pageOffset = 0;
    private Location lastLocation;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        // Initialize helpers here (not in onFragmentCreate) because
        // parentLayout isn't set until after onFragmentCreate returns,
        // so getParentActivity() would return null there.
        if (locationHelper == null) {
            locationHelper = new LocationHelper(context);
            deviceId = DeviceIdHelper.INSTANCE.getDeviceId(context);
        }
        // Only show back button if not the root fragment (e.g., opened from drawer)
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
            android.content.res.ColorStateList.valueOf(Theme.getColor(Theme.key_chats_actionBackground))
        );
        fabCreate.setOnClickListener(v -> showCreatePostSheet());
        frameLayout.addView(fabCreate, LayoutHelper.createFrame(
            56, 56, Gravity.END | Gravity.BOTTOM, 0, 0, 16, 16
        ));

        checkLocationPermissionAndLoad();
        return fragmentView;
    }

    private void checkLocationPermissionAndLoad() {
        if (!locationHelper.hasLocationPermission()) {
            requestLocationPermission();
        } else {
            loadInitialPosts();
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
            getParentActivity(),
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
                Toast.makeText(getParentActivity(), "Location permission required. Go to Settings to enable.", Toast.LENGTH_LONG).show();
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
        isLoading = true;

        CommunityBridge.getCurrentLocation(getParentActivity(), location -> {
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
                Toast.makeText(getParentActivity(), "Unable to get location. Pull down to retry.", Toast.LENGTH_LONG).show();
                isLoading = false;
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void showEmptyState() {
        Toast.makeText(getParentActivity(), "No posts nearby. Be the first!", Toast.LENGTH_LONG).show();
    }

    private void onPostClick(Post post) {
        PostDetailFragment fragment = PostDetailFragment.newInstance(post);
        presentFragment(fragment);
    }

    private void onLikeClick(Post post, int position) {
        // Store original state for rollback
        boolean wasLiked = post.isLiked();
        long originalCount = post.getLikeCount();

        // Toggle like optimistically
        post.setLiked(!wasLiked);
        post.setLikeCount(originalCount + (post.isLiked() ? 1 : -1));
        adapter.notifyItemChanged(position);

        // Sync with backend
        if (post.isLiked()) {
            CommunityBridge.likePost(post.getId(), deviceId, success -> {
                if (!success) {
                    // Rollback on failure
                    post.setLiked(wasLiked);
                    post.setLikeCount(originalCount);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(getParentActivity(), "Failed to like post. Tap to retry.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            CommunityBridge.unlikePost(post.getId(), deviceId, success -> {
                if (!success) {
                    // Rollback on failure
                    post.setLiked(wasLiked);
                    post.setLikeCount(originalCount);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(getParentActivity(), "Failed to unlike post. Tap to retry.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showCreatePostSheet() {
        if (lastLocation == null) {
            Toast.makeText(getParentActivity(), "Getting your location...", Toast.LENGTH_LONG).show();
            return;
        }

        CreatePostBottomSheet sheet = new CreatePostBottomSheet(
            getParentActivity(),
            lastLocation,
            deviceId,
            post -> {
                // Add new post to top of list
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
