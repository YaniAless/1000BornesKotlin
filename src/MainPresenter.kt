import data.model.*

class MainPresenter(private val playerNumber: Int) {

    var gameEnd: Boolean = false
    lateinit var board: Board
    val mainView = MainView()
    var cardDeck = prepareCardList()
    var gameTurns = 0

    init {
        prepareGame()
        launchGame()
    }

    /**
     * Préparation du jeu, création des joueurs, de la pioche
     */
    private fun prepareGame() {

        var playerList: Array<Player> = arrayOf()
        for (p in 1.. playerNumber) {
            val cardSet = mutableListOf<Card>()
            for (i in 1..6) {
                cardSet.add(cardDeck.removeAt(0))
            }
            playerList += Player("Player $p", cardSet, mutableListOf(DebuffStatus.REDLIGHT))
        }

        board = Board(playerList)
    }

    /**
     * Fonction de déroulement du jeu.
     * Pour chaque joueur, il lui est demandé une action sur la fonction askPlayerWhatDo (paramètre = Player, valeur de retour = int)
     * La fonction askWhoToDebuff s'active dès lors que le joueur utilise un débuff, on lui demande donc l'ennemi à attaquer( paramètre = Liste de Player, valeur de retour = Player)
     */
    private fun launchGame() {
        mainView.displayGameTitle()
        mainView.displayStartMessage()
        Thread.sleep(1500)
        while (!gameEnd) {
            board.playerList.forEach { player ->
                gameTurns++
                mainView.displayTurnNumber(gameTurns)
                var response = false
                var cardChoice: Int = 0
                var hasDroppedCard = false
                while (!response) {
                    mainView.displayTurnMessage(player)
                    mainView.displayPlayerBuff(player)
                    mainView.displayPlayerDebuff(player)
                    mainView.displayPlayerScore(player)

                    cardChoice = mainView.askPlayerWhatDo(player)

                    if (cardChoice == 6) {
                        dropACard(player)
                        hasDroppedCard = true
                        response = true
                    } else {
                        val cardChoosen: Card = player.cardSet[cardChoice]
                        if (cardChoosen.buff !== null) {
                            response = handlePlayerChoice(player, cardChoosen)
                            mainView.displayCardPlayed(cardChoosen)
                        } else {
                            val otherPlayer = mainView.askWhoToDebuff(board.playerList.filter { player.name != it.name }, cardChoosen)
                            response = handlePlayerChoice(player, cardChoosen, otherPlayer)
                        }
                    }
                }
                if (!hasDroppedCard) {
                    player.cardSet.removeAt(cardChoice)
                    pickACard(player)
                }
                println("Au tour de l'adversaire !")
                Thread.sleep(2000)
                checkGameEnd()
            }
        }
    }

    private fun dropACard(player: Player) {
        val cardId = mainView.askPlayerWhichCardToDrop(player)
        val chosenCard = player.cardSet[cardId]
        player.cardSet.remove(chosenCard)
        mainView.displayCardPlayed(chosenCard,true)
        pickACard(player)
    }

    private fun pickACard(player: Player) {
        if(cardDeck.isNotEmpty()) {
            val pickedCard = cardDeck.removeAt(0)
            player.cardSet.add(pickedCard)
            mainView.displayPickedCard(pickedCard)
        }
    }

    /**
     * Vérifie le choix de la carte du joueur, et agit en conséquence
     */
    private fun handlePlayerChoice(player: Player, cardChoice: Card, foe: Player? = null): Boolean {
        when (cardChoice.id) {
            Card.SPEED25.id -> return addSpeedToPlayer(player, 25)
            Card.SPEED50.id -> return addSpeedToPlayer(player, 50)
            Card.SPEED75.id -> return addSpeedToPlayer(player, 75)
            Card.SPEED100.id -> return addSpeedToPlayer(player, 100)
            Card.SPEED200.id -> return addSpeedToPlayer(player, 200)
            Card.GREENLIGHT.id -> return removeDeBuffToPlayer(player, DebuffStatus.REDLIGHT.id)
            Card.REDLIGHT.id -> return addDeBuffToPlayer(foe, DebuffStatus.REDLIGHT.id)
            Card.REPAIR.id -> return removeDeBuffToPlayer(player, DebuffStatus.ACCIDENT.id)
            Card.ACCIDENT.id -> return addDeBuffToPlayer(foe, DebuffStatus.ACCIDENT.id)
            Card.LIMIT.id -> return addDeBuffToPlayer(foe, DebuffStatus.SPEED_LIMIT.id)
            Card.NOLIMIT.id -> return removeDeBuffToPlayer(player, DebuffStatus.SPEED_LIMIT.id)
            Card.PUNCTURE.id -> return addDeBuffToPlayer(foe, DebuffStatus.PUNCTURE.id)
            Card.PUNCTURE_REPARE.id -> return removeDeBuffToPlayer(player, DebuffStatus.PUNCTURE.id)
            Card.OUTOFFUEL.id -> return addDeBuffToPlayer(foe, DebuffStatus.BANKRUPT.id)
            Card.REFUEL.id -> return removeDeBuffToPlayer(player, DebuffStatus.BANKRUPT.id)
            Card.ACE.id -> return addBuffToPlayer(player, BotteStatus.ACE.id)
            Card.TANKER.id -> return addBuffToPlayer(player, BotteStatus.TANKER.id)
            Card.PUNCTURE_PROOF.id -> return addBuffToPlayer(player, BotteStatus.PUNCTURE_PROOF.id)
            Card.PRIMARY.id -> return addBuffToPlayer(player, BotteStatus.PRIMARY.id)
            else -> return false
        }
    }

    /**
     * Ajoute le buff choisit au joueur
     */
    private fun addBuffToPlayer(player: Player, id: Int): Boolean {
        val botteStatus = BotteStatus.fromInt(id)
        if (botteStatus != null) {
            var isBuffable = true
            player.buffStatusList.forEach {
                if (it.id == botteStatus.id) isBuffable = false
            }

            if (isBuffable) {
                player.buffStatusList.add(botteStatus)
                return true
            }
        }
        return false
    }

    /**
     * Débuff l'ennemi choisit
     */
    private fun addDeBuffToPlayer(foe: Player?, id: Int): Boolean {
        foe?.buffStatusList?.forEach {
            if (it.id == id || it.id == BotteStatus.PRIMARY.id || id == DebuffStatus.REDLIGHT.id) {
                return false
            }
        }

        val debuffStatus = DebuffStatus.fromInt(id)
        if (debuffStatus != null && foe?.debuffStatusList !== null) {
            var isDebuffable: Boolean = true
            foe.debuffStatusList.forEach {
                if (it.id == debuffStatus.id) {
                    isDebuffable = false
                }
            }

            if (isDebuffable) {
                foe.debuffStatusList.add(debuffStatus)
                return true
            }
        }
        return false
    }

    /**
     * Supprime un débuff lorsque le joueur utilise une carte buff pour supprimer un débuff
     */
    private fun removeDeBuffToPlayer(player: Player, id: Int): Boolean {
        if(!player.debuffStatusList.contains(DebuffStatus.fromInt(id))) {
            return false
        }

        val filteredDebuffStatusList: MutableList<DebuffStatus> = player.debuffStatusList.filter { it.id != id }.toMutableList()
        player.debuffStatusList = filteredDebuffStatusList
        return true
    }

    /**
     * Ajoute le nombre de points au score
     */
    private fun addSpeedToPlayer(player: Player, speed: Int): Boolean {
        if (player.debuffStatusList.size == 0) {
            player.score += speed
            return true
        } else {
            return false
        }
    }

    /**
     * Prépare la pioche
     */
    private fun prepareCardList(): MutableList<Card> {
        var cardList: MutableList<Card> = mutableListOf(Card.ACE, Card.TANKER, Card.PUNCTURE_PROOF, Card.PRIMARY)
        for (i in 1..3) {
            cardList.add(Card.ACCIDENT)
            cardList.add(Card.OUTOFFUEL)
            cardList.add(Card.PUNCTURE)
        }

        for (i in 1..4) {
            cardList.add(Card.LIMIT)
            cardList.add(Card.SPEED200)
        }

        for (i in 1..4) {
            cardList.add(Card.REDLIGHT)
        }

        for (i in 1..6) {
            cardList.add(Card.REPAIR)
            cardList.add(Card.REFUEL)
            cardList.add(Card.PUNCTURE_REPARE)
            cardList.add(Card.NOLIMIT)
        }

        for (i in 1..14) {
            cardList.add(Card.GREENLIGHT)
        }

        for (i in 1..10) {
            cardList.add(Card.SPEED25)
            cardList.add(Card.SPEED50)
            cardList.add(Card.SPEED75)
        }

        for (i in 1..12) {
            cardList.add(Card.SPEED100)
        }

        return cardList.apply { this.shuffle() }
    }

    /**
     * Vérifie la fin du jeu ( possiblement non finie)
     */
    private fun checkGameEnd() {
        board.playerList.forEach {
            if (it.score >= 1000 || cardDeck.size == 0) {
                gameEnd = true
            }
        }
    }
}