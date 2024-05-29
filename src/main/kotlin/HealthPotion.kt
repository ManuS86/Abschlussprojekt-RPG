import kotlin.math.roundToInt

class HealthPotion : Potion() {

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