class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun healingHands(target: Hero) {
        val healAmnt = (30..40).random()
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println("$green$bold$name$reset ${green}heals $bold${target.name}$reset ${green}for $bold$amntHealed$reset")
        println()
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { it.second - it.first }
        println("$green$bold$name$reset ${green}heals all allies for $bold$amntsHealed$reset$green with Healing Wave.$reset")
        println()
    }

    fun dispel(target: Hero) {
        println("$green$bold$name$reset$green dispelled $bold${target.name}'s$reset$green curse.$reset")
        println()
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("$green$bold$name$reset$green crippled $yellow$bold${target.name}$reset$green (reducing his damage by 10%)$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp}hp)"
    }
}