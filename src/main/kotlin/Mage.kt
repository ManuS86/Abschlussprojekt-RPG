class Mage(name: String, hp: Int = 70) : Hero(name, hp) {
    fun fireball(targets: MutableList<Enemy>) {
        targets.forEach { it.currentHp -= 40 }
        println("$name deals 40 damage with Fireball to each enemy.")
    }

    fun lightningBolt(target: Enemy) {
        target.currentHp -= 60
        println("$name deals 60 damage with Lightning Bolt to ${target.name}.")
    }

    fun magicMissile(targets: MutableList<Enemy>) {
        val missile1 = (20..30).random()
        var target1 = targets.random()
        target1.currentHp -= missile1
        val missile2 = (20..30).random()
        var target2 = targets.random()
        target2.currentHp -= missile2
        println("$name deals $missile1 to ${target1.name} and $missile2 to ${target2.name} with Magic Missile.")
    }

    fun burn(target: Enemy) {
        if (!target.burning) {
            target.currentHp -= 20
            target.burning = true
        }
    }
}