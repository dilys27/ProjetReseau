package chat;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import salon.RoomModel;

public class HandleChatClient implements Runnable, ChatProtocol, ChatModelEvents {
	private final Socket s;
	private ChatOutput cho;
	private ChatInput chi;
	private String name = "";
	private IChatLogger logger = null;

	private enum ClientState {
		ST_INIT, ST_NORMAL
	};

	private ClientState state = ClientState.ST_INIT;
	private boolean stop = false;

	public HandleChatClient(Socket s, IChatLogger logger) throws IOException {
		this.s = s;
		this.logger = logger;
	}

	public void run() {
		try (Socket s1 = s) {
			cho = new ChatOutput(s1.getOutputStream());
			chi = new ChatInput(s1.getInputStream(), this);
			chi.doRun();
		} catch (IOException ex) {
			// if (!stop) { //
			// finish(); //
			// } //
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
			if (name != null)
				ChatModel.unregisterUser(name);
			logger.clientDisconnected(s.toString(), name);
		}
	}

	@Override
	public void sendName(String name) {
		String newName = name;
		if (ChatModel.existUserName(newName)) {
			cho.sendNameBad();
		} else {
			if (state == ClientState.ST_INIT) {
				ChatModel.registerUser(newName, this);
				state = ClientState.ST_NORMAL;
			} else {
				ChatModel.renameUser(this.name, newName, this);
			}
			this.name = newName;
			cho.sendNameOK();
			logger.clientGotName(s.toString(), name);
		}
	}

	@Override
	public void sendAskUserList() {
		if (state == ClientState.ST_INIT)
			return;
		cho.sendUserList(ChatModel.getUserNames());
	}

	@Override
	public void sendMessage(String user, String msg) {
		if (state == ClientState.ST_INIT)
			return;
		ChatModel.sendChatMessage(name, msg);
		logger.publicChat(name, msg);
	}

	@Override
	public void sendPrivateMessage(String from, String to, String msg) {
		ChatProtocol.super.sendPrivateMessage(from, to, msg);
		if (state == ClientState.ST_INIT)
			return;
		ChatModel.sendPrivateChatMessage(from, to, msg);
		logger.privateChat(from, to, msg);
	}

	@Override
	public void sendQuit() {
		// ChatModel.unregisterUser("QUIT");
		chi.stop = true;
		finish();
	}

	@Override
	public void userListChanged() {
		cho.sendUserList(ChatModel.getUserNames());
	}

	@Override
	public void chatMessageSent(String from, String message) {
		if (from != name) {
			cho.sendMessage(from, message);
		}
	}

	@Override
	public void privateChatMessageSent(String from, String to, String message) {
		if (to.equals(name)) {
			cho.sendPrivateMessage(from, to, message);
		}
	}

	@Override
	public void shutdownRequested() {
		stop = true;
		finish();
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void roomListChanged() {
		cho.sendRList(ChatModel.getRooms());
	}

	/*@Override
	public void roomUserListChanged(String room) {
		cho.sendRUList(ChatModel.roomGetUserList(room));
	}

	@Override
	public void roomChatMessageSent(String room, String from, String message) {
		if (ChatModel.roomHasUser(room, from)) {
			cho.sendRoomMessage(room, from, message);
		}
	}*/

	@Override
	public void sendCreateRoom(String room) {
		if (state == ClientState.ST_INIT) {
			return;
		}
		/*if (ChatModel.existRoom(room))
			cho.sendRoomBad(room);
		else {
			ChatModel.addRoom(room, name);
			cho.sendRoomOK(room);
		}*/
	}

	@Override
	public void sendRoomMessage(String room, String from, String message) {
		if (state == ClientState.ST_INIT) {
			cho.sendError("Not initialized…");
		}
		if (ChatModel.roomHasUser(room, name)) {
			cho.sendRoomMessage(room, from, message);
		} else {
			cho.sendError("Not in room…");
		}
	}

	@Override
	public void sendDeleteRoom(String room) {
		/*if (state != ClientState.ST_INIT) {
			ChatModel.deleteRoom(room, name);
			cho.sendDeleteRoom(room);
		} else {
			cho.sendError("Not able to delete…");
		}*/
	}

	@Override
	public void sendRList() {
		cho.sendRList(ChatModel.getRooms());
	}

	@Override
	public void sendLeaveRoom(String room) {
		if (/*ChatModel.existRoom(room) &&*/ name != null) {
			//RoomModel.unregisterUser(name);
			cho.sendLeaveRoom(room);
		} else {
			cho.sendError("Not able to leave…");
		}
	}

	@Override
	public void sendEnterRoom(String room) {
		/*if (ChatModel.existRoom(room)) {
			//RoomModel.registerUser(name, this);
			cho.sendEnterRoom(room);
		} else {
			cho.sendError("RoomModel not existing...");
		}*/
	}

	@Override
	public void sendARUList(String room) {
		/*if (ChatModel.existRoom(room) && ChatModel.existUserName(name)) {
			cho.sendARUList(room);
			sendRUList(room);
		} else {
			cho.sendError("RoomModel not existing...");
		}*/
	}

	@Override
	public void sendRUList(String room) {
		/*if (ChatModel.existRoom(room)) {
			cho.sendRUList(ChatModel.roomGetUserList(room));
		} else {
			cho.sendError("RoomModel not existing...");
		}*/
	}
	
	@Override
	public void sendFile(String to, String fName, File f) {
		/*sendProposeFile(to, fName);
		if(ChatModel.acceptfile.contains(to)) {*/
			ChatModel.sendFile(name, to, fName, f);
		/*}else {
			cho.sendError("Fichier NON ACCEPTE");
		}
		ChatModel.acceptfile.remove(to);
		ChatModel.sendfile.remove(name);*/
		f.delete();
	}
	
	@Override
	public void fileSent(String from, String fName, File f) {
		cho.sendFile(from, fName, f);
	}

	@Override
	public void sendProposeFile(String to, String nom_f) {
		// TODO : Envoie une demande d'envoi du fichier nom_f vers user
		ChatModel.sendProposeFile(name, to, nom_f);
	}

	@Override
	public void sendAcceptFile(String to, String fName) {
		// TODO : Envoie une réponse positive à la demande d'envoi du fichier nom_f vers user
		//ChatModel.acceptfile.add(name);
		ChatModel.sendAcceptFile(name, to, fName);
	}

	@Override
	public void sendRefuseFile(String to, String nom_f) {
		// TODO : Envoie une réponse négative à la demande d'envoi du fichier nom_f vers user
		//if(ChatModel.acceptfile.contains(to))
			//ChatModel.acceptfile.remove(to);
		ChatModel.sendRefuseFile(name, to, nom_f);
	}

	@Override
	public void acceptFileSent(String from, String fName) {
		//if(ChatModel.sendfile.containsKey(name)) {
			//ChatModel.sendFile(to,name, fName,ChatModel.sendfile.get(fName));
		//}
		cho.acceptFileSent(from, fName);
	}

	@Override
	public void refuseFileSent(String to, String nom_f) {
		cho.refuseFileSent(to, nom_f);
	}

	@Override
	public void proposeFileSent(String from, String nom_f) {
		cho.sendProposeFile(from, nom_f);
	}

	@Override
	public void roomUserListChanged(int roomName) {
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

}
