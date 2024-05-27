import kotlin.math.roundToInt

class Warrior(name: String, maxHp: Int = 100) : Hero(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0
    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun stab(target: Enemy) {
        val dmgAmnt = (50 * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("$green$bold$name$reset deals $red$bold$dmgAmnt dmg$reset$green to $yellow$bold${target.name}$reset$green with Stab.$reset")
        println()
    }

    fun swordSwipe(enemies: List<Enemy>) {
        enemies.forEach { it.hp -= (30 * dmgMod).roundToInt() }
        println("$green$bold$name$reset$green deals $red$bold${(30 * dmgMod).roundToInt()} dmg$reset$green to $yellow${bold}each enemy$reset$green with Sword Swipe.$reset")
        println()
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("$green$bold$name$reset$green is taunting the $yellow${bold}enemies$reset$green forcing them to attack him for the next 3 turns.$reset")
        println()
    }

    fun battleShout() {
        durability += 0.1
        println("$green$bold$name$reset$green made himself more durable (x10% dmg reduction) with Battle Shout.$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Warrior, ${hp}hp)"
    }
}