import kotlin.math.roundToInt

class HealthPotion : Potion() {
    private val green = "\u001B[32m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    override fun use(target: Hero) {
        val healAmount = (target.maxHp * 0.5).roundToInt()
        val preHealHp = target.hp
        target.heal(healAmount)
        val amountHealed = target.hp - preHealHp
        println("$blue$bold${target.name}$reset$blue drinks a Health Potion to heal for $green$bold$amountHealed hp$reset")
    }

    override fun toString(): String {
        return "Health Potion"
    }
}