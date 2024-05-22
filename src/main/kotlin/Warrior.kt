class Warrior(name: String, maxHp: Int = 100) : Hero(name, maxHp) {
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

    override fun toString(): String {
        return "${name} (Warrior, ${hp}hp)"
    }
}