# Tony Chat Smoke Test Checklist

## Prerequisites
- Two Telegram accounts (test sender + test receiver)
- Device with Android 8+ (API 26+)
- Internet connection
- OpenAI or Anthropic API key (for cloud AI tests)

## Core Messaging
- [ ] Login with phone number
- [ ] Chat list loads with existing conversations
- [ ] Open 1:1 chat
- [ ] Send text message - delivered
- [ ] Receive text message
- [ ] Send photo from gallery
- [ ] Send voice message
- [ ] Open group chat
- [ ] Open channel, view posts
- [ ] Search chats
- [ ] Create new chat

## AI Features
- [ ] AI Settings accessible from drawer
- [ ] Configure API key (OpenAI or Anthropic)
- [ ] Enable Smart Reply - consent dialog shown
- [ ] Accept consent - smart replies appear after received message
- [ ] Tap chip - text inserted into compose field
- [ ] Enable Summarize - summarize last 50 messages in group
- [ ] Summary bottom sheet shows loading then result
- [ ] Enable Tone Rewrite - overflow menu "Rewrite Tone" works
- [ ] Tone popup shows 5 options, rewritten text replaces compose
- [ ] Enable Translate - long-press message context menu "AI Translate"
- [ ] Translation dialog shows translated text with copy button
- [ ] Clear AI cache in settings

## Privacy / Ghost Mode
- [ ] Privacy Settings accessible from drawer
- [ ] Enable Ghost Mode (master toggle)
- [ ] Ghost icon visible in chat action bar
- [ ] Tap ghost icon toggles with toast feedback
- [ ] From second account: no read receipts received
- [ ] From second account: user appears offline
- [ ] From second account: no typing indicator shown
- [ ] Individual toggles work independently
- [ ] Disable Ghost Mode - all signals resume
- [ ] Settings persist across app restart

## Visual / Branding
- [ ] About screen accessible from drawer
- [ ] About shows version, tagline, links, credits
- [ ] Dark mode toggle works (Settings > Themes)
- [ ] No visible "Telegram" branding in UI (except account/service refs)
- [ ] All Tony Chat screens (AI, Privacy, About) match Telegram UI style

## Stability
- [ ] No crash in 5 minutes of normal use
- [ ] No ANR dialogs
- [ ] Notification received when app backgrounded
- [ ] App survives orientation change
- [ ] App recovers from force stop

## Build Verification
- [ ] Release APK builds without errors
- [ ] APK installs on fresh device without crash
- [ ] APK size noted: ______ MB (target < 80MB)
