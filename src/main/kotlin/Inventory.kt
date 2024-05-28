class Inventory(
    private val content: MutableList<Potion> = mutableListOf(
        HealthPotion(),
        HealthPotion(),
        HealthPotion(),
        Elixir()
    )
) {

    fun tryUseHealthPotion(target: Hero): Boolean {
        val healthPotion = content.find { it is HealthPotion }
        if (healthPotion == null) {
            println("${red1}You are out of Health Potions. Try using another action.$reset")
            return false
        }
        if (target.cantHeal) {
            println("${red1}The target is grievously wounded and can't be healed currently. Try another action.$reset")
            return false
        }
        healthPotion.use(target)
        content.remove(healthPotion)
        return true
    }

    fun tryUseElixir(target: Hero): Boolean {
        val elixir = content.find { it is Elixir }
        if (elixir == null) {
            println("${red1}You are out of Elixirs. Try using another action.$reset")
            return false
        }
        elixir.use(target)
        content.remove(elixir)
        return true
    }

    fun size(): Int {
        return content.size
    }

    override fun toString(): String {
        return "Inventory: $content"
    }
}