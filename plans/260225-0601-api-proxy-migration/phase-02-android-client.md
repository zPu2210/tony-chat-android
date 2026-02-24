# Phase 2: Android Client Migration

**Priority:** High | **Status:** Pending | **Depends on:** Phase 1

## Overview

Update Android client to call Supabase Edge Functions instead of direct API calls. Remove API key input UI. Add device ID for rate limiting.

## Key Insights

- ClipDropProvider.kt already handles multipart + error mapping — swap URLs only
- AiManagerBridge.kt bridge pattern stays same — just different provider internals
- AiConfig.kt clipDropApiKey storage → remove entirely
- AiSettingsActivity.java → remove ClipDrop/Gemini key rows
- Device ID: generate UUID on first launch, persist in SharedPrefs
- Rate limit display: read `X-Remaining` header from Edge Function response

## Related Code Files

**Modify:**
- `android/tonychat-ai/src/main/java/com/tonychat/ai/provider/ClipDropProvider.kt` — swap URLs to Edge Function
- `android/tonychat-ai/src/main/java/com/tonychat/ai/AiManagerBridge.kt` — add device ID header
- `android/tonychat-ai/src/main/java/com/tonychat/ai/AiManager.kt` — remove apiKey param dependency
- `android/tonychat-ai/src/main/java/com/tonychat/ai/config/AiConfig.kt` — remove clipDropApiKey
- `android/TMessagesProj/src/main/java/org/telegram/ui/TonyChat/AiSettingsActivity.java` — remove key input rows
- `android/tonychat-ai/src/main/java/com/tonychat/ai/AiFeatureType.kt` — keep as-is (for UI display)

**Create:**
- `android/tonychat-ai/src/main/java/com/tonychat/ai/DeviceIdentifier.kt` — UUID generation + persistence

## Implementation Steps

### Step 1: Create DeviceIdentifier.kt

```kotlin
// Generate stable device ID: ANDROID_ID + app-install UUID
// Persist UUID in SharedPrefs "tonychat_device"
// Public method: DeviceIdentifier.getId(context): String
```

### Step 2: Update ClipDropProvider.kt

**Changes:**
- Replace `BASE_URL = "https://clipdrop-api.co"` → Supabase Edge Function base URL
- Replace `.header("x-api-key", apiKey)` → `.header("X-Device-Id", deviceId)`
- Keep multipart format (Edge Function receives same format)
- Update error handling: add `X-Remaining` header parsing from response
- Remove `apiKey` parameter from constructor/methods

**URL mapping:**
```
/remove-background/v1     → {SUPABASE_URL}/functions/v1/ai-remove-bg
/image-upscaling/v1/upscale → {SUPABASE_URL}/functions/v1/ai-upscale
/cleanup/v1               → {SUPABASE_URL}/functions/v1/ai-remove-text
/text-to-image/v1         → {SUPABASE_URL}/functions/v1/ai-generate-image
```

### Step 3: Add Text Tool Edge Function Calls

**For AiManagerBridge.kt `standaloneRewrite()`:**
- Currently calls local Gemini/OpenAI provider
- Add new path: call `/ai/rewrite` Edge Function
- Input: `{ text, style }` JSON POST
- Output: parse `{ result, remaining }` JSON

**For AiManagerBridge.kt translate methods:**
- Add new path: call `/ai/translate` Edge Function
- Input: `{ text, targetLang, sourceLang }` JSON POST
- Output: parse `{ result, detectedLang, remaining }` JSON

### Step 4: Update AiConfig.kt

- Remove `clipDropApiKey` property
- Remove `geminiApiKey` property (if used for client-side calls)
- Keep other config (feature flags, preferences)

### Step 5: Update AiSettingsActivity.java

- Remove `clipDropKeyRow` and its click handler
- Remove `geminiKeyRow` if exists
- Remove the `showApiKeyDialog` calls for removed keys
- Keep OpenAI/Anthropic key rows (for future/power users) OR remove all key rows
- Update row count calculation

### Step 6: Add Supabase URL Config

- Add Edge Function base URL to app config (hardcoded or BuildConfig)
- `https://omuajrrvkhzeruupwjot.supabase.co/functions/v1/`
- Add Supabase anon key header for auth: `Authorization: Bearer <anon_key>`

### Step 7: Update Rate Limit Display

- Parse `X-Remaining` header from Edge Function responses
- Update UI to show server-reported remaining count
- Handle 429 response: show "Daily limit reached, try again tomorrow"

## Todo

- [ ] Create DeviceIdentifier.kt (UUID generation)
- [ ] Update ClipDropProvider.kt (swap URLs, remove apiKey, add deviceId)
- [ ] Add Edge Function calls for rewrite/translate in AiManagerBridge
- [ ] Remove clipDropApiKey from AiConfig.kt
- [ ] Remove key input rows from AiSettingsActivity.java
- [ ] Add Supabase Edge Function URL config
- [ ] Add X-Remaining header parsing + 429 handling
- [ ] Build and verify compilation

## Success Criteria

- App compiles without API key references for ClipDrop/Gemini
- No key input UI visible in Settings
- All 6 tools call Edge Functions (verified via logcat)
- Rate limit display shows server-reported remaining count
- 429 response shows user-friendly "limit reached" message

## Security Considerations

- Device ID is not sensitive (no PII), safe in SharedPrefs unencrypted
- Supabase anon key is public (designed to be in client apps)
- No API keys in APK, SharedPrefs, or logcat
- Edge Function auth: Supabase anon key + CORS (sufficient for mobile)
