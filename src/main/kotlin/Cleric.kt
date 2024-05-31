import kotlin.math.roundToInt

class Cleric(name: String = "Elara", maxHp: Double = 90.0) : Hero(name, maxHp) {

    fun healingHands(target: Hero) {
        val healAmnt = (35..45).random() * skillMod
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println(
            "    $white>>>$reset $bold$blue2$name$reset heals $bold$blue2${
                if (target.name == name) {
                    "herself"
                } else {
                    target.name
                }
            }$reset for $green2${amntHealed.roundToInt()} hp$reset with $bold${blue1}Healing Hands$reset $white<<<$reset"
        )
        Thread.sleep(200)
        println("               $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (25..35).random() * skillMod
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { (it.second - it.first).roundToInt() }
        println("   $white>>>$reset $bold$blue2$name$reset heals all allies $bold$blue2${targets.map { it.name }}$reset for $green2$amntsHealed hp$reset with $bold${blue1}Healing Wave$reset $white<<<$reset")
        Thread.sleep(200)
        println("                   $white>>>$reset $bold$blue2${targets.map { it.name }}$reset now have $green2${targets.map { it.hp.roundToInt() }} hp$reset $white<<<$reset")
    }

    fun dispel(target: Hero) {
        target.cantHeal = false
        target.cantHealTimer = 0
        println(
            "    $white>>>$reset $bold$blue2$name$reset removed all of $bold$blue2${
                if (target.name == name) {
                    "her"
                } else {
                    "${target.name}'s"
                }
            }$reset ${yellow1}negative effects$reset with $bold${blue1}Dispel$reset $white<<<$reset"
        )
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("    $white>>>$reset $bold$blue2$name$reset reduced $red2$bold${target.name}'s$reset dmg by ${yellow2}10%$reset with $bold${blue1}Cripple$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold$blue2$name$reset $white($reset${blue1}Cleric$reset, $green2${hp.roundToInt()} hp$reset$white)$reset"
    }
}