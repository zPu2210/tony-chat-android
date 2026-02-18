package com.tonychat.community.ui;

import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonychat.community.model.Comment;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Simple adapter for displaying comments in a RecyclerView.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout itemLayout = new LinearLayout(parent.getContext());
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setLayoutParams(new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        itemLayout.setPadding(
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(12)
        );

        // Comment content
        TextView contentText = new TextView(parent.getContext());
        contentText.setTextSize(15);
        contentText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        itemLayout.addView(contentText, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4
        ));

        // Time ago
        TextView timeText = new TextView(parent.getContext());
        timeText.setTextSize(12);
        timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        itemLayout.addView(timeText, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        return new CommentViewHolder(itemLayout, contentText, timeText);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.contentText.setText(comment.getContent());
        holder.timeText.setText(formatTimeAgo(comment.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
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

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView contentText;
        TextView timeText;

        CommentViewHolder(View itemView, TextView contentText, TextView timeText) {
            super(itemView);
            this.contentText = contentText;
            this.timeText = timeText;
        }
    }
}
