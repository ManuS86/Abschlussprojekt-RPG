class Elixir : Potion() {
    override fun use(target: Hero) {
        target.damageModifier += 0.1
        println("${target.name} drank an Elixir to increase their damage by 10%")
    }
}