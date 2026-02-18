package com.tonychat.community.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tonychat.community.CommunityBridge;
import com.tonychat.community.model.Comment;
import com.tonychat.community.model.CreateCommentRequest;
import com.tonychat.community.model.Post;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Post detail view showing full content, comments, and like functionality.
 */
public class PostDetailFragment extends BaseFragment {
    private final Post post;
    private final String deviceId;

    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private EditText commentInput;
    private ImageView sendButton;
    private ImageView likeButton;
    private TextView likeCountText;

    private List<Comment> comments = new ArrayList<>();

    public PostDetailFragment(Post post, String deviceId) {
        this.post = post;
        this.deviceId = deviceId;
    }

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Post");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        // Main content container
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayout.addView(mainLayout, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT
        ));

        // Post content card
        LinearLayout postCard = createPostCard(context);
        mainLayout.addView(postCard, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        // Comments section
        TextView commentsHeader = new TextView(context);
        commentsHeader.setText("Comments");
        commentsHeader.setTextSize(14);
        commentsHeader.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        commentsHeader.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(12), 0, AndroidUtilities.dp(8));
        mainLayout.addView(commentsHeader, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        // Comments RecyclerView
        commentsRecyclerView = new RecyclerView(context);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        commentsRecyclerView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        mainLayout.addView(commentsRecyclerView, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, 0, 1.0f
        ));

        commentAdapter = new CommentAdapter(comments);
        commentsRecyclerView.setAdapter(commentAdapter);

        // Comment input bar
        LinearLayout inputBar = createCommentInputBar(context);
        frameLayout.addView(inputBar, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, 50, Gravity.BOTTOM
        ));

        loadComments();
        return fragmentView;
    }

    private LinearLayout createPostCard(Context context) {
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        card.setPadding(
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(16)
        );

        // Content text
        TextView contentText = new TextView(context);
        contentText.setText(post.getContent());
        contentText.setTextSize(16);
        contentText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        card.addView(contentText, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 12
        ));

        // Image (if exists)
        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
            card.addView(imageView, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, AndroidUtilities.dp(250), 0, 0, 0, 12
            ));
        }

        // Bottom row (time, like)
        LinearLayout bottomRow = new LinearLayout(context);
        bottomRow.setOrientation(LinearLayout.HORIZONTAL);
        bottomRow.setGravity(Gravity.CENTER_VERTICAL);
        card.addView(bottomRow, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        TextView timeText = new TextView(context);
        timeText.setText(formatTimeAgo(post.getCreatedAt()));
        timeText.setTextSize(13);
        timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        bottomRow.addView(timeText, LayoutHelper.createLinear(
            0, LayoutHelper.WRAP_CONTENT, 1.0f
        ));

        // Like button
        LinearLayout likeLayout = new LinearLayout(context);
        likeLayout.setOrientation(LinearLayout.HORIZONTAL);
        likeLayout.setGravity(Gravity.CENTER_VERTICAL);
        likeLayout.setPadding(AndroidUtilities.dp(8), AndroidUtilities.dp(4), AndroidUtilities.dp(8), AndroidUtilities.dp(4));
        likeLayout.setOnClickListener(v -> toggleLike());
        bottomRow.addView(likeLayout, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT
        ));

        likeButton = new ImageView(context);
        likeButton.setImageResource(R.drawable.msg_input_like);
        updateLikeButton();
        likeLayout.addView(likeButton, LayoutHelper.createLinear(
            20, 20, 0, 0, 4, 0
        ));

        likeCountText = new TextView(context);
        likeCountText.setText(String.valueOf(post.getLikeCount()));
        likeCountText.setTextSize(13);
        likeCountText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        likeLayout.addView(likeCountText);

        return card;
    }

    private LinearLayout createCommentInputBar(Context context) {
        LinearLayout inputBar = new LinearLayout(context);
        inputBar.setOrientation(LinearLayout.HORIZONTAL);
        inputBar.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        inputBar.setPadding(
            AndroidUtilities.dp(8),
            AndroidUtilities.dp(8),
            AndroidUtilities.dp(8),
            AndroidUtilities.dp(8)
        );
        inputBar.setGravity(Gravity.CENTER_VERTICAL);

        commentInput = new EditText(context);
        commentInput.setHint("Add a comment...");
        commentInput.setTextSize(15);
        commentInput.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        commentInput.setPadding(
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(8),
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(8)
        );
        commentInput.setImeOptions(EditorInfo.IME_ACTION_SEND);
        inputBar.addView(commentInput, LayoutHelper.createLinear(
            0, LayoutHelper.WRAP_CONTENT, 1.0f, 0, 0, 8, 0
        ));

        sendButton = new ImageView(context);
        sendButton.setImageResource(R.drawable.msg_send);
        sendButton.setEnabled(false);
        sendButton.setAlpha(0.5f);
        sendButton.setOnClickListener(v -> postComment());
        inputBar.addView(sendButton, LayoutHelper.createLinear(32, 32));

        commentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean hasText = s.length() > 0;
                sendButton.setEnabled(hasText);
                sendButton.setAlpha(hasText ? 1.0f : 0.5f);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return inputBar;
    }

    private void loadComments() {
        CommunityBridge.getComments(post.getId(), loadedComments -> {
            comments.clear();
            comments.addAll(loadedComments);
            commentAdapter.notifyDataSetChanged();
        });
    }

    private void postComment() {
        String content = commentInput.getText().toString().trim();
        if (content.isEmpty()) return;

        CreateCommentRequest request = new CreateCommentRequest(post.getId(), deviceId, content);

        CommunityBridge.createComment(request, newComment -> {
            if (newComment != null) {
                comments.add(newComment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
                commentsRecyclerView.scrollToPosition(comments.size() - 1);
                commentInput.setText("");
                post.setCommentCount(post.getCommentCount() + 1);
            } else {
                Toast.makeText(getParentActivity(), "Failed to post comment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleLike() {
        boolean wasLiked = post.isLiked();
        post.setLiked(!wasLiked);
        post.setLikeCount(post.getLikeCount() + (post.isLiked() ? 1 : -1));
        updateLikeButton();
        likeCountText.setText(String.valueOf(post.getLikeCount()));

        if (post.isLiked()) {
            CommunityBridge.likePost(post.getId(), deviceId, success -> {
                if (!success) {
                    post.setLiked(wasLiked);
                    post.setLikeCount(post.getLikeCount() + (wasLiked ? 1 : -1));
                    updateLikeButton();
                    likeCountText.setText(String.valueOf(post.getLikeCount()));
                    Toast.makeText(getParentActivity(), "Failed to update like", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            CommunityBridge.unlikePost(post.getId(), deviceId, success -> {
                if (!success) {
                    post.setLiked(wasLiked);
                    post.setLikeCount(post.getLikeCount() + (wasLiked ? 1 : -1));
                    updateLikeButton();
                    likeCountText.setText(String.valueOf(post.getLikeCount()));
                    Toast.makeText(getParentActivity(), "Failed to update like", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateLikeButton() {
        likeButton.setColorFilter(post.isLiked()
            ? Theme.getColor(Theme.key_avatar_nameInMessageRed)
            : Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
    }

    private String formatTimeAgo(String createdAt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(createdAt);
            if (date != null) {
                long diff = System.currentTimeMillis() - date.getTime();
                long minutes = diff / (60 * 1000);
                long hours = diff / (60 * 60 * 1000);
                long days = diff / (24 * 60 * 60 * 1000);

                if (minutes < 1) return "just now";
                if (minutes < 60) return minutes + "m ago";
                if (hours < 24) return hours + "h ago";
                if (days < 7) return days + "d ago";
                return new SimpleDateFormat("MMM dd", Locale.US).format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "recently";
    }
}
