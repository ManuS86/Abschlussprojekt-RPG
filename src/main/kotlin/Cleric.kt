class Cleric(name: String, maxHp: Int = 90) : Hero(name, maxHp) {

    fun healingHands(target: Hero) {
        val healAmnt = (30..40).random()
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println("   $white>>>$reset $bold$blue2$name$reset heals $bold$blue2${target.name}$reset for $green2$amntHealed hp$reset with $bold${blue1}Healing Hands$reset $white<<<$reset")
        println()
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { it.second - it.first }
        println("   $white>>>$reset $bold$blue2$name$reset heals $bold${blue2}all allies$reset for $green2$amntsHealed hp$reset with $bold${blue1}Healing Wave$reset $white<<<$reset")
        println()
    }

    fun dispel(target: Hero) {
        println("   $white>>>$reset $bold$blue2$name$reset removed $bold$blue2${target.name}'s$reset curse with $bold${blue1}Dispel$reset $white<<<$reset")
        println()
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("   $white>>>$reset $bold$blue2$name$reset reduced $yellow2$bold${target.name}'s$reset dmg by ${red2}10%$reset with $bold${blue1}Cripple$reset $white<<<$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp} hp)"
    }
}