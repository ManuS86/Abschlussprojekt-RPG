import kotlin.math.roundToInt
import kotlin.system.exitProcess

class Game(private val heroes: List<Hero>, private val enemies: MutableList<Enemy>, private val inventory: Inventory) {
    private val necro: Necromancer = enemies[0] as Necromancer
    private var golem: Golem? = null
    private val cleric: Cleric = heroes[0] as Cleric
    private val mage: Mage = heroes[1] as Mage
    private val warrior: Warrior = heroes[2] as Warrior
    private var cursedHero: Hero? = null

    fun gameLoop() {
        println(
            "$bold$white\n" +
                    "      _______   _______    ______         _______                                      ________  __            __          __     \n" +
                    "     /       \\ /       \\  /      \\       /       \\                                    /        |/  |          /  |        /  |    \n" +
                    "     \$\$\$\$\$\$\$  |\$\$\$\$\$\$\$  |/\$\$\$\$\$\$  |      \$\$\$\$\$\$\$  |  ______    _______  _______       \$\$\$\$\$\$\$\$/ \$\$/   ______  \$\$ |____   _\$\$ |_   \n" +
                    "     \$\$ |__\$\$ |\$\$ |__\$\$ |\$\$ | _\$\$/       \$\$ |__\$\$ | /      \\  /       |/       |      \$\$ |__    /  | /      \\ \$\$      \\ / \$\$   |  \n" +
                    "     \$\$    \$\$< \$\$    \$\$/ \$\$ |/    |      \$\$    \$\$< /\$\$\$\$\$\$  |/\$\$\$\$\$\$\$//\$\$\$\$\$\$\$/       \$\$    |   \$\$ |/\$\$\$\$\$\$  |\$\$\$\$\$\$\$  |\$\$\$\$\$\$/   \n" +
                    "     \$\$\$\$\$\$\$  |\$\$\$\$\$\$\$/  \$\$ |\$\$\$\$ |      \$\$\$\$\$\$\$  |\$\$ |  \$\$ |\$\$      \\\$\$      \\       \$\$\$\$\$/    \$\$ |\$\$ |  \$\$ |\$\$ |  \$\$ |  \$\$ | __ \n" +
                    "     \$\$ |  \$\$ |\$\$ |      \$\$ \\__\$\$ |      \$\$ |__\$\$ |\$\$ \\__\$\$ | \$\$\$\$\$\$  |\$\$\$\$\$\$  |      \$\$ |      \$\$ |\$\$ \\__\$\$ |\$\$ |  \$\$ |  \$\$ |/  |\n" +
                    "     \$\$ |  \$\$ |\$\$ |      \$\$    \$\$/       \$\$    \$\$/ \$\$    \$\$/ /     \$\$//     \$\$/       \$\$ |      \$\$ |\$\$    \$\$ |\$\$ |  \$\$ |  \$\$  \$\$/ \n" +
                    "     \$\$/   \$\$/ \$\$/        \$\$\$\$\$\$/        \$\$\$\$\$\$\$/   \$\$\$\$\$\$/  \$\$\$\$\$\$\$/ \$\$\$\$\$\$\$/        \$\$/       \$\$/  \$\$\$\$\$\$\$ |\$\$/   \$\$/    \$\$\$\$/  \n" +
                    "                                                                                                    /  \\__\$\$ |                    \n" +
                    "                                                                                                    \$\$    \$\$/                     \n" +
                    "                                                                                                     \$\$\$\$\$\$/                      \n$reset"
        )
        println()
        println(
            """
            The heroes $cleric, $mage and $warrior are fighting the boss $necro.
                                                        Defeat him before it's too late!
            """.trimIndent()
        )
        println()

        var nr = 1

        while (!gameOverCheck()) {
            round(nr)
            nr++
        }

        gameOver(nr - 1)

        Thread.sleep(500)

        newGame()
    }

    private fun gameOver(nr: Int) {
        println("$bold$white                  ---------------------------------------- GAME OVER ----------------------------------------$reset")
        if (heroes.all { it.hp <= 0 }) {
            Thread.sleep(200)
            println()
            println("                                             $white>>> The fight lasted $nr rounds <<<$reset")
            println()
            println("                                         $white>>>$reset $yellow2${bold}All your ${blue2}heroes$reset$yellow2${bold} are dead. You lost!$reset $white<<<$reset")
        } else {
            Thread.sleep(200)
            println()
            println("                                              $white>>> The fight lasted $nr rounds <<<$reset")
            println()
            println("                                         $white>>>$reset $green2${bold}All ${red2}enemies$reset$green2${bold} are defeated. You won!$reset $white<<<$reset")
        }
    }

    private fun newGame() {
        val prompt =
            """
            Do you want to play again?
            1. $bold${green2}Yes$reset
            2. $bold${red2}No$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again$reset."
        when (select(prompt, errMsg, 2)) {
            1 -> {
                Thread.sleep(500)
                main()
            }

            2 -> {
                exitProcess(0)
            }
        }
    }

    private fun gameOverCheck(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round(nr: Int) {
        println("$bold$white               ------------------------------------------- ROUND $nr -------------------------------------------$reset")
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                cursedHero = null
            }
            cursedHero!!.hp -= (cursedHero!!.maxHp * 0.1).roundToInt()
            println()
            println("                                         $white>>>$reset $blue2$bold${cursedHero!!.name}$reset is ${yellow1}cursed$reset and loses $yellow2${(cursedHero!!.maxHp * 0.1).roundToInt()} hp$reset $white<<<$reset")
            Thread.sleep(200)
        }

        enemies.forEach {
            if (it.burning) {
                it.hp -= (15 * mage.skillMod).roundToInt()
                println()
                println("                                       $white>>>$reset $red2$bold${it.name}$reset is ${yellow1}burning$reset and takes ${yellow2}${(15 * mage.skillMod).roundToInt()} dmg$reset $white<<<$reset")
                Thread.sleep(200)
            }
        }

        println()
        println("Your party of ${heroes.filter { it.hp > 0 }} attacks ${enemies.filter { it.hp > 0 }}.")

        val attackers = heroes.filter { it.hp > 0 }.toMutableList()

        while (!gameOverCheck() && attackers.size > 0) {
            val prompt =
                """
                $attackers
                Select an attacker ${blue2}1, 2, ...$reset:
                """.trimIndent()
            val errMsg = "${red1}Invalid Input. Please try again:$reset"

            Thread.sleep(500)
            if (attackers.size > 1) {
                when (attackers[select(prompt, errMsg, attackers.size) - 1]) {
                    cleric -> {
                        clericTurn(attackers)
                    }

                    mage -> {
                        mageTurn(attackers)
                    }

                    warrior -> {
                        warriorTurn(attackers)
                    }
                }
            } else {
                when (attackers[0]) {
                    cleric -> {
                        clericTurn(attackers)
                    }

                    mage -> {
                        mageTurn(attackers)
                    }

                    warrior -> {
                        warriorTurn(attackers)
                    }
                }
            }
        }

        Thread.sleep(600)

        if (necro.hp > 0) {
            println()
            println("$necro attacks your party of ${heroes.filter { it.hp > 0 }}.")
            println()
            Thread.sleep(400)
            necroAttack()
            println()
            if (gameOverCheck()) {
                return
            }
        }

        Thread.sleep(600)

        if (golem != null && golem!!.hp > 0) {
            println()
            println("The $golem attacks your party of ${heroes.filter { it.hp > 0 }}.")
            println()
            Thread.sleep(400)
            golemAttack()
            println()
            if (gameOverCheck()) {
                return
            }
        }

        if (warrior.isTaunting) {
            warrior.tauntTimer--
        }

        if (warrior.tauntTimer == 0) {
            warrior.isTaunting = false
        }

        if (golem != null && golem!!.isTaunting) {
            golem!!.tauntTimer--
        }

        if (golem != null && golem!!.tauntTimer == 0) {
            golem!!.isTaunting = false
        }

        heroes.forEach {
            if (it.cantHeal) {
                it.cantHealTimer--
            }
        }

        heroes.forEach {
            if (it.cantHealTimer == 0) {
                it.cantHeal = false
            }
        }
        println()
    }

    private fun warriorTurn(attackers: MutableList<Hero>) {
        Thread.sleep(400)
        warriorAttack()
        attackers.remove(warrior)
        println()
    }

    private fun mageTurn(attackers: MutableList<Hero>) {
        Thread.sleep(400)
        mageAttack()
        attackers.remove(mage)
        println()
    }

    private fun clericTurn(attackers: MutableList<Hero>) {
        Thread.sleep(400)
        clericAttack()
        attackers.remove(cleric)
        println()
    }

    private fun golemAttack() {
        when ((1..3).random()) {
            1 -> {
                Thread.sleep(500)
                if (warrior.isTaunting && warrior.hp > 0) {
                    golem!!.smash(warrior)
                } else {
                    golem!!.smash(heroes.filter { it.hp > 0 }.random())
                }
            }

            2 -> {
                Thread.sleep(500)
                golem!!.groundSlam(heroes.filter { it.hp > 0 })
            }

            3 -> {
                Thread.sleep(500)
                golem!!.taunt()
            }
        }
    }

    private fun necroAttack() {
        when ((1..6).random()) {
            1 -> {
                Thread.sleep(500)
                necro.deathWave(heroes.filter { it.hp > 0 })
            }

            2 -> {
                Thread.sleep(500)
                if (warrior.isTaunting && warrior.hp > 0) {
                    necro.blight(warrior)
                } else {
                    necro.blight(heroes.filter { it.hp > 0 }.random())
                }
            }

            3 -> {
                Thread.sleep(500)
                if (warrior.isTaunting && warrior.hp > 0) {
                    necro.vampiricTouch(warrior)
                } else {
                    necro.vampiricTouch(heroes.filter { it.hp > 0 }.random())
                }
            }

            4 -> {
                Thread.sleep(500)
                if (warrior.isTaunting && warrior.hp > 0) {
                    necro.grievousWounds(warrior)
                } else {
                    necro.grievousWounds(heroes.filter { it.hp > 0 }.random())
                }
            }

            5 -> {
                Thread.sleep(500)
                if (cursedHero == null) {
                    if (warrior.isTaunting && warrior.hp > 0) {
                        cursedHero = warrior
                        necro.bestowCurse(cursedHero!!)
                    } else {
                        cursedHero = heroes.filter { it.hp > 0 }.random()
                        necro.bestowCurse(cursedHero!!)
                    }
                } else {
                    necroAttack()
                }
            }

            6 -> {
                Thread.sleep(500)
                if (golem == null && necro.hp <= necro.maxHp * 0.5) {
                    necro.summonGolem(enemies)
                    golem = enemies[1] as Golem
                } else {
                    necroAttack()
                }
            }
        }
    }

    private fun warriorAttack() {
        val prompt =
            """
                $white>>>$reset It's $warrior$blue2$bold's$reset turn. $white<<<$reset
            
            Choose which ability to use:
            1. $bold${blue1}Stab$reset (Deal ${yellow2}${(50 * warrior.skillMod).roundToInt()} dmg$reset to $bold${red2}an enemy$reset.)
            2. $bold${blue1}Cleave$reset (Deal ${yellow2}${(30 * warrior.skillMod).roundToInt()} dmg$reset to $bold${red2}each enemy$reset.)
            3. $bold${blue1}Taunt$reset (Force $bold${red2}enemies$reset to target $bold$blue2${warrior.name}$reset for ${green2}3 turns$reset.)
            4. $bold${blue1}Battle Shout$reset (Increase $bold${blue2}your$reset tenacity by ${green2}10%$reset.)
            5. $bold${blue1}Use Item$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(400)
                if (enemies.filter { it.hp > 0 }.size > 1) {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        warrior.stab(golem!!)
                    } else {
                        val target = targetEnemy()
                        warrior.stab(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        warrior.stab(golem!!)
                    } else {
                        warrior.stab(enemies.filter { it.hp > 0 }[0])
                    }
                }
            }

            2 -> {
                Thread.sleep(400)
                warrior.cleave(enemies.filter { it.hp > 0 }.toMutableList())
            }

            3 -> {
                Thread.sleep(400)
                warrior.taunt()
            }

            4 -> {
                warrior.battleShout()
            }

            5 -> {
                Thread.sleep(200)
                if (!useInventory()) {
                    warriorAttack()
                }
            }
        }
    }

    private fun mageAttack() {
        val prompt =
            """
                $white>>>$reset It's $mage$blue2$bold's$reset turn. $white<<<$reset
            
            Choose which ability to use:
            1. $bold${blue1}Fireball$reset (Deal ${yellow2}${(35 * mage.skillMod).roundToInt()}-${(45 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}each enemy$reset.)
            2. $bold${blue1}Lightning Bolt$reset (Deal ${yellow2}${(50 * mage.skillMod).roundToInt()}-${(60 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}an enemy$reset.)
            3. $bold${blue1}Magic Missiles$reset (Deal ${yellow2}${(20 * mage.skillMod).roundToInt()}-${(35 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}a random enemy$reset, then repeat $bold${blue1}this$reset.)
            4. $bold${blue1}Searing Touch$reset (Deal ${yellow2}${(30 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}an enemy$reset and burn them for an additional ${yellow2}${(15 * mage.skillMod).roundToInt()} dmg$reset ${green2}each turn$reset.)
            5. $bold${blue1}Use Item$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(400)
                mage.fireball(enemies.filter { it.hp > 0 }.toMutableList())
            }

            2 -> {
                Thread.sleep(400)
                if (enemies.filter { it.hp > 0 }.size > 1) {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        mage.lightningBolt(golem!!)
                    } else {
                        val target = targetEnemy()
                        mage.lightningBolt(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        mage.lightningBolt(golem!!)
                    } else {
                        mage.lightningBolt(enemies.filter { it.hp > 0 }[0])
                    }
                }
            }

            3 -> {
                Thread.sleep(400)
                mage.magicMissiles(enemies.filter { it.hp > 0 }.toMutableList())
            }

            4 -> {
                Thread.sleep(400)
                if (enemies.filter { it.hp > 0 }.size > 1) {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        mage.searingTouch(golem!!)
                    } else {
                        val target = targetEnemy()
                        mage.searingTouch(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        mage.searingTouch(golem!!)
                    } else {
                        val target = enemies.filter { it.hp > 0 }[0]
                        mage.searingTouch(target)
                    }
                }
            }

            5 -> {
                Thread.sleep(200)
                if (!useInventory()) {
                    mageAttack()
                }
            }
        }
    }

    private fun clericAttack() {
        val prompt =
            """
                $white>>>$reset It's $cleric$blue2$bold's$reset turn. $white<<<$reset
            
            Choose which ability to use:
            1. $bold${blue1}Healing Hands$reset (Heal $bold${blue2}an ally$reset for ${green2}${(35 * mage.skillMod).roundToInt()}-${(45 * mage.skillMod).roundToInt()} hp$reset.)
            2. $bold${blue1}Healing Wave$reset (Heal $bold${blue2}each ally$reset for ${green2}${(25 * mage.skillMod).roundToInt()}-${(35 * mage.skillMod).roundToInt()} hp$reset.)
            3. $bold${blue1}Dispel$reset (Dispel $bold${blue2}an ally's$reset debuff.)
            4. $bold${blue1}Cripple$reset (Reduce $bold${red2}an enemy's$reset dmg dealt by ${yellow2}10%$reset.)
            5. $bold${blue1}Use Item$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(400)
                val target = targetHero()
                if (target.cantHeal) {
                    println("${yellow2}The target is grievously wounded and can't be healed currently. Try another action.$reset")
                    clericAttack()
                } else {
                    cleric.healingHands(target)
                }
            }

            2 -> {
                Thread.sleep(400)
                cleric.healingWave(heroes.filter { it.hp > 0 })
            }

            3 -> {
                Thread.sleep(400)
                val target = targetHero()
                cleric.dispel(target)
                cursedHero = null
            }

            4 -> {
                Thread.sleep(400)
                if (enemies.filter { it.hp > 0 }.size > 1) {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        cleric.cripple(golem!!)
                    } else {
                        val target = targetEnemy()
                        cleric.cripple(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        cleric.cripple(golem!!)
                    } else {
                        cleric.cripple(enemies.filter { it.hp > 0 }[0])
                    }
                }
            }

            5 -> {
                Thread.sleep(200)
                if (!useInventory()) {
                    clericAttack()
                }
            }
        }
    }

    private fun useInventory(): Boolean {
        val prompt =
            """
            
                >>> $inventory <<<
            1. $bold${green1}Health Potion$reset
            2. $bold${green1}Elixir$reset
            Select an item to use:
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"
        when (select(prompt, errMsg, 2)) {
            1 -> {
                Thread.sleep(400)
                val target = targetHero()
                return inventory.tryUseHealthPotion(target)
            }

            2 -> {
                Thread.sleep(400)
                val target = targetHero()
                return inventory.tryUseElixir(target)
            }
        }
        return false
    }

    private fun targetEnemy(): Enemy {
        val prompt =
            """
            
            ${enemies.filter { it.hp > 0 }}
            Select a target ${red2}1, 2, ...$reset:
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"
        val target =
            enemies.filter { it.hp > 0 }[select(prompt, errMsg, enemies.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun targetHero(): Hero {
        val prompt =
            """
            
            ${heroes.filter { it.hp > 0 }}
            Select a target ${blue2}1, 2, ...$reset:
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"
        val target =
            heroes.filter { it.hp > 0 }[select(prompt, errMsg, heroes.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun select(prompt: String, errMsg: String, max: Int): Int {
        println()
        println(prompt)
        while (true) {
            print("$white>$reset ")
            val input = readln().toIntOrNull()
            println()
            if (input != null && input in (1..max)) {
                return input
            }
            println(errMsg)
        }
    }
}