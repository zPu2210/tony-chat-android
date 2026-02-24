package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;

/**
 * "Connect to Telegram" CTA shown in Chats tab when user is not logged in.
 * Displays an illustration, title, description, and a yellow "Connect" button.
 */
public class TelegramConnectView extends FrameLayout {

    private OnConnectClickListener listener;

    public interface OnConnectClickListener {
        void onConnectClicked();
    }

    public TelegramConnectView(Context context) {
        super(context);
        setBackgroundColor(0xFFFFFFFF);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        int pad = AndroidUtilities.dp(40);
        container.setPadding(pad, 0, pad, 0);

        // Chat icon
        ImageView icon = new ImageView(context);
        icon.setImageResource(R.drawable.msg_discussion);
        icon.setColorFilter(0xFFF9E000);
        icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(
                AndroidUtilities.dp(80), AndroidUtilities.dp(80));
        iconLp.bottomMargin = AndroidUtilities.dp(24);
        container.addView(icon, iconLp);

        // Title
        TextView title = new TextView(context);
        title.setText("Connect to Telegram");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setTextColor(0xFF111111);
        title.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleLp.bottomMargin = AndroidUtilities.dp(8);
        container.addView(title, titleLp);

        // Description
        TextView desc = new TextView(context);
        desc.setText("Sign in with your phone number to start\nchatting with friends and family.");
        desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        desc.setTextColor(0xFF666666);
        desc.setGravity(Gravity.CENTER);
        desc.setLineSpacing(AndroidUtilities.dp(3), 1f);
        LinearLayout.LayoutParams descLp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        descLp.bottomMargin = AndroidUtilities.dp(32);
        container.addView(desc, descLp);

        // Connect button
        TextView button = new TextView(context);
        button.setText("Connect");
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        button.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        button.setTextColor(0xFF111111);
        button.setGravity(Gravity.CENTER);
        button.setPadding(AndroidUtilities.dp(48), AndroidUtilities.dp(14),
                AndroidUtilities.dp(48), AndroidUtilities.dp(14));

        GradientDrawable buttonBg = new GradientDrawable();
        buttonBg.setShape(GradientDrawable.RECTANGLE);
        buttonBg.setCornerRadius(AndroidUtilities.dp(24));
        buttonBg.setColor(0xFFF9E000);
        button.setBackground(buttonBg);
        button.setOnClickListener(v -> {
            if (listener != null) listener.onConnectClicked();
        });
        container.addView(button, new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        addView(container, new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
    }

    public void setOnConnectClickListener(OnConnectClickListener listener) {
        this.listener = listener;
    }
}
