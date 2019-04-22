package chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChatInput {
	private ChatProtocol handler;
	private InputStream in;
	public boolean stop = false;

	public ChatInput(InputStream in, ChatProtocol handler) throws IOException {
		this.in = in;
		this.handler = handler;
	}

	public void doRun() throws IOException {
		String strMsg, strName;
		String strRoom;
		String nom_f;
		ArrayList<String> userList;
		ArrayList<String> roomList;
		ArrayList<String> ruList;
		try (LineBufferedInputStream is = new LineBufferedInputStream(in)) {
			while (!stop) {
				String line = is.readLine();
				if (line == null) {
					return;
				}
				switch (line) {
				case "NAME":
					strName = is.readLine();
					handler.sendName(strName);
					break;
				case "MESSAGE":
					strName = is.readLine();
					strMsg = is.readLine();
					handler.sendMessage(strName, strMsg);
					break;
				case "ULIST":
					userList = new ArrayList<>();
					String x;
					while (!(x = is.readLine()).equals(".")) {
						userList.add(x);
					}
					handler.sendUserList(userList);
					break;
				case "AULIST":
					handler.sendAskUserList();
					break;
				case "PRIVATE MESSAGE":
					String strFrom = is.readLine();
					String strTo = is.readLine();
					String strM = is.readLine();
					handler.sendPrivateMessage(strFrom, strTo, strM);
					break;
				case "NAME OK":
					handler.sendNameOK();
					break;
				case "NAME BAD":
					handler.sendNameBad();
					break;
				case "QUIT":
					handler.sendQuit();
					break;
/////////////////////////////////////////////////////////////////////////
				case "CREATE ROOM":
					strRoom = is.readLine();
					handler.sendCreateRoom(strRoom);
					break;
				case "ROOM MESSAGE":
					strRoom = is.readLine();
					strName = is.readLine();
					strMsg = is.readLine();
					handler.sendRoomMessage(strRoom, strName, strMsg);
					break;
				case "ROOM OK":
					strRoom = is.readLine();
					handler.sendRoomOK(strRoom);
					break;
				case "ROOM BAD":
					strRoom = is.readLine();
					handler.sendRoomBad(strRoom);
					break;
				case "DELETE ROOM":
					strRoom = is.readLine();
					handler.sendDeleteRoom(strRoom);
					break;
				case "RLIST":
					// roomList = new ArrayList<>();
					/*
					 * String z; while (!(z = is.readLine()).equals(".")) { roomList.add(z); }
					 */
					handler.sendRList();
					break;
				case "ENTER ROOM":
					strRoom = is.readLine();
					handler.sendEnterRoom(strRoom);
					break;
				case "LEAVE ROOM":
					strRoom = is.readLine();
					handler.sendLeaveRoom(strRoom);
					break;
				case "ARULIST":
					strRoom = is.readLine();
					handler.sendARUList(strRoom);
					break;
				case "RULIST":
					strRoom = is.readLine();
					handler.sendRUList(strRoom);
					break;
				case "ERR":
					strRoom = is.readLine();
					handler.sendError(strRoom);
					break;
///////////////////////////////////////////////////////////////////////
				case "PROPOSE FILE":
					strName = is.readLine();
					nom_f = is.readLine();
					handler.sendProposeFile(strName, nom_f);
					break;
				case "ACCEPT FILE":
					strName = is.readLine();
					nom_f = is.readLine();
					handler.sendAcceptFile(strName, nom_f);
					break;
				case "REFUSE FILE":
					strName = is.readLine();
					nom_f = is.readLine();
					handler.sendRefuseFile(strName, nom_f);
					break;
				case "SEND FILE":
					strName = is.readLine();
					String FName = is.readLine();
					int FSize = Integer.parseInt(is.readLine());
					File f = File.createTempFile(FName, "t");
					try (FileOutputStream fo = new FileOutputStream(f)) {
						byte buf[] = new byte[8192];
						int len = 0;
						int reste = FSize;
						while (reste > 0 && len != -1) {
							int toRead = buf.length;
							if (toRead > reste)
								toRead = reste;
							len = is.read(buf, 0, toRead);
							fo.write(buf, 0, len);
							reste -= len;
						}
					}
					handler.sendFile(strName, FName, f);
					break;
				default:
					throw new ChatProtocolException("Invalid input");
				}
			}
		}
	}
}
