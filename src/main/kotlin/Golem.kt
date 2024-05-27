import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0
    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

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
        isTaunting = true
        tauntTimer = 3
        println("The $name is taunting the heroes forcing them to attack him for the next 2 turns.")
    }

    override fun toString(): String {
        return "Golem (${hp}hp)"
    }
}