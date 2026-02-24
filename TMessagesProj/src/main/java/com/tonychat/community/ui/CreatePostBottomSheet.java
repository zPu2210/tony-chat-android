package com.tonychat.community.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Bottom sheet for creating a new community post.
 * Supports text input, optional photo attachment, anonymous toggle, and character counter.
 */
public class CreatePostBottomSheet extends BottomSheet {
    private static final int MAX_CHARS = 500;
    private static final int PICK_IMAGE_REQUEST = 201;

    private final Location location;
    private final String deviceId;
    private final PostCreatedCallback callback;

    private EditText contentInput;
    private TextView charCounter;
    private Switch anonymousSwitch;
    private Button postButton;
    private ImageView imagePreview;
    private TextView addPhotoButton;
    private Uri selectedImageUri;
    private File imageFile;

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
        contentInput.setHint("What's on your mind?");
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

        // Image preview (hidden initially)
        imagePreview = new ImageView(activity);
        imagePreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imagePreview.setVisibility(android.view.View.GONE);
        containerView.addView(imagePreview, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, AndroidUtilities.dp(180), 0, 0, 0, 8
        ));

        // Add photo button
        addPhotoButton = new TextView(activity);
        addPhotoButton.setText("\uD83D\uDCF7 Add Photo");
        addPhotoButton.setTextSize(14);
        addPhotoButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText2));
        addPhotoButton.setPadding(0, AndroidUtilities.dp(4), 0, AndroidUtilities.dp(4));
        addPhotoButton.setOnClickListener(v -> pickImage());
        containerView.addView(addPhotoButton, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8
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

        Button cancelButton = new Button(activity);
        cancelButton.setText("Cancel");
        cancelButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        cancelButton.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        cancelButton.setOnClickListener(v -> dismiss());
        buttonsRow.addView(cancelButton, LayoutHelper.createLinear(
            0, AndroidUtilities.dp(44), 1.0f, 0, 0, 8, 0
        ));

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

    private void pickImage() {
        Activity activity = (Activity) getContext();
        if (activity == null) return;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * Called from CommunityFeedFragment.onActivityResultFragment after user picks an image.
     */
    public void onImagePicked(Uri uri) {
        if (uri == null) return;
        selectedImageUri = uri;
        try {
            Context ctx = getContext();
            if (ctx == null) return;
            InputStream is = ctx.getContentResolver().openInputStream(uri);
            if (is != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                if (bitmap != null) {
                    imagePreview.setImageBitmap(bitmap);
                    imagePreview.setVisibility(android.view.View.VISIBLE);
                    addPhotoButton.setText("\uD83D\uDCF7 Change Photo");

                    // Save to temp file for upload
                    imageFile = new File(ctx.getCacheDir(), "community_upload_" + System.currentTimeMillis() + ".jpg");
                    FileOutputStream fos = new FileOutputStream(imageFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos);
                    fos.close();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
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

        if (imageFile != null && imageFile.exists()) {
            // Upload image first, then create post
            CommunityBridge.uploadImage(imageFile, imageUrl -> submitPost(content, imageUrl, locationPoint));
        } else {
            submitPost(content, null, locationPoint);
        }
    }

    private void submitPost(String content, String imageUrl, LocationPoint locationPoint) {
        CreatePostRequest request = new CreatePostRequest(
            deviceId,
            content,
            imageUrl,
            locationPoint,
            anonymousSwitch.isChecked()
        );

        CommunityBridge.createPost(request, newPost -> {
            if (newPost != null) {
                if (imageFile != null) imageFile.delete();
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
