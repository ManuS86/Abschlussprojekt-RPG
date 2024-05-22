class Game(val heroes: List<Hero>, val enemies: MutableList<Enemy>, val inventory: Inventory) {
    val boss = enemies.find { it is Necromancer }!!

    fun gameLoop() {
        println("The heroes ${heroes[0]}, ${heroes[1]} and ${heroes[2]} are fighting the boss $boss.")
    }

    fun round() {
        //if (heroes.find { it is Hero is cursed } != null)
        println("It's your turn to attack $boss.")
        println(
            """
                Choose which ability to use for ${heroes[0]}:
                1. Healing Hands (Heal an ally for 30-40hp.)
                2. Healing Wave (Heal each ally for 20-30hp.)
                3. Dispel (Dispel a debuff.)
                4. Cripple (Reduce an enemies damage dealt by 10%.)
                """.trimIndent()
        )

        when (attackSelect()) {
            "1" -> {
                heroes[0].healingHands(heroes[readln()])
            }

            "2" -> {
                heroes[0].healingWave(heroes)
            }

            "3" -> {
                heroes[0].dispel(heroes[readln()])
            }

            "4" -> {
                heroes[0].cripple(enemies[readln()])
            }

            else -> {
                println("Invalid selection. Please select a valid attack:")
                attackSelect()
            }
        }

        println(
            """
                Choose which ability to use for ${heroes[1]}:
                1. Fireball (Deal 40 damage to each enemy.)
                2. Lightning Bolt (Deal 60 damage to an enemy.)
                3. Magic Missile (Deal 20-30 damage to a random enemy then repeat this.)
                4. Burn (Set target enemy on fire dealing 20 damage and burning them for 10 each turn.)
                """.trimIndent()
        )

        when (attackSelect()) {
            "1" -> {
                heroes[1].fireball(enemies)
            }

            "2" -> {
                heroes[1].lightningBolt(enemies[readln()])
            }

            "3" -> {
                heroes[1].magicMissile(enemies)
            }

            "4" -> {
                heroes[1].burn(enemies[readln()])
            }

            else -> {
                println("Invalid selection. Please select a valid attack:")
                attackSelect()
            }
        }

        println(
            """
                Choose which ability to use for ${heroes[2]}:
                1. Slam (Deal 50 damage to an enemy.)
                2. Shield Block ()
                3. Taunt ()
                4. Battle Shout (Increase your damage by 10%.)
                """.trimIndent()
        )

        when (attackSelect()) {
            "1" -> {
                heroes[2].slam(enemies[readln()])
            }

            "2" -> {
                heroes[2].shieldBlock()
            }

            "3" -> {
                heroes[2].taunt()
            }

            "4" -> {
                heroes[2].battleShout()
            }

            else -> {
                println("Invalid selection. Please select a valid attack:")
                attackSelect()
            }
        }

        println("It's ${enemies[0]} turn to attack your party.")
        bossAttack()

        if (enemies.size > 1) {
            println("It's ${enemies[1]} turn to attack your party.")

            when ((1..6).random().toString()) {
                "1" -> {
                    enemies[0].(heroes[(0..2).random()])
                }

                "2" -> {
                    enemies[0].(heroes)
                }

                "3" -> {
                    enemies[0].()
                }
            }
        }
    }

    fun bossAttack() {
        when ((1..6).random().toString()) {
            "1" -> {
                enemies[0].(heroes[(0..2).random()])
            }

            "2" -> {
                enemies[0].(heroes[(0..2).random()])
            }

            "3" -> {
                enemies[0].(heroes[(0..2).random()])
            }

            "4" -> {
                enemies[0].(heroes[(0..2).random()])
            }

            "5" -> {
                if (!enemies[].curseActive) {
                    enemies[0].(heroes[(0..2).random()])
                } else {
                    bossAttack()
                }
            }

            "6" -> {
                    enemies[0].()
            }
        }
    }
}

fun attackSelect(): String {
    return readln()
}