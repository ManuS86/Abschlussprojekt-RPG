package game.enemies

import game.heroes.Hero
import blue2
import bold
import green2
import red1
import red2
import reset
import white
import yellow2
import kotlin.math.roundToInt

class Golem(name: String = "Enemies.Golem", maxHp: Double = 250.0) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    fun smash(target: Hero) {
        val dmgAmnt = 50 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        println("                         $white>>>$reset $bold$red2$name$reset deals $yellow2${dmgAmnt.roundToInt()} dmg$reset to $blue2$bold${target.name}$reset with $bold${red1}Smash$reset $white<<<$reset")
        Thread.sleep(200)
        println("                                 $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= 30 * dmgMod / it.tenacity }
        println("                 $white>>>$reset $bold$red2$name$reset deals $yellow2${targets.map { (20 * dmgMod / it.tenacity).roundToInt() }} dmg$reset to the heroes $bold${blue2}${targets.map { it.name }}$reset with $bold${red1}Ground Slam$reset $white<<<$reset")
        Thread.sleep(200)
        println(
            "                                $white>>>$reset $bold$blue2${targets.map { it.name }}$reset now ${
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
        println("           $white>>>$reset The $bold$red2$name$reset is $bold${red1}taunting$reset the $bold${blue2}heroes$reset, forcing them to attack $bold${red2}him$reset for the next ${green2}2 turns$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold$red2$name$reset $white($reset$green2${hp.roundToInt()} hp$reset$white)$reset"
    }
}