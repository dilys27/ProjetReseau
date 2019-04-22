package salon;

public interface RoomEvents {
	public void createRoom(int nb);
	public void modifyRoom(int player);
	public void roomUserListChanged(int roomName);
	//public void roomChatMessageSent(int roomName, String from, String message);
}
