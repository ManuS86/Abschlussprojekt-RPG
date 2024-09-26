package game.heroes

import BLUE1
import BLUE2
import BOLD
import game.enemies.Enemy
import GREEN2
import RED2
import RESET
import WHITE
import YELLOW1
import YELLOW2
import kotlin.math.roundToInt

class Cleric(name: String = "Elara", maxHp: Double = 90.0) : Hero(name, maxHp) {

    fun healingHands(target: Hero) {
        val healAmnt = (35..45).random() * skillMod
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println(
            "    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET heals $BOLD$BLUE2${
                if (target.name == name) {
                    "herself"
                } else {
                    target.name
                }
            }$RESET for $GREEN2${amntHealed.roundToInt()} hp$RESET with $BOLD${BLUE1}Healing Hands$RESET $WHITE<<<$RESET"
        )
        Thread.sleep(200)
        println("               $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (25..35).random() * skillMod
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { (it.second - it.first).roundToInt() }
        println("   $WHITE>>>$RESET $BOLD$BLUE2$name$RESET heals all allies $BOLD$BLUE2${targets.map { it.name }}$RESET for $GREEN2$amntsHealed hp$RESET with $BOLD${BLUE1}Healing Wave$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("                   $WHITE>>>$RESET $BOLD$BLUE2${targets.map { it.name }}$RESET now have $GREEN2${targets.map { it.hp.roundToInt() }} hp$RESET $WHITE<<<$RESET")
    }

    fun dispel(target: Hero) {
        target.cantHeal = false
        target.cantHealTimer = 0
        println(
            "    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET removed all of $BOLD$BLUE2${
                if (target.name == name) {
                    "her"
                } else {
                    "${target.name}'s"
                }
            }$RESET ${YELLOW1}negative effects$RESET with $BOLD${BLUE1}Dispel$RESET $WHITE<<<$RESET"
        )
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("    $WHITE>>>$RESET $BOLD$BLUE2$name$RESET reduced $RED2$BOLD${target.name}'s$RESET dmg by ${YELLOW2}10%$RESET with $BOLD${BLUE1}Cripple$RESET $WHITE<<<$RESET")
    }

    override fun toString(): String {
        return "$BOLD$BLUE2$name$RESET $WHITE($RESET${BLUE1}Cleric$RESET, $GREEN2${hp.roundToInt()} hp$RESET$WHITE)$RESET"
    }
}