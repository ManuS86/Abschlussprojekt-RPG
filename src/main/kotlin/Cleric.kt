class Cleric(name: String, maxHp: Int = 80) : Hero(name, maxHp) {
    fun healingHands(target: Hero) {
        val heal = (30..40).random()
        target.hp += heal
        println("$name heals ${target.name} for $heal")
    }

    fun healingWave(targets: MutableList<Hero>) {
        val heal = (20..30).random()
        targets.forEach { it.hp += heal }
        println("$name heals each ally for $heal with Healing Wave.")
    }

    fun dispel(target: Hero) {
        target.cursed = false
        println("$name dispelled ${target.name}'s curse.")
    }

    fun cripple(target: Enemy) {
        if (target.damageModifier > 0.1) {
            target.damageModifier -= 0.1
        }
        println("$name crippled ${target.name}")
    }

    override fun toString(): String {
        return "${name} (Cleric, ${hp}hp)"
    }
}