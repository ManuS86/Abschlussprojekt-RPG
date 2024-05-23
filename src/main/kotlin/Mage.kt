import kotlin.math.roundToInt

class Mage(name: String, maxHp: Int = 70) : Hero(name, maxHp) {
    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= (40 * dmgMod).roundToInt() }
        println("$name deals ${(40 * dmgMod).roundToInt()} dmg to each enemy with Fireball.")
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmount = (60 * dmgMod).roundToInt()
        target.hp -= dmgAmount
        println("$name deals $dmgAmount dmg to ${target.name} with Lightning Bolt.")
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val missile1 = (20..45).random()
        val dmgAmount1 = (missile1 * dmgMod).roundToInt()
        val target1 = targets.random()
        target1.hp -= dmgAmount1
        val missile2 = (20..45).random()
        val dmgAmount2 = (missile2 * dmgMod).roundToInt()
        val target2 = targets.random()
        target2.hp -= dmgAmount2
        println("$name deals $dmgAmount1 to ${target1.name} and $dmgAmount2 to ${target2.name} with Magic Missile.")
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            val dmgAmount = (20 * dmgMod).roundToInt()
            target.hp -= dmgAmount
            target.burning = true
            println("$name deals $dmgAmount dmg to ${target.name} with Burn and sets it on fire.")
        }
    }

    override fun toString(): String {
        return "$name (Mage, ${hp}hp)"
    }
}