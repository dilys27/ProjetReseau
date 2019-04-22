package chat;

import java.io.File;

import salon.RoomEvents;

public interface ChatModelEvents extends ChatEvents, RoomEvents{
	public void userListChanged();
	public void chatMessageSent(String from, String message);
	public void privateChatMessageSent(String from, String to, String message);
	public void shutdownRequested();
	public void fileSent(String from, String fName, File f);
	public void acceptFileSent(String to, String nom_f);
	public void proposeFileSent(String from, String nom_f);
	public void refuseFileSent(String to, String nom_f);
}
