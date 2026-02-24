# Bottom Navigation Implementation Guide

Floating pill bottom nav that replaces Telegram's hamburger drawer. Reference for current devs and future contributors.

---

## Architecture

```
LaunchActivity
└── drawerLayoutContainer (drawer LOCKED when bottom nav active)
    └── tonyMainContainer (FrameLayout)
        ├── tonyTabContainer (FrameLayout, fills screen)
        │   ├── actionBarLayout.getView()  ← TAB_CHATS (DialogsActivity)
        │   ├── tonyTabViews[0]            ← TAB_TONY_AI (AiAssistFragment)
        │   ├── tonyTabViews[1]            ← TAB_EXPLORE (CommunityFeedFragment)
        │   └── tonyTabViews[3]            ← TAB_SETTINGS (TonySettingsFragment)
        └── TonyBottomNavView (340dp pill, floating, elevation 15dp)
```

**Key files:**
- `LaunchActivity.java` — lines 1247-1441: setup, tab switching, visibility control
- `TonyBottomNavView.java` — 324-line custom FrameLayout with pill animation
- `TonyConfig.kt` — `bottomNavEnabled` feature flag (default: true)

---

## How It Works

### Tab Switching
- Tabs use `View.VISIBLE / View.GONE` — NO fragment replace. Preserves scroll position, form state, etc.
- Lazy creation: non-Chats tabs only instantiate on first visit via `createTonyTabView()`
- Chats tab reuses existing `actionBarLayout`; other tabs get their own `INavigationLayout`

### Nav Visibility
- `updateTonyBottomNavVisibility()` checks **current tab's** stack depth
- Stack > 1 (sub-screen open) → slide nav down 100dp (hide)
- Stack == 1 (root screen) → slide nav up to 0 (show)
- Animation: 200ms with AccelerateInterpolator (hide) / DecelerateInterpolator (show)

### Trigger Points
Nav visibility is re-evaluated via `layout.getView().post(this::updateTonyBottomNavVisibility)` in:
1. `needPresentFragment()` — when a fragment is presented
2. `needAddFragmentToStack()` — when a fragment is added to stack
3. `needCloseLastFragment()` — when back/close pops a fragment
4. `switchTonyTab()` — when user taps a different tab
5. `onResume()` — when app returns from background

### Drawer Lockdown
When bottom nav is active, drawer is locked at 4 guard points:
1. `setupTonyBottomNav()` — initial setup
2. `needPresentFragment()` — on every fragment push
3. `needAddFragmentToStack()` — on every stack addition
4. `switchToAccount()` — after account switch (respects `isTonyBottomNavActive()`)

---

## Lessons Learned (Bug Fixes Applied 2026-02-24)

### 1. Always call `updateTonyBottomNavVisibility()` after tab switch
`switchTonyTab()` changes which tab view is visible. If the previous tab had a deep stack (nav hidden), the new tab's shallow stack should show the nav. Without re-evaluating, nav stays hidden.

### 2. Always `cancel()` before `animate()`
Android `ViewPropertyAnimator` does NOT auto-cancel. Rapid push+pop creates overlapping animations → nav stuck at intermediate Y. Always: `tonyBottomNav.animate().cancel()` before starting new animation.

### 3. Never compare `translationY` with exact equality
`currentY == 0` fails after animation interruption (float precision). Use threshold:
```java
if (Math.abs(currentY - targetY) > 1f) { ... }
```

### 4. Check only CURRENT tab's stack depth
Old code checked ALL tabs' stacks — if ANY tab had depth > 1, nav would hide. But user can only see one tab at a time. Hiding nav because a different tab has a deep stack is confusing. Only check `tonyCurrentTab`'s stack.

### 5. Guard `switchToAccount()` against drawer re-enable
`switchToAccount()` calls `setAllowOpenDrawer(true)` unconditionally. Must check `isTonyBottomNavActive()`:
```java
drawerLayoutContainer.setAllowOpenDrawer(!isTonyBottomNavActive(), false);
```

### 6. Re-evaluate nav on `onResume()`
App coming back from background (notification tap, task switcher) may have stale nav state. Post a visibility check in `onResume()`.

### 7. Auto-switch to Chats tab on deep link / notification
Deep links push `ChatActivity` onto `actionBarLayout` (Chats tab stack). If user is on Tony AI tab, the chat opens on an invisible view. Fix: in `needPresentFragment` and `needAddFragmentToStack`, detect `ChatActivity` on `actionBarLayout` and switch to Chats tab first.

### 8. Back press on AI root: minimize, don't exit
`moveTaskToBack(true)` is better UX than destroying the activity. User expects back = minimize when on the default tab (matching iOS/modern Android behavior).

---

## Audit Checklist

When modifying navigation code, verify:

- [ ] Every code path that pushes/pops fragments calls `updateTonyBottomNavVisibility()`
- [ ] `grep setAllowOpenDrawer` — every instance checks `isTonyBottomNavActive()`
- [ ] `grep presentFragment` in `handleIntent` — ensure Chats tab is active before ChatActivity push
- [ ] Tab switch preserves fragment state (no `removeAllFragments` on inactive tabs)
- [ ] Feature flag fallback: setting `bottomNavEnabled=false` restores full drawer behavior
- [ ] Animation cancel before new animation on `tonyBottomNav`

---

## Feature Flag

`TonyConfig.bottomNavEnabled` (default: `true`). When disabled:
- Bottom nav removed, drawer unlocked
- `cleanupTonyBottomNav()` nulls all tab views and layouts
- Standard Telegram hamburger + drawer behavior restored
- Ship with confidence — users can toggle if issues arise
