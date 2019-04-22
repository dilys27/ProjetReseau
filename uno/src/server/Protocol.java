package server;

import java.util.Collection;

//Classe abstraite regroupant les méthodes à appeler en cas d'events du jeu
public interface Protocol {
	default void sendName(String name){}
	default void sendNameOK(){}
	default void sendplay_card(String user, String card){}
	default void sendpick_card(String user){}
	default void sendpass(String user) {}
	default void senduno(String user) {}
	default void sendcontre_uno(String user) {}
	default void sendchange_color(String user, String color) {}
	default void senddisconnect_game(String user) {}
	default void sendAskUserList() {}
	default void sendUserList(Collection<String> ulist) {}
	default void sendquit() {}
	default void sendNameBad() {}
}
