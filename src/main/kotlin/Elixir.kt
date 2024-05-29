class Elixir : Potion() {

    override fun use(target: Hero) {
        target.skillMod += 0.1
        println("   $white>>>$reset $bold$blue2${target.name}$reset drinks an $bold${green1}Elixir$reset to increase their dmg by ${green2}+10%$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold${green1}Elixir$reset"
    }
}