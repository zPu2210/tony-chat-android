package com.tonychat.community.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonychat.community.model.Post;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Adapter for displaying posts in RecyclerView.
 * Shows content, image, like/comment counts, and time ago.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> posts;
    private final PostClickListener clickListener;
    private final LikeClickListener likeListener;

    public interface PostClickListener {
        void onPostClick(Post post);
    }

    public interface LikeClickListener {
        void onLikeClick(Post post, int position);
    }

    public PostAdapter(List<Post> posts, PostClickListener clickListener, LikeClickListener likeListener) {
        this.posts = posts;
        this.clickListener = clickListener;
        this.likeListener = likeListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout cardLayout = new FrameLayout(parent.getContext());
        cardLayout.setLayoutParams(new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        cardLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        cardLayout.setPadding(
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(12)
        );

        LinearLayout contentLayout = new LinearLayout(parent.getContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.addView(contentLayout, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        // Content text
        TextView contentText = new TextView(parent.getContext());
        contentText.setId(View.generateViewId());
        contentText.setTextSize(16);
        contentText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        contentLayout.addView(contentText, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8
        ));

        // Image (optional)
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setId(View.generateViewId());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setVisibility(View.GONE);
        contentLayout.addView(imageView, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, AndroidUtilities.dp(200), 0, 0, 0, 8
        ));

        // Bottom info row (time, likes, comments)
        LinearLayout infoLayout = new LinearLayout(parent.getContext());
        infoLayout.setOrientation(LinearLayout.HORIZONTAL);
        infoLayout.setGravity(Gravity.CENTER_VERTICAL);
        contentLayout.addView(infoLayout, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        // Time ago
        TextView timeText = new TextView(parent.getContext());
        timeText.setId(View.generateViewId());
        timeText.setTextSize(13);
        timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        infoLayout.addView(timeText, LayoutHelper.createLinear(
            0, LayoutHelper.WRAP_CONTENT, 1.0f
        ));

        // Like button + count
        LinearLayout likeLayout = new LinearLayout(parent.getContext());
        likeLayout.setOrientation(LinearLayout.HORIZONTAL);
        likeLayout.setGravity(Gravity.CENTER_VERTICAL);
        likeLayout.setPadding(AndroidUtilities.dp(8), 0, AndroidUtilities.dp(8), 0);
        infoLayout.addView(likeLayout, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT
        ));

        ImageView likeIcon = new ImageView(parent.getContext());
        int likeIconId = View.generateViewId();
        likeIcon.setId(likeIconId);
        likeIcon.setImageResource(R.drawable.msg_input_like);
        likeIcon.setColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        likeLayout.addView(likeIcon, LayoutHelper.createLinear(
            20, 20, 0, 0, 4, 0
        ));

        TextView likeCount = new TextView(parent.getContext());
        int likeCountId = View.generateViewId();
        likeCount.setId(likeCountId);
        likeCount.setTextSize(13);
        likeCount.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        likeLayout.addView(likeCount);

        // Comment count
        LinearLayout commentLayout = new LinearLayout(parent.getContext());
        commentLayout.setOrientation(LinearLayout.HORIZONTAL);
        commentLayout.setGravity(Gravity.CENTER_VERTICAL);
        commentLayout.setPadding(AndroidUtilities.dp(8), 0, 0, 0);
        infoLayout.addView(commentLayout, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT
        ));

        ImageView commentIcon = new ImageView(parent.getContext());
        commentIcon.setImageResource(R.drawable.msg_msgbubble3);
        commentIcon.setColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        commentLayout.addView(commentIcon, LayoutHelper.createLinear(
            20, 20, 0, 0, 4, 0
        ));

        TextView commentCount = new TextView(parent.getContext());
        int commentCountId = View.generateViewId();
        commentCount.setId(commentCountId);
        commentCount.setTextSize(13);
        commentCount.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        commentLayout.addView(commentCount);

        return new PostViewHolder(cardLayout, likeLayout, likeIconId, likeCountId, commentCountId);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        LinearLayout contentLayout = (LinearLayout)((FrameLayout)holder.itemView).getChildAt(0);
        TextView contentText = (TextView)contentLayout.getChildAt(0);
        contentText.setText(post.getContent());

        ImageView imageView = (ImageView)contentLayout.getChildAt(1);
        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            // Load image using URL (simplified - production should use image loader)
            // For now, just show placeholder
            imageView.setBackgroundColor(Theme.getColor(Theme.key_chat_inBubble));
        } else {
            imageView.setVisibility(View.GONE);
        }

        LinearLayout infoLayout = (LinearLayout)contentLayout.getChildAt(2);
        TextView timeText = (TextView)infoLayout.getChildAt(0);
        timeText.setText(formatTimeAgo(post.getCreatedAt()));

        ImageView likeIcon = holder.itemView.findViewById(holder.likeIconId);
        TextView likeCount = holder.itemView.findViewById(holder.likeCountId);
        likeIcon.setColorFilter(post.isLiked()
            ? Theme.getColor(Theme.key_avatar_nameInMessageRed)
            : Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        likeCount.setText(String.valueOf(post.getLikeCount()));

        TextView commentCount = holder.itemView.findViewById(holder.commentCountId);
        commentCount.setText(String.valueOf(post.getCommentCount()));

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onPostClick(post);
            }
        });

        holder.likeLayout.setOnClickListener(v -> {
            if (likeListener != null) {
                likeListener.onLikeClick(post, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    private String formatTimeAgo(String createdAt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(createdAt);
            if (date != null) {
                long timeMs = date.getTime();
                CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(
                    timeMs,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                );
                return relativeTime.toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "recently";
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        LinearLayout likeLayout;
        int likeIconId;
        int likeCountId;
        int commentCountId;

        PostViewHolder(View itemView, LinearLayout likeLayout, int likeIconId, int likeCountId, int commentCountId) {
            super(itemView);
            this.likeLayout = likeLayout;
            this.likeIconId = likeIconId;
            this.likeCountId = likeCountId;
            this.commentCountId = commentCountId;
        }
    }
}
