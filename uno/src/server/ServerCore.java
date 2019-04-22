package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import chat.ChatModel;
import chat.HandleChatClient;
import chat.IChatLogger;
import chat.TextChatLogger;

public class ServerCore extends Thread {
	
	public static final int DEFAULT_PORT = 1234;
	ServerSocket ss;
	private boolean stop = false;
	private IChatLogger logger = null;

	public ServerCore() throws IOException {
		logger = new TextChatLogger();
		logger.systemMessage("Server started...");
		this.start();
	}

	public void run() {
		try (ServerSocket ss = new ServerSocket(DEFAULT_PORT)) {
			ss.setSoTimeout(1000);
			while (!stop) {
				try {
					Socket s = ss.accept();
					logger.clientConnected(s.toString());
					new Thread(new HandleClient(s)).start();
				} catch (SocketTimeoutException ex) {
					//ex.printStackTrace(); 
				}
			}
		} catch (IOException e) {
			System.out.println("Could not bind port " + DEFAULT_PORT);
			e.printStackTrace(); 
		}
	}
	
	public synchronized void finish() {
		ChatModel.clearAll();
		stop = true;
	}
}
