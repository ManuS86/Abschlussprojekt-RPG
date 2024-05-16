class Elixir : Potion() {
    fun useElixir(target: Hero) {
        target.damageModifier += 0.1
    }
}