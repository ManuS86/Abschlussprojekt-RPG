import kotlin.math.min

abstract class Enemy(var name: String, val maxHp: Int) {
    var hp = maxHp
    var dmgMod = 1.0
    var burning = false

    fun heal(healAmount: Int) {
        hp = min(hp + healAmount, maxHp)
    }

    fun deathCheck(target: Hero) {
        if (target.hp <= 0) {
            println("       $white>>>$reset $bold$blue2${target.name}$reset is dead. $white<<<$reset")
        }
    }

    fun deathCheckAoe(targets: List<Hero>) {
        if (targets.any { it.hp <= 0 }) {
            println(
                "       $white>>>$reset $bold${blue2}2${
                    targets.filter { it.hp <= 0 }.map { it.name }
                }$reset are dead. $white<<<$reset"
            )
        }
    }

}
