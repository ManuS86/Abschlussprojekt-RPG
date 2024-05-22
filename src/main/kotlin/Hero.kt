import kotlin.math.max
import kotlin.math.min

open class Hero(val name: String, val maxHp: Int) {
    var hp = maxHp
    var damageModifier = 1.0
    var cursed = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }
    }