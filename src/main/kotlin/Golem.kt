import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    private val red = "\u001B[91m"
    private val green = "\u001B[92m"
    private val yellow = "\u001B[93m"
    private val blue = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun smash(target: Hero) {
        val dmgAmnt = (40 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("   >>> $bold$yellow$name$reset deals $bold$dmgAmnt dmg$reset to $blue$bold${target.name}$reset with    >>> $bold${yellow}Smash$reset <<<")
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (20 * dmgMod / it.tenacity).roundToInt() }
        println("   >>> $bold$yellow$name$reset deals $red${targets.forEach { (20 * dmgMod / it.tenacity).roundToInt() }} dmg$reset to $bold${blue}each hero$reset with    >>> $bold${yellow}Ground Slam$reset <<<")
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("   >>> The $bold$yellow$name$reset is $bold${yellow}taunting$reset the $bold${blue}heroes$reset forcing them to attack him for the next ${green}2$bold turns$reset <<<")
    }

    override fun toString(): String {
        return "Golem (${hp}hp)"
    }
}