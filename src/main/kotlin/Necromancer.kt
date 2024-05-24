import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 300) : Enemy(name, maxHp) {
    fun deathWave(heroes: List<Hero>) {
        heroes.forEach { it.hp -= (30 * dmgMod / it.durability).roundToInt() }
        println("$name deals ${heroes.forEach { (30 * dmgMod / it.durability).roundToInt() }} dmg to each hero with Death Wave.")
    }

    fun blight(target: Hero) {
        val dmgAmnt = (50 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmnt
        println("$name deals $dmgAmnt dmg to ${target.name} with Blight.")
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("$name drains ${target.name} for $dmgAmnt dmg and heals himself for $amntHealed with Vampiric Touch.")
    }

    fun grievousWound(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.durability).roundToInt()
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        target.cantHeal = true
        target.cantHealTimer = 2
        println("$name grievously wounds (can't heal for one turn) ${target.name} and deals $dmgAmnt dmg.")

    }

    fun bestowCurse(target: Hero) {
        if (hp > (maxHp * 0.2)) {
            hp -= (maxHp * 0.1).roundToInt()
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