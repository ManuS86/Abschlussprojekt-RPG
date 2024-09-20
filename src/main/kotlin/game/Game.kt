package game

import blue1
import blue2
import bold
import game.consumables.Inventory
import game.enemies.Enemy
import game.enemies.Golem
import game.enemies.Necromancer
import game.heroes.Cleric
import game.heroes.Hero
import game.heroes.Mage
import green1
import green2
import game.heroes.Warrior
import main
import red1
import red2
import reset
import white
import yellow1
import yellow2
import kotlin.math.roundToInt
import kotlin.system.exitProcess

class Game(private val heroes: List<Hero>, private val enemies: MutableList<Enemy>, private val inventory: Inventory) {
    private val necro: Necromancer = enemies[0] as Necromancer
    private var golem: Golem? = null
    private val cleric: Cleric = heroes[0] as Cleric
    private val mage: Mage = heroes[1] as Mage
    private val warrior: Warrior = heroes[2] as Warrior
    private var cursedHero: Hero? = null
    private var inventoryUsed = false

    fun gameLoop() {
        gameIntro()

        var nr = 1

        Thread.sleep(600)

        while (!gameOverCheck()) {
            round(nr)
            nr++
        }

        gameOver(nr - 1)

        Thread.sleep(600)

        newGame()
    }

    private fun gameIntro() {
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
        Thread.sleep(600)
        println()
        println("The heroes $cleric, $mage and $warrior are fighting the boss $necro.")
        Thread.sleep(400)
        println("                                            Defeat him before it's too late!")
        println()
        println()
    }

    private fun newGame() {
        val prompt =
            """
            Do you want to play again?
            [1] $bold${green2}Yes$reset
            [2] $bold${red2}No$reset
            """.trimIndent()
        when (select(prompt, 2)) {
            1 -> {
                Thread.sleep(600)
                main()
            }

            2 -> {
                exitProcess(0)
            }
        }
    }

    private fun gameOver(nr: Int) {
        //println("$bold$white                  ---------------------------------------- GAME OVER ----------------------------------------$reset")
        println(
            "$bold$white                               ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ \n" +
                    "                              ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗\n" +
                    "                              ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝\n" +
                    "                              ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
                    "                              ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║\n" +
                    "                               ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝\n$reset"
        )
        if (heroes.all { it.hp <= 0 }) {
            Thread.sleep(200)
            println("                                                $white>>> The fight lasted $nr rounds <<<$reset")
            println()
            println("                                            $white>>>$reset $yellow2${bold}All your ${blue2}heroes$reset$yellow2${bold} are dead. You lost!$reset $white<<<$reset")
        } else {
            Thread.sleep(200)
            println()
            println("                                                $white>>> The fight lasted $nr rounds <<<$reset")
            println()
            println("                                            $white>>>$reset $green2${bold}All ${red2}enemies$reset$green2${bold} are defeated. You won!$reset $white<<<$reset")
        }
    }

    private fun gameOverCheck(): Boolean {
        return enemies.all { it.hp <= 0 } || heroes.all { it.hp <= 0 }
    }

    private fun round(nr: Int) {
        beginningOfRound(nr)

        heroesTurn()

        Thread.sleep(600)

        if (necro.hp > 0) {
            necroAttack()

            if (gameOverCheck()) {
                return
            }
        }

        Thread.sleep(600)

        if (golem != null && golem!!.hp > 0) {
            golemAttack()

            if (gameOverCheck()) {
                return
            }
        }

        endOfRound()
    }

    private fun beginningOfRound(nr: Int) {
        println("$bold$white               ------------------------------------------- ROUND $nr -------------------------------------------$reset")
        curseTracker()
        burnTracker()
    }

    private fun endOfRound() {
        warriorTauntTracker()

        golemTauntTracker()

        cantHealTracker()

        inventoryUsed = false
        println()
    }

    private fun cantHealTracker() {
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
    }

    private fun golemTauntTracker() {
        if (golem != null && golem!!.isTaunting) {
            golem!!.tauntTimer--
        }

        if (golem != null && golem!!.tauntTimer == 0) {
            golem!!.isTaunting = false
        }
    }

    private fun warriorTauntTracker() {
        if (warrior.isTaunting) {
            warrior.tauntTimer--
        }

        if (warrior.tauntTimer == 0) {
            warrior.isTaunting = false
        }
    }

    private fun heroesTurn() {
        println()
        println("Your party of ${heroes.filter { it.hp > 0 }} attacks ${enemies.filter { it.hp > 0 }}.")

        val attackers = heroes.filter { it.hp > 0 }.toMutableList()

        while (!gameOverCheck() && attackers.size > 0) {
            val prompt =
                """
                    $attackers
                    Select an attacker ${blue2}[1, 2, ..]$reset:
                    """.trimIndent()

            Thread.sleep(400)

            if (attackers.size > 1) {
                when (attackers[select(prompt, attackers.size) - 1]) {
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
    }

    private fun burnTracker() {
        enemies.forEach {
            if (it.burning) {
                it.hp -= 15 * mage.skillMod
                println()
                println("                                         $white>>>$reset $red2$bold${it.name}$reset is ${yellow1}burning$reset and takes ${yellow2}${(15 * mage.skillMod).roundToInt()} dmg$reset $white<<<$reset")
                Thread.sleep(200)
            }
        }
    }

    private fun curseTracker() {
        if (cursedHero != null) {
            if (cursedHero!!.hp <= cursedHero!!.maxHp * 0.2) {
                println("$cursedHero's curse ended.")
                cursedHero = null
            }
            cursedHero!!.hp -= cursedHero!!.maxHp * 0.1
            println()
            println("                                           $white>>>$reset $blue2$bold${cursedHero!!.name}$reset is ${yellow1}cursed$reset and loses $yellow2${(cursedHero!!.maxHp * 0.1).roundToInt()} hp$reset $white<<<$reset")
            Thread.sleep(200)
        }
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
        golemLogo()
        when ((1..3).random()) {
            1 -> {
                Thread.sleep(600)
                if (warrior.isTaunting && warrior.hp > 0) {
                    golem!!.smash(warrior)
                } else {
                    golem!!.smash(heroes.filter { it.hp > 0 }.random())
                }
            }

            2 -> {
                Thread.sleep(600)
                golem!!.groundSlam(heroes.filter { it.hp > 0 })
            }

            3 -> {
                Thread.sleep(600)
                golem!!.taunt()
            }
        }
        println()
    }

    private fun golemLogo() {
        println(
            """
                                                        ⠀⠀⠀⠀$red1$bold⢶⡆⠀⠀⣴⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⠀⢠⣾⣿⣦⣤⣭⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⣰⠏⠀⢹⣻⣭⣭⡧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⢠⠏⠀⠴⠚⣷⣿⣿⠀⠀⢀⡤⠖⠛⠹⠶⠤⢄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⡏⠀⠀⠀⡼⠉⠉⠁⢀⡴⠋⠀⠀⠤⢄⡀⠀⠀⠈⢢⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⡇⠀⠀⠀⢧⡀⠀⢠⠎⠀⢠⣤⡞⠒⠲⡌⠃⠀⠀⠀⠱⢤⡀⠀⢀⣀⣀⣀⠀⠀
                                                        ⠀⣧⠀⠀⠀⠀⠙⠲⠏⠀⢀⡀⠙⣇⠀⠀⢘⡶⠆⣤⠤⠔⢲⣯⡖⠉⠀⠀⠈⢧⠀
                                                        ⠀⢺⣦⡀⠀⠂⠀⠀⠀⠀⠀⢠⣄⠼⣗⠒⠋⠀⠀⠹⣄⣠⣿⡋⡀⢠⣤⡆⠀⢸⠀
                                                        ⠀⠀⠀⣇⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠈⠦⣠⠴⣄⢀⣠⣄⣸⠇⠀⣳⣿⣧⠈⢹⠁
                                                        ⠀⠀⠀⠘⠶⡆⠀⠆⢶⣴⠀⢾⠀⠀⠀⠀⠀⠀⠈⠉⡼⡭⣭⡴⠖⠼⠛⣿⣿⠏⠀
                                                        ⠀⠀⠀⠀⠀⢻⠀⠀⠀⠁⠀⠘⡄⠀⣠⢤⣀⡤⡄⢸⣿⣿⠋⠀⠀⠀ ⠀⠙⠁⠀⠀
                                                        ⠀⠀⠀⠀⠀⣏⠀⠀⠀⠀⠀⠀⠈⠉⠁⠀⠀⠀⠘⠛⢱⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⠀⠀⠀⣸⠁⠀⠀⠸⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⡞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠚⠃⠀⢿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⠀⠀⠀⠹⡆⠀⠀⠀⣷⣄⢠⡀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⠀⠀⠀⢸⠃⠀⡄⠀⠀⠺⠾⠃⠀⠀⠀⠀⠾⠀⢹⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⠀⣀⣀⡴⠋⠀⠛⠁⠀⠀⠀⠀⠀⠀⢀⡄⠀⠀⠀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠀⡞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠃⠀⢀⣾⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⢸⠁⠀⠀⠀⠀⣤⡄⠀⠀⠀⡴⠛⠲⡄⠀⠀⠀⠀⠸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⡇⠀⠀⠀⣀⠀⠘⠀⠀⣠⠞⠁⠀⠀⢣⠀⠀⠀⠀⠠⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                        ⠘⠒⠒⠶⠁⠉⠉⠉⠉⠀⠀⠀⠀⡞⠀⠀⠰⠇⠐⠛⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀
                                                            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣼⠁⠀⠀⠀⠀⠀⠀⠈⢳⡄⠀⠀⠀⠀⠀⠀⠀
                                                            ⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀⠈⠉⠙⠷⠤⠤⠤⠤⠿⠉⠁
                        $reset
                        The $golem attacks your party of ${heroes.filter { it.hp > 0 }}.
                        """.trimIndent()
        )
        println()
        Thread.sleep(400)
    }

    private fun necroAttack() {
        necroLogo()
        if (golem == null && necro.hp <= necro.maxHp * 0.5) {
            Thread.sleep(600)
            necro.summonGolem(enemies)
            golem = enemies[1] as Golem
        } else {
            when ((1..5).random()) {
                1 -> {
                    Thread.sleep(600)
                    necro.deathWave(heroes.filter { it.hp > 0 })
                }

                2 -> {
                    Thread.sleep(600)
                    if (warrior.isTaunting && warrior.hp > 0) {
                        necro.blight(warrior)
                    } else {
                        necro.blight(heroes.filter { it.hp > 0 }.random())
                    }
                }

                3 -> {
                    Thread.sleep(600)
                    if (warrior.isTaunting && warrior.hp > 0) {
                        necro.vampiricTouch(warrior)
                    } else {
                        necro.vampiricTouch(heroes.filter { it.hp > 0 }.random())
                    }
                }

                4 -> {
                    Thread.sleep(600)
                    if (warrior.isTaunting && warrior.hp > 0) {
                        necro.grievousWounds(warrior)
                    } else {
                        necro.grievousWounds(heroes.filter { it.hp > 0 }.random())
                    }
                }

                5 -> {
                    Thread.sleep(600)
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
            }
        }
        println()
    }

    private fun necroLogo() {
        println(
            """
                                                         $red1$bold.                                                      .
                                                       .n                   .                 .                  n.
                                                 .   .dP                  dP                   9b                 9b.    .
                                                4    qXb         .       dX                     Xb       .        dXp     t
                                               dX.    9Xb      .dXb    __                         __    dXb.     dXP     .Xb
                                               9XXb._       _.dXXXXb dXXXXbo.                 .odXXXXb dXXXXb._       _.dXXP
                                                9XXXXXXXXXXXXXXXXXXXVXXXXXXXXOo.           .oOXXXXXXXXVXXXXXXXXXXXXXXXXXXXP
                                                 `9XXXXXXXXXXXXXXXXXXXXX'~   ~`OOO8b   d8OOO'~   ~`XXXXXXXXXXXXXXXXXXXXXP'
                                                   `9XXXXXXXXXXXP' `9XX'          `98v8P'          `XXP' `9XXXXXXXXXXXP'
                                                       ~~~~~~~       9X.          .db|db.          .XP       ~~~~~~~
                                                                       )b.  .dbo.dP'`v'`9b.odb.  .dX(
                                                                     ,dXXXXXXXXXXXb     dXXXXXXXXXXXb.
                                                                    dXXXXXXXXXXXP'   .   `9XXXXXXXXXXXb
                                                                   dXXXXXXXXXXXXb   d|b   dXXXXXXXXXXXXb
                                                                   9XXb'   `XXXXXb.dX|Xb.dXXXXX'   `dXXP
                                                                    `'      9XXXXXX(   )XXXXXXP      `'
                                                                             XXXX X.`v'.X XXXX
                                                                             XP^X'`b   d'`X^XX
                                                                             X. 9  `   '  P )X
                                                                             `b  `       '  d'
                                                                              `             '
                        $reset
                        $necro attacks your party of ${heroes.filter { it.hp > 0 }}.
                        """.trimIndent()
        )
        println()
        Thread.sleep(400)
    }

    private fun warriorAttack() {
        val prompt =
            """
                                 $bold$blue1/}
                                //
                               /{     />
                ,_____________///----/{_______________________________________________
              /|=============|/\|---/-/_______________________________________________\
              \|=============|\/|---\-\_______________________________________________/
                '~~~~~~~~~~~~~\\\----\{
                               \{     \>
                                \\
                                 \}
            $reset
                $white>>>$reset It's $warrior$blue2$bold's$reset turn $white<<<$reset

            Choose which ability to use:
            $white[1] $bold${blue1}Stab$reset (Deal ${yellow2}${(50 * warrior.skillMod).roundToInt()} dmg$reset to $bold${red2}an enemy$reset.)
            $white[2] $bold${blue1}Cleave$reset (Deal ${yellow2}${(30 * warrior.skillMod).roundToInt()} dmg$reset to $bold${red2}each enemy$reset.)
            $white[3] $bold${blue1}Taunt$reset (Force $bold${red2}enemies$reset to target $bold$blue2${warrior.name}$reset for ${green2}3 turns$reset.)
            $white[4] $bold${blue1}Battle Shout$reset (Increase $bold${blue2}your$reset tenacity by ${green2}10%$reset.)
            $white[5] $bold${blue1}Use Item$reset
            """.trimIndent()

        when (select(prompt, 5)) {
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
                if (!inventoryUsed) {
                    if (!useInventory()) {
                        warriorAttack()
                    } else {
                        inventoryUsed = true
                    }
                } else {
                    println("${red1}!You have already used your inventory this round, try something else!$reset")
                    warriorAttack()
                }
            }
        }
    }

    private fun mageAttack() {
        val prompt =
            """
                    $blue1${bold}__...--~~~~~-._   _.-~~~~~--...__
                  //               `V'               \\ 
                 //                 |                 \\ 
                //__...--~~~~~~-._  |  _.-~~~~~~--...__\\ 
               //__.....----~~~~._\ | /_.~~~~----.....__\\
              ====================\\|//====================
            $reset
                $white>>>$reset It's $mage$blue2$bold's$reset turn $white<<<$reset

            Choose which ability to use:
            $white[1] $bold${blue1}Fireball$reset (Deal ${yellow2}${(35 * mage.skillMod).roundToInt()}-${(45 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}each enemy$reset.)
            $white[2] $bold${blue1}Lightning Bolt$reset (Deal ${yellow2}${(50 * mage.skillMod).roundToInt()}-${(60 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}an enemy$reset.)
            $white[3] $bold${blue1}Magic Missiles$reset (Deal ${yellow2}${(20 * mage.skillMod).roundToInt()}-${(35 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}a random enemy$reset, then repeat $bold${blue1}this$reset.)
            $white[4] $bold${blue1}Searing Touch$reset (Deal ${yellow2}${(30 * mage.skillMod).roundToInt()} dmg$reset to $bold${red2}an enemy$reset and burn them for an additional ${yellow2}${(15 * mage.skillMod).roundToInt()} dmg$reset ${green2}each turn$reset.)
            $white[5] $bold${blue1}Use Item$reset
            """.trimIndent()

        when (select(prompt, 5)) {
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
                if (!inventoryUsed) {
                    if (!useInventory()) {
                        mageAttack()
                    } else {
                        inventoryUsed = true
                    }
                } else {
                    println("${red1}!You have already used your inventory this round, try something else!$reset")
                    mageAttack()
                }
            }
        }
    }

    private fun clericAttack() {
        val prompt =
            """
                                    $blue1$bold.-.
                              ___  (   )  ___
                         ,_.-'   `'-| |-'`   '-._,
                          '.      .-| |-.      .'
                            `~~~~`  |.') `~~~~`
                                   (_.|
                                    |._)
                                    |.')
                                   (_.|
                                    |._)
                                   ('.|
                                    |._)
                                    '-'
            $reset
                $white>>>$reset It's $cleric$blue2$bold's$reset turn $white<<<$reset
            
            Choose which ability to use:
            $white[1] $bold${blue1}Healing Hands$reset (Heal $bold${blue2}an ally$reset for ${green2}${(35 * mage.skillMod).roundToInt()}-${(45 * mage.skillMod).roundToInt()} hp$reset.)
            $white[2] $bold${blue1}Healing Wave$reset (Heal $bold${blue2}each ally$reset for ${green2}${(25 * mage.skillMod).roundToInt()}-${(35 * mage.skillMod).roundToInt()} hp$reset.)
            $white[3] $bold${blue1}Dispel$reset (Dispel $bold${blue2}an ally's$reset debuff.)
            $white[4] $bold${blue1}Cripple$reset (Reduce $bold${red2}an enemy's$reset dmg dealt by ${yellow2}10%$reset.)
            $white[5] $bold${blue1}Use Item$reset
            """.trimIndent()

        when (select(prompt, 5)) {
            1 -> {
                Thread.sleep(400)
                val target = targetHero()
                if (target.cantHeal) {
                    println("${yellow2}!The target is grievously wounded and can't be healed currently. Try another action!$reset")
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
                if (!inventoryUsed) {
                    if (!useInventory()) {
                        clericAttack()
                    } else {
                        inventoryUsed = true
                    }
                } else {
                    println("${red1}!You have already used your inventory this round, try something else!$reset")
                    clericAttack()
                }
            }
        }
    }

    private fun useInventory(): Boolean {
        val prompt =
            """
            
                >>> $inventory <<<
            [1] $bold${green1}Health Consumables.Potion$reset
            [2] $bold${green1}Consumables.Elixir$reset
            Select an item to use:
            """.trimIndent()

        when (select(prompt, 2)) {
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
            Select a target ${red2}[1, 2, ..]$reset:
            """.trimIndent()

        val target =
            enemies.filter { it.hp > 0 }[select(prompt, enemies.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun targetHero(): Hero {
        val prompt =
            """
            
            ${heroes.filter { it.hp > 0 }}
            Select a target ${blue2}[1, 2, ..]$reset:
            """.trimIndent()
        val target =
            heroes.filter { it.hp > 0 }[select(prompt, heroes.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun select(prompt: String, max: Int): Int {
        println()
        println(prompt)
        while (true) {
            print("$white>$reset ")
            val input = readln().toIntOrNull()
            println()
            if (input != null && input in (1..max)) {
                return input
            }
            println("${red1}!Invalid Input. Please try again!$reset")
        }
    }
}