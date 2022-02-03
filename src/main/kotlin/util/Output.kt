package util

import java.util.concurrent.TimeUnit

object Output {
    fun postDelayed(ms: Int, msg: String?) {
        try {
            println(msg)
            TimeUnit.MILLISECONDS.sleep(ms.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun tutorial() {
        println("""
        |COMMANDS
        |--------
        |* Action commands
        |- move N -> go to the room to the north
        |- move S -> go to the room to the south
        |- move E -> go to the room to the east
        |- move W -> go to the room to the west
        |- examine <thing> -> look at something to gather information about it
        |- fight <person> -> start a fight routine against someone
        |- talk <person> -> start a dialogue with someone
        |- grab <thing> -> put something in your inventory
        |- drop <thing> -> throw something away from your inventory
        |- use <thing> -> activate the effect of an item that you have in your inventory (use an item, equip it, open a door, and so on)
        |- takeoff <thing> -> takes off a piece of equipment that you previously equipped
        |* Information commands
        |- inventory -> lists all the items in your inventory
        |- equip -> lists the items you have equipped at the moment
        |- where ->  gives information about the current room
        |- whoami -> gives information about your character
        |- stats -> lists all the stats of your character, counting item effects
        |- number -> shows the number of commands you used in this adventure
        |- tutorial -> prints this tutorial again""".trimMargin())
    }
}