package com.tonychat.community.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonychat.community.model.Post;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Instagram/Threads-style post card adapter.
 * Shows avatar, author, content, image, like/comment actions.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private final List<Post> posts;
    private final PostClickListener clickListener;
    private final LikeClickListener likeListener;
    private static final Map<String, Bitmap> imageCache = new ConcurrentHashMap<>();

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
        Context ctx = parent.getContext();

        // Root card container
        LinearLayout card = new LinearLayout(ctx);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setLayoutParams(new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        card.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        // === Header row: avatar + name + time ===
        LinearLayout header = new LinearLayout(ctx);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(12), AndroidUtilities.dp(16), AndroidUtilities.dp(8));
        card.addView(header, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // Avatar circle (custom draw)
        View avatar = new View(ctx) {
            private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            {
                bgPaint.setColor(0xFFE0E0E0);
                textPaint.setColor(0xFF757575);
                textPaint.setTextSize(AndroidUtilities.dp(14));
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            }
            @Override
            protected void onDraw(Canvas canvas) {
                float cx = getWidth() / 2f;
                float cy = getHeight() / 2f;
                float r = Math.min(cx, cy);
                canvas.drawCircle(cx, cy, r, bgPaint);
                canvas.drawText("T", cx, cy + AndroidUtilities.dp(5), textPaint);
            }
        };
        header.addView(avatar, LayoutHelper.createLinear(36, 36, 0, 0, 10, 0));

        // Name + time column
        LinearLayout nameTimeCol = new LinearLayout(ctx);
        nameTimeCol.setOrientation(LinearLayout.VERTICAL);
        header.addView(nameTimeCol, LayoutHelper.createLinear(0, LayoutHelper.WRAP_CONTENT, 1.0f));

        TextView authorName = new TextView(ctx);
        authorName.setTextSize(14);
        authorName.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        authorName.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        nameTimeCol.addView(authorName, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));

        TextView timeText = new TextView(ctx);
        timeText.setTextSize(12);
        timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        nameTimeCol.addView(timeText, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));

        // === Content text ===
        TextView contentText = new TextView(ctx);
        contentText.setTextSize(15);
        contentText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        contentText.setLineSpacing(AndroidUtilities.dp(3), 1.0f);
        contentText.setPadding(AndroidUtilities.dp(16), 0, AndroidUtilities.dp(16), AndroidUtilities.dp(8));
        card.addView(contentText, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // === Image (optional) ===
        ImageView imageView = new ImageView(ctx);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setVisibility(View.GONE);
        card.addView(imageView, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 220, 16, 0, 16, 8));

        // === Action row: like + comment ===
        LinearLayout actions = new LinearLayout(ctx);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setGravity(Gravity.CENTER_VERTICAL);
        actions.setPadding(AndroidUtilities.dp(12), AndroidUtilities.dp(4), AndroidUtilities.dp(12), AndroidUtilities.dp(12));
        card.addView(actions, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // Like button area
        LinearLayout likeArea = new LinearLayout(ctx);
        likeArea.setOrientation(LinearLayout.HORIZONTAL);
        likeArea.setGravity(Gravity.CENTER_VERTICAL);
        likeArea.setPadding(AndroidUtilities.dp(4), AndroidUtilities.dp(4), AndroidUtilities.dp(12), AndroidUtilities.dp(4));
        actions.addView(likeArea, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));

        ImageView likeIcon = new ImageView(ctx);
        likeIcon.setImageResource(R.drawable.msg_input_like);
        likeArea.addView(likeIcon, LayoutHelper.createLinear(22, 22, 0, 0, 6, 0));

        TextView likeCount = new TextView(ctx);
        likeCount.setTextSize(14);
        likeCount.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        likeArea.addView(likeCount);

        // Comment button area
        LinearLayout commentArea = new LinearLayout(ctx);
        commentArea.setOrientation(LinearLayout.HORIZONTAL);
        commentArea.setGravity(Gravity.CENTER_VERTICAL);
        commentArea.setPadding(AndroidUtilities.dp(4), AndroidUtilities.dp(4), AndroidUtilities.dp(12), AndroidUtilities.dp(4));
        actions.addView(commentArea, LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));

        ImageView commentIcon = new ImageView(ctx);
        commentIcon.setImageResource(R.drawable.msg_msgbubble3);
        commentIcon.setColorFilter(new android.graphics.PorterDuffColorFilter(
            Theme.getColor(Theme.key_windowBackgroundWhiteGrayText), android.graphics.PorterDuff.Mode.SRC_IN));
        commentArea.addView(commentIcon, LayoutHelper.createLinear(22, 22, 0, 0, 6, 0));

        TextView commentCount = new TextView(ctx);
        commentCount.setTextSize(14);
        commentCount.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        commentArea.addView(commentCount);

        // === Divider ===
        View divider = new View(ctx);
        divider.setBackgroundColor(Theme.getColor(Theme.key_divider));
        card.addView(divider, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 1));

        return new PostViewHolder(card, likeArea);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        LinearLayout card = (LinearLayout) holder.itemView;

        // Header
        LinearLayout header = (LinearLayout) card.getChildAt(0);
        LinearLayout nameTimeCol = (LinearLayout) header.getChildAt(1);
        TextView authorName = (TextView) nameTimeCol.getChildAt(0);
        TextView timeText = (TextView) nameTimeCol.getChildAt(1);

        String deviceId = post.getDeviceId();
        if (post.getAnonymous()) {
            authorName.setText("Anonymous");
        } else if (deviceId != null && deviceId.startsWith("demo-")) {
            authorName.setText("Tony Chat");
        } else {
            authorName.setText("User " + (deviceId != null && deviceId.length() > 6 ? deviceId.substring(0, 6) : "???"));
        }
        timeText.setText(formatTimeAgo(post.getCreatedAt()));

        // Content
        TextView contentText = (TextView) card.getChildAt(1);
        contentText.setText(post.getContent());

        // Image
        ImageView imageView = (ImageView) card.getChildAt(2);
        String imageUrl = post.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
            loadImage(imageView, imageUrl);
        } else {
            imageView.setVisibility(View.GONE);
            imageView.setImageBitmap(null);
        }

        // Actions
        LinearLayout actions = (LinearLayout) card.getChildAt(3);
        LinearLayout likeArea = (LinearLayout) actions.getChildAt(0);
        ImageView likeIcon = (ImageView) likeArea.getChildAt(0);
        TextView likeCountView = (TextView) likeArea.getChildAt(1);

        boolean isLiked = post.isLiked();
        likeIcon.setColorFilter(new android.graphics.PorterDuffColorFilter(
            isLiked ? 0xFFFF3B30 : Theme.getColor(Theme.key_windowBackgroundWhiteGrayText),
            android.graphics.PorterDuff.Mode.SRC_IN));
        likeCountView.setText(post.getLikeCount() > 0 ? String.valueOf(post.getLikeCount()) : "");
        likeCountView.setTextColor(isLiked ? 0xFFFF3B30 : Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));

        LinearLayout commentArea = (LinearLayout) actions.getChildAt(1);
        TextView commentCountView = (TextView) commentArea.getChildAt(1);
        commentCountView.setText(post.getCommentCount() > 0 ? String.valueOf(post.getCommentCount()) : "");

        // Click handlers
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onPostClick(post);
        });
        holder.likeArea.setOnClickListener(v -> {
            if (likeListener != null) likeListener.onLikeClick(post, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * Async image loader with in-memory bitmap cache. Uses tag to prevent recycling issues.
     * Uses HttpURLConnection (no external deps required in TMessagesProj).
     */
    private void loadImage(ImageView imageView, String url) {
        Bitmap cached = imageCache.get(url);
        if (cached != null) {
            imageView.setImageBitmap(cached);
            return;
        }

        imageView.setTag(url);
        imageView.setImageBitmap(null);

        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);
                conn.setDoInput(true);
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    if (bitmap != null) {
                        imageCache.put(url, bitmap);
                        AndroidUtilities.runOnUIThread(() -> {
                            if (url.equals(imageView.getTag())) {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                // Silently fail — image just won't show
            } finally {
                if (conn != null) conn.disconnect();
            }
        }).start();
    }

    private String formatTimeAgo(String createdAt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(createdAt);
            if (date != null) {
                return DateUtils.getRelativeTimeSpanString(
                    date.getTime(),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                ).toString();
            }
        } catch (ParseException e) {
            // ignore
        }
        return "recently";
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        LinearLayout likeArea;

        PostViewHolder(View itemView, LinearLayout likeArea) {
            super(itemView);
            this.likeArea = likeArea;
        }
    }
}
