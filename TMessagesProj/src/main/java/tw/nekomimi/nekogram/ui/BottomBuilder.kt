package tw.nekomimi.nekogram.ui

import android.content.Context
import android.content.DialogInterface
import android.widget.EditText
import android.widget.TextView
import org.telegram.ui.Cells.HeaderCell
import org.telegram.ui.Cells.RadioButtonCell
import org.telegram.ui.Cells.TextCell
import org.telegram.ui.Cells.TextCheckCell

/**
 * Stub: Bottom sheet builder - no-op implementation
 */
class BottomBuilder(val ctx: Context, val needFocus: Boolean = true, val bgColor: Int = 0) {
    @JvmField
    val bottomSheet = org.telegram.ui.ActionBar.BottomSheet(ctx, false)

    constructor(ctx: Context) : this(ctx, true)
    constructor(ctx: Context, needFocus: Boolean) : this(ctx, needFocus, 0)

    fun addTitle(title: CharSequence): HeaderCell = HeaderCell(ctx)
    fun addTitle(title: CharSequence, bigTitle: Boolean): HeaderCell = HeaderCell(ctx)
    fun addTitle(title: CharSequence, subTitle: CharSequence): HeaderCell = HeaderCell(ctx)
    fun addTitle(title: CharSequence, bigTitle: Boolean, subTitle: CharSequence?): HeaderCell = HeaderCell(ctx)

    fun addCheckItem(text: String, value: Boolean, listener: ((cell: TextCheckCell, isChecked: Boolean) -> Unit)?): TextCheckCell = TextCheckCell(ctx)
    fun addCheckItem(text: String, value: Boolean, switch: Boolean, valueText: String?): TextCheckCell = TextCheckCell(ctx)
    fun addCheckItem(text: String, value: Boolean, switch: Boolean, valueText: String?, listener: ((cell: TextCheckCell, isChecked: Boolean) -> Unit)?): TextCheckCell = TextCheckCell(ctx)
    fun addCheckItems(text: Array<String>, value: (Int) -> Boolean, listener: (index: Int, text: String, cell: TextCheckCell, isChecked: Boolean) -> Unit): List<TextCheckCell> = emptyList()
    fun addCheckItems(text: Array<String>, value: (Int) -> Boolean, switch: Boolean, listener: (index: Int, text: String, cell: TextCheckCell, isChecked: Boolean) -> Unit): List<TextCheckCell> = emptyList()
    fun addCheckItems(text: Array<String>, value: (Int) -> Boolean, switch: Boolean, valueText: ((Int) -> String)?, listener: (index: Int, text: String, cell: TextCheckCell, isChecked: Boolean) -> Unit): List<TextCheckCell> = emptyList()

    fun doRadioCheck(cell: RadioButtonCell) {}
    fun addRadioItem(text: String, value: Boolean, listener: (cell: RadioButtonCell) -> Unit): RadioButtonCell = RadioButtonCell(ctx, true)
    fun addRadioItem(text: String, value: Boolean, valueText: String?, listener: (cell: RadioButtonCell) -> Unit): RadioButtonCell = RadioButtonCell(ctx, true)
    fun addRadioItems(text: Array<String>, value: (Int, String) -> Boolean, listener: (index: Int, text: String, cell: RadioButtonCell) -> Unit): List<RadioButtonCell> = emptyList()
    fun addRadioItems(text: Array<String>, value: (Int, String) -> Boolean, valueText: ((Int, String) -> String)?, listener: (index: Int, text: String, cell: RadioButtonCell) -> Unit): List<RadioButtonCell> = emptyList()

    fun addCancelItem() {}
    fun addCancelButton() {}
    fun addCancelButton(left: Boolean) {}
    fun addOkButton(listener: ((TextView) -> Unit)) {}
    fun addOkButton(listener: ((TextView) -> Unit), noAutoDismiss: Boolean) {}
    fun addButton(text: String, listener: ((TextView) -> Unit)): TextView = TextView(ctx)
    fun addButton(text: String, noAutoDismiss: Boolean, listener: ((TextView) -> Unit)): TextView = TextView(ctx)
    fun addButton(text: String, noAutoDismiss: Boolean, left: Boolean, listener: ((TextView) -> Unit)): TextView = TextView(ctx)

    fun addItem(text: CharSequence, icon: Int, listener: ((cell: TextCell) -> Unit)?): TextCell = TextCell(ctx)
    fun addItem(text: CharSequence, icon: Int, red: Boolean, listener: ((cell: TextCell) -> Unit)?): TextCell = TextCell(ctx)
    fun addItems(text: Array<CharSequence?>, icon: IntArray?, listener: (index: Int, text: CharSequence, cell: TextCell) -> Unit): List<TextCell> = emptyList()

    fun addEditText(hintText: String? = null): EditText = EditText(ctx)

    fun setTitleMultipleLines(multilines: Boolean) {}
    fun create(): org.telegram.ui.ActionBar.BottomSheet = org.telegram.ui.ActionBar.BottomSheet(ctx, false)
    fun show(): android.app.Dialog {
        val bs = create()
        bs.show()
        return bs
    }
    fun dismiss() {}
    fun getBuilder(): BottomBuilder = this
    fun setOnPreDismissListener(onDismissListener: DialogInterface.OnDismissListener?): BottomBuilder = this
}
