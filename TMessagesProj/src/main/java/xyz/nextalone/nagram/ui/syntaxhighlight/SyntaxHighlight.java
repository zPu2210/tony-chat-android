package xyz.nextalone.nagram.ui.syntaxhighlight;

import android.text.Spannable;
import org.telegram.ui.Components.TextStyleSpan;

/**
 * Stub: Syntax highlighting - no-op
 */
public class SyntaxHighlight {
    public static void highlight(TextStyleSpan.TextStyleRun run, Spannable spannable) {
        // Syntax highlighting disabled in Tony Chat
    }

    public static Spannable highlight(String code, String language) {
        return null;
    }
}
