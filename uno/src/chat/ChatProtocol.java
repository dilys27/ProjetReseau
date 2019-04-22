package chat;

import java.io.File;
import java.util.Collection;

public interface ChatProtocol {
	default void sendName(String name){}
	default void sendNameOK(){}
	default void sendNameBad() {}
	default void sendMessage(String user, String msg){}
	default void sendAskUserList() {}
	default void sendUserList(Collection<String> ulist) {}
	default void sendPrivateMessage(String from, String to, String msg){}
	default void sendQuit(){}
///////////////////////////////////////////////////////////////////
	default void sendCreateRoom(String room) {}
	default void sendRoomMessage(String room, String from, String message) {}
	default void sendRoomOK(String room) {}
	default void sendRoomBad(String room) {}
	default void sendDeleteRoom(String room) {}
	default void sendRList() {}
	default void sendLeaveRoom(String room) {}
	default void sendEnterRoom(String room) {}
	default void sendARUList(String room) {}
	default void sendRUList(String room) {}
	default void sendError(String string) {}
//////////////////////////////////////////////////////////////////
	void sendFile(String to, String fName, File f);
	void sendProposeFile(String user, String nom_f);
	void acceptFileSent(String user, String nom_f);
	void refuseFileSent(String user, String nom_f);
	void sendAcceptFile(String user, String nom_f);
	void sendRefuseFile(String user, String nom_f);
	
}
