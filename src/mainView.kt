import data.model.*
import javafx.beans.binding.When
import java.lang.NumberFormatException


class MainView {

    fun askPlayerWhatDo(player : Player): Int {
        clear()
        println("Alors, que voulez-vous faire cher ${player.name} ?\n")
        println("Voici tes cartes :")
        displayPlayerCardSet(player)
        return readPlayerCardChoice()
    }

    private fun readPlayerCardChoice(isDropping: Boolean = false): Int{
        var playerChoice: String? = readLine()

        var max = if(isDropping) 6 else 7

        playerChoice?.let {
            try {
                var choice: Int = playerChoice.toInt()
                if(choice in 1..max){
                    return choice-1
                }
                else{
                    println("Il te faut choisir une valeur comprise entre 1 et 7")
                    readPlayerCardChoice()
                }
            }
            catch (e: NumberFormatException){
                println("Désolé je n'ai pas compris ton choix, insert une valeur comprise entre 1 et 7")
                readPlayerCardChoice()
            }
        }
        return -1
    }


    private fun displayPlayerCardSet(player : Player, isDropping: Boolean = false){
        var i = 1
        player.cardSet.forEach {
            println("$i - ${it.title} : ${if (it.buff != null) it.buff else it.debuff}")
            i++
        }
        if(!isDropping) println("7 - Defausser : Tu choisis de defausser une carte")
    }

    fun askWhoToDebuff(playerList: List<Player>, card: Card): Int {
        clear()
        println("Ah ! Un peu d'action ! Qui veux-tu attaquer avec ta carte ${card.title}")
        displayPlayerList(playerList)
        return readPlayerTargetChoice(playerList)
    }

    fun askPlayerWhichCardToDrop(player: Player): Int{
        clear()
        displayPlayerCardSet(player, true)
        return readPlayerCardChoice()
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

    fun displayPlayerScore(player: Player){
        when(player.score){
            0 -> println("Vous êtes à ${player.score} bornes, il serait temps de démarrer nan ?")
            in 100..500 -> println("Ah vous êtes parti ! Continuez comme ça ! Vous êtes à ${player.score} bornes")
            else -> println("Vous y êtes presque ! Vous êtes à ${player.score} bornes, allez encore un peu et vous y serez !")
        }
    }

    fun displayPickedCard(pickedCard: Card){
        println("Vous piochez une carte")
        println("Vous avez pioché la carte : ${pickedCard.title}")
        if(pickedCard.buff != null) println("Effet : ${pickedCard.buff}") else println("Effet : ${pickedCard.debuff}")
    }

    fun displayPlayerBuff(player: Player){
        if(player.buffStatusList.count() > 0) println("Voici vos bonus --> ${player.buffStatusList}") else println("Vous n'avez aucun bonus")
    }

    fun displayPlayerDebuff(player: Player){
        if(player.debuffStatusList.count() > 0) println("Voici vos malus --> ${player.debuffStatusList}") else println("Vous n'avez aucun malus")

    }


    /**
     * Les fonctions ci-dessous permettent de rendre plus agréable l'application en affichant les titres / séparateurs etc.
     */
    fun displayGameTitle(){
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

    fun displayStartMessage() {
        println("La partie commence !")
    }

    fun clear(){
        print("\u001b[H\u001b[2J")
    }
}