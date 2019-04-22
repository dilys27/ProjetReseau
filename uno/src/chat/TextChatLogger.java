package chat;

public class TextChatLogger implements IChatLogger {

	@Override
	public void clientConnected(String ip) {
		System.out.println("Client Connecté Adresse IP : "+ip);
	}

	@Override
	public void clientDisconnected(String ip, String name) {
		System.out.println("Client Déconnecté Adresse IP : "+ip);
		System.out.println("Client Déconnecté Nom : "+name);
	}

	@Override
	public void clientGotName(String ip, String name) {
		System.out.println("GotName Adresse IP : "+ip);
		System.out.println("GotName Nom :"+name);
	}

	@Override
	public void clientGotCommand(String name, int command) {
		System.out.println("GotCommand Nom :"+name);
		System.out.println("GotCommand Command :"+command);
	}

	@Override
	public void publicChat(String from, String msg) {
		System.out.println("Public Chat From : "+from);
		System.out.println("Public Chat MSG : "+msg);
	}

	@Override
	public void privateChat(String from, String to, String msg) {
		System.out.println("Private Chat From : "+from);
		System.out.println("Private Chat To : "+to);
		System.out.println("Private Chat MSG :"+msg);
	}

	@Override
	public void systemMessage(String msg) {
		System.out.println("System Message : "+msg);
	}

}
