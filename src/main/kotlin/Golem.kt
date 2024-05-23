import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 100) : Enemy(name, maxHp) {
    fun smash(target: Hero) {
        val dmgAmount = (40 * dmgModifier / target.durability).roundToInt()
        target.hp -= dmgAmount
        println("$name deals $dmgAmount dmg to ${target.name} with Smash.")
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (20 * dmgModifier / it.durability).roundToInt() }
        println("$name deals ${targets.forEach { (20 * dmgModifier / it.durability).roundToInt() }} dmg to each hero with Ground Slam.")
    }

    fun taunt() {
    }

    override fun toString(): String {
        return "Golem (${hp}hp)"
    }
}