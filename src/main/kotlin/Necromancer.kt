import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 500) : Enemy(name, maxHp) {
    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun deathWave(heroes: List<Hero>) {
        heroes.forEach { it.hp -= (30 * dmgMod / it.tenacity).roundToInt() }
        println("   >>> $bold$yellow$name$reset deals $bold$red${heroes.forEach { (30 * dmgMod / it.tenacity).roundToInt() }} dmg$reset to each hero with Death Wave <<<")
    }

    fun blight(target: Hero) {
        val dmgAmnt = (50 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("   >>> $bold$yellow$name$reset deals $bold$red$dmgAmnt dmg$reset to $bold$blue${target.name}$reset with $bold${yellow}Blight$reset <<<")
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("   >>> $bold$yellow$name$reset drains $bold$blue${target.name}$reset for $bold$red$dmgAmnt dmg$reset and heals $bold${yellow}himself$reset for $bold$green$amntHealed$reset with $bold${yellow}Vampiric Touch$reset <<<")
    }

    fun grievousWounds(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        target.cantHeal = true
        target.cantHealTimer = 2
        println("   >>> $bold$yellow$name$reset grievously wounds (can't heal for one turn) $bold$blue${target.name}$reset and deals $bold$red$dmgAmnt dmg$reset <<<")

    }

    fun bestowCurse(target: Hero) {
        if (hp > (maxHp * 0.2)) {
            hp -= (maxHp * 0.1).roundToInt()
            println("   >>> $bold$yellow$name$reset casts $bold${yellow}Bestow Curse$reset on $blue$bold$target$reset and they lose $bold${red}10%$reset of their max. health <<<")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
        enemies.add(golem)
        println("   >>> $bold$yellow$name$reset has summoned a $bold$yellow$golem$reset <<<")
    }

    override fun toString(): String {
        return "$name (Necromancer, ${hp}hp)"
    }
}