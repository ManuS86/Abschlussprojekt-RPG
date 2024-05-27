import kotlin.math.roundToInt
import kotlin.system.exitProcess

class Game(private val heroes: List<Hero>, private val enemies: MutableList<Enemy>, private val inventory: Inventory) {
    private val necro: Necromancer = enemies[0] as Necromancer
    private var golem: Golem? = null
    private val cleric: Cleric = heroes[0] as Cleric
    private val mage: Mage = heroes[1] as Mage
    private val warrior: Warrior = heroes[2] as Warrior
    private var cursedHero: Hero? = null

    private val red = "\u001B[31m"
    private val green = "\u001B[32m"
    private val yellow = "\u001B[33m"
    private val blue = "\u001B[34m"
    private val bold = "\u001B[1m"
    private val reset = "\u001B[0m"

    fun gameLoop() {
        println()
        println("The heroes $blue$bold$cleric, $mage and $warrior$reset are fighting the boss $yellow$bold$necro$reset.")
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
        println("${bold}Game Over$reset")
        if (heroes.all { it.hp <= 0 }) {
            Thread.sleep(200)
            println()
            println("$red${bold}All your heroes are dead. You lost!$reset")
        } else {
            Thread.sleep(200)
            println()
            println("$green${bold}All enemies are defeated. You won!$reset")
        }
    }

    private fun newGame() {
        Thread.sleep(500)
        val prompt =
            """
            Do you want to play again?
            1. $bold${green}Yes$reset
            2. $bold${red}No$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again$reset."
        when (select(prompt, errMsg, 2)) {
            1 -> main()
            2 -> exitProcess(0)
        }
    }

    private fun gameOverCheck(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round(round: Int) {
        println("$bold----- Round $round -----$reset")
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                cursedHero = null
            }
            println()
            println("$blue$bold${cursedHero!!.name}$reset is cursed and loses $red$bold${cursedHero!!.maxHp * 0.1}hp$reset.")
            cursedHero!!.hp -= (cursedHero!!.maxHp * 0.1).roundToInt()
            Thread.sleep(200)
        }

        enemies.forEach {
            if (it.burning) {
                it.hp -= (10 * mage.dmgMod).roundToInt()
                println()
                println("$yellow$bold$it$reset is burning and takes $red${bold}10 dmg$reset.")
                Thread.sleep(200)
            }
        }

        println()
        println("Your party of $blue$bold${heroes.filter { it.hp > 0 }}$reset attacks $yellow$bold${enemies.filter { it.hp > 0 }}$reset.")
        Thread.sleep(200)

        val attackers = heroes.filter { it.hp > 0 }.toMutableList()


        while (attackers.size > 0) {
            val prompt =
                """
            $blue$bold$attackers$reset$blue
            Select an attacker 1, 2, ...:$reset
            """.trimIndent()
            val errMsg = "${red}Invalid Input. Please try again:$reset"

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
            println("$yellow$bold${necro}$reset attacks your party of $blue$bold${heroes.filter { it.hp > 0 }}$reset.")
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
            println("The $yellow$bold${golem?.name} attacks your party of $blue$bold${heroes.filter { it.hp > 0 }}$reset.")
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
            $blue
            It's $bold$warrior's$reset$blue turn.
            
            Choose which ability to use:
            1. Stab (Deal $red${bold}50 dmg$reset$blue to an enemy.)
            2. Sword Swipe (Deal $red${bold}30 dmg$reset$blue to all enemies.)
            3. Taunt (Force enemies to target $bold${warrior.name}$reset$blue for 3 turns.)
            4. Battle Shout (Increase your durability by ${green}10%$reset$blue.)
            5. Use Item$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again:$reset"

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
            $blue
            It's $bold$mage's$reset$blue turn.
            
            Choose which ability to use:
            1. Fireball (Deal $red${bold}35-45 dmg$reset$blue to each enemy.)
            2. Lightning Bolt (Deal $red${bold}50-60 dmg$reset$blue to an enemy.)
            3. Magic Missile (Deal $red${bold}20-35 dmg$reset$blue to a random enemy then repeat this.)
            4. Burn (Deal $red${bold}30 dmg$reset$blue and set the target on fire burning them for 10 each turn.)
            5. Use Item$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again:$reset"

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
            $blue
            It's $bold$cleric's$reset$blue turn.
            
            Choose which ability to use:
            1. Healing Hands (Heal an ally for $green${bold}30-40hp$reset$blue.)
            2. Healing Wave (Heal each ally for $green${bold}20-30hp$reset$blue.)
            3. Dispel (Dispel a debuff.)
            4. Cripple (Reduce an enemy's dmg dealt by $red${bold}10%$reset$blue.)
            5. Use Item$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again:$reset"

        when (select(prompt, errMsg, 5)) {
            1 -> {
                Thread.sleep(500)
                val target = targetHero()
                if (target.cantHeal) {
                    println("${red}The target is grievously wounded and can't be healed currently. Try another action.$reset")
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
            $blue
            $bold$inventory$reset$blue
            1. Health Potion
            2. Elixir
            Select an item to use:$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again:$reset"
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
            $blue
            $yellow$bold${enemies.filter { it.hp > 0 }}$reset$blue
            Select a target 1, 2, ...:$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again:$reset"
        val target =
            enemies.filter { it.hp > 0 }[select(prompt, errMsg, enemies.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun targetHero(): Hero {
        val prompt =
            """
            $blue
            $bold${heroes.filter { it.hp > 0 }}$reset$blue
            Select a target 1, 2, ...:$reset
            """.trimIndent()
        val errMsg = "${red}Invalid Input. Please try again:$reset"
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