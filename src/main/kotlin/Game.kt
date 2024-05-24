import kotlin.math.roundToInt

class Game(private val heroes: List<Hero>, private val enemies: MutableList<Enemy>, private val inventory: Inventory) {
    private val necro: Necromancer = enemies[0] as Necromancer
    private var golem: Golem? = null
    private val cleric: Cleric = heroes[0] as Cleric
    private val mage: Mage = heroes[1] as Mage
    private val warrior: Warrior = heroes[2] as Warrior
    private var cursedHero: Hero? = null

    fun gameLoop() {
        println("The heroes $cleric, $mage and $warrior are fighting the boss $necro.")
        var round = 1

        while (!gameOver()) {
            round(round)
            round++
        }

        println("Game Over")
        if (heroes.all { it.hp <= 0 }) {
            println()
            println("All your heroes are dead. You lost!")
        } else {
            println()
            println("All enemies are defeated. You won!")
        }
    }

    private fun gameOver(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round(round: Int) {
        println("----- Combat round $round -----")
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                cursedHero = null
            }
            println()
            println("${cursedHero!!.name} is cursed and loses 10% of their max. health.")
            cursedHero!!.hp -= (cursedHero!!.maxHp * 0.1).roundToInt()
        }

        enemies.forEach {
            if (it.burning) {
                it.hp -= (10 * mage.dmgMod).roundToInt()
                println("$it is burning and takes 10 dmg.")
            }
        }

        println()
        println("Your party of $heroes attacks $enemies.")
        if (cleric.hp > 0) {
            clericAttack()
        }
        if (mage.hp > 0) {
            mageAttack()
        }
        if (warrior.hp > 0) {
            warriorAttack()
        }

        if (necro.hp > 0) {
            println()
            println("${necro.name} attack's your party of $heroes.")
            necroAttack()
        }

        if (golem != null && golem!!.hp > 0) {
            println()
            println("The ${golem?.name} attacks your party of $heroes.")
            golemAttack()
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

    private fun golemAttack() {
        when ((1..3).random()) {
            1 -> {
                if (warrior.isTaunting) {
                    golem!!.smash(warrior)
                } else {
                    golem!!.smash(heroes.filter { it.hp > 0 }.random())
                }
            }

            2 -> {
                golem!!.groundSlam(heroes)
            }

            3 -> {
                golem!!.taunt()
            }
        }
    }

    private fun necroAttack() {
        when ((1..6).random()) {
            1 -> {
                necro.deathWave(heroes)
            }

            2 -> {
                if (warrior.isTaunting) {
                    necro.blight(warrior)
                } else {
                    necro.blight(heroes.filter { it.hp > 0 }.random())
                }
            }

            3 -> {
                if (warrior.isTaunting) {
                    necro.vampiricTouch(warrior)
                } else {
                    necro.vampiricTouch(heroes.filter { it.hp > 0 }.random())
                }
            }

            4 -> {
                necro.grievousWound(heroes.filter { it.hp > 0 }.random())
            }

            5 -> {
                if (cursedHero == null) {
                    cursedHero = heroes.filter { it.hp > 0 }.random()
                    necro.bestowCurse(cursedHero!!)
                } else if (warrior.isTaunting) {
                    cursedHero = warrior
                    necro.bestowCurse(cursedHero!!)
                } else {
                    necroAttack()
                }
            }

            6 -> {
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
            Choose which ability to use for $warrior:
            1. Slam (Deal 50 dmg to an enemy.)
            2. Sword Swipe (Deal 30 dmg to all enemies.)
            3. Taunt (Force enemies to target ${warrior.name} for 3 turns.)
            4. Battle Shout (Increase your dmg by 10%.)
            5. Use Item
            """.trimIndent()
        val errMsg = "Invalid selection. Please select a valid attack:"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                if (enemies.size > 1) {
                    if (golem != null && golem!!.isTaunting) {
                        warrior.slam(golem!!)
                    } else {
                        val prompt =
                            """
                    $enemies
                    Select a target 1, 2, ...:
                    """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = enemies[select(prompt, errMsg, enemies.size) - 1]
                        warrior.slam(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting) {
                        warrior.slam(golem!!)
                    } else {
                        warrior.slam(enemies[0])
                    }
                }
            }

            2 -> {
                warrior.swordSwipe(enemies)
            }

            3 -> {
                warrior.taunt()
            }

            4 -> {
                warrior.battleShout()
            }

            5 -> {
                val prompt =
                    """
                    $inventory
                    1. Health Potion
                    2. Elixir
                    Select an item to use:
                    """.trimIndent()
                val errMsg = "Please select an existing item:"
                when (select(prompt, errMsg, 2)) {
                    1 -> {
                        val prompt =
                            """
                            $heroes
                            Select a target 1, 2, ...:
                            """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                        if (!inventory.tryUseHealthPotion(target)) {
                            warriorAttack()
                        }
                    }

                    2 -> {
                        val prompt =
                            """
                            $heroes
                            Select a target 1, 2, ...:
                            """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                        if (!inventory.tryUseElixir(target)) {
                            warriorAttack()
                        }
                    }
                }
            }
        }
    }

    private fun mageAttack() {
        val prompt =
            """
            Choose which ability to use for $mage:
            1. Fireball (Deal 40 dmg to each enemy.)
            2. Lightning Bolt (Deal 60 dmg to an enemy.)
            3. Magic Missile (Deal 20-30 dmg to a random enemy then repeat this.)
            4. Burn (Set target enemy on fire dealing 20 dmg and burning them for 10 each turn.)
            5. Use Item
            """.trimIndent()
        val errMsg = "Invalid selection. Please select a valid attack:"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                mage.fireball(enemies)
            }

            2 -> {
                if (enemies.size > 1) {
                    if (golem != null && golem!!.isTaunting) {
                        mage.lightningBolt(golem!!)
                    } else {
                        val prompt =
                            """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = enemies[select(prompt, errMsg, enemies.size) - 1]
                        mage.lightningBolt(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting) {
                        mage.lightningBolt(golem!!)
                    } else {
                        mage.lightningBolt(enemies[0])
                    }
                }
            }

            3 -> {
                mage.magicMissile(enemies)
            }

            4 -> {
                if (enemies.size > 1) {
                    if (golem != null && golem!!.isTaunting) {
                        mage.burn(golem!!)
                    } else {
                        val prompt =
                            """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = enemies[select(prompt, errMsg, enemies.size) - 1]
                        mage.burn(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting) {
                        mage.burn(golem!!)
                    } else {
                        val target = enemies[0]
                        mage.burn(target)
                    }
                }
            }

            5 -> {
                val prompt =
                    """
                    $inventory
                    1. Health Potion
                    2. Elixir
                    Select an item to use:
                    """.trimIndent()
                val errMsg = "Please select an existing item:"
                when (select(prompt, errMsg, 2)) {
                    1 -> {
                        val prompt =
                            """
                            $heroes
                            Select a target 1, 2, ...:
                            """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                        if (!inventory.tryUseHealthPotion(target)) {
                            mageAttack()
                        }
                    }

                    2 -> {
                        val prompt =
                            """
                            $heroes
                            Please select a target 1, 2, ...:
                            """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                        if (!inventory.tryUseElixir(target)) {
                            mageAttack()
                        }
                    }
                }
            }
        }
    }

    private fun clericAttack() {
        val prompt =
            """
            Choose which ability to use for ${cleric}:
            1. Healing Hands (Heal an ally for 30-40hp.)
            2. Healing Wave (Heal each ally for 20-30hp.)
            3. Dispel (Dispel a debuff.)
            4. Cripple (Reduce an enemies dmg dealt by 10%.)
            5. Use Item
            """.trimIndent()
        val errMsg = "Please select a valid attack:"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                val prompt =
                    """
                    $heroes
                    Select a target 1, 2, ...:
                    """.trimIndent()
                val errMsg = "Please select a valid target:"
                val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                if (target.cantHeal) {
                    println("The target is grievously wounded and can't be healed currently. Try another action.")
                    clericAttack()
                } else {
                    cleric.healingHands(target)
                }
            }

            2 -> {
                cleric.healingWave(heroes)
            }

            3 -> {
                val prompt =
                    """
                $heroes
                Select a target 1, 2, ...:
                """.trimIndent()
                val errMsg = "Please select a valid target:"
                val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                cleric.dispel(target)
                cursedHero = null
            }

            4 -> {
                if (enemies.size > 1) {
                    if (golem != null && golem!!.isTaunting) {
                        cleric.cripple(golem!!)
                    } else {
                        val prompt =
                            """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = enemies[select(prompt, errMsg, enemies.size) - 1]
                        cleric.cripple(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting) {
                        cleric.cripple(golem!!)
                    } else {
                        cleric.cripple(enemies[0])
                    }
                }
            }

            5 -> {
                val prompt =
                    """
                    $inventory
                    1. Health Potion
                    2. Elixir
                    Select an item to use:
                    """.trimIndent()
                val errMsg = "Please select an existing item:"
                when (select(prompt, errMsg, 2)) {
                    1 -> {
                        val prompt =
                            """
                            $heroes
                            Select a target 1, 2, ...:
                            """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                        if (!inventory.tryUseHealthPotion(target)) {
                            clericAttack()
                        }
                    }

                    2 -> {
                        val prompt =
                            """
                            $heroes
                            Select a target 1, 2, ...:
                            """.trimIndent()
                        val errMsg = "Please select a valid target:"
                        val target = heroes[select(prompt, errMsg, heroes.size) - 1]
                        if (!inventory.tryUseElixir(target)) {
                            clericAttack()
                        }
                    }
                }
            }
        }
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