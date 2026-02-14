# Phase 5C Implementation Report: VoIP/Calls Feature Removal

## Executed Phase
- **Phase**: 5C - VoIP/Calls Removal
- **Plan**: plans/260213-0952-tony-chat-bootstrap/
- **Status**: Partial (Build errors remain)
- **Date**: 2026-02-14

## Summary
Executed surgical removal of VoIP/Calls features from Tony Chat Android codebase. Gutted service files to maintain AndroidManifest compatibility, deleted major UI components, created minimal stubs.

## Files Modified

### Gutted (kept shell, emptied bodies)
1. **VoIPService.java** - 5847→250 lines
   - Kept Service structure for AndroidManifest
   - Removed all VoIP logic, native call handling
   - Maintained public static constants for compatibility
   - Added StateListener interface stub
   - Added VoIPServiceState implementation

2. **VoIPPreNotificationService.java** - 632→110 lines
   - Kept State inner class
   - Removed notification/ringing logic

3. **TelegramConnectionService.java** - ~200→40 lines
   - Kept ConnectionService shell
   - Removed Telecom integration

4. **GroupCallMessagesController.java** - Full gut
   - Kept BaseController inheritance
   - Removed all message handling

5. **GroupCallMessage.java** - Full gut
   - Minimal stub with required fields

### Deleted & Stubbed (UI Components)
1. **GroupCallActivity.java** - 10,803 lines deleted
   - Created minimal stub (30 lines)
   - Exposed groupCallInstance as public

2. **VoIPFragment.java** - Deleted, stubbed
   - Minimal getInstance/show methods

3. **CallLogActivity.java** - Deleted, stubbed
   - BaseFragment inheritance maintained

4. **GroupCall UI Files** - All deleted:
   - GroupCallSheet.java
   - CreateGroupCallSheet.java
   - GroupCallTabletGridAdapter.java
   - GroupCallPip.java
   - GroupCallFullscreenAdapter.java
   - GroupCallRecordAlert.java
   - GroupCallInvitedCell.java
   - GroupCallTextCell.java
   - GroupCallUserCell.java (stubbed with AvatarWavesDrawable)

5. **VoIP Activities** - Deleted, stubbed:
   - VoIPPermissionActivity.java
   - VoIPFeedbackActivity.java

6. **VoIP Components** - Directory deleted:
   - `/Components/voip/` - 60+ files deleted
   - Created minimal stubs:
     - VoIPHelper.java (20 lines)
     - CellFlickerDrawable.java (with public fields)
     - RTMPStreamPipOverlay.java

7. **Conference Components** - Deleted:
   - `/Components/conference/` directory removed
   - Created GroupCallMessageCell stub

## Build Status

### Compilation State
- **Errors**: 189 total (100 shown)
- **Initial errors**: ~0 (clean build before changes)
- **Progress**: Major structural changes complete, coupling issues remain

### Error Categories

#### 1. Missing VoIPHelper Methods (~20 errors)
- `showGroupCallAlert()` referenced but not stubbed
- `joinConference()` signature mismatches
- `canRateCall()` missing
- Method overload variations needed

#### 2. FragmentContextView (~30 errors)
- VoIPService.StateListener interface incomplete
- Missing: `onScreenOnChange()`, `onMediaStateUpdated()`, etc.
- GroupCallMessage references throughout
- Call message handling code not removed

#### 3. AvatarsDrawable (~20 errors)
- GroupCallUserCell.AvatarWavesDrawable constructor mismatches
- Missing public fields (amplitude, animateToAmplitude, animateAmplitudeDiff)
- draw() method signature variations

#### 4. AlertsCreator (~10 errors)
- Type conversion errors (User→Chat)
- Missing VoIPHelper methods
- Group call alert dialogs still referenced

#### 5. LaunchActivity (~10 errors)
- RTMPStreamPipOverlay references
- GroupCallActivity instance checks

#### 6. MessagesController (~5 errors)
- VoIPService method calls (onGroupCallUpdated, onSignalingData)
- Update processing still has VoIP hooks

#### 7. ChatActivity (~50 errors)
- Extensive VoIP feature integration
- GroupCallActivity.create() signature mismatches
- VoIPHelper method calls
- Cell rendering with CellFlickerDrawable

#### 8. Miscellaneous (~40 errors)
- CreateGroupCallSheet constructor mismatches
- Premium component flicker animations
- Session activity QR scan flicker
- Storage usage view animations

## Next Steps

### Immediate (to reach 0 errors)
1. Fix VoIPHelper method signatures (all variants)
2. Complete StateListener interface methods
3. Fix AvatarWavesDrawable constructor/method signatures
4. Add missing VoIPService methods
5. Fix type conversion errors in AlertsCreator
6. Remove remaining VoIP code from FragmentContextView

### Systematic Approach
- Work file-by-file on error clusters
- Add missing method stubs as needed
- Maintain minimal viable signatures
- Don't delete code from org.telegram.* unless absolutely required

### Estimated Remaining Work
- 2-3 hours to fix all compilation errors
- Another pass needed to clean up unused references
- Test build → fix → repeat until 0 errors

## Files Created
1. `/plans/reports/fullstack-developer-260214-0258-voip-removal-phase5c.md` - This report

## Unresolved Questions
1. Should we remove more VoIP integration from FragmentContextView or just stub methods?
2. Are there native library dependencies (libtgcalls.so) that need AndroidManifest/build.gradle cleanup?
3. Should VoIPActionsReceiver be gutted too?

## Technical Debt
- 189 compilation errors to resolve
- Potential runtime NPEs if VoIP code paths are triggered
- AndroidManifest still has VoIP service registrations (intentional for now)
- Native tgcalls library still linked (may cause APK bloat)
