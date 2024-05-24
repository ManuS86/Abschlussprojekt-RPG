import kotlin.math.min

open class Hero(val name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgMod = 1.0
    var durability = 1.0
    var cantHeal = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
}