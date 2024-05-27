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
        println("   >>> $bold$yellow$name$reset deals $red$bold$dmgAmnt dmg$reset to $blue$bold${target.name}$reset with    >>> $bold${yellow}Smash$reset <<<")
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (20 * dmgMod / it.durability).roundToInt() }
        println("   >>> $bold$yellow$name$reset deals $red$bold${targets.forEach { (20 * dmgMod / it.durability).roundToInt() }} dmg$reset to $bold${blue}each hero$reset with    >>> $bold${yellow}Ground Slam$reset <<<")
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("   >>> The $bold$yellow$name$reset is $bold${yellow}taunting$reset the $bold${blue}heroes$reset forcing them to attack him for the next $bold${green}2$reset turns <<<")
    }

    override fun toString(): String {
        return "Golem (${hp}hp)"
    }
}