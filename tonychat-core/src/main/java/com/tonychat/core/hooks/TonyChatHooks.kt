package com.tonychat.core.hooks

/**
 * Hook interface for message events
 */
interface MessageHook {
    fun onMessageSent(message: Any)
    fun onMessageReceived(message: Any)
}

/**
 * Hook interface for UI events
 */
interface UIHook {
    fun onChatOpened(chatId: Long)
    fun onSettingsOpened()
}

/**
 * Singleton manager for registering and invoking hooks
 */
object HookManager {
    private val messageHooks = mutableSetOf<MessageHook>()
    private val uiHooks = mutableSetOf<UIHook>()

    // Message hooks
    fun registerMessageHook(hook: MessageHook) {
        messageHooks.add(hook)
    }

    fun unregisterMessageHook(hook: MessageHook) {
        messageHooks.remove(hook)
    }

    fun notifyMessageSent(message: Any) {
        messageHooks.forEach { it.onMessageSent(message) }
    }

    fun notifyMessageReceived(message: Any) {
        messageHooks.forEach { it.onMessageReceived(message) }
    }

    // UI hooks
    fun registerUIHook(hook: UIHook) {
        uiHooks.add(hook)
    }

    fun unregisterUIHook(hook: UIHook) {
        uiHooks.remove(hook)
    }

    fun notifyChatOpened(chatId: Long) {
        uiHooks.forEach { it.onChatOpened(chatId) }
    }

    fun notifySettingsOpened() {
        uiHooks.forEach { it.onSettingsOpened() }
    }

    /**
     * Clear all registered hooks
     */
    fun clearAll() {
        messageHooks.clear()
        uiHooks.clear()
    }
}
