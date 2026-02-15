package com.tonychat.ai.consent

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tonychat.ai.AiFeatureType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class AiConsentManagerTest {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        AiConsentManager.init(context)
        AiConsentManager.revokeAll()
    }

    @Test
    fun `no consent by default`() {
        AiFeatureType.entries.forEach {
            assertFalse("${it.name} should not have consent", AiConsentManager.hasConsent(it))
        }
    }

    @Test
    fun `hasAnyConsent false when none granted`() {
        assertFalse(AiConsentManager.hasAnyConsent())
    }

    @Test
    fun `grantConsent enables specific feature`() {
        AiConsentManager.grantConsent(AiFeatureType.SMART_REPLY)
        assertTrue(AiConsentManager.hasConsent(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `hasAnyConsent true after granting one`() {
        AiConsentManager.grantConsent(AiFeatureType.TRANSLATE)
        assertTrue(AiConsentManager.hasAnyConsent())
    }

    @Test
    fun `revokeConsent disables specific feature`() {
        AiConsentManager.grantConsent(AiFeatureType.SUMMARY)
        AiConsentManager.revokeConsent(AiFeatureType.SUMMARY)
        assertFalse(AiConsentManager.hasConsent(AiFeatureType.SUMMARY))
    }

    @Test
    fun `revokeAll clears all consents`() {
        AiFeatureType.entries.forEach { AiConsentManager.grantConsent(it) }
        AiConsentManager.revokeAll()
        assertFalse(AiConsentManager.hasAnyConsent())
    }

    @Test
    fun `granting one feature does not affect others`() {
        AiConsentManager.grantConsent(AiFeatureType.SMART_REPLY)
        assertFalse(AiConsentManager.hasConsent(AiFeatureType.SUMMARY))
        assertFalse(AiConsentManager.hasConsent(AiFeatureType.TRANSLATE))
        assertFalse(AiConsentManager.hasConsent(AiFeatureType.TONE_REWRITE))
    }

    @Test
    fun `grant revoke grant roundtrip`() {
        AiConsentManager.grantConsent(AiFeatureType.SUMMARY)
        assertTrue(AiConsentManager.hasConsent(AiFeatureType.SUMMARY))
        AiConsentManager.revokeConsent(AiFeatureType.SUMMARY)
        assertFalse(AiConsentManager.hasConsent(AiFeatureType.SUMMARY))
        AiConsentManager.grantConsent(AiFeatureType.SUMMARY)
        assertTrue(AiConsentManager.hasConsent(AiFeatureType.SUMMARY))
    }

    @Test
    fun `multiple features can be granted independently`() {
        AiConsentManager.grantConsent(AiFeatureType.SMART_REPLY)
        AiConsentManager.grantConsent(AiFeatureType.TRANSLATE)
        assertTrue(AiConsentManager.hasConsent(AiFeatureType.SMART_REPLY))
        assertTrue(AiConsentManager.hasConsent(AiFeatureType.TRANSLATE))
        assertFalse(AiConsentManager.hasConsent(AiFeatureType.SUMMARY))
    }
}
