import com.sun.org.apache.xpath.internal.operations.Bool
import data.model.*

const val PLAYER_NUMBER = 2

class MainPresenter {

    var gameEnd : Boolean = false
    var board = Board(playerList = Array<Player>(PLAYER_NUMBER) { i -> Player("Player ${i+1}") })
    val mainView = MainView()
    //private var presenter: GamePresenter = GamePresenter(this)

    init {
        prepareGame()
        launchGame()
    }


    private fun prepareGame() {

    }

    private fun  launchGame() {
        while (gameEnd == false) {
            board.playerList.forEach {
                mainView.askPlayerWhatDo(it)
                gameEnd = true
            }
        }
    }
}