import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 500) : Enemy(name, maxHp) {

    fun deathWave(targets: List<Hero>) {
        targets.forEach { it.hp -= (40 * dmgMod / it.tenacity).roundToInt() }
        println("       $white>>>$reset $bold$yellow2$name$reset deals $red2${(40 * dmgMod).roundToInt()} dmg$reset to each hero with $bold${yellow1}Death Wave$reset $white<<<$reset")
        println("      $white>>>$reset $bold$blue2${targets.map { it.name }}$reset have $green2${targets.map { it.hp }} hp$reset left. $white<<<$reset")
    }

    fun blight(target: Hero) {
        val dmgAmnt = (60 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("       $white>>>$reset $bold$yellow2$name$reset deals $red2$dmgAmnt dmg$reset to $bold$blue2${target.name}$reset with $bold${yellow1}Blight$reset $white<<<$reset")
        println("               $white>>>$reset $bold$blue2${target.name}$reset has $green2${target.hp} hp$reset left. $white<<<$reset")
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("       $white>>>$reset $bold$yellow2$name$reset drains $bold$blue2${target.name}$reset for $red2$dmgAmnt dmg$reset and heals $bold${yellow2}himself$reset for $green2$amntHealed hp$reset with $bold${yellow1}Vampiric Touch$reset $white<<<$reset")
        println("               $white>>>$reset $bold$blue2${target.name}$reset has $green2${target.hp} hp$reset left. $white<<<$reset")
    }

    fun grievousWounds(target: Hero) {
        val dmgAmnt = (40 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        target.cantHeal = true
        target.cantHealTimer = 2
        println("       $white>>>$reset $bold$yellow2$name$reset $bold${yellow1}wounds$reset $bold$blue2${target.name}$reset (can't heal for one turn) and deals $red2$dmgAmnt dmg$reset with $bold${yellow1}Grievous Wounds$reset $white<<<$reset")
        println("               $white>>>$reset $bold$blue2${target.name}$reset has $green2${target.hp} hp$reset left. $white<<<$reset")
    }

    fun bestowCurse(target: Hero) {
        if (hp > (maxHp * 0.2)) {
            hp -= (maxHp * 0.1).roundToInt()
            println("       $white>>>$reset $bold$yellow2$name$reset casts $bold${yellow1}Bestow Curse$reset on $blue2$bold$target$reset and they lose ${red2}10%$reset of their max. health $white<<<$reset")
            println("               $white>>>$reset $bold$blue2${target.name}$reset has $green2${target.hp} hp$reset left. $white<<<$reset")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
        enemies.add(golem)
        println("       $white>>>$reset $bold$yellow2$name$reset has summoned a $bold$yellow2$golem$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$name (Necromancer, ${hp} hp)"
    }
}