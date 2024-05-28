import kotlin.math.roundToInt
import kotlin.system.exitProcess

class Game(private val heroes: List<Hero>, private val enemies: MutableList<Enemy>, private val inventory: Inventory) {
    private val necro: Necromancer = enemies[0] as Necromancer
    private var golem: Golem? = null
    private val cleric: Cleric = heroes[0] as Cleric
    private val mage: Mage = heroes[1] as Mage
    private val warrior: Warrior = heroes[2] as Warrior
    private var cursedHero: Hero? = null

    private val white = "\u001B[97m"
    private val red1 = "\u001B[31m"
    private val red2 = "\u001B[91m"
    private val green1 = "\u001B[32m"
    private val green2 = "\u001B[92m"
    private val yellow = "\u001B[93m"
    private val blue1 = "\u001B[34m"
    private val blue2 = "\u001B[94m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun gameLoop() {
        println("$bold$white\n" +
                " _______   _______    ______         _______                                      ________  __            __          __     \n" +
                "/       \\ /       \\  /      \\       /       \\                                    /        |/  |          /  |        /  |    \n" +
                "\$\$\$\$\$\$\$  |\$\$\$\$\$\$\$  |/\$\$\$\$\$\$  |      \$\$\$\$\$\$\$  |  ______    _______  _______       \$\$\$\$\$\$\$\$/ \$\$/   ______  \$\$ |____   _\$\$ |_   \n" +
                "\$\$ |__\$\$ |\$\$ |__\$\$ |\$\$ | _\$\$/       \$\$ |__\$\$ | /      \\  /       |/       |      \$\$ |__    /  | /      \\ \$\$      \\ / \$\$   |  \n" +
                "\$\$    \$\$< \$\$    \$\$/ \$\$ |/    |      \$\$    \$\$< /\$\$\$\$\$\$  |/\$\$\$\$\$\$\$//\$\$\$\$\$\$\$/       \$\$    |   \$\$ |/\$\$\$\$\$\$  |\$\$\$\$\$\$\$  |\$\$\$\$\$\$/   \n" +
                "\$\$\$\$\$\$\$  |\$\$\$\$\$\$\$/  \$\$ |\$\$\$\$ |      \$\$\$\$\$\$\$  |\$\$ |  \$\$ |\$\$      \\\$\$      \\       \$\$\$\$\$/    \$\$ |\$\$ |  \$\$ |\$\$ |  \$\$ |  \$\$ | __ \n" +
                "\$\$ |  \$\$ |\$\$ |      \$\$ \\__\$\$ |      \$\$ |__\$\$ |\$\$ \\__\$\$ | \$\$\$\$\$\$  |\$\$\$\$\$\$  |      \$\$ |      \$\$ |\$\$ \\__\$\$ |\$\$ |  \$\$ |  \$\$ |/  |\n" +
                "\$\$ |  \$\$ |\$\$ |      \$\$    \$\$/       \$\$    \$\$/ \$\$    \$\$/ /     \$\$//     \$\$/       \$\$ |      \$\$ |\$\$    \$\$ |\$\$ |  \$\$ |  \$\$  \$\$/ \n" +
                "\$\$/   \$\$/ \$\$/        \$\$\$\$\$\$/        \$\$\$\$\$\$\$/   \$\$\$\$\$\$/  \$\$\$\$\$\$\$/ \$\$\$\$\$\$\$/        \$\$/       \$\$/  \$\$\$\$\$\$\$ |\$\$/   \$\$/    \$\$\$\$/  \n" +
                "                                                                                               /  \\__\$\$ |                    \n" +
                "                                                                                               \$\$    \$\$/                     \n" +
                "                                                                                                \$\$\$\$\$\$/                      \n$reset")
        println()
        println(
            """
            The heroes $blue2$bold$cleric, $mage and $warrior$reset are fighting the boss $yellow$bold$necro$reset.
            Defeat him before it's too late!
        """.trimIndent()
        )
        println()

        var nr = 1

        while (!gameOverCheck()) {
            round(nr)
            nr++
        }

        gameOver()

        Thread.sleep(500)

        newGame()
    }

    private fun gameOver() {
        println("$bold$white      ------------------ Game Over ------------------$reset")
        if (heroes.all { it.hp <= 0 }) {
            Thread.sleep(200)
            println()
            println("        >>> $red2${bold}All your ${blue2}heroes$reset$red2${bold} are dead. You lost!$reset <<<")
        } else {
            Thread.sleep(200)
            println()
            println("        >>> $green2${bold}All ${yellow}enemies$reset$green2${bold} are defeated. You won!$reset <<<")
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
        println("$bold$white                  -------------------------- ROUND $nr --------------------------$reset")
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                cursedHero = null
            }
            println()
            println("   >>> $blue2$bold${cursedHero!!.name}$reset is cursed and loses $red2$bold${cursedHero!!.maxHp * 0.1}hp$reset <<<")
            cursedHero!!.hp -= (cursedHero!!.maxHp * 0.1).roundToInt()
            Thread.sleep(200)
        }

        enemies.forEach {
            if (it.burning) {
                it.hp -= (10 * mage.dmgMod).roundToInt()
                println()
                println("   $white>>>$reset $yellow$bold$it$reset is burning and takes $red2${bold}10 dmg$reset $white<<<$reset")
                Thread.sleep(200)
            }
        }

        println()
        println("Your party of $blue2$bold${heroes.filter { it.hp > 0 }}$reset attacks $yellow$bold${enemies.filter { it.hp > 0 }}$reset.")

        val attackers = heroes.filter { it.hp > 0 }.toMutableList()

        while (!gameOverCheck() && attackers.size > 0) {
            val prompt =
                """
            $blue2$bold$attackers$reset
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
            println("$yellow$bold${necro}$reset attacks your party of $blue2$bold${heroes.filter { it.hp > 0 }}$reset.")
            println()
            Thread.sleep(600)
            necroAttack()
            if (gameOverCheck()) {
                return
            }
        }

        Thread.sleep(600)

        if (golem != null && golem!!.hp > 0) {
            println()
            println("The $yellow$bold${golem?.name} attacks your party of $blue2$bold${heroes.filter { it.hp > 0 }}$reset.")
            println()
            Thread.sleep(600)
            golemAttack()
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
        Thread.sleep(500)
        warriorAttack()
        attackers.remove(warrior)
    }

    private fun mageTurn(attackers: MutableList<Hero>) {
        Thread.sleep(500)
        mageAttack()
        attackers.remove(mage)
    }

    private fun clericTurn(attackers: MutableList<Hero>) {
        Thread.sleep(500)
        clericAttack()
        attackers.remove(cleric)
    }

    private fun golemAttack() {
        when ((1..3).random()) {
            1 -> {
                if (warrior.isTaunting && warrior.hp > 0) {
                    golem!!.smash(warrior)
                } else {
                    golem!!.smash(heroes.filter { it.hp > 0 }.random())
                }
            }

            2 -> {
                golem!!.groundSlam(heroes.filter { it.hp > 0 })
            }

            3 -> {
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
            
                $white>>>$reset It's $blue2$bold$warrior's$reset turn. $white<<<$reset
            
            Choose which ability to use:
            1. $bold${blue1}Stab$reset (Deal ${red2}50 dmg$reset to $bold${yellow}an enemy$reset.)
            2. $bold${blue1}Sword Swipe$reset (Deal ${red2}30 dmg$reset to $bold${yellow}each enemy$reset.)
            3. $bold${blue1}Taunt$reset (Force $bold${yellow}enemies$reset to target $bold$blue2${warrior.name}$reset for ${green2}3 turns$reset.)
            4. $bold${blue1}Battle Shout$reset (Increase $bold${blue2}your$reset tenacity by ${green2}10%$reset.)
            5. $bold${blue1}Use Item$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(500)
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
                Thread.sleep(500)
                warrior.swordSwipe(enemies.filter { it.hp > 0 }.toMutableList())
            }

            3 -> {
                Thread.sleep(500)
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
            
                $white>>>$reset It's $blue2$bold$mage's$reset turn. $white<<<$reset
            
            Choose which ability to use:
            1. $bold${blue1}Fireball$reset (Deal ${red2}35-45 dmg$reset to $bold${yellow}each enemy$reset.)
            2. $bold${blue1}Lightning Bolt$reset (Deal ${red2}50-60 dmg$reset to $bold${yellow}an enemy$reset.)
            3. $bold${blue1}Magic Missile$reset (Deal ${red2}20-35 dmg$reset to $bold${yellow}a random enemy$reset, then repeat $bold${blue1}this$reset.)
            4. $bold${blue1}Burn$reset (Deal ${red2}30 dmg$reset to $bold${yellow}an enemy$reset and burn them for an additional ${red2}10 dmg$reset each turn.)
            5. $bold${blue1}Use Item$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(500)
                mage.fireball(enemies.filter { it.hp > 0 }.toMutableList())
            }

            2 -> {
                Thread.sleep(500)
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
                Thread.sleep(500)
                mage.magicMissile(enemies.filter { it.hp > 0 }.toMutableList())
            }

            4 -> {
                Thread.sleep(500)
                if (enemies.filter { it.hp > 0 }.size > 1) {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        mage.burn(golem!!)
                    } else {
                        val target = targetEnemy()
                        mage.burn(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        mage.burn(golem!!)
                    } else {
                        val target = enemies.filter { it.hp > 0 }[0]
                        mage.burn(target)
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
            
                $white>>>$reset It's $blue2$bold$cleric's$reset turn. $white<<<$reset
            
            Choose which ability to use:
            1. $bold${blue1}Healing Hands$reset (Heal $bold${blue2}an ally$reset for ${green2}30-40 hp$reset.)
            2. $bold${blue1}Healing Wave$reset (Heal $bold${blue2}each ally$reset for ${green2}20-30 hp$reset.)
            3. $bold${blue1}Dispel$reset (Dispel $bold${blue2}an ally's$reset debuff.)
            4. $bold${blue1}Cripple$reset (Reduce $bold${yellow}an enemy's$reset dmg dealt by ${red2}10%$reset.)
            5. $bold${blue1}Use Item$reset
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(500)
                val target = targetHero()
                if (target.cantHeal) {
                    println("${red2}The target is grievously wounded and can't be healed currently. Try another action.$reset")
                    clericAttack()
                } else {
                    cleric.healingHands(target)
                }
            }

            2 -> {
                Thread.sleep(500)
                cleric.healingWave(heroes.filter { it.hp > 0 })
            }

            3 -> {
                Thread.sleep(500)
                val target = targetHero()
                cleric.dispel(target)
                cursedHero = null
            }

            4 -> {
                Thread.sleep(500)
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
            
                >>> $green1$bold$inventory$reset <<<
            1. $bold${green1}Health Potion$reset
            2. $bold${green1}Elixir$reset
            Select an item to use:
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"
        when (select(prompt, errMsg, 2)) {
            1 -> {
                Thread.sleep(200)
                val target = targetHero()
                return inventory.tryUseHealthPotion(target)
            }

            2 -> {
                Thread.sleep(200)
                val target = targetHero()
                return inventory.tryUseElixir(target)
            }
        }
        return false
    }

    private fun targetEnemy(): Enemy {
        val prompt =
            """
            
            $yellow$bold${enemies.filter { it.hp > 0 }}$reset
            Select a target ${blue2}1, 2, ...$reset:
            """.trimIndent()
        val errMsg = "${red1}Invalid Input. Please try again:$reset"
        val target =
            enemies.filter { it.hp > 0 }[select(prompt, errMsg, enemies.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun targetHero(): Hero {
        val prompt =
            """
            $blue2
            $bold${heroes.filter { it.hp > 0 }}$reset
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
            print("> ")
            val input = readln().toIntOrNull()
            if (input != null && input in (1..max)) {
                return input
            }
            println(errMsg)
        }
    }
}