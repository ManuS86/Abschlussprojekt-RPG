class Cleric(name: String, hp: Int) : Hero(name, hp) {
    fun heal(target: Hero) {
        val heal = (20..30).random()
        target.hp += heal
        println("Heal heals ${target.name} to $heal")
    }

    fun dispel(target: Hero) {
    target.cursed = false
    }

    fun shield(target: Hero) {
        val shield = (30..40).random()
        target.hp
        println("Shield ${target.name} for $")
    }

    fun cripple(target: Enemy) {
    target.damageModifier -= 0.1
    }
}