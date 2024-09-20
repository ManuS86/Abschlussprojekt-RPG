package game.consumables

import game.heroes.Hero

abstract class Potion {
    open fun use(target: Hero) {
    }
}