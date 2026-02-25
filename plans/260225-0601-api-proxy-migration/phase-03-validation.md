# Phase 3: Validation + Cleanup

**Priority:** High | **Status:** Complete (code validation) | **Depends on:** Phase 2
**Date:** 2026-02-25

## Overview

End-to-end testing of all 6 AI tools through Edge Function proxy. Verify security (no keys in APK), rate limiting, and error handling. Build release APK.

## Todo

- [ ] Test ai-rewrite: enter text + style → get rewritten text (device test)
- [ ] Test ai-translate: enter text + target lang → get translation (device test)
- [ ] Test ai-remove-bg: pick photo → get transparent PNG (device test)
- [ ] Test ai-upscale: pick photo → get 4x upscaled image (device test)
- [ ] Test ai-remove-text: pick photo → get text-removed image (device test)
- [ ] Test ai-generate-image: enter prompt → get generated image (device test)
- [ ] Test rate limiting: hit daily limit → verify 429 + friendly message (device test)
- [ ] Test remaining quota: verify X-Remaining decrements correctly (device test)
- [ ] Test offline/error: disable network → verify graceful error (device test)
- [x] Decompile APK: no old ClipDrop URLs found in DEX files
- [x] Build release APK: BUILD SUCCESSFUL (47MB)
- [x] Verify APK size: 47MB (unchanged)
- [x] Remove old API key migration code: clipDropApiKey removed from AiConfig
- [x] Clean up unused imports: all files verified clean (no dead imports)

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
