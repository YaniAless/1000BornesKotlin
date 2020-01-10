import data.model.*


class MainView {

    fun askPlayerWhatDo(player : Player): Int {
        println("Blyat ! What do you want to do ${player.name} ?\n")
        println("Voici tes cartes :")
        var i = 1
        player.cardSet.forEach {
            println("${i} - ${it.title} : ${if (it.buff != null) it.buff else it.debuff}")
            i++
        }
        return i - 2 //return card choice
    }

    fun askWhoToDebuff(playerList: List<Player>): Int {
        println("Un débuff ? T'es un BADASS. A Qui veux tu arracher ses grands morts ?")
        var i = 1
        playerList.forEach {
            println("$i - ${it.name}")
            i++
        }
        return i - 1
    }
}