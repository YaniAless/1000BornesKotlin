package data.model

enum class Card(val id : Int, val title : String, val buff: String?, val debuff : String? ){
    SPEED50(1, "+50 bornes", "Augmente la vitesse de 50", null),
    SPEED75(2, "+75 bornes", "Augmente la vitesse de 75", null),
    SPEED100(3, "+100 bornes", "Augmente la vitesse de 100", null),
    SPEED200(4, "+200 bornes", "Augmente la vitesse de 200", null),
    GREENLIGHT(5, "Feu vert", "Feu vert, vous pouvez rouler", null),
    REDLIGHT(6, "Feu rouge", null, "Feu rouge ! L'adversaire doit s'arreter"),
    REPAIR(7, "Réparation", "Votre voiture est réparée, roulez jeunesse !", null),
    ACCIDENT(8, "Accident", null, "Aïe, accident ! L'adversaire doit s'arreter"),


}