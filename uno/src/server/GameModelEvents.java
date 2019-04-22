package server;

import java.io.File;

import chat.ChatEvents;
import salon.RoomEvents;

public interface GameModelEvents extends ChatEvents, RoomEvents{
	public void userListChanged();
	public void chatMessageSent(String from, String message);
	public default void playCard(String user, String card){}
	public default void pickCard(String user){}
	public default void pass(String user) {}
	public default void uno(String user) {}
	public default void contreUno(String user) {}
	public default void changeColor(String user, String color) {}
	public void disconnectRequested();
	public void shutdownRequested();
}
