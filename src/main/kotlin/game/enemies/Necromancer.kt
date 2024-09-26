package game.enemies

import BLUE2
import BOLD
import game.heroes.Hero
import GREEN2
import RED1
import RED2
import RESET
import WHITE
import YELLOW1
import YELLOW2
import kotlin.math.roundToInt

class Necromancer(name: String = "Acheron", maxHp: Double = 500.0) : Enemy(name, maxHp) {

    fun deathWave(targets: List<Hero>) {
        targets.forEach { it.hp -= 40 * dmgMod / it.tenacity }
        println("                     $WHITE>>>$RESET $BOLD$RED2$name$RESET deals $YELLOW2${(40 * dmgMod).roundToInt()} dmg$RESET to the heroes $BOLD${BLUE2}${targets.map { it.name }}$RESET with $BOLD${RED1}Death Wave$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println(
            "                                  $WHITE>>>$RESET $BOLD$BLUE2${targets.map { it.name }}$RESET now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $GREEN2${targets.map { it.hp.roundToInt() }} hp$RESET $WHITE<<<$RESET"
        )
        deathCheckAoE(targets)
    }

    fun blight(target: Hero) {
        val dmgAmnt = 60 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        println("                                     $WHITE>>>$RESET $BOLD$RED2$name$RESET deals $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET to $BOLD$BLUE2${target.name}$RESET with $BOLD${RED1}Blight$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("                                                $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        deathCheck(target)
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = 30 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("                      $WHITE>>>$RESET $BOLD$RED2$name$RESET drains $BOLD$BLUE2${target.name}$RESET for $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET and heals $BOLD${RED2}himself$RESET for $GREEN2${amntHealed.roundToInt()} hp$RESET with $BOLD${RED1}Vampiric Touch$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("                                      $WHITE>>>$RESET $BOLD$RED2$name$RESET now has $GREEN2$hp hp$RESET and $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        deathCheck(target)
    }

    fun grievousWounds(target: Hero) {
        val dmgAmnt = 40 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        target.cantHeal = true
        target.cantHealTimer = 2
        println("                 $WHITE>>>$RESET $BOLD$RED2$name$RESET wounds $BOLD$BLUE2${target.name}$RESET (${YELLOW1}can't heal for 1 turn$RESET) and deals $YELLOW2${dmgAmnt.roundToInt()} dmg$RESET with $BOLD${RED1}Grievous Wounds$RESET $WHITE<<<$RESET")
        Thread.sleep(200)
        println("                                                $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        deathCheck(target)
    }

    fun bestowCurse(target: Hero) {
        if (target.hp > (target.maxHp * 0.2)) {
            target.hp -= target.maxHp * 0.1
            println("              $WHITE>>>$RESET $BOLD$RED2$name$RESET casts $BOLD${RED1}Bestow Curse$RESET on $BLUE2$BOLD${target.name}$RESET and they lose ${YELLOW2}10%$RESET ($YELLOW2${(target.maxHp * 0.1).roundToInt()}$RESET) of their max. health  $WHITE<<<$RESET")
            Thread.sleep(200)
            println("                                                $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem()
        enemies.add(golem)
        println("                                      $WHITE>>>$RESET $BOLD$RED2$name$RESET has summoned a $BOLD$RED2$golem$RESET $WHITE<<<$RESET")
    }

    override fun toString(): String {
        return "$BOLD$RED2$name$RESET $WHITE($RESET${RED1}Enemies.Necromancer$RESET, $GREEN2${hp.roundToInt()} hp$RESET$WHITE)$RESET"
    }
}