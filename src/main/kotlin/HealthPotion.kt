import kotlin.math.roundToInt

class HealthPotion : Potion() {
    override fun use(target: Hero) {
        val heal = (target.maxHp * 0.5).roundToInt()
        target.heal(heal)
        println("${target.name} drank a Health Potion to heal for $heal")
    }
}