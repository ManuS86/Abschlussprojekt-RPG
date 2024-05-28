class Elixir : Potion() {
    private val white = "\u001B[97m"
    private val green1 = "\u001B[32m"
    private val green2 = "\u001B[92m"
    private val blue = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    override fun use(target: Hero) {
        target.dmgMod += 0.1
        println("   $white>>>$reset $bold$blue${target.name}$reset drinks an $bold${green1}Elixir$reset to increase their dmg by ${green2}+10%$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "Elixir"
    }
}