package game.consumables

import game.heroes.Hero
import blue2
import bold
import green1
import green2
import reset
import white
import kotlin.math.roundToInt

class HealthPotion : Potion() {

    override fun use(target: Hero) {
        val healAmount = target.maxHp * 0.5
        val preHealHp = target.hp
        target.heal(healAmount)
        val amountHealed = target.hp - preHealHp
        println("   $white>>>$reset $bold$blue2${target.name}$reset drinks a $bold${green1}Health Consumables.Potion$reset to heal for $green2${amountHealed.roundToInt()} hp$reset. $white<<<$reset")
        println("               $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold${green1}Health Consumables.Potion$reset"
    }
}