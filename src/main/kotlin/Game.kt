import kotlin.math.roundToInt

class Game(private val heroes: List<Hero>, private val enemies: MutableList<Enemy>, private val inventory: Inventory) {
    private val boss: Necromancer = enemies[0] as Necromancer
    private var golem: Golem? = null
    private val cleric: Cleric = heroes[0] as Cleric
    private val mage: Mage = heroes[1] as Mage
    private val warrior: Warrior = heroes[2] as Warrior
    private var cursedHero: Hero? = null
    private var burningEnemies: MutableList<Enemy> = mutableListOf()

    fun gameLoop() {
        println("The heroes $cleric, $mage and $warrior} are fighting the boss $boss.")

        while (!isGameOver()) {
            round()
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

    private fun isGameOver(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round() {
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                cursedHero!!.cursed = false
                cursedHero = null
            }
            println()
            println("${cursedHero!!.name} is cursed and loses 10% of their max. health.")
            cursedHero!!.hp -= (cursedHero!!.maxHp * 0.1).roundToInt()
        }

        burningEnemies.forEach { it.hp -= 10 }
        burningEnemies.forEach { println("$it is burning and takes 10 dmg.") }

        println()
        println("Your party attacks $boss and his minions.")
        if (cleric.hp > 0) {
            clericAttack()
        }
        if (mage.hp > 0) {
            mageAttack()
        }
        if (warrior.hp > 0) {
            warriorAttack()
        }

        if (boss.hp > 0) {
            println()
            println("${boss.name} attack's your party.")
            bossAttack()
        }

        if (golem != null && golem!!.hp > 0) {
            println()
            println("The ${golem?.name} attacks your party.")
            golemAttack()
        }
    }

    private fun golemAttack() {
        when ((1..3).random()) {
            1 -> {
                golem!!.smash(heroes.filter { it.hp > 0 }.random())
            }

            2 -> {
                golem!!.groundSlam(heroes)
            }

            3 -> {
                golem!!.taunt()
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
                    val prompt =
                        """
                    $enemies
                    Select a target 1, 2, ...:
                    """.trimIndent()
                    val errMsg = "Please select a valid target:"
                    val selection = select(prompt, errMsg, enemies.size)
                    warrior.slam(enemies[selection - 1])
                } else {
                    warrior.slam(enemies[0])
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
                        val selection = select(prompt, errMsg, heroes.size)
                        if (!inventory.tryUseHealthPotion(heroes[selection - 1])) {
                            println("You are out of Health Potions. Try using another action.")
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
                        val selection = select(prompt, errMsg, heroes.size)
                        if (!inventory.tryUseElixir(heroes[selection - 1])) {
                            println("You are out of Elixirs. Try using another action.")
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
                    val prompt =
                        """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                    val errMsg = "Please select a valid target:"
                    val selection = select(prompt, errMsg, enemies.size)
                    mage.lightningBolt(enemies[selection - 1])
                } else {
                    mage.lightningBolt(enemies[0])
                }
            }

            3 -> {
                mage.magicMissile(enemies)
            }

            4 -> {
                if (enemies.size > 1) {
                    val prompt =
                        """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                    val errMsg = "Please select a valid target:"
                    val selection = select(prompt, errMsg, enemies.size)
                    mage.burn(enemies[selection - 1])
                } else {
                    mage.burn(enemies[0])
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
                        val selection = select(prompt, errMsg, heroes.size)
                        if (!inventory.tryUseHealthPotion(heroes[selection - 1])) {
                            println("You are out of Health Potions. Try using another action.")
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
                        val selection = select(prompt, errMsg, heroes.size)
                        if (!inventory.tryUseElixir(heroes[selection - 1])) {
                            println("You are out of Elixirs. Try using another action.")
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
                    $enemies
                    Select a target 1, 2, ...:
                    """.trimIndent()
                val errMsg = "Please select a valid target:"
                val selection = select(prompt, errMsg, heroes.size)
                cleric.healingHands(heroes[selection - 1])
            }

            2 -> {
                cleric.healingWave(heroes)
            }

            3 -> {
                val prompt =
                    """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                val errMsg = "Please select a valid target:"
                val selection = select(prompt, errMsg, heroes.size)
                cleric.dispel(heroes[selection - 1])
            }

            4 -> {
                if (enemies.size > 1) {
                    val prompt =
                        """
                $enemies
                Select a target 1, 2, ...:
                """.trimIndent()
                    val errMsg = "Please select a valid target:"
                    val selection = select(prompt, errMsg, enemies.size)
                    cleric.cripple(enemies[selection - 1])
                } else {
                    cleric.cripple(enemies[0])
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
                        val selection = select(prompt, errMsg, heroes.size)
                        if (!inventory.tryUseHealthPotion(heroes[selection - 1])) {
                            println("You are out of Health Potions. Try using another action.")
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
                        val selection = select(prompt, errMsg, heroes.size)
                        if (!inventory.tryUseElixir(heroes[selection - 1])) {
                            println("You are out of Elixirs. Try using another action.")
                            clericAttack()
                        }
                    }
                }
            }
        }
    }

    private fun bossAttack() {
        when ((1..6).random()) {
            1 -> {
                boss.deathWave(heroes)
            }

            2 -> {
                boss.blight(heroes.filter { it.hp > 0 }.random())
            }

            3 -> {
                boss.vampiricTouch(heroes.filter { it.hp > 0 }.random())
            }

            4 -> {
                boss.(heroes.filter { it.hp > 0 }.random())
            }

            5 -> {
                if (cursedHero == null) {
                    cursedHero = heroes.filter { it.hp > 0 }.random()
                    boss.bestowCurse(cursedHero!!)
                } else {
                    bossAttack()
                }
            }

            6 -> {
                if (golem == null && boss.hp <= boss.maxHp * 0.5) {
                    boss.summonGolem(enemies)
                    golem = enemies[1] as Golem
                } else {
                    bossAttack()
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