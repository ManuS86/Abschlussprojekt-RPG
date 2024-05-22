import kotlin.math.roundToInt

class Necromancer(name: String, hp: Int = 200) : Enemy(name, hp) {
    fun firebreath() {

    }

    fun tailswipe() {

    }

    fun claw() {

    }

    fun bite() {

    }

    fun curse(target: Hero, heroes: MutableList<Hero>) {
        val cursedCheck =
            if (hp > maxHp * 0.2 &&) {
                hp -= (maxHp * 0.1).roundToInt()
                target.cursed = true
            } else {
                // redo action
            }
    }

    fun summonGolem(enemies: MutableList<Enemy>, golem: Golem) {
        if (enemies.size < 2) {
            enemies.add(golem)
            println("${name} has summoned a Golem with ${golem.maxHp}.")
        }
    }
}