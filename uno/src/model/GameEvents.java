package model;

//Classe regroupant les différents évènements se déclenchant lors du jeu
public enum GameEvents {
	PLAY_CARD, PICK_CARD, PASS, UNO, CONTRE_UNO, CHANGE_COLOR, //évènements qui peuvent se produire au cours du jeu
	DISCONNECT_GAME, //demande de déconnexion de la partie 
	QUIT //deconnexion du serveur lorsque le joueur clique sur exit
}

