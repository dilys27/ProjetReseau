package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import chat.ChatProtocolException;
import chat.LineBufferedInputStream;
import model.GameEvents;

//Classe qui permet au serveur de recevoir les events du jeu
public class Input {
	private Protocol handler;
	// private GameEvents event;
	private InputStream in;
	public boolean stop = false;

	public Input(InputStream in, Protocol handler) throws IOException {
		this.in = in;
		this.handler = handler;
	}

	public void doRun() throws IOException {
		int player = -1;
		String strName;
		String strCard;
		ArrayList <String> playerList;
		try (LineBufferedInputStream is = new LineBufferedInputStream(in)) {
			while (!stop) {
				// récuperer l'évènement et le numéro du joueur par une notification du jeu
				String line = is.readLine();
				if (line == null) {
					return;
				}
				switch (line) {
				case "NAME":
					strName = is.readLine();
					handler.sendName(strName);
					break;
				case "NAME OK":
					handler.sendNameOK();
					break;
				case "NAME BAD":
					handler.sendNameBad();
					break;
				case "PLAY CARD":
					strName =is.readLine();
					strCard=is.readLine();
					handler.sendplay_card(strName,strCard);
					break;
				case "PICK CARD":
					strName =is.readLine();
					handler.sendpick_card(strName);
					break;
				case "PASS":
					strName= is.readLine();
					handler.sendpass(strName);
					break;
				case "UNO":
					strName= is.readLine();
					handler.senduno(strName);
					break;
				case "CONTRE UNO":
					strName= is.readLine();
					handler.sendcontre_uno(strName);
					break;
				case "NEW COLOR":
					strName= is.readLine();
					String strColor =is.readLine();
					handler.sendchange_color(strName, strColor);
					break;
				case "APLYRLIST":
					handler.sendAskUserList();
					break;
				case "PLYRLIST":
					playerList = new ArrayList<>();
					String x;
					while (!(x = is.readLine()).equals(".")) {
						playerList.add(x);
					}
					handler.sendUserList(playerList);
					break;
				case "DISCONNECT":
					strName= is.readLine();
					handler.senddisconnect_game(strName);
					break;
				case "QUIT":
					handler.sendquit();
					break;
				default:
					throw new ChatProtocolException("Invalid input");
				}
			}
		}
	}

}
