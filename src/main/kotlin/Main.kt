fun main() {
    val game = Game(
        listOf(
            Cleric("Elara"),
            Mage("Keros"),
            Warrior("Harkon")
        ),
        mutableListOf(
            Necromancer("Acheron")
        ),
        Inventory()
    )
    game.gameLoop()
}