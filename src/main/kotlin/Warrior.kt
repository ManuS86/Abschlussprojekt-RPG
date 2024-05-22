class Warrior(name: String, hp: Int = 100) : Hero(name, hp) {
    fun slam(target: Enemy) {
        target.hp -= 50
        println("$name deals 50 damage to ${target.name} with Slam.")
    }

    fun shieldBlock() {

    }

    fun taunt(target: Enemy) {

    }

    fun battleShout() {
        damageModifier += 0.1
        println("$name buffed himself with Battleshout.")
    }
}