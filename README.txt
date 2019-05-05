UNO

Pour lancer le jeu :
- Installer la version 11 du jdk JAVA si pas déjà installée (lien : https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
- Lancer uno.jar (le serveur du jeu se met en route au lancement de l'application et le joueur se connecte automatiquement en cliquant sur le bouton Play UNO)
- Après avoir cliqué sur Play UNO, il faut choisir avec combien de joueurs vous voulez jouer (cependant il n'y a qu'avec 2 joueurs que le jeu fonctionne)
- Une fois le nombre de joueurs choisis, la partie commence

Si vous n'avez pas Windows, pour lancer le jeu, il faut le faire en utilisant votre IDE.
Le main du programme est dans src/app/App.java.

Pour tester le jeu :
Lorsque la partie a été lancée, il y a une deuxième fenêtre qui apparaît qui correspond à un deuxième joueur. Et vous pouvez donc, utiliser l'une des fenêtres en alternant pour donner l'illusion de deux joueurs qui jouent en même temps (car le jeu ne fonctionne qu'avec un seul client).
Il faut commencer par la fenêtre ou se trouve le Player 1 en bas et juste en dessous il y a la mention "Playing" qui permet de savoir à quel joueur c'est le tour de jouer.
Dans le menu déroulant, vous trouverez toutes les cartes en main du joueur que vous pouvez jouer en cliquant dessus. Et sur le côté, des boutons pour effectuer des actions comme passer son tour, prendre une carte, dire uno et contre-uno.
Au milieu de l'écran vert se trouve le nom de la carte en haut de la pile du jeu avec la couleur et le numéro et/ou le symbole à jouer. 
Pour plus d'infos sur les règles du jeu, voici le lien : https://fr.wikipedia.org/wiki/Uno

Partie Réseau :
Vous pouvez vérifiez la connexion d'un client en lançant le jeu à partir d'Eclipse.
Dans la console, un message tel que le suivant devrait apparaître si vous effectuer les étapes pour commencer une partie dans l'application telles que décritent précédemment :
System Message : Server started...
Client Connecté Adresse IP : Socket[addr=/127.0.0.1,port=50090,localport=1234]
 