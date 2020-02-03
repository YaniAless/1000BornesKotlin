import data.model.*
import javafx.beans.binding.When
import java.lang.NumberFormatException


class MainView {

    private fun readPlayerChoice(): Int{
        var playerChoice: String? = readLine()
        println("Your choice is => $playerChoice")

        playerChoice?.let {
            try {
                var choice: Int = playerChoice.toInt()
                if(choice >= 0 && choice < 6){
                    return choice
                }
                else{
                    println("Il faut choisir une valeur de 0 à 5")
                    readPlayerChoice()
                }
            }
            catch (e: NumberFormatException){
                println("Désolé je n'ai pas compris ton choix, essaye encore")
                readPlayerChoice()
            }
        }
        return -1
    }


    private fun displayPlayerCardSet(player : Player){
        var i = 1
        player.cardSet.forEach {
            println("${i} - ${it.title} : ${if (it.buff != null) it.buff else it.debuff}")

            i++
        }
    }

    public fun displayPlayerScore(player: Player){
        when(player.score){
            0 -> println("Vous êtes à ${player.score} bornes, il serait temps de démarrer nan ?")
            in 100..500 -> println("Ah vous êtes parti ! Continuez comme ça ! Vous êtes à ${player.score} bornes")
            else -> println("Vous y êtes presque ! Vous êtes à ${player.score} bornes, allez encore un peu et vous y serez !")
        }

    }

    public fun askPlayerWhatDo(player : Player): Int {
        println("Alors, que voulez-vous faire cher ${player.name} ?\n")
        println("Voici tes cartes :")
        displayPlayerCardSet(player)

        return readPlayerChoice()
        /*
        var i = 1
        player.cardSet.forEach {
            println("${i} - ${it.title} : ${if (it.buff != null) it.buff else it.debuff}")
            i++
        } */
        //return i - 2 //return card choice
    }



    public fun askWhoToDebuff(playerList: List<Player>): Int {
        println("Un débuff ? T'es un BADASS. A Qui veux tu arracher ses grands morts ?")
        var i = 1
        playerList.forEach {
            println("$i - ${it.name}")
            i++
        }
        return i - 1
    }


    /**
     * Les fonctions ci-dessous permettent de rendre plus agréable l'application en affichant les titres / séparateurs etc.
     */
    public fun displayGameTitle(){
        println("\n" +
                "    ▄▄▄▄      ▄▄▄▄▄▄▄▄▄    ▄▄▄▄▄▄▄▄▄    ▄▄▄▄▄▄▄▄▄        ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄ \n" +
                "  ▄█░░░░▌    ▐░░░░░░░░░▌  ▐░░░░░░░░░▌  ▐░░░░░░░░░▌      ▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌\n" +
                " ▐░░▌▐░░▌   ▐░█░█▀▀▀▀▀█░▌▐░█░█▀▀▀▀▀█░▌▐░█░█▀▀▀▀▀█░▌     ▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀▀▀ ▐░█▀▀▀▀▀▀▀▀▀ \n" +
                "  ▀▀ ▐░░▌   ▐░▌▐░▌    ▐░▌▐░▌▐░▌    ▐░▌▐░▌▐░▌    ▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌▐░▌    ▐░▌▐░▌          ▐░▌          \n" +
                "     ▐░░▌   ▐░▌ ▐░▌   ▐░▌▐░▌ ▐░▌   ▐░▌▐░▌ ▐░▌   ▐░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄▄▄ ▐░█▄▄▄▄▄▄▄▄▄ \n" +
                "     ▐░░▌   ▐░▌  ▐░▌  ▐░▌▐░▌  ▐░▌  ▐░▌▐░▌  ▐░▌  ▐░▌     ▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌\n" +
                "     ▐░░▌   ▐░▌   ▐░▌ ▐░▌▐░▌   ▐░▌ ▐░▌▐░▌   ▐░▌ ▐░▌     ▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░█▀▀▀▀█░█▀▀ ▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀█░▌\n" +
                "     ▐░░▌   ▐░▌    ▐░▌▐░▌▐░▌    ▐░▌▐░▌▐░▌    ▐░▌▐░▌     ▐░▌       ▐░▌▐░▌       ▐░▌▐░▌     ▐░▌  ▐░▌    ▐░▌▐░▌▐░▌                    ▐░▌\n" +
                " ▄▄▄▄█░░█▄▄▄▐░█▄▄▄▄▄█░█░▌▐░█▄▄▄▄▄█░█░▌▐░█▄▄▄▄▄█░█░▌     ▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌      ▐░▌ ▐░▌     ▐░▐░▌▐░█▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄█░▌\n" +
                "▐░░░░░░░░░░░▌▐░░░░░░░░░▌  ▐░░░░░░░░░▌  ▐░░░░░░░░░▌      ▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌       ▐░▌▐░▌      ▐░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌\n" +
                " ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀    ▀▀▀▀▀▀▀▀▀    ▀▀▀▀▀▀▀▀▀        ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀▀  ▀         ▀  ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀▀▀▀ \n" +
                "                                                                                                                                      \n")
    }

    public fun displayStartMessage() {
        println("La partie commence !")
    }
}