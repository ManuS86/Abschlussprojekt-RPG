class Game(val heroes: List<Hero>, val enemies: MutableList<Enemy>, val inventory: Inventory) {
    val boss = enemies.find { it is Necromancer }!!

    fun gameLoop() {
        println("The heroes ${heroes[0]}, ${heroes[1]} and ${heroes[2]} are fighting the boss ${boss}.")
    }

    fun round() {
    //if (heroes.find { it is Hero is cursed } != null)


    }
}