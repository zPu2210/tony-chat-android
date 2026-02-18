/**
 * Centralized dependency versions for Tony Chat
 */
object Versions {
    const val compileSdk = 35
    const val minSdk = 21
    const val targetSdk = 35
    const val buildTools = "35.0.0"

    const val kotlin = "1.9.0"
    const val androidxCore = "1.16.0"
}

object Libs {
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.androidxCore}"
}
