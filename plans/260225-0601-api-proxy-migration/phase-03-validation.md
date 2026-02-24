# Phase 3: Validation + Cleanup

**Priority:** High | **Status:** Pending | **Depends on:** Phase 2

## Overview

End-to-end testing of all 6 AI tools through Edge Function proxy. Verify security (no keys in APK), rate limiting, and error handling. Build release APK.

## Todo

- [ ] Test ai-rewrite: enter text + style → get rewritten text
- [ ] Test ai-translate: enter text + target lang → get translation
- [ ] Test ai-remove-bg: pick photo → get transparent PNG
- [ ] Test ai-upscale: pick photo → get 4x upscaled image
- [ ] Test ai-remove-text: pick photo → get text-removed image
- [ ] Test ai-generate-image: enter prompt → get generated image
- [ ] Test rate limiting: hit daily limit → verify 429 + friendly message
- [ ] Test remaining quota: verify X-Remaining decrements correctly
- [ ] Test offline/error: disable network → verify graceful error
- [ ] Decompile APK: verify no API keys in strings/resources
- [ ] Build release APK: `TMessagesProj_App:assembleAfatRelease`
- [ ] Verify APK size hasn't significantly changed
- [ ] Remove old API key migration code if any
- [ ] Clean up unused imports

## Success Criteria

- All 6 tools work end-to-end on device
- Rate limiting enforced server-side (can't bypass by reinstalling)
- No API keys found in decompiled APK
- Release APK builds and installs successfully
- Settings screen clean (no key input rows)

## Post-Validation

- Commit + push with message: `feat: migrate AI APIs to Supabase Edge Function proxy`
- Update MEMORY.md with new architecture
- Continue with Phase 5 (Store Submission)
