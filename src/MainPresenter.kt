import com.sun.org.apache.xpath.internal.operations.Bool
import data.model.*

const val PLAYER_NUMBER = 2

class MainPresenter {

    var gameEnd : Boolean = false
    lateinit var board : Board
    val mainView = MainView()
    var cardDeck = prepareCardList()
    //private var presenter: GamePresenter = GamePresenter(this)

    init {
        prepareGame()
        launchGame()
    }


    private fun prepareGame() {

        var playerList : Array<Player> = arrayOf()
        for(p in 1 .. PLAYER_NUMBER) {
            var cardSet = mutableListOf<Card>()
            for( i in 1 .. 6) {
                cardSet.add(cardDeck.removeAt(0))
            }
            playerList += Player("Player $p", cardSet, mutableListOf(DebuffStatus.REDLIGHT))
        }

        board = Board(playerList)
    }

    private fun  launchGame() {
        while (gameEnd == false) {
            board.playerList.forEach {player ->
                var response = false
                var cardChoice: Int = 0
                while(!response) {
                    cardChoice = mainView.askPlayerWhatDo(player)
                    val cardChoosen: Card = player.cardSet[cardChoice]


                    if (cardChoosen.buff !== null) {
                        response = handlePlayerChoice(player, cardChoosen)
                    } else {
                        val otherPlayer = mainView.askWhoToDebuff(board.playerList.filter { player.name != it.name })
                        response = handlePlayerChoice(player, cardChoosen)
                    }
                }
                player.cardSet.removeAt(cardChoice)

                player.cardSet.add(cardDeck.removeAt(0))

                checkGameEnd()
            }
        }
    }

    private fun handlePlayerChoice(player: Player, cardChoice : Card, foe : Player? = null): Boolean {
        when(cardChoice.id) {
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

    private fun addBuffToPlayer(player: Player, id: Int): Boolean {
        val botteStatus = BotteStatus.fromInt(id)
        if(botteStatus != null) {
            var isBuffable: Boolean = true
            player.buffStatusList.forEach {
                if(it.id == botteStatus.id) {
                    isBuffable = false
                }
            }

            if(isBuffable) {
                player.buffStatusList.add(botteStatus)
                return true
            }
        }
        return false
    }

    private fun addDeBuffToPlayer(foe: Player?, id: Int): Boolean {
        foe?.buffStatusList?.forEach {
            if(it.id == id || it.id == BotteStatus.PRIMARY.id && id == DebuffStatus.REDLIGHT.id) {
                return false
            }
        }

        val debuffStatus = DebuffStatus.fromInt(id)
        if(debuffStatus != null && foe?.debuffStatusList !== null) {
            var isDebuffable: Boolean = true
            foe.debuffStatusList.forEach {
                if(it.id == debuffStatus.id) {
                    isDebuffable = false
                }
            }

            if(isDebuffable) {
                foe.debuffStatusList.add(debuffStatus)
                return true
            }
        }
        return false
    }

    private fun removeDeBuffToPlayer(player: Player, id: Int): Boolean {
        val filteredDebuffStatusList: MutableList<DebuffStatus> = player.debuffStatusList.filter {it.id != id}.toMutableList()
        if(filteredDebuffStatusList.isNotEmpty()) {
            player.debuffStatusList = filteredDebuffStatusList
            return true
        }
        return false
    }

    private fun addSpeedToPlayer(player: Player, speed: Int): Boolean {
        if(player.debuffStatusList.size == 0) {
            player.score += speed
            return true
        } else {
            return false
        }
    }

    fun prepareCardList() : MutableList<Card> {
        var cardList : MutableList<Card> =  mutableListOf(Card.ACE, Card.TANKER, Card.PUNCTURE_PROOF, Card.PRIMARY)
        for (i in 1 .. 3) {
            cardList.add(Card.ACCIDENT)
            cardList.add(Card.OUTOFFUEL)
            cardList.add(Card.PUNCTURE)
        }

        for (i in 1 .. 4) {
            cardList.add(Card.LIMIT)
            cardList.add(Card.SPEED200)
        }

        for (i in 1 .. 4) {
            cardList.add(Card.REDLIGHT)
        }

        for (i in 1 .. 6) {
            cardList.add(Card.REPAIR)
            cardList.add(Card.REFUEL)
            cardList.add(Card.PUNCTURE_REPARE)
            cardList.add(Card.NOLIMIT)
        }

        for (i in 1 .. 14) {
            cardList.add(Card.GREENLIGHT)
        }

        for (i in 1 .. 10) {
            cardList.add(Card.SPEED25)
            cardList.add(Card.SPEED50)
            cardList.add(Card.SPEED75)
        }

        for (i in 1 .. 12) {
            cardList.add(Card.SPEED100)
        }

        return cardList.apply { this.shuffle() }
    }

    private fun checkGameEnd() {
        board.playerList.forEach {
            if(it.score >= 1000 || cardDeck.size == 0) {
                gameEnd = true
            }
        }
    }
}