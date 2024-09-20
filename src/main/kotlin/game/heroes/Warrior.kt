package game.heroes

import game.enemies.Enemy
import blue1
import blue2
import bold
import green2
import red2
import reset
import white
import yellow2
import kotlin.math.roundToInt

class Warrior(name: String = "Haarkon", maxHp: Double = 100.0) : Hero(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    fun stab(target: Enemy) {
        val dmgAmnt = 50 * skillMod
        target.hp -= dmgAmnt
        println("    $white>>>$reset $bold$blue2$name$reset deals $yellow2${dmgAmnt.roundToInt()} dmg$reset to $red2$bold${target.name}$reset with $bold${blue1}Stab$reset $white<<<$reset")
        Thread.sleep(200)
        println("             $white>>>$reset $bold$red2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun cleave(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= 30 * skillMod }
        println("    $white>>>$reset $bold$blue2$name$reset deals $yellow2${(30 * skillMod).roundToInt()} dmg$reset to $bold${red2}each enemy$reset with $bold${blue1}Cleave$reset $white<<<$reset")
        Thread.sleep(200)
        println(
            "              $white>>>$reset $bold$red2${targets.map { it.name }}$reset now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $green2${targets.map { it.hp.roundToInt() }} hp$reset $white<<<$reset"
        )
        deathCheckAoe(targets)
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("    $white>>>$reset $bold$blue2$name$reset is $bold${blue1}taunting$reset the $red2${bold}enemies$reset, forcing them to attack $bold${blue2}him$reset for the next ${green2}3 turns$reset $white<<<$reset")
    }

    fun battleShout() {
        tenacity += 0.1
        println("    $white>>>$reset $bold$blue2$name$reset made himself more tenacious (${green2}x10% dmg reduction$reset) with $bold${blue1}Battle Shout$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold$blue2$name$reset $white($reset${blue1}Heroes.Warrior$reset, $green2${hp.roundToInt()} hp$reset$white)$reset"
    }
}