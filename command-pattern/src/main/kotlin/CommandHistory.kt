class CommandHistory {
    private val history = ArrayDeque<Command>()

    fun push(command: Command) {
        history.add(command)
    }

    fun pop() : Command {
        return history.removeLast()
    }

    fun isEmpty() : Boolean {
        return history.isEmpty()
    }
}
