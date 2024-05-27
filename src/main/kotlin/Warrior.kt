import kotlin.math.roundToInt

class Warrior(name: String, maxHp: Int = 100) : Hero(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun stab(target: Enemy) {
        val dmgAmnt = (50 * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("   >>> $bold$blue$name$reset deals $red$bold$dmgAmnt dmg$reset to $yellow$bold${target.name}$reset with $bold${blue}Stab$reset <<<")
        println()
    }

    fun swordSwipe(enemies: List<Enemy>) {
        enemies.forEach { it.hp -= (30 * dmgMod).roundToInt() }
        println("   >>> $bold$blue$name$reset deals $red$bold${(30 * dmgMod).roundToInt()} dmg$reset to $yellow${bold}each enemy$reset with $bold${blue}Sword Swipe$reset <<<")
        println()
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("   >>> $bold$blue$name$reset is $bold${blue}taunting$reset the $yellow${bold}enemies$reset forcing them to attack him for the next $bold${green}3 turns$reset <<<")
        println()
    }

    fun battleShout() {
        tenacity += 0.1
        println("   >>> $bold$blue$name$reset made himself more tenacious ($bold${green}x10% dmg reduction$reset) with $bold${blue}Battle Shout$reset <<<")
        println()
    }

    override fun toString(): String {
        return "$name (Warrior, ${hp}hp)"
    }
}