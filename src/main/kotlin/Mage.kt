import kotlin.math.roundToInt

class Mage(name: String, maxHp: Int = 70) : Hero(name, maxHp) {
    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= (40 * dmgMod).roundToInt() }
        println("$name deals ${(40 * dmgMod).roundToInt()} dmg to each enemy with Fireball.")
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = (60 * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("$name deals $dmgAmnt dmg to ${target.name} with Lightning Bolt.")
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val missile1 = (20..45).random()
        val dmgAmnt1 = (missile1 * dmgMod).roundToInt()
        val target1 = targets.random()
        target1.hp -= dmgAmnt1
        val missile2 = (20..45).random()
        val dmgAmnt2 = (missile2 * dmgMod).roundToInt()
        val target2 = targets.random()
        target2.hp -= dmgAmnt2
        println("$name deals $dmgAmnt1 to ${target1.name} and $dmgAmnt2 to ${target2.name} with Magic Missile.")
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = (20 * dmgMod).roundToInt()
            target.hp -= dmgAmnt
            target.burning = true
            println("$name deals $dmgAmnt dmg to ${target.name} with Burn and sets it on fire.")
        }
    }

    override fun toString(): String {
        return "$name (Mage, ${hp}hp)"
    }
}