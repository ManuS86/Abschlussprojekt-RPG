class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    fun healingHands(target: Hero) {
        val healAmnt = (30..40).random()
        val preHealHp = target.hp
        target.heal(healAmnt)
        val amntHealed = hp - preHealHp
        println("$name heals ${target.name} for $amntHealed")
        println()
    }

    fun healingWave(targets: List<Hero>) {
        val healAmnt = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { if (!it.cantHeal) it.heal(healAmnt) }
        val postHealHp = targets.map { it.hp }
        val amntsHealed = (preHealHp zip postHealHp).map { it.second - it.first }
        println("$name heals all allies for $amntsHealed with Healing Wave.")
        println()
    }

    fun dispel(target: Hero) {
        println("$name dispelled ${target.name}'s curse.")
        println()
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("$name crippled ${target.name} (reducing his damage by 10%)")
        println()
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp}hp)"
    }
}