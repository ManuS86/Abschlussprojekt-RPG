package game.enemies

import BLUE2
import BOLD
import game.heroes.Hero
import GREEN2
import RED1
import RED2
import RESET
import WHITE
import YELLOW2
import kotlin.math.roundToInt

class Golem(name: String = "Golem", maxHp: Double = 250.0) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    fun smash(target: Hero) {
        val dmgAmnt = 50 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        println("                         $WHITE>>>$RESET $BOLD$RED2$name$RESET deals $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET to $BLUE2$BOLD${target.name}$RESET with $BOLD${RED1}Smash$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("                                 $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        deathCheck(target)
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= 30 * dmgMod / it.tenacity }
        println("                 $WHITE>>>$RESET $BOLD$RED2$name$RESET deals $YELLOW2${targets.map { (20 * dmgMod / it.tenacity).roundToInt() }} dmg$RESET to the heroes $BOLD${BLUE2}${targets.map { it.name }}$RESET with $BOLD${RED1}Ground Slam$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println(
            "                                $WHITE>>>$RESET $BOLD$BLUE2${targets.map { it.name }}$RESET now ${
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
        println("           $WHITE>>>$RESET The $BOLD$RED2$name$RESET is $BOLD${RED1}taunting$RESET the $BOLD${BLUE2}heroes$RESET, forcing them to attack $BOLD${RED2}him$RESET for the next ${GREEN2}2 turns$RESET $WHITE<<<$RESET")
    }

    override fun toString(): String {
        return "$BOLD$RED2$name$RESET $WHITE($RESET$GREEN2${hp.roundToInt()} hp$RESET$WHITE)$RESET"
    }
}