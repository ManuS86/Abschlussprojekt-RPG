import kotlin.math.roundToInt

class Warrior(name: String, maxHp: Int = 100) : Hero(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    fun stab(target: Enemy) {
        val dmgAmnt = (50 * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("    $white>>>$reset $bold$blue2$name$reset deals $red2$dmgAmnt dmg$reset to $yellow2$bold${target.name}$reset with $bold${blue1}Stab$reset $white<<<$reset")
        println("               $white>>>$reset $bold$yellow2${target.name}$reset has $green2${target.hp} hp$reset left. $white<<<$reset")
    }

    fun swordSwipe(targets: List<Enemy>) {
        targets.forEach { it.hp -= (30 * dmgMod).roundToInt() }
        println("    $white>>>$reset $bold$blue2$name$reset deals $red2${(30 * dmgMod).roundToInt()} dmg$reset to $yellow2${bold}each enemy$reset with $bold${blue1}Sword Swipe$reset $white<<<$reset")
        println("      $white>>>$reset $bold$yellow2${targets.map { it.name }}$reset have $green2${targets.map { it.hp }} hp$reset left. $white<<<$reset")
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("    $white>>>$reset $bold$blue2$name$reset is $bold${blue1}taunting$reset the $yellow2${bold}enemies$reset forcing them to attack $bold${blue2}him$reset for the next ${green2}3 turns$reset $white<<<$reset")
    }

    fun battleShout() {
        tenacity += 0.1
        println("    $white>>>$reset $bold$blue2$name$reset made himself more tenacious (${green2}x10% dmg reduction$reset) with $bold${blue1}Battle Shout$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$name (Warrior, ${hp} hp)"
    }
}