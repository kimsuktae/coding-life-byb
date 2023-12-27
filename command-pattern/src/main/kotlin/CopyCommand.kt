class CopyCommand(
    editor: Editor
) : Command(editor) {
    override fun execute(): Boolean {
        editor.clipboard = editor.textField.selectedText

        return false
    }
}
