class Game(val heroes: List<Hero>, val enemies: MutableList<Enemy>, val inventory: Inventory) {
    val boss = enemies.find { it is Necromancer }!!

    fun gameLoop() {
        println("The hero party of ${heroes[0]}, ${heroes[1]}, ${heroes[2]} is fighting the ${boss}.")
    }

    fun round() {

    }
}