package game.consumables

import game.heroes.Hero
import blue2
import bold
import green1
import green2
import reset
import white

class Elixir : Potion() {

    override fun use(target: Hero) {
        target.skillMod += 0.1
        println("   $white>>>$reset $bold$blue2${target.name}$reset drinks an $bold${green1}Consumables.Elixir$reset to increase their ability effects by ${green2}+10%$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold${green1}Consumables.Elixir$reset"
    }
}