package setup

import entities.GameMap
import entities.Room
import entities.items.*
import exceptions.DeserializingException
import util.Direction
import util.EquipPart

data class ItemDTO(val name: String, val description: String, val matchers: Set<String>?, val oneShot: Modifiers?,
                   val equip: Modifiers?, val keyLock: KeyLock?) {
    data class Modifiers(val hp: Int = 0, val poison: Int = 0, val stun: Int = 0, val burn: Int = 0, val attack: Int = 0,
                    val part: EquipPart?)
    data class KeyLock(val source: String, val destination: Room, val direction: Direction)

    fun toItem(map: GameMap? = null): Item {
        val item = Item(name, description).also {
            this.matchers?.let { them -> it.matchers.addAll(them) }
        }
        if (oneShot==null && equip==null && keyLock!=null) {
            if (map == null) {
                throw IllegalArgumentException("The argument \"map\" of the function toItem() is mandatory when " +
                        "deserializing a KeyLockItem and cannot be null")
            }
            val roomSource = map.getRoomByName(keyLock.source)
                ?: throw DeserializingException("The key/lock item $name is bad formed: could not find the source room in the map")
            return KeyLockItem(item, KeyLockEffect(roomSource, keyLock.destination, keyLock.direction))
        } else if (oneShot==null && equip!=null && keyLock==null) {
            val (hp, poison, stun, burn, attack, part) = equip
            return EquipItem(item, StatsEffect(hp, poison, stun, burn, attack), part
                ?: throw DeserializingException("The equip item $name is bad formed: equip.part is missing or null"))
        } else if (oneShot!=null && equip==null && keyLock==null) {
            val (hp, poison, stun, burn, attack) = oneShot
            return OneShotItem(item, StatsEffect(hp, poison, stun, burn, attack))
        } else if (oneShot==null && equip==null && keyLock==null) {
            return item
        } else {
            throw DeserializingException("The item ${name} is bad formed: it can have only one property between oneShot," +
                    " equip and keyLock")
        }
    }
}