import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    fun deathWave(heroes: List<Hero>) {
    heroes.forEach { it.hp -= 30 }
    }

    fun tailswipe() {

    }

    fun claw() {

    }

    fun bite() {

    }

    fun curse(target: Hero) {
        if (hp > (maxHp * 0.2)) {
            hp -= (maxHp * 0.1).roundToInt()
            target.cursed = true
            println("$target is cursed and loses 10% of their max. health.")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
        enemies.add(golem)
        println("${name} has summoned $golem.")
    }

    override fun toString(): String {
        return "${name} (Necromancer, ${hp}hp)"
    }
}