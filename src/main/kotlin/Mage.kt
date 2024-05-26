import kotlin.math.roundToInt

class Mage(name: String, maxHp: Int = 70) : Hero(name, maxHp) {
    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= ((35..45).random() * dmgMod).roundToInt() }
        println("$name deals ${(40 * dmgMod).roundToInt()} dmg to each enemy with Fireball.")
        println()
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = ((50..60).random() * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("$name deals $dmgAmnt dmg to ${target.name} with Lightning Bolt.")
        println()
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val dmgAmnt1 = ((20..35).random() * dmgMod).roundToInt()
        val target1 = targets.random()
        target1.hp -= dmgAmnt1
        val dmgAmnt2 = ((20..35).random() * dmgMod).roundToInt()
        val target2 = targets.random()
        target2.hp -= dmgAmnt2
        println("$name deals $dmgAmnt1 to ${target1.name} and $dmgAmnt2 to ${target2.name} with Magic Missile.")
        println()
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = (30 * dmgMod).roundToInt()
            target.hp -= dmgAmnt
            target.burning = true
            println("$name deals $dmgAmnt dmg to ${target.name} with Burn and sets them on fire.")
            println()
        }
    }

    override fun toString(): String {
        return "$name (Mage, ${hp}hp)"
    }
}