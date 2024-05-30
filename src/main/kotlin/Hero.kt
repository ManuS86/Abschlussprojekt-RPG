import kotlin.math.min

abstract class Hero(val name: String, val maxHp: Double) {
    var hp = maxHp
    var skillMod = 1.0
    var tenacity = 1.0
    var cantHeal = false
    var cantHealTimer = 0

    fun heal(healAmount: Double) {
        hp = min(hp + healAmount, maxHp)
    }

    fun deathCheck(target: Enemy) {
        if (target.hp <= 0) {
            println("                $white>>>$reset $bold$red2${target.name}$reset is dead. $white<<<$reset")
        }
    }

    fun deathCheckAoe(targets: MutableList<Enemy>) {
        if (targets.any { it.hp <= 0 }) {
            println(
                "               $white>>>$reset $bold$red2${
                    targets.filter { it.hp <= 0 }.map { it.name }
                }$reset ${
                    if (targets.filter { it.hp <= 0 }.size == 1) {
                        "is"
                    } else {
                        "are"
                    }
                } dead. $white<<<$reset"
            )
        }
    }
}