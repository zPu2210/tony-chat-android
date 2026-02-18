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
        assertTrue(TonyConfig.sendOnlinePackets)
        assertTrue(TonyConfig.sendReadMessagePackets)
        assertTrue(TonyConfig.sendReadStoryPackets)
        assertTrue(TonyConfig.sendUploadProgress)
        assertFalse(TonyConfig.suppressTypingIndicator)
        assertFalse(TonyConfig.isGhostModeActive)
    }

    // ── setGhostMode ──

    @Test
    fun `setGhostMode true enables all suppressions`() {
        TonyConfig.setGhostMode(true)

        assertTrue(TonyConfig.isGhostModeActive)
        assertFalse(TonyConfig.sendOnlinePackets)
        assertFalse(TonyConfig.sendReadMessagePackets)
        assertFalse(TonyConfig.sendReadStoryPackets)
        assertFalse(TonyConfig.sendUploadProgress)
        assertTrue(TonyConfig.suppressTypingIndicator)
    }

    @Test
    fun `setGhostMode false restores defaults`() {
        TonyConfig.setGhostMode(true)
        TonyConfig.setGhostMode(false)

        assertFalse(TonyConfig.isGhostModeActive)
        assertTrue(TonyConfig.sendOnlinePackets)
        assertTrue(TonyConfig.sendReadMessagePackets)
        assertTrue(TonyConfig.sendReadStoryPackets)
        assertTrue(TonyConfig.sendUploadProgress)
        assertFalse(TonyConfig.suppressTypingIndicator)
    }

    @Test
    fun `setGhostMode toggle roundtrip`() {
        TonyConfig.setGhostMode(true)
        assertTrue(TonyConfig.isGhostModeActive)
        TonyConfig.setGhostMode(false)
        assertFalse(TonyConfig.isGhostModeActive)
        TonyConfig.setGhostMode(true)
        assertTrue(TonyConfig.isGhostModeActive)
    }

    // ── Property read/write roundtrips ──

    @Test
    fun `boolean property roundtrip`() {
        TonyConfig.darkMode = true
        assertTrue(TonyConfig.darkMode)
        TonyConfig.darkMode = false
        assertFalse(TonyConfig.darkMode)
    }

    @Test
    fun `int property roundtrip`() {
        TonyConfig.actionBarDecoration = 42
        assertEquals(42, TonyConfig.actionBarDecoration)
    }

    @Test
    fun `float property roundtrip`() {
        TonyConfig.stickerSize = 20.5f
        assertEquals(20.5f, TonyConfig.stickerSize, 0.001f)
    }

    @Test
    fun `string property roundtrip`() {
        TonyConfig.translateToLang = "ko"
        assertEquals("ko", TonyConfig.translateToLang)
    }

    @Test
    fun `long property roundtrip`() {
        TonyConfig.openPGPKeyId = 123456789L
        assertEquals(123456789L, TonyConfig.openPGPKeyId)
    }

    // ── Theme defaults ──

    @Test
    fun `dark mode defaults to false`() {
        assertFalse(TonyConfig.darkMode)
    }

    @Test
    fun `useTonyTheme defaults to true`() {
        assertTrue(TonyConfig.useTonyTheme)
    }

    // ── resetToDefaults ──

    @Test
    fun `resetToDefaults clears all custom values`() {
        TonyConfig.darkMode = true
        TonyConfig.showStories = true
        TonyConfig.setGhostMode(true)
        TonyConfig.actionBarDecoration = 99
        TonyConfig.stickerSize = 50f
        TonyConfig.translateToLang = "ja"

        TonyConfig.resetToDefaults()

        assertFalse(TonyConfig.darkMode)
        assertFalse(TonyConfig.showStories)
        assertFalse(TonyConfig.isGhostModeActive)
        assertTrue(TonyConfig.sendOnlinePackets)
        assertEquals(0, TonyConfig.actionBarDecoration)
        assertEquals(14.0f, TonyConfig.stickerSize, 0.001f)
        assertEquals("en", TonyConfig.translateToLang)
    }

    // ── Individual ghost sub-flags ──

    @Test
    fun `individual ghost flags can be set independently`() {
        TonyConfig.sendOnlinePackets = false
        assertFalse(TonyConfig.sendOnlinePackets)
        assertTrue(TonyConfig.sendReadMessagePackets) // unaffected

        TonyConfig.suppressTypingIndicator = true
        assertTrue(TonyConfig.suppressTypingIndicator)
        assertTrue(TonyConfig.sendUploadProgress) // unaffected
    }
}
