class Elixir : Potion() {
    override fun use(target: Hero) {
        target.dmgMod += 0.1
        println("${target.name} drinks an Elixir to increase their dmg by +10%")
    }

    override fun toString(): String {
        return "Elixir"
    }
}