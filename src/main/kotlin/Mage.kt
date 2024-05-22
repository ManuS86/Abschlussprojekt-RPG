class Mage(name: String, maxHp: Int = 70) : Hero(name, maxHp) {
    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.hp -= 40 }
        println("$name deals 40 damage to each enemy with Fireball.")
    }

    fun lightningBolt(target: Enemy) {
        target.hp -= 60
        println("$name deals 60 damage to ${target.name} with Lightning Bolt.")
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val missile1 = (20..45).random()
        var target1 = targets.random()
        target1.hp -= missile1
        val missile2 = (20..45).random()
        var target2 = targets.random()
        target2.hp -= missile2
        println("$name deals $missile1 to ${target1.name} and $missile2 to ${target2.name} with Magic Missile.")
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            target.hp -= 20
            target.burning = true
        }
    }

    override fun toString(): String {
        return "${name} (Mage, ${hp}hp)"
    }
}