import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 150) : Enemy(name, maxHp) {
    var taunting = false
    var tauntTimer = 0

    fun smash(target: Hero) {
        val dmgAmnt = (40 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmnt
        println("$name deals $dmgAmnt dmg to ${target.name} with Smash.")
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (20 * dmgMod / it.durability).roundToInt() }
        println("$name deals ${targets.forEach { (20 * dmgMod / it.durability).roundToInt() }} dmg to each hero with Ground Slam.")
    }

    fun taunt() {
        taunting = true
        tauntTimer = 2
        println("The $name is taunting the heroes forcing them to attack him for the next 2 turns.")
    }

    override fun toString(): String {
        return "Golem (${hp}hp)"
    }
}