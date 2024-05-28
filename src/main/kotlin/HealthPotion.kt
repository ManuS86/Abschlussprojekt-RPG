import kotlin.math.roundToInt

class HealthPotion : Potion() {
    private val white = "\u001B[97m"
    private val green1 = "\u001B[32m"
    private val green2 = "\u001B[92m"
    private val blue2 = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    override fun use(target: Hero) {
        val healAmount = (target.maxHp * 0.5).roundToInt()
        val preHealHp = target.hp
        target.heal(healAmount)
        val amountHealed = target.hp - preHealHp
        println("   $white>>>$reset $bold$blue2${target.name}$reset drinks a $bold${green1}Health Potion$reset to heal for $green2$amountHealed hp$reset. $white<<<$reset")
    }

    override fun toString(): String {
        return "Health Potion"
    }
}