package server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

public class GameModel {
	private static final TreeMap<String, GameModelEvents> clientList = new TreeMap<>();
	//static ArrayList<String> sendfile = new ArrayList<String>();
	//static ArrayList<String> acceptfile = new ArrayList<String>();

	public static synchronized boolean registerUser(String name, GameModelEvents client) {
		if (!existUserName(name) && !name.equals("")) {
			clientList.put(name, client);
			notifyNewName();
			return true;
		}
		return false;
	}

	public static synchronized void unregisterUser(String name) {
		if (existUserName(name)) {
			clientList.remove(name);
			notifyNewName();
		}
	}

	public static synchronized boolean renameUser(String oldname, String newname, HandleClient client) {
		if (existUserName(oldname) && !newname.equals("") && clientList.containsValue(client)) {
			client.setName(newname);
			clientList.remove(oldname);
			notifyNewName();
			return true;
		}
		return false;
	}

	public static synchronized boolean existUserName(String name) {
		return clientList.containsKey(name);
	}

	public static synchronized Set<String> getUserNames() {
		return clientList.keySet();
	}

	public static void sendChatMessage(String from, String msg) {
		clientList.values().forEach(c -> c.chatMessageSent(from, msg));
	}

	private static void notifyNewName() {
		clientList.values().forEach(GameModelEvents::userListChanged);
	}

	public static void clearAll() {
		clientList.clear();
	}

	/*private static final TreeMap<String, RoomModel> roomList = new TreeMap<>();

	public static synchronized boolean addRoom(String room, String master) {
		if (!existRoom(room) && existUserName(master)) {
			roomList.put(room, new RoomModel(room, master, clientList.get(master)));
			notifyChangeRooms();
			return true;
		}
		return false;
	}

	public static synchronized boolean existRoom(String name) {
		return roomList.containsKey(name);
	}

	public static synchronized void deleteRoom(String room, String user) {
		if (existRoom(room) && roomList.get(room).canDelete(user)) {
			roomList.remove(room);
			notifyChangeRooms();
		}
	}

	private static synchronized void notifyChangeRooms() {
		clientList.values().forEach(GameModelEvents::roomListChanged);
	}

	public static synchronized void enterRoom(String room, String user) {
		if (!existUserName(user) || !existRoom(room)) {
			return;
		}
		roomList.get(room).registerUser(user, clientList.get(user));
	}

	public static synchronized void leaveRoom(String room, String user) {
		if (!existUserName(user) || !existRoom(room)) {
			return;
		}
		roomList.get(room).unregisterUser(user);
		if (roomList.get(room).userCount() == 0) {
			roomList.remove(room);
		}
	}

	public static synchronized void roomSendChatMessage(String room, String from, String message) {
		if (!existUserName(from) || !existRoom(room)) {
			return;
		}
		roomList.get(room).chatMessage(from, message);
	}

	public static synchronized Collection<String> getRooms() {
		return roomList.keySet();
	}

	public static synchronized Collection<String> roomGetUserList(String room) {
		return roomList.get(room).userList();
	}

	public static synchronized boolean roomHasUser(String room, String user) {
		if (roomList.get(room).hasUser(user)) {
			return true;
		}
		return false;
	}

	/*public static void sendFile(String from, String to, String fName, File f) {
		if (existUserName(to) && existUserName(from)) {
			if (acceptfile.contains(to)) {
				acceptfile.remove(to);
				clientList.get(to).fileSent(from, fName, f);
			}
		}
	}

	public static void sendProposeFile(String from, String to, String nom_f) {
		// TODO
		if (existUserName(to) && existUserName(from)) {
			sendfile.add(to);
			clientList.get(to).proposeFileSent(from, nom_f);
		}
	}

	public static void sendAcceptFile(String from, String to, String nom_f) {
		// TODO
		if (existUserName(to) && existUserName(from)) {
			if (sendfile.contains(from)) {
				acceptfile.add(from);
				sendfile.remove(from);
				clientList.get(to).acceptFileSent(to, nom_f);
			}
		}
	}

	public static void sendRefuseFile(String from, String to, String nom_f) {
		// TODO
		if (existUserName(to) && existUserName(from)) {
			if (sendfile.contains(to)) {
				sendfile.remove(from);
				clientList.get(to).refuseFileSent(to, nom_f);
			}
		}
	}
	*/

}
