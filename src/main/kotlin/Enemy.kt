import kotlin.math.min

open class Enemy(var name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgMod = 1.0
    var burning = false
    var cantHeal = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
}
