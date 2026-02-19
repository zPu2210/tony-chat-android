package com.tonychat.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class TonyConfigTest {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        TonyConfig.init(context)
        TonyConfig.resetToDefaults()
    }

    // ── Feature flag defaults ──

    @Test
    fun `feature flags default to false`() {
        assertFalse(TonyConfig.showStories)
        assertFalse(TonyConfig.showStars)
        assertFalse(TonyConfig.showGifts)
        assertFalse(TonyConfig.showBots)
        assertFalse(TonyConfig.showCalls)
        assertFalse(TonyConfig.showTranslation)
        assertFalse(TonyConfig.showSponsored)
        assertFalse(TonyConfig.showPassport)
        assertFalse(TonyConfig.showBusiness)
    }

    // ── Ghost mode defaults ──

    @Test
    fun `ghost mode flags default correctly`() {
        assertTrue(TonyConfig.privacy.sendOnlinePackets)
        assertTrue(TonyConfig.privacy.sendReadMessagePackets)
        assertTrue(TonyConfig.privacy.sendReadStoryPackets)
        assertTrue(TonyConfig.privacy.sendUploadProgress)
        assertFalse(TonyConfig.privacy.suppressTypingIndicator)
        assertFalse(TonyConfig.privacy.isGhostModeActive)
    }

    // ── setGhostMode ──

    @Test
    fun `setGhostMode true enables all suppressions`() {
        TonyConfig.privacy.setGhostMode(true)

        assertTrue(TonyConfig.privacy.isGhostModeActive)
        assertFalse(TonyConfig.privacy.sendOnlinePackets)
        assertFalse(TonyConfig.privacy.sendReadMessagePackets)
        assertFalse(TonyConfig.privacy.sendReadStoryPackets)
        assertFalse(TonyConfig.privacy.sendUploadProgress)
        assertTrue(TonyConfig.privacy.suppressTypingIndicator)
    }

    @Test
    fun `setGhostMode false restores defaults`() {
        TonyConfig.privacy.setGhostMode(true)
        TonyConfig.privacy.setGhostMode(false)

        assertFalse(TonyConfig.privacy.isGhostModeActive)
        assertTrue(TonyConfig.privacy.sendOnlinePackets)
        assertTrue(TonyConfig.privacy.sendReadMessagePackets)
        assertTrue(TonyConfig.privacy.sendReadStoryPackets)
        assertTrue(TonyConfig.privacy.sendUploadProgress)
        assertFalse(TonyConfig.privacy.suppressTypingIndicator)
    }

    @Test
    fun `setGhostMode toggle roundtrip`() {
        TonyConfig.privacy.setGhostMode(true)
        assertTrue(TonyConfig.privacy.isGhostModeActive)
        TonyConfig.privacy.setGhostMode(false)
        assertFalse(TonyConfig.privacy.isGhostModeActive)
        TonyConfig.privacy.setGhostMode(true)
        assertTrue(TonyConfig.privacy.isGhostModeActive)
    }

    // ── Property read/write roundtrips ──

    @Test
    fun `boolean property roundtrip`() {
        TonyConfig.ui.darkMode = true
        assertTrue(TonyConfig.ui.darkMode)
        TonyConfig.ui.darkMode = false
        assertFalse(TonyConfig.ui.darkMode)
    }

    @Test
    fun `int property roundtrip`() {
        TonyConfig.ui.actionBarDecoration = 42
        assertEquals(42, TonyConfig.ui.actionBarDecoration)
    }

    @Test
    fun `float property roundtrip`() {
        TonyConfig.ui.stickerSize = 20.5f
        assertEquals(20.5f, TonyConfig.ui.stickerSize, 0.001f)
    }

    @Test
    fun `string property roundtrip`() {
        TonyConfig.chat.translateToLang = "ko"
        assertEquals("ko", TonyConfig.chat.translateToLang)
    }

    @Test
    fun `long property roundtrip`() {
        TonyConfig.openPGPKeyId = 123456789L
        assertEquals(123456789L, TonyConfig.openPGPKeyId)
    }

    // ── Theme defaults ──

    @Test
    fun `dark mode defaults to false`() {
        assertFalse(TonyConfig.ui.darkMode)
    }

    @Test
    fun `useTonyTheme defaults to true`() {
        assertTrue(TonyConfig.ui.useTonyTheme)
    }

    // ── resetToDefaults ──

    @Test
    fun `resetToDefaults clears all custom values`() {
        TonyConfig.ui.darkMode = true
        TonyConfig.showStories = true
        TonyConfig.privacy.setGhostMode(true)
        TonyConfig.ui.actionBarDecoration = 99
        TonyConfig.ui.stickerSize = 50f
        TonyConfig.chat.translateToLang = "ja"

        TonyConfig.resetToDefaults()

        assertFalse(TonyConfig.ui.darkMode)
        assertFalse(TonyConfig.showStories)
        assertFalse(TonyConfig.privacy.isGhostModeActive)
        assertTrue(TonyConfig.privacy.sendOnlinePackets)
        assertEquals(0, TonyConfig.ui.actionBarDecoration)
        assertEquals(14.0f, TonyConfig.ui.stickerSize, 0.001f)
        assertEquals("en", TonyConfig.chat.translateToLang)
    }

    // ── Individual ghost sub-flags ──

    @Test
    fun `individual ghost flags can be set independently`() {
        TonyConfig.privacy.sendOnlinePackets = false
        assertFalse(TonyConfig.privacy.sendOnlinePackets)
        assertTrue(TonyConfig.privacy.sendReadMessagePackets) // unaffected

        TonyConfig.privacy.suppressTypingIndicator = true
        assertTrue(TonyConfig.privacy.suppressTypingIndicator)
        assertTrue(TonyConfig.privacy.sendUploadProgress) // unaffected
    }
}
