package game.consumables

import game.heroes.Hero
import bold
import reset
import white
import yellow1

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
            println("${yellow1}You are out of Health Potions. Try using another action.$reset")
            return false
        }
        if (target.cantHeal) {
            println("${yellow1}The target is wounded and can't be healed currently. Try another action.$reset")
            return false
        }
        healthPotion.use(target)
        content.remove(healthPotion)
        return true
    }

    fun tryUseElixir(target: Hero): Boolean {
        val elixir = content.find { it is Elixir }
        if (elixir == null) {
            println("${yellow1}You are out of Elixirs. Try using another action.$reset")
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
        return "$white${bold}Consumables.Inventory$reset: $content"
    }
}