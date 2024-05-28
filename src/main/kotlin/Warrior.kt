import kotlin.math.roundToInt

class Warrior(name: String, maxHp: Int = 100) : Hero(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    private val white = "\u001B[97m"
    private val red = "\u001B[91m"
    private val green = "\u001B[92m"
    private val yellow = "\u001B[93m"
    private val blue1 = "\u001B[34m"
    private val blue2 = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun stab(target: Enemy) {
        val dmgAmnt = (50 * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("   $white>>>$reset $bold$blue2$name$reset deals $red$dmgAmnt dmg$reset to $yellow$bold${target.name}$reset with $bold${blue1}Stab$reset $white<<<$reset")
        println()
    }

    fun swordSwipe(enemies: List<Enemy>) {
        enemies.forEach { it.hp -= (30 * dmgMod).roundToInt() }
        println("   $white>>>$reset $bold$blue2$name$reset deals $red${(30 * dmgMod).roundToInt()} dmg$reset to $yellow${bold}each enemy$reset with $bold${blue1}Sword Swipe$reset $white<<<$reset")
        println()
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("   $white>>>$reset $bold$blue2$name$reset is $bold${blue1}taunting$reset the $yellow${bold}enemies$reset forcing them to attack him for the next ${green}3 turns$reset $white<<<$reset")
        println()
    }

    fun battleShout() {
        tenacity += 0.1
        println("   $white>>>$reset $bold$blue2$name$reset made himself more tenacious (${green}x10% dmg reduction$reset) with $bold${blue1}Battle Shout$reset $white<<<$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Warrior, ${hp} hp)"
    }
}