import data.model.*
import java.lang.NumberFormatException


class MainView {

    fun displayTurnMessage(player: Player) {
        println("--------------------------------------${player.name}--------------------------------------")
        println("A ton tour ${player.name} ! Que veux-tu faire ?")
    }

    fun askPlayerWhatDo(player: Player): Int {
        Thread.sleep(1000)
        println("\nVoici tes cartes :")
        displayPlayerCardSet(player)
        return readPlayerCardChoice()
    }

    private fun readPlayerCardChoice(isDropping: Boolean = false): Int {
        val playerChoice: String? = readLine()
        val max = if (isDropping) 6 else 7

        playerChoice?.let {
            try {
                val choice: Int = playerChoice.toInt()
                if (choice in 1..max) {
                    return choice - 1
                } else {
                    println("Il te faut choisir une valeur comprise entre 1 et 7")
                    return readPlayerCardChoice()
                }
            } catch (e: NumberFormatException) {
                println("Désolé je n'ai pas compris ton choix, insert une valeur comprise entre 1 et 7")
                return readPlayerCardChoice()
            }
        }
        return -1
    }

    private fun displayPlayerCardSet(player: Player, isDropping: Boolean = false) {
        player.cardSet.forEachIndexed { i, it ->
            println("${i+1} - ${it.title} : ${if (it.buff != null) it.buff else it.debuff}")
        }
        if (!isDropping) println("7 - Defausser : Tu choisis de defausser une carte")
    }

    fun askWhoToDebuff(playerList: List<Player>, card: Card): Player {
        println("Ah ! Un peu d'action ! Qui veux-tu attaquer avec ta carte ${card.title}")
        displayPlayerList(playerList)
        val chosenPlayerId = readPlayerTargetChoice(playerList)
        return playerList[chosenPlayerId]
    }

    fun askPlayerWhichCardToDrop(player: Player): Int {
        displayPlayerCardSet(player, true)
        return readPlayerCardChoice()
    }

    fun displayCardPlayed(card: Card, wasDrop: Boolean = false) {
        if (wasDrop) println("Tu as défaussé la carte : ${card.title}") else println("Tu as joué la carte : ${card.title}")
    }

    private fun readPlayerTargetChoice(playerList: List<Player>): Int {
        val playerChoice: String? = readLine()
        playerChoice?.let {
            try {
                val choice: Int = playerChoice.toInt()
                if (choice in 0..playerList.count()) {
                    return choice
                } else {
                    println("Il faut choisir une valeur comprise entre 0 et ${playerList.count()}")
                    return readPlayerTargetChoice(playerList)
                }
            } catch (e: NumberFormatException) {
                println("Désolé je n'ai pas compris ton choix, insert une valeur comprise entre 1 et 6")
                return readPlayerTargetChoice(playerList)
            }
        }
        return -1
    }

    private fun displayPlayerList(playerList: List<Player>) {
        println("Voici la liste des cibles, fais le bon choix !")
        playerList.forEachIndexed { i, it ->
            println("-------------------------------------CIBLE NUMERO '$i'------------------------------------")
            print("Cible : '${it.name}' --- Score actuel : '${it.score}'\n")
            if (it.buffStatusList.count() > 0) println("Liste des bonus : ${it.buffStatusList}") else println("Il ne possède aucun bonus")
            if (it.debuffStatusList.count() > 0) println("Liste des malus : ${it.debuffStatusList}") else println("Il ne possède aucun malus")
            println("-------------------------------------CIBLE NUMERO '$i'------------------------------------")
        }
    }

    fun displayPlayerScore(player: Player) {
        when (player.score) {
            0 -> println("Tu es à ${player.score} bornes, il serait temps de démarrer nan ?")
            in 100..500 -> println("Ah tu es parti ! Continue comme ça ! Tu es à ${player.score} bornes")
            else -> println("Tu y es presque ! Tu es à ${player.score} bornes, allez encore un peu et tu y seras !")
        }
    }

    fun displayPickedCard(pickedCard: Card) {
        println("Tu as pioché la carte : ${pickedCard.title}")
        if (pickedCard.buff != null) println("Effet : ${pickedCard.buff}") else println("Effet : ${pickedCard.debuff}")
    }

    fun displayPlayerBuff(player: Player) {
        if (player.buffStatusList.count() > 0) println("Voici tes bonus --> ${player.buffStatusList }") else println("Tu n'as aucun bonus")
    }

    fun displayPlayerDebuff(player: Player) {
        if (player.debuffStatusList.count() > 0) println("Voici tes malus --> ${player.debuffStatusList}") else println("Tu n'as aucun malus")
    }

    fun displayTurnNumber(turnNum: Int){
        println("\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n------------------------------------------ TOUR $turnNum ------------------------------------------")
    }


    /**
     * Les fonctions ci-dessous permettent de rendre plus agréable l'application en affichant les titres / séparateurs etc.
     */
    fun displayGameTitle() {
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
}