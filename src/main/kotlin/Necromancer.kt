class Necromancer(name: String, hp: Int = 200) : Enemy(name, hp) {

    fun firebreath() {

    }

    fun tailswipe() {

    }

    fun claw() {

    }

    fun curse(target: Hero, heroes: MutableList<Hero>) {
        val cursedCheck =
        if (currentHp > hp * 0.2 && ) {
            currentHp -= (hp * 0.1).toInt()
            target.cursed = true
        }
    }

    fun bite() {

    }

    fun summonGolem(enemies: MutableList<Enemy>, golem: Golem) {
    if (enemies.size < 2){
        enemies.add(golem)
        println("${name} has summoned a Golem with ${golem.hp}.")
    }

    }
}