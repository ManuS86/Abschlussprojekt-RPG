import kotlin.math.roundToInt

class Mage(name: String, maxHp: Int = 80) : Hero(name, maxHp) {

    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= ((35..45).random() * dmgMod).roundToInt() }
        println("    $white>>>$reset $bold$blue2$name$reset deals $red2${(40 * dmgMod).roundToInt()} dmg$reset to $bold${yellow2}each enemy$reset with $bold${blue1}Fireball$reset $white<<<$reset")
        println()
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = ((50..60).random() * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("    $white>>>$reset $bold$blue2$name$reset deals $red2$dmgAmnt dmg$reset to $bold$yellow2${target.name}$reset with $bold${blue1}Lightning Bolt$reset $white<<<$reset")
        println()
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val dmgAmnt1 = ((20..35).random() * dmgMod).roundToInt()
        val target1 = targets.filter { it.hp > 0 }.random()
        target1.hp -= dmgAmnt1
        val dmgAmnt2 = ((20..35).random() * dmgMod).roundToInt()
        val target2 = targets.filter { it.hp > 0 }.random()
        target2.hp -= dmgAmnt2
        println("    $white>>>$reset $bold$blue2$name$reset deals $red2$dmgAmnt1 dmg$reset to $yellow2$bold${target1.name}$reset and $red2$dmgAmnt2 dmg$reset to $yellow2$bold${target2.name}$reset with $blue1${bold}Magic Missile$reset $white<<<$reset")
        println()
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = (30 * dmgMod).roundToInt()
            target.hp -= dmgAmnt
            target.burning = true
            println("    $white>>>$reset $bold$blue2$name$reset deals $red2$dmgAmnt dmg$reset to $yellow2$bold${target.name}$reset with $bold${blue1}Burn$reset and sets them on fire $white<<<$reset")
            println()
        }
    }

    override fun toString(): String {
        return "$name (Mage, ${hp} hp)"
    }
}