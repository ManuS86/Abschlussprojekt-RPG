import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    fun deathWave(heroes: List<Hero>) {
        heroes.forEach { it.hp -= (30 * dmgMod / it.durability).roundToInt() }
        println("$name deals ${heroes.forEach { (30 * dmgMod / it.durability).roundToInt() }} dmg to each hero with Death Wave.")
    }

    fun blight(target: Hero) {
        val dmgAmount = (50 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmount
        println("$name deals $dmgAmount dmg to ${target.name} with Blight.")
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmount = (30 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmount
        val preHealHp = hp
        heal((30 * dmgMod / target.durability).roundToInt())
        val amountHealed = hp - preHealHp
        println("$name drains ${target.name} for $dmgAmount dmg and heals himself for $amountHealed with Vampiric Touch.")
    }

    fun bite() {
    }

    fun bestowCurse(target: Hero) {
        if (hp > (maxHp * 0.2)) {
            hp -= (maxHp * 0.1).roundToInt()
            target.cursed = true
            println("$name casts Bestow Curse on $target and they lose 10% of their max. health.")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
        enemies.add(golem)
        println("$name has summoned a $golem.")
    }

    override fun toString(): String {
        return "$name (Necromancer, ${hp}hp)"
    }
}