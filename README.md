# 1000BornesKotlin

Ce projet a pour objectif de recréer le jeu du 1000 bornes en ligne de commande en utilisant Kotlin

## Explication du code
### Le modèle
Le modèle est basé sur 3 classes :

* La classe Board, qui est la classe contenant les joueurs et le jeu en général 
* La classe Player, qui est la classe de chaque joueur. Elle contient les cartes de sa main, son nombre de points, ses buffs et debuffs, etc
* La classe Card, qui contient les différentes cartes, une description ainsi que les avantages qu'elles confèrent


### Déroulement
Pour chaque lancement du jeu, un nombre de Player fixe est créé.

On va donc commencer par préparer la pioche, avec les mêmes règles que le 1000 bornes classique.
Par la suite, nous allons créer les joueurs sont créés, et leur main est ajoutée depuis la pioche.
Ensuite vient le commencement du jeu. Pour chaque tour, tous les joueurs sont appelés. Ils vont donc utiliser une carte dans leur main.
Au choix de cette carte, nous allons vérifier si c'est un malus ou un bonus. Si c'est un bonus, alors le bonus est directement ajouté au joueur.
Si c'est un malus, alors nous allons demander au joueur sur quel ennemi l'utiliser.
Enfin, le joueur pioche une carte depuis la pioche, et son tour est fini.

### Fin du jeu
Le jeu se termine lorsqu'un joueur a atteint un certain nombre de points (qui est modifiable), ou alors si la pioche est vide et que personne ne peut plus jouer. Dans ce cas, la personne la plus proche du nombre de points maximal est le gagnant.
