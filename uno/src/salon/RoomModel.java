package salon;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import chat.ChatModelEvents;
import chat.HandleChatClient;
import server.Client;
import server.GameModelEvents;
import server.HandleClient;

public class RoomModel {
	private static final TreeMap<Integer, Client> clientList = new TreeMap<>();
	private static final TreeMap<Integer, Room> roomList = new TreeMap<>();
	
	public static synchronized boolean registerUser(int player, Client client) {
		if (!existUserName(player)) {
			clientList.put(player, client);
			//notifyNewName();
			return true;
		}
		return false;
	}

	public static synchronized void unregisterUser(int player) {
		if (existUserName(player)) {
			clientList.remove(player);
			//notifyNewName();
		}
	}

	public static synchronized boolean renameUser(int oldname, int newname, HandleClient client) {
		if (existUserName(oldname) && clientList.containsValue(client)) {
		//	client.setName(newname);
			clientList.remove(oldname);
			//notifyNewName();
			return true;
		}
		return false;
	}

	public static synchronized boolean existUserName(int player) {
		return clientList.containsKey(player);
	}

	public static synchronized Set<Integer> getUserNames() {
		return clientList.keySet();
	}

	/*public static void sendChatMessage(String from, String msg) {
		clientList.values().forEach(c -> c.chatMessageSent(from, msg));
	}

	public static void sendPrivateChatMessage(String from, String to, String msg) {
		clientList.values().forEach(c -> c.privateChatMessageSent(from, to, msg));
	}*/

	/*private static void notifyNewName() {
		clientList.values().forEach(GameModelEvents::userListChanged);
	}*/

	public static void clearAll() {
		clientList.clear();
	}

	public static synchronized boolean addRoom(int nb, int room, int master) {
		if (!existRoom(room)) {
			//roomList.put(room, new Room(nb, room, master, clientList.get(master)));
			//notifyChangeRooms();
			return true;
		}
		return false;
	}

	public static synchronized boolean existRoom(int room) {
		return roomList.containsKey(room);
	}

	public static synchronized void deleteRoom(int room, int user) {
		if (existRoom(room) && roomList.get(room).canDelete(user)) {
			roomList.remove(room);
			//notifyChangeRooms();
		}
	}

	/*private static synchronized void notifyChangeRooms() {
		clientList.values().forEach(ChatModelEvents::roomListChanged);
	}*/

	public static synchronized void enterRoom(int room, int player) {
		if (!existUserName(player) || !existRoom(room)) {
			return;
		}
		//roomList.get(room).registerUser(user, clientList.get(user));
	}

	public static synchronized void leaveRoom(int room, int user) {
		if (!existUserName(user) || !existRoom(room)) {
			return;
		}
		roomList.get(room).unregisterUser(user);
		if (roomList.get(room).userCount() == 0) {
			roomList.remove(room);
		}
	}

	/*public static synchronized void roomSendChatMessage(String room, String from, String message) {
		if (!existUserName(from) || !existRoom(room)) {
			return;
		}
		//roomList.get(room).chatMessage(from, message);
	}*/

	public static synchronized Collection<Integer> getRooms() {
		return roomList.keySet();
	}

	public static synchronized Collection<String> roomGetUserList(String room) {
		//return roomList.get(room).userList();
		return null;
	}

	public static synchronized boolean roomHasUser(int room, int player) {
		if (roomList.get(room).hasUser(player)) {
			return true;
		}
		return false;
	}
}
