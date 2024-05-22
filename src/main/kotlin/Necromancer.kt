import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 200) : Enemy(name, maxHp) {
    var curseActive = false

    fun firebreath() {

    }

    fun tailswipe() {

    }

    fun claw() {

    }

    fun bite() {

    }

    fun curse(target: Hero): Boolean {
        if (curseActive) return false
        if (hp > maxHp * 0.2) {
            hp -= (maxHp * 0.1).roundToInt()
            target.cursed = true
            curseActive = true
        }
        return true
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
            enemies.add(golem)
            println("${name} has summoned $golem.")
    }
    override fun toString(): String {
        return "Necromancer boss ${name} with ${maxHp} HP"
    }
}