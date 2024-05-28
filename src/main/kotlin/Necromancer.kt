import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 500) : Enemy(name, maxHp) {
    private val white = "\u001B[97m"
    private val red = "\u001B[91m"
    private val green = "\u001B[92m"
    private val yellow1 = "\u001B[33m"
    private val yellow2 = "\u001B[93m"
    private val blue = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun deathWave(heroes: List<Hero>) {
        heroes.forEach { it.hp -= (40 * dmgMod / it.tenacity).roundToInt() }
        println("   $white>>>$reset $bold$yellow2$name$reset deals $red${heroes.forEach { (40 * dmgMod / it.tenacity).roundToInt() }} dmg$reset to each hero with $bold${yellow1}Death Wave$reset $white<<<$reset")
    }

    fun blight(target: Hero) {
        val dmgAmnt = (60 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("   $white>>>$reset $bold$yellow2$name$reset deals $red$dmgAmnt dmg$reset to $bold$blue${target.name}$reset with $bold${yellow1}Blight$reset $white<<<$reset")
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("   $white>>>$reset $bold$yellow2$name$reset drains $bold$blue${target.name}$reset for $red$dmgAmnt dmg$reset and heals $bold${yellow2}himself$reset for $green$amntHealed hp$reset with $bold${yellow1}Vampiric Touch$reset $white<<<$reset")
    }

    fun grievousWounds(target: Hero) {
        val dmgAmnt = (40 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        target.cantHeal = true
        target.cantHealTimer = 2
        println("   $white>>>$reset $bold$yellow2$name$reset $bold${yellow1}wounds$reset $bold$blue${target.name}$reset (can't heal for one turn) and deals $red$dmgAmnt dmg$reset with $bold${yellow1}Grievous Wounds$reset $white<<<$reset")

    }

    fun bestowCurse(target: Hero) {
        if (hp > (maxHp * 0.2)) {
            hp -= (maxHp * 0.1).roundToInt()
            println("   $white>>>$reset $bold$yellow2$name$reset casts $bold${yellow1}Bestow Curse$reset on $blue$bold$target$reset and they lose ${red}10%$reset of their max. health $white<<<$reset")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
        enemies.add(golem)
        println("   $white>>>$reset $bold$yellow2$name$reset has summoned a $bold$yellow2$golem$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$name (Necromancer, ${hp} hp)"
    }
}