package commands.data

import commands.Command
import util.Output

class TutorialCommand: Command(false, false) {
    override fun doAction() {
        Output.tutorial()
    }
}