import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0
    private val red = "\u001B[31m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun smash(target: Hero) {
        val dmgAmnt = (40 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmnt
        println("$yellow$bold$name$reset$yellow deals $red$bold$dmgAmnt dmg$reset$yellow to $blue$bold${target.name}$reset$yellow with ${bold}Smash$reset$yellow.$reset")
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (20 * dmgMod / it.durability).roundToInt() }
        println("$yellow$bold$name$reset$yellow deals $red$bold${targets.forEach { (20 * dmgMod / it.durability).roundToInt() }} dmg$reset$yellow to ${blue}each hero$reset$yellow with ${bold}Ground Slam$reset$yellow.$reset")
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("${yellow}The $bold$name$reset$yellow is ${bold}taunting$reset$yellow the ${blue}heroes$reset$yellow forcing them to attack him for the next 2 turns.$reset")
    }

    override fun toString(): String {
        return "Golem (${hp}hp)"
    }
}