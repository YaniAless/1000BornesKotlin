package data.model


enum class Card(val id : Int, val title : String, val buff: String?, val debuff : String? ){
    SPEED25(1, "+25 bornes", "Augmente la vitesse de 25", null),
    SPEED50(1, "+50 bornes", "Augmente la vitesse de 50", null),
    SPEED75(2, "+75 bornes", "Augmente la vitesse de 75", null),
    SPEED100(3, "+100 bornes", "Augmente la vitesse de 100", null),
    SPEED200(4, "+200 bornes", "Augmente la vitesse de 200", null),
    GREENLIGHT(5, "Feu vert", "Feu vert, vous pouvez rouler", null),
    REDLIGHT(6, "Feu rouge", null, "Feu rouge ! L'adversaire doit s'arreter"),
    REPAIR(7, "Réparation", "Votre voiture est réparée, roulez jeunesse !", null),
    ACCIDENT(8, "Accident", null, "Aïe, accident ! L'adversaire doit s'arreter"),
    LIMIT(9, "Limitation de vitesse", null, "Met une limitation de vitesse à l'adversaire"),
    NOLIMIT(10, "Fin de limitation de vitesse","Enlève votre limitation de vitesse", null),
    PUNCTURE(11, "Crevaison", null,"Attention ! La roue de votre adversaire est crevée, il faut la changer"),
    PUNCTURE_REPARE(12, "Roue de secours", "Change votre roue crevée", null),
    OUTOFFUEL(13, "Panne d'essence", null,"Plus d'essence ! Votre adversaire doit attendre et remplir son reservoir"),
    REFUEL(14, "Essence", "Votre réservoir est plein, mettez le pied au plancher !", null),
    ACE(15, "As du volant (Botte)", "Vous ne pouvez plus être atteint par un accident", null),
    TANKER(16, "Camion-citerne (Botte)", "Vous ne pouvez plus être à court d'essence", null),
    PUNCTURE_PROOF(17, "Increvalbe (Botte)", "Avec vos roues increvables, vous ne pouvez plus être atteitn de crevaison", null),
    PRIMARY(18, "Véhicule prioritaire (Botte)", "Vous êtes prioritaires, vous ne vous arretez plus à un feu rouge", null );
}