package xyz.nextalone.nagram.helper

import android.media.AudioRecord
import android.media.audiofx.AcousticEchoCanceler
import android.media.audiofx.AutomaticGainControl
import android.media.audiofx.NoiseSuppressor

/**
 * Audio enhancement helper stub
 * Feature removed - all methods are no-ops
 */
object AudioEnhance {
    var automaticGainControl: AutomaticGainControl? = null
    var acousticEchoCanceler: AcousticEchoCanceler? = null
    var noiseSuppressor: NoiseSuppressor? = null

    fun initVoiceEnhance(audioRecord: AudioRecord) {
        // No-op: audio enhancement feature removed
    }

    fun releaseVoiceEnhance() {
        // No-op: audio enhancement feature removed
    }

    fun isAvailable(): Boolean = false
}
