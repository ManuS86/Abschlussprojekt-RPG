class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    private val white = "\u001B[97m"
    private val red = "\u001B[91m"
    private val green = "\u001B[92m"
    private val yellow = "\u001B[93m"
    private val blue1 = "\u001B[34m"
    private val blue2 = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun healingHands(target: Hero) {
        val healAmnt = (30..40).random()
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println("   $white>>>$reset $bold$blue2$name$reset heals $bold$blue2${target.name}$reset for $green$amntHealed hp$reset with $bold${blue1}Healing Hands$reset $white<<<$reset")
        println()
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { it.second - it.first }
        println("   $white>>>$reset $bold$blue2$name$reset heals $bold${blue2}all allies$reset for $green$amntsHealed hp$reset with $bold${blue1}Healing Wave$reset $white<<<$reset")
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
        println("   $white>>>$reset $bold$blue2$name$reset reduced $yellow$bold${target.name}'s$reset dmg by ${red}10%$reset with $bold${blue1}Cripple$reset $white<<<$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp} hp)"
    }
}