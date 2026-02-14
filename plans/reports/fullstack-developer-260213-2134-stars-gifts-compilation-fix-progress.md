# Stars/Gifts Removal Compilation Fix - Progress Report

## Status
**In Progress** - 329 compilation errors remaining (down from initial ~6500)

## Work Completed

### 1. Created Stub Classes (20+ files)
All in `TMessagesProj/src/main/java/org/telegram/ui/Stars/`:
- `AuctionBidSheet.java` + Params inner class
- `BalanceCloud.java`
- `BotStarsController.java` (extensive stub, 166 lines)
- `ExplainStarsSheet.java` + FeatureCell
- `FeatureCell.java`
- `GiftOfferSheet.java`
- `GiftSheet.java` + CardBackground
- `GiftThemeReuseTopView.java`
- `ISuperRipple.java`
- `MessageSuggestionOfferSheet.java` (MODE_INPUT/MODE_EDIT, formatDateTime)
- `Particles.java` (with bounds, draw, process methods)
- `ProfileGiftsView.java` (with set methods)
- `ResaleGiftsList.java`
- `Ribbon.java`
- `StarGiftDrawableIcon.java`
- `StarGiftPatterns.java` (TYPE_LINK_PREVIEW constant)
- `StarGiftSheet.java` (multiple constructors, set methods)
- `StarGiftUniqueActionLayout.java` (attach/detach/has/onTouchEvent)
- `StarReactionsOverlay.java`
- `StarsController.java` (massive stub, 344 lines with balance, insufficientSubscriptions, etc.)
- `StarsIntroActivity.java` (formatStarsAmount, replaceStars* variants, showTransactionSheet)
- `StarsNeededSheet.java`
- `StarsOptionsSheet.java`
- `StarsReactionsSheet.java` + Particles inner class
- `StarsSlider.java`

### 2. Extended Existing Stubs
- `ResaleGiftsFragment.java` - added ResaleGiftsList inner class
- `GiftSheet.java` (Gifts package) - added CardBackground, RibbonDrawable, PADDING constants

### 3. Added Imports to Core Files
- `AlertDialog.java` - BalanceCloud
- `ChatActionCell.java` - GiftOfferSheet, StarGiftUniqueActionLayout
- `ChatActivity.java` - GiftSheet, MessageSuggestionOfferSheet, StarReactionsOverlay, StarsNeededSheet, StarsReactionsSheet
- `ChatAttachAlert.java` - MessageSuggestionOfferSheet
- `LaunchActivity.java` - ISuperRipple
- `PeerColorActivity.java` - FeatureCell, Ribbon, StarGiftDrawableIcon, StarGiftPatterns
- `ProfileActivity.java` - ProfileGiftsView

### 4. Method Signatures Added
**StarsIntroActivity:**
- `formatStarsAmount()`, `formatStarsAmountShort()`
- Multiple `replaceStars()` overloads (10+ variants)
- Multiple `replaceStarsWithPlain()` overloads (4 variants)
- `showTransactionSheet()`, `getTonGiftEmoji()`
- `showMediaPriceSheet()`

**StarsController:**
- `balance` field (TL_stars.StarsAmount)
- `insufficientSubscriptions` list
- `findAttribute()` with generics
- `sendPaidReaction()`
- All balance/subscription/transaction stub methods

**Particles classes:**
- Both `Stars.Particles` and `StarsReactionsSheet.Particles`
- bounds field (Rect vs RectF)
- setVisible(), setBounds(), process(), draw() methods

**StarGiftSheet:**
- Multiple constructors
- set() overloads (1 and 3 params)
- replaceSingleTagToLink()

**StarGiftUniqueActionLayout:**
- Multiple constructors (Context order matters!)
- attach(), detach(), has(), onTouchEvent(float, float, Object), drawOutbounds()

## Remaining Issues

### Constructor Signature Mismatches
- Several classes have (int, Context, Object) vs (Context, int, Object) confusion
- GiftSheet needs more constructor variants

### Type Mismatches
- CharSequence vs String conversions
- Object vs functional interface issues
- RectF vs Rect confusion

### Missing Variable/Field References
Multiple files reference:
- `repost` variable
- Various has() methods
- drawOutbounds() methods

### Files with Most Errors
1. ChatActionCell.java (~20+ errors)
2. ChatActivity.java (~15+ errors)
3. Components (ReactionsContainerLayout, etc.)

## Next Steps

1. Fix remaining constructor signatures
2. Add missing repost field where needed
3. Fix type conversion issues (CharSequence/String)
4. Add missing method stubs systematically
5. Verify all imports are correct

## Notes

- Total progress: ~95% reduction in errors (6500 → 329)
- Most stub classes follow pattern: extend View/BottomSheet, no-op methods
- Key challenge: matching exact method signatures from call sites
- Some classes need both `org.telegram.ui.Stars` and `org.telegram.ui.Gifts` variants
