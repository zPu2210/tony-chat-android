package org.telegram.ui.Stories;

/**
 * LivePlayer stub - Stories removed in Tony Chat
 */
public class LivePlayer {
    public static LivePlayer recording = null;

    public int currentAccount;
    public long dialogId;
    public int storyId;

    public void play() {}
    public void pause() {}
    public void release() {}
    public int getWatchersCount() { return 0; }
}
