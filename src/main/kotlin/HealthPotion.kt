import kotlin.math.roundToInt

class HealthPotion : Potion() {
    override fun use(target: Hero) {
        val healAmount = (target.maxHp * 0.5).roundToInt()
        val preHealHp = target.hp
        target.heal(healAmount)
        val amountHealed = target.hp - preHealHp
        println("${target.name} drinks a Health Potion to heal for $amountHealed")
    }

    override fun toString(): String {
        return "Health Potion"
    }
}