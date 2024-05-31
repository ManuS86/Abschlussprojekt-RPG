import kotlin.math.roundToInt

class Necromancer(name: String = "Acheron", maxHp: Double = 500.0) : Enemy(name, maxHp) {

    fun deathWave(targets: List<Hero>) {
        targets.forEach { it.hp -= 40 * dmgMod / it.tenacity }
        println("                     $white>>>$reset $bold$red2$name$reset deals $yellow2${(40 * dmgMod).roundToInt()} dmg$reset to the heroes $bold${blue2}${targets.map { it.name }}$reset with $bold${red1}Death Wave$reset $white<<<$reset")
        Thread.sleep(200)
        println(
            "                                  $white>>>$reset $bold$blue2${targets.map { it.name }}$reset now ${
                if (targets.size == 1) {
                    "has"
                } else {
                    "have"
                }
            } $green2${targets.map { it.hp.roundToInt() }} hp$reset $white<<<$reset"
        )
        deathCheckAoe(targets)
    }

    fun blight(target: Hero) {
        val dmgAmnt = 60 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        println("                                     $white>>>$reset $bold$red2$name$reset deals $yellow2${dmgAmnt.roundToInt()} dmg$reset to $bold$blue2${target.name}$reset with $bold${red1}Blight$reset $white<<<$reset")
        Thread.sleep(200)
        println("                                                $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun vampiricTouch(target: Hero) {
        val dmgAmnt = 30 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        val preHealHp = hp
        heal(dmgAmnt)
        val amntHealed = hp - preHealHp
        println("                      $white>>>$reset $bold$red2$name$reset drains $bold$blue2${target.name}$reset for $yellow2${dmgAmnt.roundToInt()} dmg$reset and heals $bold${red2}himself$reset for $green2${amntHealed.roundToInt()} hp$reset with $bold${red1}Vampiric Touch$reset $white<<<$reset")
        Thread.sleep(200)
        println("                                      $white>>>$reset $bold$red2$name$reset now has $green2$hp hp$reset and $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun grievousWounds(target: Hero) {
        val dmgAmnt = 40 * dmgMod / target.tenacity
        target.hp -= dmgAmnt
        target.cantHeal = true
        target.cantHealTimer = 2
        println("                 $white>>>$reset $bold$red2$name$reset wounds $bold$blue2${target.name}$reset (${yellow1}can't heal for 1 turn$reset) and deals $yellow2${dmgAmnt.roundToInt()} dmg$reset with $bold${red1}Grievous Wounds$reset $white<<<$reset")
        Thread.sleep(200)
        println("                                                $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun bestowCurse(target: Hero) {
        if (target.hp > (target.maxHp * 0.2)) {
            target.hp -= target.maxHp * 0.1
            println("              $white>>>$reset $bold$red2$name$reset casts $bold${red1}Bestow Curse$reset on $blue2$bold$target$reset and they lose ${yellow2}10%$reset ($yellow2${(target.maxHp * 0.1).roundToInt()}$reset) of their max. health  $white<<<$reset")
            Thread.sleep(200)
            println("                                                $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp.roundToInt()} hp$reset $white<<<$reset")
        }
    }

    fun summonGolem(enemies: MutableList<Enemy>) {
        val golem = Golem()
        enemies.add(golem)
        println("                                      $white>>>$reset $bold$red2$name$reset has summoned a $bold$red2$golem$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold$red2$name$reset $white($reset${red1}Necromancer$reset, $green2${hp.roundToInt()} hp$reset$white)$reset"
    }
}