import kotlin.math.roundToInt

class Warrior(name: String, maxHp: Int = 100) : Hero(name, maxHp) {
    fun slam(target: Enemy) {
        val dmgAmnt = (50 * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("$name deals $dmgAmnt dmg to ${target.name} with Slam.")
    }

    fun swordSwipe(enemies: List<Enemy>) {
        enemies.forEach { it.hp -= (30 * dmgMod).roundToInt() }
        println("$name deals ${(30 * dmgMod).roundToInt()} dmg to each enemy with Sword Swipe.")
    }

    fun taunt() {
    }

    fun battleShout() {
        durability += 0.1
        println("$name made himself more durable (x10% dmg reduction) with Battle Shout.")
    }

    override fun toString(): String {
        return "$name (Warrior, ${hp}hp)"
    }
}