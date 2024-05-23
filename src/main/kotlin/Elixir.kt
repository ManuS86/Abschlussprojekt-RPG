class Elixir : Potion() {
    override fun use(target: Hero) {
        target.dmgModifier += 0.1
        println("${target.name} drank an Elixir to increase their dmg by 10%")
    }
}