package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

public class ChatOutput implements ChatProtocol {
	private PrintWriter os;
	private OutputStream oso;

	public ChatOutput(OutputStream out) throws IOException {
		this.os = new PrintWriter(out, true);
		this.oso = out;
	}

	public synchronized void sendName(String name) {
		os.println("NAME");
		os.println(name);
	}

	public synchronized void sendMessage(String user, String msg) {
		os.println("MESSAGE");
		os.println(user);
		os.println(msg);
	}

	public synchronized void sendPrivateMessage(String from, String to, String msg) {
		os.println("PRIVATE MESSAGE");
		os.println(from);
		os.println(to);
		os.println(msg);
	}

	public synchronized void sendUserList(Collection<String> ulist) {
		os.println("ULIST");
		ulist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendAskUserList() {
		os.println("AULIST");
	}

	public synchronized void sendQuit() {
		os.println("QUIT");
	}

	public synchronized void sendNameOK() {
		os.println("NAME OK");
	}

	public synchronized void sendNameBad() {
		os.println("NAME BAD");
	}

	public synchronized void sendCreateRoom(String room) {
		os.println("CREATE ROOM");
		os.println(room);
	}

	public synchronized void sendRoomOK(String room) {
		os.println("ROOM OK");
		os.println(room);
	}

	public synchronized void sendRoomBad(String room) {
		os.println("ROOM BAD");
		os.println(room);
	}

	public synchronized void sendDeleteRoom(String room) {
		os.println("DELETE ROOM");
		os.println(room);
	}

	public synchronized void sendRList(Collection<String> rlist) {
		os.println("RLIST");
		rlist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendRoomMessage(String room, String user, String msg) {
		os.println("ROOM MESSAGE");
		os.println(user);
		os.println(msg);
	}

	public synchronized void sendEnterRoom(String room) {
		os.println("ENTER ROOM");
		os.println(room);
	}

	public synchronized void sendLeaveRoom(String room) {
		os.println("LEAVE ROOM");
		os.println(room);
	}

	public synchronized void sendARUList(String room) {
		os.println("ARULIST");
		os.println(room);
	}

	public synchronized void sendRUList(Collection<String> rulist) {
		os.println("RULIST");
		rulist.forEach(os::println);
		os.println(".");
	}

	public synchronized void sendError(String string) {
		os.println("ERR");
		os.println(string);
	}
	
	public synchronized void sendFile(String to, String fName, File f) {
		try (FileInputStream fi = new FileInputStream(f)) {
			os.println("SEND FILE");
			os.println(to);
			os.println(fName);
			os.println(f.length());
			os.flush();
			byte buf[] = new byte[8192];
			int len = 0;
			while ((len = fi.read(buf)) != -1) {
				oso.write(buf, 0, len);
			}
		} catch (IOException ex) {
		}
	}

	public synchronized void sendProposeFile(String user, String nom_f) {
		os.println("PROPOSE FILE");
		os.println(user);
		os.println(nom_f);
	}

	public synchronized void acceptFileSent(String user, String nom_f) {
		os.println("FILE ACCEPTED");
		os.println(user);
		os.println(nom_f);
	}

	public synchronized void refuseFileSent(String user, String nom_f) {
		os.println("FILE REFUSED");
		os.println(user);
		os.println(nom_f);
	}

	@Override
	public void sendAcceptFile(String user, String nom_f) {
		// TODO Auto-generated method stub
		os.println("ACCEPT FILE");
		os.println(user);
		os.println(nom_f);
	}

	@Override
	public void sendRefuseFile(String user, String nom_f) {
		// TODO Auto-generated method stub
		os.println("REFUSE FILE");
		os.println(user);
		os.println(nom_f);
	}

}
