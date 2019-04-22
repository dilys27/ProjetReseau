package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import chat.ChatModel;
import model.Card;
import model.Game;

//Classe qui permet de contrôler le jeu via le serveur 
public class HandleClient implements Runnable, Protocol, GameModelEvents {

	private final Socket s;
	private Output o;
	private Input i;
	private String name;
	private Game game;
	
	private enum ClientState {
		ST_INIT, ST_NORMAL
	};

	private ClientState state = ClientState.ST_INIT;
	private boolean stop = false;
	
	public HandleClient(Socket s) throws IOException {
		this.s = s;
	}

	@Override
	public void run() {
		try (Socket s1 = s) {
			o = new Output(s1.getOutputStream(), game);
			i = new Input(s1.getInputStream(), this);
			i.doRun();
		} catch (IOException ex) {
			if (!stop) {
				finish();
			}
		}
	}

	public synchronized void finish() {
		if (!stop) {
			stop = true;
			try {
				s.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			/*
			 * if (name != null) ChatModel.unregisterUser(name);
			 * logger.clientDisconnected(s.toString(), name);
			 */
		}
	}
	
	@Override
	public void sendName(String name) {
		String newName = name;
		if (ChatModel.existUserName(newName)) {
			o.sendNameBad();
		} else {
			if (state == ClientState.ST_INIT) {
				GameModel.registerUser(newName, this);
				state = ClientState.ST_NORMAL;
			} else {
				GameModel.renameUser(this.name, newName, this);
			}
			this.name = newName;
			o.sendNameOK();
			//logger.clientGotName(s.toString(), name);
		}
	}

	@Override
	public void sendAskUserList() {
		if (state == ClientState.ST_INIT)
			return;
		o.sendUserList(ChatModel.getUserNames());
	}
	
	

	@Override
	public void sendplay_card(String user, String card) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.playCard(user, card);
	}

	@Override
	public void sendpick_card(String user) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.pickCard(user);
	}

	@Override
	public void sendpass(String user) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.pass(user);
	}
	
	public void shutdownRequested() {
		stop = true;
		finish();
	}

	@Override
	public void senduno(String user) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.uno(user);
	}

	@Override
	public void sendcontre_uno(String user) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.contreUno(user);
	}

	@Override
	public void sendchange_color(String user, String color) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.changeColor(user, color);
	}
	
	public void setName(String newname) {
		name = newname;
	}

	@Override

	public void senddisconnect_game(String user) {
		if (state == ClientState.ST_INIT)
			return;
//		GameModel.disconnectRequested(user);
	}



	@Override
	public void sendquit() {
		i.stop = true;
		finish();
	}

	@Override
	public void privateChatMessageSent(String from, String to, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void roomListChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createRoom(int nb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyRoom(int player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void roomUserListChanged(int roomName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userListChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chatMessageSent(String from, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnectRequested() {
		// TODO Auto-generated method stub
		
	}




}
