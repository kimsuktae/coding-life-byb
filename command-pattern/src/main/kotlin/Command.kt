abstract class Command(
    val editor: Editor,
    private var backup: String? = null,
) {

    fun backup() {
        backup = editor.textField.text
    }

    fun undo() {
        editor.textField.text = backup
    }

    abstract fun execute(): Boolean
}
