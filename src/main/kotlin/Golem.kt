import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    private val white = "\u001B[97m"
    private val red = "\u001B[91m"
    private val green = "\u001B[92m"
    private val yellow1 = "\u001B[33m"
    private val yellow2 = "\u001B[93m"
    private val blue = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun smash(target: Hero) {
        val dmgAmnt = (50 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("   $white>>>$reset $bold$yellow2$name$reset deals $bold$dmgAmnt dmg$reset to $blue$bold${target.name}$reset with    >>> $bold${yellow1}Smash$reset $white<<<$reset")
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (30 * dmgMod / it.tenacity).roundToInt() }
        println("   $white>>>$reset $bold$yellow2$name$reset deals $red${targets.forEach { (20 * dmgMod / it.tenacity).roundToInt() }} dmg$reset to $bold${blue}each hero$reset with $bold${yellow1}Ground Slam$reset $white<<<$reset")
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("   $white>>>$reset The $bold$yellow2$name$reset is $bold${yellow1}taunting$reset the $bold${blue}heroes$reset forcing them to attack him for the next ${green}2$bold turns$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "Golem (${hp} hp)"
    }
}