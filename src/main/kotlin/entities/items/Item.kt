package entities.items

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import entities.Examinable
import entities.people.Player
import util.EquipPart

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = Item::class)
@JsonSubTypes(
    JsonSubTypes.Type(value = KeyLockItem::class, name = "KEY_LOCK"),
    JsonSubTypes.Type(value = OneShotItem::class, name = "ONE_SHOT"),
    JsonSubTypes.Type(value = EquipItem::class, name = "EQUIP")
)
open class Item(val name: String, override var description: String) : Examinable {
    override val matchers = mutableSetOf(name)
}