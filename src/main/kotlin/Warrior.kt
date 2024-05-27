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
        println("$blue$bold$name$reset$blue deals $red$bold$dmgAmnt dmg$reset$blue to $yellow$bold${target.name}$reset$blue with ${bold}Stab$reset$blue.$reset")
        println()
    }

    fun swordSwipe(enemies: List<Enemy>) {
        enemies.forEach { it.hp -= (30 * dmgMod).roundToInt() }
        println("$blue$bold$name$reset$blue deals $red$bold${(30 * dmgMod).roundToInt()} dmg$reset$blue to $yellow${bold}each enemy$reset$blue with ${bold}Sword Swipe$reset$blue.$reset")
        println()
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("$blue$bold$name$reset$blue is ${bold}taunting$reset$blue the $yellow${bold}enemies$reset$blue forcing them to attack him for the next ${bold}3$reset$blue turns.$reset")
        println()
    }

    fun battleShout() {
        durability += 0.1
        println("$blue$bold$name$reset$blue made himself more durable ($bold${green}x10% dmg reduction$reset$blue) with ${bold}Battle Shout$reset$blue.$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Warrior, ${hp}hp)"
    }
}