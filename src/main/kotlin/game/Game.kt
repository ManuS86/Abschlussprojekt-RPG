package game

import BLUE1
import BLUE2
import BOLD
import game.consumables.Inventory
import game.enemies.Enemy
import game.enemies.Golem
import game.enemies.Necromancer
import game.heroes.Cleric
import game.heroes.Hero
import game.heroes.Mage
import game.heroes.Warrior
import GREEN1
import GREEN2
import main
import RED1
import RED2
import RESET
import WHITE
import YELLOW1
import YELLOW2
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
            "$BOLD$WHITE\n" +
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
                    "                                                                                                     \$\$\$\$\$\$/                      \n$RESET"
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
            [1] $BOLD${GREEN2}Yes$RESET
            [2] $BOLD${RED2}No$RESET
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
            "$BOLD$WHITE                               ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ \n" +
                    "                              ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗\n" +
                    "                              ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝\n" +
                    "                              ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
                    "                              ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║\n" +
                    "                               ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝\n$RESET"
        )
        if (heroes.all { it.hp <= 0 }) {
            Thread.sleep(200)
            println("                                                $WHITE>>> The fight lasted $nr rounds <<<$RESET")
            println()
            println("                                            $WHITE>>>$RESET $YELLOW2${BOLD}All your ${BLUE2}heroes$RESET$YELLOW2${BOLD} are dead. You lost!$RESET $WHITE<<<$RESET")
        } else {
            Thread.sleep(200)
            println()
            println("                                                $WHITE>>> The fight lasted $nr rounds <<<$RESET")
            println()
            println("                                            $WHITE>>>$RESET $GREEN2${BOLD}All ${RED2}enemies$RESET$GREEN2${BOLD} are defeated. You won!$RESET $WHITE<<<$RESET")
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
        println("$BOLD$WHITE               ------------------------------------------- ROUND $nr -------------------------------------------$RESET")
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
                    Select an attacker ${BLUE2}[1, 2, ..]$RESET:
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
                println("                                         $WHITE>>>$RESET $RED2$BOLD${it.name}$RESET is ${YELLOW1}burning$RESET and takes ${YELLOW2}${(15 * mage.skillMod).roundToInt()} dmg$RESET $WHITE<<<$RESET")
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
            println("                                           $WHITE>>>$RESET $BLUE2$BOLD${cursedHero!!.name}$RESET is ${YELLOW1}cursed$RESET and loses $YELLOW2${(cursedHero!!.maxHp * 0.1).roundToInt()} hp$RESET $WHITE<<<$RESET")
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
                                                        ⠀⠀⠀⠀$RED1$BOLD⢶⡆⠀⠀⣴⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
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
                        $RESET
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
                                                         $RED1$BOLD.                                                      .
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
                        $RESET
                        $necro attacks your party of ${heroes.filter { it.hp > 0 }}.
                        """.trimIndent()
        )
        println()
        Thread.sleep(400)
    }

    private fun warriorAttack() {
        val prompt =
            """
                                 $BOLD$BLUE1/}
                                //
                               /{     />
                ,_____________///----/{_______________________________________________
              /|=============|/\|---/-/_______________________________________________\
              \|=============|\/|---\-\_______________________________________________/
                '~~~~~~~~~~~~~\\\----\{
                               \{     \>
                                \\
                                 \}
            $RESET
                $WHITE>>>$RESET It's $warrior$BLUE2$BOLD's$RESET turn $WHITE<<<$RESET

            Choose which ability to use:
            $WHITE[1] $BOLD${BLUE1}Stab$RESET (Deal ${YELLOW2}${(50 * warrior.skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}an enemy$RESET.)
            $WHITE[2] $BOLD${BLUE1}Cleave$RESET (Deal ${YELLOW2}${(30 * warrior.skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}each enemy$RESET.)
            $WHITE[3] $BOLD${BLUE1}Taunt$RESET (Force $BOLD${RED2}enemies$RESET to target $BOLD$BLUE2${warrior.name}$RESET for ${GREEN2}3 turns$RESET.)
            $WHITE[4] $BOLD${BLUE1}Battle Shout$RESET (Increase $BOLD${BLUE2}your$RESET tenacity by ${GREEN2}10%$RESET.)
            $WHITE[5] $BOLD${BLUE1}Use Item$RESET
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
                    println("${RED1}!You have already used your inventory this round, try something else!$RESET")
                    warriorAttack()
                }
            }
        }
    }

    private fun mageAttack() {
        val prompt =
            """
                    $BLUE1${BOLD}__...--~~~~~-._   _.-~~~~~--...__
                  //               `V'               \\ 
                 //                 |                 \\ 
                //__...--~~~~~~-._  |  _.-~~~~~~--...__\\ 
               //__.....----~~~~._\ | /_.~~~~----.....__\\
              ====================\\|//====================
            $RESET
                $WHITE>>>$RESET It's $mage$BLUE2$BOLD's$RESET turn $WHITE<<<$RESET

            Choose which ability to use:
            $WHITE[1] $BOLD${BLUE1}Fireball$RESET (Deal ${YELLOW2}${(35 * mage.skillMod).roundToInt()}-${(45 * mage.skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}each enemy$RESET.)
            $WHITE[2] $BOLD${BLUE1}Lightning Bolt$RESET (Deal ${YELLOW2}${(50 * mage.skillMod).roundToInt()}-${(60 * mage.skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}an enemy$RESET.)
            $WHITE[3] $BOLD${BLUE1}Magic Missiles$RESET (Deal ${YELLOW2}${(20 * mage.skillMod).roundToInt()}-${(35 * mage.skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}a random enemy$RESET, then repeat $BOLD${BLUE1}this$RESET.)
            $WHITE[4] $BOLD${BLUE1}Searing Touch$RESET (Deal ${YELLOW2}${(30 * mage.skillMod).roundToInt()} dmg$RESET to $BOLD${RED2}an enemy$RESET and burn them for an additional ${YELLOW2}${(15 * mage.skillMod).roundToInt()} dmg$RESET ${GREEN2}each turn$RESET.)
            $WHITE[5] $BOLD${BLUE1}Use Item$RESET
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
                    println("${RED1}!You have already used your inventory this round, try something else!$RESET")
                    mageAttack()
                }
            }
        }
    }

    private fun clericAttack() {
        val prompt =
            """
                                    $BLUE1$BOLD.-.
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
            $RESET
                $WHITE>>>$RESET It's $cleric$BLUE2$BOLD's$RESET turn $WHITE<<<$RESET
            
            Choose which ability to use:
            $WHITE[1] $BOLD${BLUE1}Healing Hands$RESET (Heal $BOLD${BLUE2}an ally$RESET for ${GREEN2}${(35 * cleric.skillMod).roundToInt()}-${(45 * cleric.skillMod).roundToInt()} hp$RESET.)
            $WHITE[2] $BOLD${BLUE1}Healing Wave$RESET (Heal $BOLD${BLUE2}each ally$RESET for ${GREEN2}${(25 * cleric.skillMod).roundToInt()}-${(35 * cleric.skillMod).roundToInt()} hp$RESET.)
            $WHITE[3] $BOLD${BLUE1}Dispel$RESET (Dispel $BOLD${BLUE2}an ally's$RESET debuff.)
            $WHITE[4] $BOLD${BLUE1}Cripple$RESET (Reduce $BOLD${RED2}an enemy's$RESET dmg dealt by ${YELLOW2}10%$RESET.)
            $WHITE[5] $BOLD${BLUE1}Use Item$RESET
            """.trimIndent()

        when (select(prompt, 5)) {
            1 -> {
                Thread.sleep(400)
                val target = targetHero()
                if (target.cantHeal) {
                    println("${YELLOW2}!The target is grievously wounded and can't be healed currently. Try another action!$RESET")
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
                    println("${RED1}!You have already used your inventory this round, try something else!$RESET")
                    clericAttack()
                }
            }
        }
    }

    private fun useInventory(): Boolean {
        val prompt =
            """
            
                >>> $inventory <<<
            [1] $BOLD${GREEN1}Health Potion$RESET
            [2] $BOLD${GREEN1}Elixir$RESET
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
            Select a target ${RED2}[1, 2, ..]$RESET:
            """.trimIndent()

        val target =
            enemies.filter { it.hp > 0 }[select(prompt, enemies.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun targetHero(): Hero {
        val prompt =
            """
            
            ${heroes.filter { it.hp > 0 }}
            Select a target ${BLUE2}[1, 2, ..]$RESET:
            """.trimIndent()
        val target =
            heroes.filter { it.hp > 0 }[select(prompt, heroes.filter { it.hp > 0 }.size) - 1]
        return target
    }

    private fun select(prompt: String, max: Int): Int {
        println()
        println(prompt)
        while (true) {
            print("$WHITE>$RESET ")
            val input = readln().toIntOrNull()
            println()
            if (input != null && input in (1..max)) {
                return input
            }
            println("${RED1}!Invalid Input. Please try again!$RESET")
        }
    }
}