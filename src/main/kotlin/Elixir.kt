class Elixir : Potion() {
    override fun use(target: Hero) {
        target.damageModifier += 0.1
    }
}