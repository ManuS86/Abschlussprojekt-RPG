import kotlin.math.roundToInt

class Mage(name: String, maxHp: Double = 80.0) : Hero(name, maxHp) {

    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= (35..45).random() * skillMod }
        println("    $white>>>$reset $bold$blue2$name$reset deals $yellow2${(30 * skillMod).roundToInt()} dmg$reset to $bold${red2}each enemy$reset with $bold${blue1}Fireball$reset $white<<<$reset")
        println(
            "        $white>>>$reset $bold$red2${targets.map { it.name }}$reset now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $green2${targets.map { it.hp.roundToInt() }} hp$reset $white<<<$reset"
        )
        deathCheckAoe(targets)
    }

    fun lightningBolt(target: Enemy) {
        val dmgAmnt = (50..60).random() * skillMod
        target.hp -= dmgAmnt
        println("    $white>>>$reset $bold$blue2$name$reset deals $yellow2${dmgAmnt.roundToInt()} dmg$reset to $bold$red2${target.name}$reset with $bold${blue1}Lightning Bolt$reset $white<<<$reset")
        Thread.sleep(200)
        println("               $white>>>$reset $bold$red2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun magicMissiles(targets: MutableList<Enemy>) {
        val dmgAmnt1 = (20..35).random() * skillMod
        val target1 = targets.filter { it.hp > 0 }.random()
        target1.hp -= dmgAmnt1
        val dmgAmnt2 = (20..35).random() * skillMod
        val target2 = targets.filter { it.hp > 0 }.random()
        target2.hp -= dmgAmnt2
        println("    $white>>>$reset $bold$blue2$name$reset deals $yellow2${dmgAmnt1.roundToInt()} dmg$reset to $red2$bold${target1.name}$reset and $yellow2${dmgAmnt2.roundToInt()} dmg$reset to $red2$bold${target2.name}$reset with $blue1${bold}Magic Missiles$reset $white<<<$reset")
        Thread.sleep(200)
        println(
            "                          $white>>>$reset $bold$red2${targets.map { it.name }}$reset now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $green2${targets.map { it.hp.roundToInt() }} hp$reset $white<<<$reset"
        )
        deathCheckAoe(targets)
    }

    fun searingTouch(target: Enemy) {
        if (!target.burning) {
            val dmgAmnt = 30 * skillMod
            target.hp -= dmgAmnt
            target.burning = true
            println("    $white>>>$reset $bold$blue2$name$reset deals $yellow2${dmgAmnt.roundToInt()} dmg$reset to $red2$bold${target.name}$reset with $bold${blue1}Searing Touch$reset and sets them on fire $white<<<$reset")
            Thread.sleep(200)
            println("                            $white>>>$reset $bold$red2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
            deathCheck(target)
        }
    }

    override fun toString(): String {
        return "$bold$blue2$name$reset $white($reset${blue1}Mage$reset, $green2${hp.roundToInt()} hp$reset$white)$reset"
    }
}