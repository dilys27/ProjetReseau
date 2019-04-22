package salon;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import chat.ChatModelEvents;
import server.Client;
import server.GameModelEvents;

public class Room {
	private static int master; //numéro du joueur qui a créé le salon
	private static int roomName = 0; //numéro du salon 
	private static int nb_players; //nombre de joueurs du salon créé
	static final private Map<Integer, GameModelEvents> roomObserverList = new TreeMap<>();
	
	public Room(int nb, int roomName, int master, GameModelEvents handler, Client c) {
		this.nb_players = nb;
		this.roomName = roomName;
		this.master = 0; //le premier joueur est celui qui a créé le salon
		roomObserverList.put(roomName, handler);
	}

	private static void notifyUserListChanged() {
		roomObserverList.values().forEach(c -> c.roomUserListChanged(roomName));
	}

	public synchronized static void registerUser(int player, GameModelEvents handler) {
		roomObserverList.put(player, handler);
		notifyUserListChanged();
	}

	public static synchronized void unregisterUser(int user) {
		if (user == master) {
			master = -1;
		}
		roomObserverList.remove(user);
		notifyUserListChanged();
	}

	/*public static synchronized void chatMessage(String from, String message) {
		roomObserverList.values().forEach(c -> c.roomChatMessageSent(roomName, from, message));
	}*/

	public static synchronized boolean canDelete(int player) {
		if(master == -1) {
			return true;
		}
		if(player == master) {
			return true;
		}
		return false;
	}

	public static synchronized Collection<Integer> userList() {
		return roomObserverList.keySet();
	}

	public static synchronized int userCount() {
		return nb_players;
	}

	public static synchronized boolean hasUser(int player) {
		return roomObserverList.containsKey(player);
	}

}
