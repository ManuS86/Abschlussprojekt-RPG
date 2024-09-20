import game.Game
import game.consumables.Inventory
import game.enemies.Necromancer
import game.heroes.Cleric
import game.heroes.Mage
import game.heroes.Warrior

fun main() {
    val game = Game(
        listOf(
            Cleric(),
            Mage(),
            Warrior()
        ),
        mutableListOf(
            Necromancer()
        ),
        Inventory()
    )
    game.gameLoop()
}