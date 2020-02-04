import data.model.*
import javafx.beans.binding.When
import java.lang.NumberFormatException


class MainView {

    public fun askPlayerWhatDo(player : Player): Int {
        println("Alors, que voulez-vous faire cher ${player.name} ?\n")
        println("Voici tes cartes :")
        displayPlayerCardSet(player)
        return readPlayerCardChoice()
    }

    private fun readPlayerCardChoice(): Int{
        var playerChoice: String? = readLine()
        playerChoice?.let {
            try {
                var choice: Int = playerChoice.toInt()
                if(choice > 0 && choice <= 6){
                    return choice-1
                }
                else{
                    println("Il faut choisir une valeur comprise entre 1 et 6")
                    readPlayerCardChoice()
                }
            }
            catch (e: NumberFormatException){
                println("Désolé je n'ai pas compris ton choix, insert une valeur comprise entre 1 et 6")
                readPlayerCardChoice()
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

    public fun askWhoToDebuff(playerList: List<Player>, card: Card): Int {
        println("Ah ! Un peu d'action ! Qui veux-tu attaquer avec ta carte ${card.title}")
        displayPlayerList(playerList)
        return readPlayerTargetChoice(playerList)
    }

    private fun readPlayerTargetChoice(playerList: List<Player>): Int{
        var playerChoice: String? = readLine()
        playerChoice?.let {
            try {
                var choice: Int = playerChoice.toInt()
                if(choice >= 0 && choice <= playerList.count()){
                    return choice
                }
                else{
                    println("Il faut choisir une valeur comprise entre 0 et ${playerList.count()}")
                    readPlayerTargetChoice(playerList)
                }
            }
            catch (e: NumberFormatException){
                println("Désolé je n'ai pas compris ton choix, insert une valeur comprise entre 1 et 6")
                readPlayerTargetChoice(playerList)
            }
        }
        return -1
    }

    private fun displayPlayerList(playerList: List<Player>){
        println("Voici la liste des cibles, fais le bon choix !")
        var i = 0
        playerList.forEach {
            println("-------------------------------------CIBLE NUMERO '$i'------------------------------------")
            print("Cible : '${it.name}' --- Score actuel : '${it.score}'\n")
            if(it.buffStatusList.count() > 0) println("Liste des bonus : ${it.buffStatusList}") else println("Il ne possède aucun bonus")
            if(it.debuffStatusList.count() > 0) println("Liste des malus : ${it.debuffStatusList}") else println("Il ne possède aucun malus")
            println("-------------------------------------CIBLE NUMERO '$i'------------------------------------")
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

    public fun displayPickedCard(pickedCard: Card){
        println("Vous piochez une carte")
        println("Vous avez pioché la carte : ${pickedCard.title}")
        if(pickedCard.buff != null) println("Elle a pour effet : ${pickedCard.buff}") else println("Elle a pour effet : ${pickedCard.debuff}")
    }

    public fun displayPlayerBuff(player: Player){
        if(player.buffStatusList.count() > 0) println("Voici vos bonus --> ${player.buffStatusList}") else println("Vous n'avez aucun bonus")
    }

    public fun displayPlayerDebuff(player: Player){
        if(player.debuffStatusList.count() > 0) println("Voici vos malus --> ${player.debuffStatusList}") else println("Vous n'avez aucun malus")

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