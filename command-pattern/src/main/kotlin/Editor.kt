import java.awt.FlowLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.WindowConstants


class Editor(
    val textField: JTextArea = JTextArea(),
    var clipboard: String = "",
    private val history: CommandHistory = CommandHistory()
) {

    fun init() {
        val frame = JFrame("Text editor")
        val content = JPanel();
        frame.contentPane = content
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        content.layout = BoxLayout(content, BoxLayout.Y_AXIS)
        textField.lineWrap = true
        content.add(textField)
        val buttons = JPanel(FlowLayout(FlowLayout.CENTER))
        val ctrlC = JButton("Ctrl+C")
        val ctrlX = JButton("Ctrl+X")
        val ctrlV = JButton("Ctrl+V")
        val ctrlZ = JButton("Ctrl+Z")
        val editor = this

        ctrlC.addActionListener {
            executeCommand(CopyCommand(editor))
        }
    }

    private fun executeCommand(command: Command) {
        if (command.execute()) {
            history.push(command)
        }
    }
}
