package org.telegram.ui.TonyChat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.R;
import org.telegram.ui.LaunchActivity;

/**
 * First-time onboarding — 4 swipeable slides on yellow #F9E000 background.
 * Shows once, stored via SharedPreferences "onboarding_seen" flag.
 */
public class OnboardingActivity extends Activity {

    private static final String PREFS_NAME = "tonychat";
    private static final String KEY_ONBOARDING_SEEN = "onboarding_seen";

    private ViewPager2 viewPager;
    private View[] dots;
    private TextView actionButton;

    private static final int[] ICONS = {
            R.drawable.msg_edit,          // Slide 1: Writing
            R.drawable.msg_photos,        // Slide 2: Image Tools
            R.drawable.msg_log,            // Slide 3: News Feed
            R.drawable.msg_discussion,    // Slide 4: Secure Chat
    };

    private static final String[] TITLES = {
            "Write better, instantly",
            "Edit photos with AI",
            "Stay informed, faster",
            "Chat securely",
    };

    private static final String[] DESCRIPTIONS = {
            "Fix grammar, adjust tone, translate\nany language — all powered by AI.",
            "Remove backgrounds, upscale, remove\ntext, generate images — one tap.",
            "AI-summarized news from top sources.\nTech, Business, World & more.",
            "End-to-end encrypted messaging\npowered by Telegram protocol.",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(0xFFF9E000);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        int YELLOW = 0xFFF9E000;
        int DARK = 0xFF111111;

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(YELLOW);

        // ViewPager2
        viewPager = new ViewPager2(this);
        viewPager.setAdapter(new SlideAdapter());
        root.addView(viewPager, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Bottom controls container
        LinearLayout bottomBar = new LinearLayout(this);
        bottomBar.setOrientation(LinearLayout.HORIZONTAL);
        bottomBar.setGravity(Gravity.CENTER_VERTICAL);
        int pad = AndroidUtilities.dp(24);
        bottomBar.setPadding(pad, 0, pad, 0);

        // Dot indicators
        LinearLayout dotsContainer = new LinearLayout(this);
        dotsContainer.setOrientation(LinearLayout.HORIZONTAL);
        dotsContainer.setGravity(Gravity.CENTER_VERTICAL);
        dots = new View[4];
        for (int i = 0; i < 4; i++) {
            View dot = new View(this);
            boolean active = (i == 0);
            int size = active ? AndroidUtilities.dp(10) : AndroidUtilities.dp(8);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
            lp.setMargins(AndroidUtilities.dp(4), 0, AndroidUtilities.dp(4), 0);
            dot.setLayoutParams(lp);
            dot.setBackgroundResource(R.drawable.msg_mini_autodelete);
            dot.setAlpha(active ? 1f : 0.3f);
            // Use a simple circle via GradientDrawable
            android.graphics.drawable.GradientDrawable circle = new android.graphics.drawable.GradientDrawable();
            circle.setShape(android.graphics.drawable.GradientDrawable.OVAL);
            circle.setColor(active ? DARK : 0x55111111);
            circle.setSize(size, size);
            dot.setBackground(circle);
            dotsContainer.addView(dot);
            dots[i] = dot;
        }

        // Spacer
        View spacer = new View(this);
        LinearLayout.LayoutParams spacerLp = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        // Skip / Get Started button
        actionButton = new TextView(this);
        actionButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        actionButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        actionButton.setTextColor(DARK);
        actionButton.setText("Skip");
        actionButton.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(12),
                AndroidUtilities.dp(24), AndroidUtilities.dp(12));
        actionButton.setOnClickListener(v -> completeOnboarding());

        bottomBar.addView(dotsContainer);
        bottomBar.addView(spacer, spacerLp);
        bottomBar.addView(actionButton);

        FrameLayout.LayoutParams bottomLp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, AndroidUtilities.dp(60),
                Gravity.BOTTOM);
        bottomLp.bottomMargin = AndroidUtilities.dp(40);
        root.addView(bottomBar, bottomLp);

        // Page change listener
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
                actionButton.setText(position == 3 ? "Get Started" : "Skip");
                if (position == 3) {
                    // Style as a button on last slide
                    android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
                    bg.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
                    bg.setCornerRadius(AndroidUtilities.dp(24));
                    bg.setColor(DARK);
                    actionButton.setBackground(bg);
                    actionButton.setTextColor(YELLOW);
                } else {
                    actionButton.setBackground(null);
                    actionButton.setTextColor(DARK);
                }
            }
        });

        setContentView(root);
    }

    private void updateDots(int current) {
        for (int i = 0; i < dots.length; i++) {
            boolean active = (i == current);
            int size = active ? AndroidUtilities.dp(10) : AndroidUtilities.dp(8);
            ViewGroup.LayoutParams lp = dots[i].getLayoutParams();
            lp.width = size;
            lp.height = size;
            dots[i].setLayoutParams(lp);
            android.graphics.drawable.GradientDrawable circle = new android.graphics.drawable.GradientDrawable();
            circle.setShape(android.graphics.drawable.GradientDrawable.OVAL);
            circle.setColor(active ? 0xFF111111 : 0x55111111);
            circle.setSize(size, size);
            dots[i].setBackground(circle);
        }
    }

    private void completeOnboarding() {
        SharedPreferences.Editor editor = ApplicationLoader.applicationContext
                .getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(KEY_ONBOARDING_SEEN, true).apply();
        startActivity(new Intent(this, LaunchActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() > 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else {
            completeOnboarding();
        }
    }

    // ---- Adapter ----

    private class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideVH> {
        @NonNull
        @Override
        public SlideVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            layout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int pad = AndroidUtilities.dp(40);
            layout.setPadding(pad, 0, pad, AndroidUtilities.dp(100));
            return new SlideVH(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull SlideVH holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class SlideVH extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView title;
            TextView desc;

            SlideVH(View itemView) {
                super(itemView);
                LinearLayout layout = (LinearLayout) itemView;

                icon = new ImageView(layout.getContext());
                icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                icon.setColorFilter(0xFF111111);
                LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(
                        AndroidUtilities.dp(80), AndroidUtilities.dp(80));
                iconLp.bottomMargin = AndroidUtilities.dp(32);
                layout.addView(icon, iconLp);

                title = new TextView(layout.getContext());
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                title.setTextColor(0xFF111111);
                title.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                titleLp.bottomMargin = AndroidUtilities.dp(16);
                layout.addView(title, titleLp);

                desc = new TextView(layout.getContext());
                desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                desc.setTextColor(0xFF333333);
                desc.setGravity(Gravity.CENTER);
                desc.setLineSpacing(AndroidUtilities.dp(4), 1f);
                layout.addView(desc, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            void bind(int position) {
                icon.setImageResource(ICONS[position]);
                title.setText(TITLES[position]);
                desc.setText(DESCRIPTIONS[position]);
            }
        }
    }
}
