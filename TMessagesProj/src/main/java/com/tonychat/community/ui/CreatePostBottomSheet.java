package com.tonychat.community.ui;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tonychat.community.CommunityBridge;
import com.tonychat.community.model.CreatePostRequest;
import com.tonychat.community.model.LocationPoint;
import com.tonychat.community.model.Post;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import java.util.Arrays;

/**
 * Bottom sheet for creating a new community post.
 * Includes text input, image picker, anonymous toggle, and character counter.
 */
public class CreatePostBottomSheet extends BottomSheet {
    private static final int MAX_CHARS = 500;

    private final Location location;
    private final String deviceId;
    private final PostCreatedCallback callback;

    private EditText contentInput;
    private TextView charCounter;
    private Switch anonymousSwitch;
    private Button postButton;

    public interface PostCreatedCallback {
        void onPostCreated(Post post);
    }

    public CreatePostBottomSheet(Activity activity, Location location, String deviceId, PostCreatedCallback callback) {
        super(activity, false);
        this.location = location;
        this.deviceId = deviceId;
        this.callback = callback;

        setApplyBottomPadding(false);
        setApplyTopPadding(false);

        LinearLayout containerView = new LinearLayout(activity);
        containerView.setOrientation(LinearLayout.VERTICAL);
        containerView.setPadding(
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(16),
            AndroidUtilities.dp(16)
        );
        containerView.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));

        // Title
        TextView title = new TextView(activity);
        title.setText("Create Post");
        title.setTextSize(18);
        title.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        title.setGravity(Gravity.CENTER);
        containerView.addView(title, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 16
        ));

        // Content input
        FrameLayout inputContainer = new FrameLayout(activity);
        inputContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        containerView.addView(inputContainer, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, AndroidUtilities.dp(120), 0, 0, 0, 8
        ));

        contentInput = new EditText(activity);
        contentInput.setHint("What's happening nearby?");
        contentInput.setTextSize(15);
        contentInput.setGravity(Gravity.TOP | Gravity.START);
        contentInput.setPadding(
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(12),
            AndroidUtilities.dp(12)
        );
        contentInput.setBackgroundColor(0);
        inputContainer.addView(contentInput, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT
        ));

        // Character counter
        charCounter = new TextView(activity);
        charCounter.setText("0/" + MAX_CHARS);
        charCounter.setTextSize(12);
        charCounter.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        containerView.addView(charCounter, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 4, 0, 12
        ));

        contentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                charCounter.setText(length + "/" + MAX_CHARS);

                if (length > MAX_CHARS) {
                    charCounter.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                } else {
                    charCounter.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
                }

                postButton.setEnabled(length > 0 && length <= MAX_CHARS);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Anonymous toggle
        LinearLayout anonymousRow = new LinearLayout(activity);
        anonymousRow.setOrientation(LinearLayout.HORIZONTAL);
        anonymousRow.setGravity(Gravity.CENTER_VERTICAL);
        containerView.addView(anonymousRow, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 16
        ));

        TextView anonymousLabel = new TextView(activity);
        anonymousLabel.setText("Post anonymously");
        anonymousLabel.setTextSize(15);
        anonymousLabel.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        anonymousRow.addView(anonymousLabel, LayoutHelper.createLinear(
            0, LayoutHelper.WRAP_CONTENT, 1.0f
        ));

        anonymousSwitch = new Switch(activity);
        anonymousSwitch.setChecked(false);
        anonymousRow.addView(anonymousSwitch, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT
        ));

        // Buttons row
        LinearLayout buttonsRow = new LinearLayout(activity);
        buttonsRow.setOrientation(LinearLayout.HORIZONTAL);
        buttonsRow.setGravity(Gravity.CENTER);
        containerView.addView(buttonsRow, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT
        ));

        // Cancel button
        Button cancelButton = new Button(activity);
        cancelButton.setText("Cancel");
        cancelButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        cancelButton.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        cancelButton.setOnClickListener(v -> dismiss());
        buttonsRow.addView(cancelButton, LayoutHelper.createLinear(
            0, AndroidUtilities.dp(44), 1.0f, 0, 0, 8, 0
        ));

        // Post button
        postButton = new Button(activity);
        postButton.setText("Post");
        postButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        postButton.setBackgroundColor(Theme.getColor(Theme.key_chats_actionBackground));
        postButton.setEnabled(false);
        postButton.setOnClickListener(v -> createPost());
        buttonsRow.addView(postButton, LayoutHelper.createLinear(
            0, AndroidUtilities.dp(44), 1.0f
        ));

        setCustomView(containerView);
    }

    private void createPost() {
        String content = contentInput.getText().toString().trim();
        if (content.isEmpty() || content.length() > MAX_CHARS) {
            return;
        }

        postButton.setEnabled(false);
        postButton.setText("Posting...");

        LocationPoint locationPoint = new LocationPoint(
            "Point",
            Arrays.asList(location.getLongitude(), location.getLatitude())
        );

        CreatePostRequest request = new CreatePostRequest(
            deviceId,
            content,
            null, // No image for now
            locationPoint,
            anonymousSwitch.isChecked()
        );

        CommunityBridge.createPost(request, newPost -> {
            if (newPost != null) {
                if (callback != null) {
                    callback.onPostCreated(newPost);
                }
                dismiss();
            } else {
                Toast.makeText(getContext(), "Failed to create post", Toast.LENGTH_SHORT).show();
                postButton.setEnabled(true);
                postButton.setText("Post");
            }
        });
    }
}
