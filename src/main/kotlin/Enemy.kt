import kotlin.math.min

abstract class Enemy(var name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgMod = 1.0
    var burning = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
}
