fun main() {
    val game = Game(
        listOf(
            Cleric("Elara"),
            Mage("Keros"),
            Warrior("Haarkon")
        ),
        mutableListOf(
            Necromancer("Acheron")
        ),
        Inventory()
    )
    game.gameLoop()
}