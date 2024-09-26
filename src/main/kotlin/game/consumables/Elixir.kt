package game.consumables

import BLUE2
import BOLD
import game.heroes.Hero
import GREEN1
import GREEN2
import RESET
import WHITE

class Elixir : Potion() {

    override fun use(target: Hero) {
        target.skillMod += 0.1
        println("   $WHITE>>>$RESET $BOLD$BLUE2${target.name}$RESET drinks an $BOLD${GREEN1}Elixir$RESET to increase their ability effects by ${GREEN2}+10%$RESET $WHITE<<<$RESET")
    }

    override fun toString(): String {
        return "$BOLD${GREEN1}Elixir$RESET"
    }
}