class Necromancer(name: String, hp: Int) : Enemy(name, hp) {

    fun firebreath() {

    }

    fun tailswipe() {

    }

    fun claw() {

    }

    fun curse() {

    }

    fun bite() {

    }

    fun summonGolem(enemies: MutableList<Enemy>, golem: Golem) {
    if (enemies.size < 2){
        enemies.add(golem)
        println("${name} has summoned a Golem.")
    }

    }
}