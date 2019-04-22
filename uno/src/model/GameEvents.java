package model;

//Classe regroupant les diff�rents �v�nements se d�clenchant lors du jeu
public enum GameEvents {
	PLAY_CARD, PICK_CARD, PASS, UNO, CONTRE_UNO, CHANGE_COLOR, //�v�nements qui peuvent se produire au cours du jeu
	DISCONNECT_GAME, //demande de d�connexion de la partie 
	QUIT //deconnexion du serveur lorsque le joueur clique sur exit
}

