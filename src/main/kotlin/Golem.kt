import kotlin.math.roundToInt

class Golem(name: String, maxHp: Int = 250) : Enemy(name, maxHp) {
    var isTaunting = false
    var tauntTimer = 0

    fun smash(target: Hero) {
        val dmgAmnt = (50 * dmgMod / target.tenacity).roundToInt()
        target.hp -= dmgAmnt
        println("           $white>>>$reset $bold$yellow2$name$reset deals $red2$dmgAmnt dmg$reset to $blue2$bold${target.name}$reset with $bold${yellow1}Smash$reset $white<<<$reset")
        println("                     $white>>>$reset $bold$blue2${target.name}$reset now has $green2${target.hp} hp$reset $white<<<$reset")
        deathCheck(target)
    }

    fun groundSlam(targets: List<Hero>) {
        targets.forEach { it.hp -= (30 * dmgMod / it.tenacity).roundToInt() }
        println("           $white>>>$reset $bold$yellow2$name$reset deals $red2${targets.map { (20 * dmgMod / it.tenacity).roundToInt() }} dmg$reset to the heroes $bold${blue2}${targets.map { it.name }}$reset with $bold${yellow1}Ground Slam$reset $white<<<$reset")
        println("               $white>>>$reset $bold$blue2${targets.map { it.name }}$reset now ${
            if (targets.size == 1) {
                "has"
            } else {
                "have"
            }
        } $green2${targets.map { it.hp }} hp$reset $white<<<$reset"
        )
        deathCheckAoe(targets)
    }

    fun taunt() {
        isTaunting = true
        tauntTimer = 3
        println("           $white>>>$reset The $bold$yellow2$name$reset is $bold${yellow1}taunting$reset the $bold${blue2}heroes$reset, forcing them to attack $bold${yellow2}him$reset for the next ${green2}2 turns$reset $white<<<$reset")
    }

    override fun toString(): String {
        return "$bold$yellow2$name$reset $white($reset$green2${hp} hp$reset$white)$reset"
    }
}