# Tony Chat

AI-powered smart messaging app for Android, built on Telegram's open-source client.

Tony Chat strips Telegram to its core messaging essentials and adds AI-native features on top.

## Features

- **Core Messaging**: Chats, groups, channels, contacts, media sharing
- **Clean Experience**: No stories, bots, games, stars, gifts, or premium upsells
- **AI-Powered** (coming soon): Smart replies, conversation summaries, content generation

## Architecture

```
android/
├── TMessagesProj/          # Main Telegram client (Java)
│   ├── src/main/java/org/telegram/
│   │   ├── messenger/      # Core services, controllers, utilities
│   │   ├── tgnet/          # Network layer (MTProto, TLRPC)
│   │   └── ui/             # Activities, components, cells, adapters
│   └── jni/                # Native C/C++ (video, crypto, SQLite)
├── tonychat-core/          # Tony Chat core module (Kotlin)
│   └── TonyConfig.kt       # Feature flags & configuration
├── tonychat-ui/            # Tony Chat UI module (Kotlin)
└── buildSrc/               # Centralized dependency versions
```

## Build

### Prerequisites

- **JDK**: Temurin 17
- **Android SDK**: Platform 33+
- **NDK**: 21.4.7075529
- macOS, Linux, or WSL2

### Build Release APK

```bash
export ANDROID_HOME=~/Library/Android/sdk
export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home

./gradlew TMessagesProj:assembleRelease
```

### Build Debug APK

```bash
./gradlew TMessagesProj:assembleDebug
```

## API Setup

Register at [my.telegram.org](https://my.telegram.org/auth) to get `api_id` and `api_hash`. Update values in `TMessagesProj/src/main/java/org/telegram/messenger/BuildVars.java`.

## License

[GNU General Public License v3.0](LICENSE)

Based on [Telegram for Android](https://github.com/AlibekDev/TelegramFork) and [Nagram](https://github.com/NextAlone/Nagram).
