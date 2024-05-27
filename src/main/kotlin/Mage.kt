import kotlin.math.roundToInt

class Mage(name: String, maxHp: Int = 70) : Hero(name, maxHp) {
    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= ((35..45).random() * dmgMod).roundToInt() }
        println("$blue$bold$name$reset$blue deals $red${(40 * dmgMod).roundToInt()} dmg$reset$blue to $bold${yellow}each enemy$reset$blue with ${bold}Fireball$reset$blue.$reset")
        println()
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = ((50..60).random() * dmgMod).roundToInt()
        target.hp -= dmgAmnt
        println("$blue$bold$name$reset$blue deals $red$bold$dmgAmnt dmg$reset$blue to $bold$yellow${target.name}$reset$blue with ${bold}Lightning Bolt$reset$blue.$reset")
        println()
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val dmgAmnt1 = ((20..35).random() * dmgMod).roundToInt()
        val target1 = targets.random()
        target1.hp -= dmgAmnt1
        val dmgAmnt2 = ((20..35).random() * dmgMod).roundToInt()
        val target2 = targets.random()
        target2.hp -= dmgAmnt2
        println("$blue$bold$name$reset$blue deals $red$bold$dmgAmnt1 dmg$reset$blue to $yellow$bold${target1.name}$reset$blue and $red$bold$dmgAmnt2 dmg$reset to $yellow$bold${target2.name}$reset$blue with ${bold}Magic Missile$reset$blue.$reset")
        println()
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = (30 * dmgMod).roundToInt()
            target.hp -= dmgAmnt
            target.burning = true
            println("$blue$bold$name$reset$blue deals $red$bold$dmgAmnt dmg${reset}$blue to $yellow$bold${target.name}$reset$blue with ${bold}Burn$reset$blue and sets them on fire.$reset")
            println()
        }
    }

    override fun toString(): String {
        return "$name (Mage, ${hp}hp)"
    }
}