class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
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
        println("$blue$bold$name$reset ${blue}heals $bold${target.name}$reset ${blue}for $green$bold$amntHealed hp$reset")
        println()
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { it.second - it.first }
        println("$blue$bold$name$reset ${blue}heals all allies for $green$bold$amntsHealed hp$reset$blue with Healing Wave.$reset")
        println()
    }

    fun dispel(target: Hero) {
        println("$blue$bold$name$reset$blue dispelled $bold${target.name}'s$reset$blue curse.$reset")
        println()
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("$blue$bold$name$reset$blue crippled $yellow$bold${target.name}$reset$blue (reducing his damage by 10%)$reset")
        println()
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp}hp)"
    }
}