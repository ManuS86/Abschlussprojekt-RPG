class Golem(name: String, hp: Int = 100) : Enemy(name, hp) {
    fun smash(target: Hero) {
        target.hp -= 50
        println("$name deals 50 damage to ${target.name} with Smash.")
    }

    fun groundSlam(targets: MutableList<Hero>) {
        targets.forEach { it.hp -= 30 }
        println("$name deals 30 damage to each hero with Ground Slam.")
    }

    fun taunt() {
    }
}