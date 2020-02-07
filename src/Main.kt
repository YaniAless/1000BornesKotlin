fun main(args: Array<String>) {
    MainPresenter(playerNumber = askPlayerNumber())
}

fun askPlayerNumber(): Int {
    println("Combien de personnes vont jouer ?")
    val playerNumber: String? = readLine()
    if(playerNumber != null) {
        return playerNumber.toInt()
    } else {
        return 2
    }
}
