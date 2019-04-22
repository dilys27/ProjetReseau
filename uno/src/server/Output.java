package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

import model.Game;

//Classe pour implémenter les méthodes du protocol et ainsi pouvoir les utiliser lorqu'il y a un event
public class Output implements Protocol {
	private Game game;
	private PrintWriter os;
	private OutputStream oso;

	public Output(OutputStream out, Game game) throws IOException { // prend en paramètre le jeu à modifier
		this.game = game;
		this.os = new PrintWriter(out, true);
		this.oso = out;
	}


	@Override
	public synchronized void sendName(String name) {
		os.println("NAME");
		os.println(name);
	}

	@Override
	public synchronized void sendNameOK() {
		os.println("NAME OK");

	}

	@Override
	public synchronized void sendplay_card(String user, String card) {
		os.println("PLAY CARD");
		os.println(user);
		os.println(card);
	}

	@Override
	public synchronized void sendpick_card(String user) {
		os.println("PICK CARD");
		os.println(user);
	}

	@Override
	public synchronized void sendpass(String user) {
		os.println("PASS");
		os.println(user);
	}

	@Override
	public synchronized void senduno(String user) {
		os.println("UNO");
		os.println(user);
	}

	@Override
	public synchronized void sendcontre_uno(String user) {
		os.println("CONTRE UNO");
		os.println(user);
	}

	@Override
	public synchronized void sendchange_color(String user, String color) {
		os.println("NEW COLOR");
		os.println(user);
		os.println(color);
	}

	@Override
	public synchronized void senddisconnect_game(String user) {
		os.println("DISCONNECT");
		os.println(user);
	}

	@Override
	public synchronized void sendquit() {
		os.println("QUIT");
	}


	@Override
	public synchronized void sendAskUserList() {
		os.println("APLYRLIST");
	}


	@Override
	public synchronized void sendUserList(Collection<String> ulist) {
		os.println("PLYRLIST");
		ulist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendNameBad() {
		os.println("NAME BAD");
	}


}
