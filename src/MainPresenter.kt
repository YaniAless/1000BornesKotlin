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
            playerList += Player("Player $p", cardSet)
        }

        board = Board(playerList)
    }

    private fun  launchGame() {
        while (gameEnd == false) {
            board.playerList.forEach {
                mainView.askPlayerWhatDo(it)
                gameEnd = true
            }
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
}