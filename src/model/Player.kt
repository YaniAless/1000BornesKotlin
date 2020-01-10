package data.model

import com.sun.org.apache.xpath.internal.operations.Bool

//Array card not null
data class Player(val name : String, var cardSet: MutableList<Card>, var debuffStatusList : MutableList<DebuffStatus>, var buffStatusList : MutableList<BotteStatus> = mutableListOf(), var score: Int = 0)


enum class BotteStatus(val id: Int, title: String) {
    ACE(1, "As du volant"),
    TANKER(2, "Camion-Citerne"),
    PUNCTURE_PROOF(3, "Increvable"),
    PRIMARY(4, "Prioritaire");

    companion object {
        fun fromInt(value: Int): BotteStatus? = BotteStatus.values().find { it.id == value }
    }
}

enum class DebuffStatus(val id: Int, title: String) {
    ACCIDENT(1, "Accident de la route"),
    BANKRUPT(2, "Panne d'essence"),
    PUNCTURE(3, "Crevaison"),
    SPEED_LIMIT(4, "Limitation de vitesse"),
    REDLIGHT(5, "Feu rouge");

    companion object {
        fun fromInt(value: Int): DebuffStatus? = DebuffStatus.values().find { it.id == value }
    }
}