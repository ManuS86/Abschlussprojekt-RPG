class Inventory(
    val content: MutableList<Potion> = mutableListOf(
        HealthPotion(),
        HealthPotion(),
        HealthPotion(),
        Elixir()
    )
) {
    fun tryUseHealthPotion(target: Hero): Boolean {
        val healthPotion = content.find { it is HealthPotion } ?: return false
        healthPotion.use(target)
        content.remove(healthPotion)
        return true
    }

    fun tryUseElixir(target: Hero): Boolean {
        val elixir = content.find { it is Elixir } ?: return false
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