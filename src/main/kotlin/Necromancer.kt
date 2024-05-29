import kotlin.math.roundToInt

class Necromancer(name: String, maxHp: Int = 500) : Enemy(name, maxHp) {

    fun deathWave(targets: List<Hero>) {
        targets.forEach { it.hp -= (40 * dmgMod / it.tenacity).roundToInt() }
        println("       $white>>>$reset $bold$yellow2$name$reset deals $red2${(40 * dmgMod).roundToInt()} dmg$reset to the heroes $bold${blue2}${targets.map { it.name }}$reset with $bold${yellow1}Death Wave$reset $white<<<$reset")
        Thread.sleep(200)
        println(
            "      $white>>>$reset $bold$blue2${targets.map { it.name }}$reset now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $green2${targets.map { it.hp }} hp$reset $white<<<$reset"
        )
        deathCheckAoe(targets)
    }

    fun blight(target: Hero) {
        val dmgAmnt = (60 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("       $white>>>$reset $bold$yellow2$name$reset deals $red2$dmgAmnt dmg$reset to $bold$blue2${target.name}$reset with $bold${yellow1}Blight$reset $white<<<$reset")
        Thread.sleep(200)
        println("                $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = (30 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("       $white>>>$reset $bold$yellow2$name$reset drains $bold$blue2${target.name}$reset for $red2$dmgAmnt dmg$reset and heals $bold${yellow2}himself$reset for $green2$amntHealed hp$reset with $bold${yellow1}Vampiric Touch$reset $white<<<$reset")
        Thread.sleep(200)
        println("                       $white>>>$reset $bold$yellow2$name$reset now has $green2$hp hp$reset and $bold$blue2${target.name}$reset now has $green2${target.hp} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun grievousWounds(target: Hero) {
        val dmgAmnt = (40 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        target.cantHeal = true
        target.cantHealTimer = 2
        println("       $white>>>$reset $bold$yellow2$name$reset wounds $bold$blue2${target.name}$reset (${red1}can't heal for 1 turn$reset) and deals $red2$dmgAmnt dmg$reset with $bold${yellow1}Grievous Wounds$reset $white<<<$reset")
        Thread.sleep(200)
        println("                            $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun bestowCurse(target: Hero) {
        if (target.hp > (target.maxHp * 0.2)) {
            target.hp -= (target.maxHp * 0.1).roundToInt()
            println("       $white>>>$reset $bold$yellow2$name$reset casts $bold${yellow1}Bestow Curse$reset on $blue2$bold$target$reset and they lose ${red2}10%$reset of their max. health $white<<<$reset")
            Thread.sleep(200)
            println("                                   $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp} hp$reset $white<<<$reset")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem("Golem")
        enemies.add(golem)
        println("            $white>>>$reset $bold$yellow2$name$reset has summoned a $bold$yellow2$golem$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold$yellow2$name$reset $white($reset${yellow1}Necromancer$reset, $green2${hp} hp$reset$white)$reset"
    }
}