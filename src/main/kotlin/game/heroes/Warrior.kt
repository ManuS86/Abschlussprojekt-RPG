package game.heroes

import BLUE1
import BLUE2
import BOLD
import game.enemies.Enemy
import GREEN2
import RED2
import RESET
import WHITE
import YELLOW2
import kotlin.math.roundToInt

class Warrior(name: String = "Haarkon", maxHp: Double = 100.0) : Hero(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    fun stab(target: Enemy) {
        val dmgAmnt = 50 * skillMod
        target.hp -= dmgAmnt
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET deals $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET to $RED2$BOLD${target.name}$RESET with $BOLD${BLUE1}Stab$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("             $WHITE>>>$RESET $BOLD$RED2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        deathCheck(target)
    }

    fun cleave(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= 30 * skillMod }
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET deals $YELLOW2${(30 * skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}each enemy$RESET with $BOLD${BLUE1}Cleave$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println(
            "              $WHITE>>>$RESET $BOLD$RED2${targets.map { it.name }}$RESET now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $GREEN2${targets.map { it.hp.roundToInt() }} hp$RESET $WHITE<<<$RESET"
        )
        deathCheckAoE(targets)
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET is $BOLD${BLUE1}taunting$RESET the $RED2${BOLD}enemies$RESET, forcing them to attack $BOLD${BLUE2}him$RESET for the next ${GREEN2}3 turns$RESET $WHITE<<<$RESET")
    }

    fun battleShout() {
        tenacity += 0.1
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET made himself more tenacious (${GREEN2}x10% dmg reduction$RESET) with $BOLD${BLUE1}Battle Shout$RESET $WHITE<<<$RESET")
    }

    override fun toString(): String {
        return "$BOLD$BLUE2$name$RESET $WHITE($RESET${BLUE1}Warrior$RESET, $GREEN2${hp.roundToInt()} hp$RESET$WHITE)$RESET"
    }
}