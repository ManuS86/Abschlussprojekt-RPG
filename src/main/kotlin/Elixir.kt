class Elixir : Potion() {
    private val green = "\u001B[32m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    override fun use(target: Hero) {
        target.dmgMod += 0.1
        println("   >>> $bold$blue${target.name}$reset drinks an $bold${blue}Elixir$reset to increase their $bold${green}dmg by +10%$reset <<<")
    }

    override fun toString(): String {
        return "Elixir"
    }
}