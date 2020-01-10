import data.model.*


class MainView {

    fun askPlayerWhatDo(player : Player) {
        println("Blyat ! What do you want to do ${player.name} ?\n")
        println("Voici tes cartes :")
        var i = 1;
        player.cardSet.forEach {
            println("${i} - ${it.title} : ${if (it.buff != null) it.buff else it.debuff}")
            i++
        }
    }
}