package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonychat.community.model.NewsArticle;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RecyclerView adapter for AI News Feed cards.
 * Card layout: thumbnail (80x80) left + source/time/category right + headline + AI summary.
 */
public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.NewsViewHolder> {
    private final List<NewsArticle> articles;
    private final OnArticleClickListener clickListener;
    private static final Map<String, Bitmap> imageCache = new ConcurrentHashMap<>();

    public interface OnArticleClickListener {
        void onArticleClick(NewsArticle article);
    }

    public NewsCardAdapter(List<NewsArticle> articles, OnArticleClickListener listener) {
        this.articles = articles;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();

        // Card root
        LinearLayout card = new LinearLayout(ctx);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        card.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        card.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(12),
                AndroidUtilities.dp(16), AndroidUtilities.dp(12));

        // Top row: thumbnail + meta
        LinearLayout topRow = new LinearLayout(ctx);
        topRow.setOrientation(LinearLayout.HORIZONTAL);

        // Thumbnail placeholder (80x80, 8dp radius)
        FrameLayout thumbFrame = new FrameLayout(ctx);
        GradientDrawable thumbBg = new GradientDrawable();
        thumbBg.setShape(GradientDrawable.RECTANGLE);
        thumbBg.setCornerRadius(AndroidUtilities.dp(8));
        thumbBg.setColor(0xFFF0F0F0);
        thumbFrame.setBackground(thumbBg);

        ImageView thumbnail = new ImageView(ctx);
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setClipToOutline(true);
        thumbFrame.addView(thumbnail, LayoutHelper.createFrame(80, 80));

        topRow.addView(thumbFrame, LayoutHelper.createLinear(80, 80, 0, 0, 12, 0));

        // Right side: meta + headline
        LinearLayout metaCol = new LinearLayout(ctx);
        metaCol.setOrientation(LinearLayout.VERTICAL);
        metaCol.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Source + time row
        LinearLayout sourceRow = new LinearLayout(ctx);
        sourceRow.setOrientation(LinearLayout.HORIZONTAL);
        sourceRow.setGravity(Gravity.CENTER_VERTICAL);

        TextView sourceName = new TextView(ctx);
        sourceName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        sourceName.setTextColor(0xFF999999);
        sourceName.setSingleLine(true);
        sourceName.setEllipsize(TextUtils.TruncateAt.END);
        sourceRow.addView(sourceName, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Category chip
        TextView categoryChip = new TextView(ctx);
        categoryChip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        categoryChip.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        categoryChip.setPadding(AndroidUtilities.dp(8), AndroidUtilities.dp(2),
                AndroidUtilities.dp(8), AndroidUtilities.dp(2));
        sourceRow.addView(categoryChip, LayoutHelper.createLinear(
                LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0));

        metaCol.addView(sourceRow, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4));

        // Headline (bold, 15sp, max 2 lines)
        TextView headline = new TextView(ctx);
        headline.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        headline.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        headline.setTextColor(0xFF111111);
        headline.setMaxLines(2);
        headline.setEllipsize(TextUtils.TruncateAt.END);
        metaCol.addView(headline, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        topRow.addView(metaCol);
        card.addView(topRow, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        // AI Summary (13sp, gray, max 3 lines)
        TextView summary = new TextView(ctx);
        summary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        summary.setTextColor(0xFF666666);
        summary.setMaxLines(3);
        summary.setEllipsize(TextUtils.TruncateAt.END);
        summary.setLineSpacing(AndroidUtilities.dp(2), 1f);
        card.addView(summary, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // Bottom divider
        View divider = new View(ctx);
        divider.setBackgroundColor(0xFFF0F0F0);
        card.addView(divider, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, 1, 0, 12, 0, 0));

        return new NewsViewHolder(card, thumbnail, sourceName, categoryChip, headline, summary);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);

        // Source + relative time
        String timeAgo = getRelativeTime(article.getPublishedAt());
        holder.sourceName.setText(article.getSourceName() + " · " + timeAgo);

        // Category chip
        holder.categoryChip.setText(capitalize(article.getCategory()));
        applyCategoryChipStyle(holder.categoryChip, article.getCategory());

        // Headline
        holder.headline.setText(article.getTitle());

        // Summary
        String summaryText = article.getSummary();
        if (summaryText != null && !summaryText.isEmpty()) {
            holder.summary.setVisibility(View.VISIBLE);
            holder.summary.setText(summaryText);
        } else {
            holder.summary.setVisibility(View.GONE);
        }

        // Thumbnail
        holder.thumbnail.setImageBitmap(null);
        String imageUrl = article.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            loadThumbnail(imageUrl, holder.thumbnail);
        }

        // Click
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onArticleClick(article);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    private void applyCategoryChipStyle(TextView chip, String category) {
        int bgColor;
        int textColor;
        switch (category) {
            case "technology":
                bgColor = 0x1A2196F3; textColor = 0xFF2196F3; break;
            case "business":
                bgColor = 0x1A4CAF50; textColor = 0xFF4CAF50; break;
            case "world":
                bgColor = 0x1AFF9800; textColor = 0xFFFF9800; break;
            case "entertainment":
                bgColor = 0x1AE91E63; textColor = 0xFFE91E63; break;
            case "sports":
                bgColor = 0x1A9C27B0; textColor = 0xFF9C27B0; break;
            default:
                bgColor = 0x1A607D8B; textColor = 0xFF607D8B; break;
        }
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(AndroidUtilities.dp(4));
        bg.setColor(bgColor);
        chip.setBackground(bg);
        chip.setTextColor(textColor);
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return "";
        return s.substring(0, 1).toUpperCase(Locale.US) + s.substring(1);
    }

    private String getRelativeTime(String isoTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            // Strip timezone info for parsing
            String clean = isoTime;
            if (clean.contains("+")) clean = clean.substring(0, clean.indexOf('+'));
            if (clean.endsWith("Z")) clean = clean.substring(0, clean.length() - 1);
            Date date = sdf.parse(clean);
            if (date != null) {
                return DateUtils.getRelativeTimeSpanString(
                        date.getTime(), System.currentTimeMillis(),
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE).toString();
            }
        } catch (Exception ignored) {}
        return "";
    }

    private void loadThumbnail(String url, ImageView target) {
        Bitmap cached = imageCache.get(url);
        if (cached != null) {
            target.setImageBitmap(cached);
            return;
        }
        new Thread(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                conn.disconnect();
                if (bitmap != null) {
                    imageCache.put(url, bitmap);
                    target.post(() -> target.setImageBitmap(bitmap));
                }
            } catch (Exception ignored) {}
        }).start();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        final ImageView thumbnail;
        final TextView sourceName;
        final TextView categoryChip;
        final TextView headline;
        final TextView summary;

        NewsViewHolder(View itemView, ImageView thumbnail, TextView sourceName,
                       TextView categoryChip, TextView headline, TextView summary) {
            super(itemView);
            this.thumbnail = thumbnail;
            this.sourceName = sourceName;
            this.categoryChip = categoryChip;
            this.headline = headline;
            this.summary = summary;
        }
    }
}
