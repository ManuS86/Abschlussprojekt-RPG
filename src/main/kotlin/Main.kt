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