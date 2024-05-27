class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun healingHands(target: Hero) {
        val healAmnt = (30..40).random()
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println("   >>> $bold$blue$name$reset heals $bold$blue${target.name}$reset for $bold$green$amntHealed hp$reset with $bold${blue}Healing Hands$reset <<<")
        println()
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { it.second - it.first }
        println("   >>> $bold$blue$name$reset heals $bold${blue}all allies$reset for $green$bold$amntsHealed hp$reset with $bold${blue}Healing Wave$reset <<<")
        println()
    }

    fun dispel(target: Hero) {
        println("   >>> $bold$blue$name$reset dispelled $bold$blue${target.name}'s$reset Curse <<<")
        println()
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("   >>> $bold$blue$name$reset crippled $yellow$bold${target.name}$reset (reducing his dmg by $bold${red}10%$reset) <<<")
        println()
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp}hp)"
    }
}