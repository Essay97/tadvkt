package commands

class NoCommand: Command(false, false) {
    override fun doAction() {
        println("I'm sorry, I don't think I understood...")
    }
}