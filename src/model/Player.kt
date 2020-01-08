package data.model
//Array card not null
data class Player(val name : String, var cardSet: Array<Card>? = null, var score: Int = 0, var buffStatusList : Array<BotteStatus>? = null, var debuffStatusList : Array<DebuffStatus>? = null)


enum class BotteStatus(id: Int, title: String) {
    ACE(1, "As du volant"),
    TANKER(2, "Camion-Citerne"),
    PUNCTURE_PROOF(3, "Increvable"),
    PRIMARY(4, "Prioritaire")
}

enum class DebuffStatus(id: Int, title: String) {
    ACCIDENT(1, "Accident de la route"),
    BANKRUPT(2, "Panne d'essence"),
    PUNCTURE(3, "Crevaison"),
    SPEED_LIMIT(4, "Limitation de vitesse"),
    REDLIGHT(5, "Feu rouge")
}