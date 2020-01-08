import data.model.*

const val PLAYER_NUMBER = 2

class MainPresenter {

    //private var presenter: GamePresenter = GamePresenter(this)

    init {
        launchGame()
    }


    private fun launchGame() {
        val board = Board(playerList = Array<Player>(PLAYER_NUMBER) { _ -> Player("Test") })
    }
}