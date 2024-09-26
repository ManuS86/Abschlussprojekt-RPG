package game.enemies

import BLUE2
import BOLD
import game.heroes.Hero
import RESET
import WHITE
import kotlin.math.min

abstract class Enemy(var name: String, val maxHp: Double) {
    var hp = maxHp
    var dmgMod = 1.0
    var burning = false

    fun heal(healAmount: Double) {
        hp = min(hp + healAmount, maxHp)
    }

    fun deathCheck(target: Hero) {
        if (target.hp <= 0) {
            println("                     $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET is dead. $WHITE<<<$RESET")
        }
    }

    fun deathCheckAoE(targets: List<Hero>) {
        if (targets.any { it.hp <= 0 }) {
            println(
                "            $WHITE>>>$RESET $BOLD${BLUE2}2${
                    targets.filter { it.hp <= 0 }.map { it.name }
                }$RESET ${
                    if (targets.filter { it.hp <= 0 }.size == 1) {
                        "is"
                    } else {
                        "are"
                    }
                } dead. $WHITE<<<$RESET"
            )
        }
    }
}
