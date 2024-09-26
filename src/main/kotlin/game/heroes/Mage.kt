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

class Mage(name: String = "Keros", maxHp: Double = 80.0) : Hero(name, maxHp) {

    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= (35..45).random() * skillMod }
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET deals $YELLOW2${(30 * skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}each enemy$RESET with $BOLD${BLUE1}Fireball$RESET $WHITE<<<$RESET")
        println(
            "            $WHITE>>>$RESET $BOLD$RED2${targets.map { it.name }}$RESET now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $GREEN2${targets.map { it.hp.roundToInt() }} hp$RESET $WHITE<<<$RESET"
        )
        deathCheckAoE(targets)
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = (50..60).random() * skillMod
        target.hp -= dmgAmnt
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET deals $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET to $BOLD$RED2${target.name}$RESET with $BOLD${BLUE1}Lightning Bolt$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("               $WHITE>>>$RESET $BOLD$RED2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        deathCheck(target)
    }

    fun magicMissiles(targets: MutableList<Enemy>) {
        val dmgAmnt1 = (20..35).random() * skillMod
        val target1 = targets.filter { it.hp > 0 }.random()
        target1.hp -= dmgAmnt1
        val dmgAmnt2 = (20..35).random() * skillMod
        var target2: Enemy? = null
        if (targets.any { it.hp > 0 }) {
            target2 = targets.filter { it.hp > 0 }.random()
            target2.hp -= dmgAmnt2
        }
        println(
            "    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET deals $YELLOW2${dmgAmnt1.roundToInt()} dmg$RESET to $RED2$BOLD${target1.name}$RESET ${
                if (target2 != null) {
                    "and $YELLOW2${dmgAmnt2.roundToInt()} dmg$RESET to $RED2$BOLD${target2.name}$RESET"
                } else {
                    ""
                }
            } with $BLUE1${BOLD}Magic Missiles$RESET $WHITE<<<$RESET"
        )
        Thread.sleep(200)
        println(
            "                $WHITE>>>$RESET $BOLD$RED2${targets.map { it.name }}$RESET now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $GREEN2${targets.map { it.hp.roundToInt() }} hp$RESET $WHITE<<<$RESET"
        )
        deathCheckAoE(targets)
    }

    fun searingTouch(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = 30 * skillMod
            target.hp -= dmgAmnt
            target.burning = true
            println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET deals $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET to $RED2$BOLD${target.name}$RESET with $BOLD${BLUE1}Searing Touch$RESET and sets them on fire $WHITE<<<$RESET")
            Thread.sleep(200)
            println("              $WHITE>>>$RESET $BOLD$RED2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
            deathCheck(target)
        }
    }

    override fun toString(): String {
        return "$BOLD$BLUE2$name$RESET $WHITE($RESET${BLUE1}Mage$RESET, $GREEN2${hp.roundToInt()} hp$RESET$WHITE)$RESET"
    }
}