import kotlin.math.min

open class Hero(val name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgModifier = 1.0
    var durability = 1.0
    var cursed = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
}