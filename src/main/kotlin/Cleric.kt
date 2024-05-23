class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    fun healingHands(target: Hero) {
        val healAmount = (30..40).random()
        val preHealHp = target.hp
        target.heal(healAmount)
        val amountHealed = hp - preHealHp
        println("$name heals ${target.name} for $amountHealed")
    }

    fun healingWave(targets: List<Hero>) {
        val healAmount = (20..30).random()
        val preHealHp = targets.map { it.hp }
        targets.forEach { it.heal(healAmount) }
        val postHealHp = targets.map { it.hp }
        val amountsHealed = (preHealHp zip postHealHp).map { it.second-it.first }
        println("$name heals all allies for $amountsHealed with Healing Wave.")
    }

    fun dispel(target: Hero) {
        target.cursed = false
        println("$name dispelled ${target.name}'s curse.")
    }

    fun cripple(target: Enemy) {
        if (target.dmgMod > 0.1) {
            target.dmgMod -= 0.1
        }
        println("$name crippled ${target.name} (reducing their damage by -10%)")
    }

    override fun toString(): String {
        return "$name (Cleric, ${hp}hp)"
    }
}