package server;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import model.Game;
import model.Player;

public class Client {
	private Socket socket;
	private Game game;
	private Player player;
	private ArrayList<GameModelEvents> events;
	
	public Client() {
		InetAddress serveur;
		try {
			serveur = InetAddress.getByName("localhost");
			socket = new Socket(serveur, ServerCore.DEFAULT_PORT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendToServer(String msg) {
		//OutputStream output = new OutputStream(socket.getOutputStream());
		//output.print(msg);
	}

}
