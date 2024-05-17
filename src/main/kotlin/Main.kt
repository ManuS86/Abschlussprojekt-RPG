fun main() {
    val hero1 = Cleric("Elara")
    val hero2 = Mage("Keros")
    val hero3 = Warrior("Harkon")
    val heroes = mutableListOf(hero1, hero2, hero3)

    val boss = Necromancer("Acheron")
    val golem = Golem("Golem")
    val enemies: MutableList<Enemy> = mutableListOf(boss)

    println("The hero party of ${heroes[0].name}, ${heroes[1].name}, ${heroes[2].name} is fighting the Necoramancer boss ${boss.name}.")
}