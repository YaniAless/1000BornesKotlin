package module

const val PLAYER_NUMBER = 2

import data.model.*

class MainPresenter: MainInterface {

    //private var presenter: GamePresenter = GamePresenter(this)

    init {
        it.launchGame()
    }

    fn launchGame() {
        var playerList = Array<Player>
        for (i in 0 ..< 2) {
            playerList[i] = Player("Test")
        }

        val board = Board(playerList = playerList)
    }
}