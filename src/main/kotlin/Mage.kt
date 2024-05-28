import kotlin.math.roundToInt

class Mage(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    private val white = "\u001B[97m"
    private val red = "\u001B[91m"
    private val yellow = "\u001B[93m"
    private val blue1 = "\u001B[34m"
    private val blue2 = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= ((35..45).random() * dmgMod).roundToInt() }
        println("   $white>>>$reset $bold$blue2$name$reset deals $red${(40 * dmgMod).roundToInt()} dmg$reset to $bold${yellow}each enemy$reset with $bold${blue1}Fireball$reset $white<<<$reset")
        println()
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = ((50..60).random() * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("   $white>>>$reset $bold$blue2$name$reset deals $red$dmgAmnt dmg$reset to $bold$yellow${target.name}$reset with $bold${blue1}Lightning Bolt$reset $white<<<$reset")
        println()
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val dmgAmnt1 = ((20..35).random() * dmgMod).roundToInt()
        val target1 = targets.filter { it.hp > 0 }.random()
        target1.hp -= dmgAmnt1
        val dmgAmnt2 = ((20..35).random() * dmgMod).roundToInt()
        val target2 = targets.filter { it.hp > 0 }.random()
        target2.hp -= dmgAmnt2
        println("   $white>>>$reset $bold$blue2$name$reset deals $red$dmgAmnt1 dmg$reset to $yellow$bold${target1.name}$reset and $red$dmgAmnt2 dmg$reset to $yellow$bold${target2.name}$reset with $blue1${bold}Magic Missile$reset $white<<<$reset")
        println()
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = (30 * dmgMod).roundToInt()
            target.hp -= dmgAmnt
            target.burning = true
            println("   $white>>>$reset $bold$blue2$name$reset deals $red$dmgAmnt dmg$reset to $yellow$bold${target.name}$reset with $bold${blue1}Burn$reset and sets them on fire $white<<<$reset")
            println()
        }
    }

    override fun toString(): String {
        return "$name (Mage, ${hp} hp)"
    }
}