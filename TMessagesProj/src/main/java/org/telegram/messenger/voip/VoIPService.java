/*
 * This is the source code of Telegram for Android v. 5.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Grishka, 2013-2016.
 * 
 * GUTTED FOR TONY CHAT - VoIP functionality removed
 */

package org.telegram.messenger.voip;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.telecom.CallAudioState;
import android.telecom.Connection;
import android.view.View;

import androidx.annotation.Nullable;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_phone;
import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

@SuppressLint("NewApi")
public class VoIPService extends Service implements AudioManager.OnAudioFocusChangeListener, NotificationCenter.NotificationCenterDelegate, VoIPServiceState {

    // State constants
    public static final int CALL_MIN_LAYER = 65;
    public static final int STATE_HANGING_UP = 10;
    public static final int STATE_EXCHANGING_KEYS = 12;
    public static final int STATE_WAITING = 13;
    public static final int STATE_REQUESTING = 14;
    public static final int STATE_WAITING_INCOMING = 15;
    public static final int STATE_RINGING = 16;
    public static final int STATE_BUSY = 17;
    public static final int STATE_WAIT_INIT = Instance.STATE_WAIT_INIT;
    public static final int STATE_WAIT_INIT_ACK = Instance.STATE_WAIT_INIT_ACK;
    public static final int STATE_ESTABLISHED = Instance.STATE_ESTABLISHED;
    public static final int STATE_FAILED = Instance.STATE_FAILED;
    public static final int STATE_RECONNECTING = Instance.STATE_RECONNECTING;
    public static final int STATE_CREATING = 6;
    public static final int STATE_ENDED = 11;
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final int ID_INCOMING_CALL_PRENOTIFICATION = 203;
    public static final int QUALITY_SMALL = 0;
    public static final int QUALITY_MEDIUM = 1;
    public static final int QUALITY_FULL = 2;
    public static final int CAPTURE_DEVICE_CAMERA = 0;
    public static final int CAPTURE_DEVICE_SCREEN = 1;
    public static final int DISCARD_REASON_HANGUP = 1;
    public static final int DISCARD_REASON_DISCONNECT = 2;
    public static final int DISCARD_REASON_MISSED = 3;
    public static final int DISCARD_REASON_LINE_BUSY = 4;
    public static final int DISCARD_REASON_CONVERT = 5;
    public static final int AUDIO_ROUTE_EARPIECE = 0;
    public static final int AUDIO_ROUTE_SPEAKER = 1;
    public static final int AUDIO_ROUTE_BLUETOOTH = 2;
    public static final boolean USE_CONNECTION_SERVICE = false;

    private static VoIPService sharedInstance;
    public static AudioLevelsCallback audioLevelsCallback;
    public static TL_phone.PhoneCall callIShouldHavePutIntoIntent;
    public ChatObject.Call groupCall;

    public static boolean hasRtmpStream() {
        return false;
    }

    public static VoIPServiceState getSharedState() {
        return sharedInstance;
    }

    public static VoIPService getSharedInstance() {
        return sharedInstance;
    }

    public static boolean isAnyKindOfCallActive() {
        return false;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        return "";
    }

    public static String getStringFromFile(String filePath) throws Exception {
        return "";
    }

    public static Bitmap getRoundAvatarBitmap(Object user) {
        return null;
    }

    public static Bitmap getRoundAvatarBitmap(Context context, int account, Object user) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedInstance = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
    }

    // VoIPServiceState implementation
    @Override
    public TLRPC.User getUser() {
        return null;
    }

    @Override
    public boolean isOutgoing() {
        return false;
    }

    @Override
    public int getCallState() {
        return STATE_ENDED;
    }

    @Override
    public TL_phone.PhoneCall getPrivateCall() {
        return null;
    }

    @Override
    public boolean isCallingVideo() {
        return false;
    }

    @Override
    public void acceptIncomingCall() {
    }

    @Override
    public void declineIncomingCall() {
    }

    @Override
    public void stopRinging() {
    }

    @Override
    public boolean isConference() {
        return false;
    }

    @Override
    public TLRPC.GroupCall getGroupCall() {
        return null;
    }

    @Override
    public java.util.ArrayList<TLRPC.GroupCallParticipant> getGroupParticipants() {
        return null;
    }

    public void setNoiseSupressionEnabled(boolean enabled) {
    }

    public void setGroupCallHash(String hash) {
    }

    public long getCallerId() {
        return 0;
    }

    public void hangUp(int discard, Runnable onDone) {
        if (onDone != null) {
            onDone.run();
        }
    }

    public boolean isJoined() {
        return false;
    }

    public void requestVideoCall(boolean screencast) {
    }

    public void switchCamera() {
    }

    public void switchToSpeaker() {
    }

    public void onAudioSettingsChanged() {
    }

    public void editCallMember(Object user, Boolean mute, Integer volume, Boolean raiseHand, Runnable onDone) {
        if (onDone != null) {
            onDone.run();
        }
    }

    public void checkVideoFrame(Object participant, boolean screencast) {
    }

    public int getAccount() {
        return 0;
    }

    public void processMessageUpdate(MessageObject messageObject) {
    }

    public void onGroupCallUpdated(Object groupCall) {
    }

    public void onSignalingData(Object data) {
    }

    public void onGroupCallParticipantsUpdate(Object update) {
    }

    public long getGroupCallID() {
        return 0;
    }

    // Tony Chat: Additional stub methods for VoIP removal
    public boolean isMicMute() {
        return false;
    }

    public void setMicMute(boolean mute, boolean hold, boolean animated) {
    }

    public boolean isHangingUp() {
        return false;
    }

    public TLRPC.Chat getChat() {
        return null;
    }

    public long getSelfId() {
        return 0;
    }

    public void registerStateListener(StateListener listener) {
    }

    public void unregisterStateListener(StateListener listener) {
    }

    public boolean isSwitchingStream() {
        return false;
    }

    public void onCallUpdated(Object call) {
    }

    public boolean hasVideoCapturer() {
        return false;
    }

    public void createCaptureDevice(boolean front) {
    }

    public void migrateToChat(TLRPC.Chat chat) {
    }

    public void setParticipantsVolume() {
    }

    public int getVideoState(boolean presentation) {
        return 0;
    }

    public TLRPC.InputPeer getGroupCallPeer() {
        return null;
    }

    public void playAllowTalkSound() {
    }

    public void stopScreenCapture() {
    }

    public void onCameraFirstFrameAvailable() {
    }

    public void setSwitchingCamera(boolean switching, boolean isFrontFace) {
    }

    public boolean onMediaButtonEvent(Object intent) {
        return false;
    }

    public void handleNotificationAction(Object intent) {
    }

    public void startRingtoneAndVibration() {
    }

    // Tony Chat: Conference stub for VoIP removal
    public static class Conference {
        public java.util.Set<Long> joiningBlockchainParticipants = new java.util.HashSet<>();

        public java.util.List<Long> getShadyLeftParticipants(java.util.ArrayList<TLRPC.GroupCallParticipant> participants) {
            return new java.util.ArrayList<>();
        }

        public java.util.List<Long> getShadyJoiningParticipants(java.util.ArrayList<TLRPC.GroupCallParticipant> participants) {
            return new java.util.ArrayList<>();
        }

        public void applyUpdate(Object a, Object update, boolean b, Object c) {
        }
    }

    public Conference conference;

    // Inner classes
    public interface AudioLevelsCallback {
        void onAudioLevelsAvailable(float[] levels);
    }

    public interface StateListener {
        void onStateChanged(int state);
        void onSignalBarsCountChanged(int count);
        void onAudioSettingsChanged();
        void onMediaStateUpdated(int audioState, int videoState);
        void onCameraSwitch(boolean isFrontFace);
        void onVideoAvailableChange(boolean isAvailable);
        void onScreenOnChange(boolean screenOn);
    }

    public static class RequestedParticipant {
        public long userId;
        public Object chat;

        public RequestedParticipant() {
        }

        // Tony Chat: Constructor for LivePlayer
        public RequestedParticipant(Object participant, int source) {
        }
    }

    public class VoIPServiceConnection extends Connection {
        @Override
        public void onCallAudioStateChanged(CallAudioState state) {
        }

        @Override
        public void onDisconnect() {
        }

        @Override
        public void onAbort() {
        }

        @Override
        public void onAnswer() {
        }

        @Override
        public void onReject() {
        }

        @Override
        public void onShowIncomingCallUi() {
        }

        @Override
        public void onSilence() {
        }
    }

    public static class SharedUIParams {
        public boolean tapToVideoTooltipWasShowed;
        public boolean cameraAlertWasShowed;
        public boolean wasVideoCall;
    }

    public static class ProxyVideoSink implements VideoSink {
        @Override
        public void onFrame(VideoFrame frame) {
        }

        public void setTarget(VideoSink sink) {
        }

        public void removeTarget(Object target) {
        }

        public void removeBackground(Object background) {
        }
    }
}
