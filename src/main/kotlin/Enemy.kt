import kotlin.math.min

open class Enemy(var name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgModifier = 1.0
    var burning = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
}
