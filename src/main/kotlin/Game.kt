class Game(val heroes: List<Hero>, val enemies: MutableList<Enemy>, val inventory: Inventory) {
    val boss: Necromancer = enemies[0] as Necromancer
    var golem: Golem? = null
    val cleric: Cleric = heroes[0] as Cleric
    val mage: Mage = heroes[1] as Mage
    val warrior: Warrior = heroes[2] as Warrior
    var cursedHero: Hero? = null
    var burningEnemies: MutableList<Enemy> = mutableListOf()

    fun gameLoop() {
        println("The heroes $cleric, $mage and $warrior} are fighting the boss $boss.")

        while (!isGameOver()) {
            round()
        }

        println("Game Over")
        if (heroes.all { it.hp <= 0 }) {
            println("All your heroes are dead. You lost!")
        } else {
            println("All enemies are dead. You won!")
        }
    }

    private fun isGameOver(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round() {
        if (cursedHero != null) {
            boss.curse(cursedHero!!)
        }

        if (burningEnemies.isNotEmpty()) {
            burningEnemies.forEach { it.hp -= 10 }
            println("${burningEnemies.forEach { it.name}} is burning and takes 10 damage.")
        }

        println("It's your party's turn to attack $boss and his minions.")
        clericAttack()
        mageAttack()
        warriorAttack()

        println("It's ${boss.name}'s turn to attack your party.")
        bossAttack()

        if (enemies.size > 1) {
            println("It's the ${golem}'s turn to attack your party.")
            golemAttack()
        }
    }

    private fun golemAttack() {
        when ((1..3).random()) {
            1 -> {
                golem!!.smash(heroes[(0..2).random()])
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
        println(
            """
            Choose which ability to use for $warrior:
            1. Slam (Deal 50 damage to an enemy.)
            2. Shield Block ()
            3. Taunt ()
            4. Battle Shout (Increase your damage by 10%.)
            """.trimIndent()
        )

        when (attackSelect()) {
            "1" -> {
                warrior.slam(enemies[readln().toInt()])
            }

            "2" -> {
                warrior.shieldBlock()
            }

            "3" -> {
                warrior.taunt()
            }

            "4" -> {
                warrior.battleShout()
            }

            else -> {
                println("Invalid selection. Please select a valid attack:")
                warriorAttack()
            }
        }
    }

    private fun mageAttack() {
        println(
            """
                    Choose which ability to use for $mage:
                    1. Fireball (Deal 40 damage to each enemy.)
                    2. Lightning Bolt (Deal 60 damage to an enemy.)
                    3. Magic Missile (Deal 20-30 damage to a random enemy then repeat this.)
                    4. Burn (Set target enemy on fire dealing 20 damage and burning them for 10 each turn.)
                    """.trimIndent()
        )

        when (attackSelect()) {
            "1" -> {
                mage.fireball(enemies)
            }

            "2" -> {
                mage.lightningBolt(enemies[readln().toInt()])
            }

            "3" -> {
                mage.magicMissile(enemies)
            }

            "4" -> {
                mage.burn(enemies[readln().toInt()])
            }

            else -> {
                println("Invalid selection. Please select a valid attack:")
                mageAttack()
            }
        }
    }

    private fun clericAttack() {
        println(
            """
            Choose which ability to use for ${cleric}:
            1. Healing Hands (Heal an ally for 30-40hp.)
            2. Healing Wave (Heal each ally for 20-30hp.)
            3. Dispel (Dispel a debuff.)
            4. Cripple (Reduce an enemies damage dealt by 10%.)
            """.trimIndent()
        )

        when (attackSelect()) {
            "1" -> {
                cleric.healingHands(heroes[readln().toInt()])
            }

            "2" -> {
                cleric.healingWave(heroes)
            }

            "3" -> {
                cleric.dispel(heroes[readln().toInt()])
            }

            "4" -> {
                cleric.cripple(enemies[readln().toInt()])
            }

            else -> {
                println("Invalid selection. Please select a valid attack:")
                clericAttack()
            }
        }
    }

    private fun bossAttack() {
        when ((1..6).random()) {
            1 -> {
                boss.(heroes[(0..2).random()])
            }

            2 -> {
                boss.(heroes[(0..2).random()])
            }

            3 -> {
                boss.(heroes[(0..2).random()])
            }

            4 -> {
                boss.(heroes[(0..2).random()])
            }

            5 -> {
                if (cursedHero == null) {
                    cursedHero = heroes[(0..2).random()]
                    boss.curse(cursedHero!!)
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

private fun attackSelect(): String {
    return readln()
}
}