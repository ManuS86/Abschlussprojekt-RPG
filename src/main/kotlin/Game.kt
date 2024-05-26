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
        println()
        println("The heroes $cleric, $mage and $warrior are fighting the boss $necro.")
        println()
        Thread.sleep(200)

        var round = 1

        while (!gameOverCheck()) {
            round(round)
            round++
            Thread.sleep(500)
        }
        Thread.sleep(200)

        gameOver()
        Thread.sleep(200)

        newGame()
    }

    private fun gameOver() {
        println("Game Over")
        if (heroes.all { it.hp <= 0 }) {
            Thread.sleep(200)
            println()
            println("All your heroes are dead. You lost!")
        } else {
            Thread.sleep(200)
            println()
            println("All enemies are defeated. You won!")
        }
    }

    private fun newGame() {
        Thread.sleep(500)
        println()
        println("Do you want to play again?")
        var input: String? = null
        while (input != "no" || input != "yes") {
            print("> ")
            input = readln().lowercase()
            when (input) {
                "yes" -> main()
                "no" -> exitProcess(0)
                else -> println("Invalid Input. Please try again.")
            }
        }
    }

    private fun gameOverCheck(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round(round: Int) {
        println("----- Round $round -----")
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                cursedHero = null
            }
            println()
            println("${cursedHero!!.name} is cursed and loses ${cursedHero!!.maxHp * 0.1}hp.")
            cursedHero!!.hp -= (cursedHero!!.maxHp * 0.1).roundToInt()
            Thread.sleep(200)
        }

        enemies.forEach {
            if (it.burning) {
                it.hp -= (10 * mage.dmgMod).roundToInt()
                println()
                println("$it is burning and takes 10 dmg.")
                Thread.sleep(200)
            }
        }

        println()
        println("Your party of ${heroes.filter { it.hp > 0 }} attacks ${enemies.filter { it.hp > 0 }}.")
        Thread.sleep(200)

        val attackers = heroes.filter { it.hp > 0 }.toMutableList()
        val prompt =
            """
            $attackers
            Select an attacker 1, 2, ...:
            """.trimIndent()
        val errMsg = "Please select a valid attacker:"

        while (attackers.size > 0) {
            if (attackers.size > 1) {
                when (attackers[select(prompt, errMsg, attackers.size) - 1]) {
                    cleric -> {
                        Thread.sleep(200)
                        clericTurn()
                        attackers.remove(cleric)
                    }

                    mage -> {
                        Thread.sleep(200)
                        mageTurn()
                        attackers.remove(mage)
                    }

                    warrior -> {
                        Thread.sleep(200)
                        warriorTurn()
                        attackers.remove(warrior)
                    }
                }
            } else {
                when (attackers[0]) {
                    cleric -> {
                        Thread.sleep(200)
                        clericTurn()
                        attackers.remove(cleric)
                    }

                    mage -> {
                        Thread.sleep(200)
                        mageTurn()
                        attackers.remove(mage)
                    }

                    warrior -> {
                        Thread.sleep(200)
                        warriorTurn()
                        attackers.remove(warrior)
                    }
                }
            }
        }

        Thread.sleep(500)

        if (necro.hp > 0) {
            println()
            println("${necro.name} attacks your party of ${heroes.filter { it.hp > 0 }}.")
            println()
            Thread.sleep(500)
            necroAttack()
            Thread.sleep(200)
            if (gameOverCheck()) {
                println()
                return
            }
        }

        Thread.sleep(500)

        if (golem != null && golem!!.hp > 0) {
            println()
            println("The ${golem?.name} attacks your party of ${heroes.filter { it.hp > 0 }}.")
            println()
            Thread.sleep(500)
            golemAttack()
            Thread.sleep(200)
            if (gameOverCheck()) {
                println()
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

    private fun warriorTurn() {
        warriorAttack()
        if (gameOverCheck()) {
            println()
        }
        Thread.sleep(500)
    }

    private fun mageTurn() {
        mageAttack()
        if (gameOverCheck()) {
            println()
        }
        Thread.sleep(500)
    }

    private fun clericTurn() {
        clericAttack()
        if (gameOverCheck()) {
            println()
        }
        Thread.sleep(500)
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
            It's $warrior's turn.
            
            Choose which ability to use:
            1. Slam (Deal 50 dmg to an enemy.)
            2. Sword Swipe (Deal 30 dmg to all enemies.)
            3. Taunt (Force enemies to target ${warrior.name} for 3 turns.)
            4. Battle Shout (Increase your durability by 10%.)
            5. Use Item
            """.trimIndent()
        val errMsg = "Invalid selection. Please select a valid attack:"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(500)
                if (enemies.filter { it.hp > 0 }.size > 1) {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        warrior.slam(golem!!)
                    } else {
                        val target = targetEnemy()
                        warrior.slam(target)
                    }
                } else {
                    if (golem != null && golem!!.isTaunting && golem!!.hp > 0) {
                        warrior.slam(golem!!)
                    } else {
                        warrior.slam(enemies.filter { it.hp > 0 }[0])
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
                Thread.sleep(500)
                if (!useInventory()) {
                    warriorAttack()
                }
            }
        }
    }

    private fun mageAttack() {
        val prompt =
            """
            It's $mage's turn.
            
            Choose which ability to use:
            1. Fireball (Deal 35-45 dmg to each enemy.)
            2. Lightning Bolt (Deal 50-60 dmg to an enemy.)
            3. Magic Missile (Deal 20-35 dmg to a random enemy then repeat this.)
            4. Burn (Set target enemy on fire dealing 30 dmg and burning them for 10 each turn.)
            5. Use Item
            """.trimIndent()
        val errMsg = "Invalid selection. Please select a valid attack:"

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
                Thread.sleep(500)
                if (!useInventory()) {
                    mageAttack()
                }
            }
        }
    }

    private fun clericAttack() {
        val prompt =
            """
            It's $cleric's turn.
            
            Choose which ability to use:
            1. Healing Hands (Heal an ally for 30-40hp.)
            2. Healing Wave (Heal each ally for 20-30hp.)
            3. Dispel (Dispel a debuff.)
            4. Cripple (Reduce an enemies dmg dealt by 10%.)
            5. Use Item
            """.trimIndent()
        val errMsg = "Please select a valid attack:"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(500)
                val target = targetHero()
                if (target.cantHeal) {
                    println("The target is grievously wounded and can't be healed currently. Try another action.")
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
                Thread.sleep(500)
                if (!useInventory()) {
                    clericAttack()
                }
            }
        }
    }

    private fun useInventory(): Boolean {
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
            ${enemies.filter { it.hp > 0 }}
            Select a target 1, 2, ...:
            """.trimIndent()
        val errMsg = "Please select a valid target:"
        val target =
            enemies.filter { it.hp > 0 }[select(prompt, errMsg, enemies.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun targetHero(): Hero {
        val prompt =
            """
            ${heroes.filter { it.hp > 0 }}
            Select a target 1, 2, ...:
            """.trimIndent()
        val errMsg = "Please select a valid target:"
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