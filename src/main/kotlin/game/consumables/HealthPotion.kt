package game.consumables

import BLUE2
import BOLD
import game.heroes.Hero
import GREEN1
import GREEN2
import RESET
import WHITE
import kotlin.math.roundToInt

class HealthPotion : Potion() {

    override fun use(target: Hero) {
        val healAmnt = target.maxHp * 0.5
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = target.hp - preHealHp
        println("   $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET drinks a $BOLD${GREEN1}Health Potion$RESET to heal for $GREEN2${amntHealed.roundToInt()} hp$RESET. $WHITE<<<$RESET")
        println("               $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET now has $GREEN2${target.hp.roundToInt()} hp$RESET $WHITE<<<$RESET")
    }

    override fun toString(): String {
        return "$BOLD${GREEN1}Health Potion$RESET"
    }
}