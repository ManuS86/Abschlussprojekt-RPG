import kotlin.math.min

abstract class Hero(val name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgMod = 1.0
    var tenacity = 1.0
    var cantHeal = false
    var cantHealTimer = 0

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
}