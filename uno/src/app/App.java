package app;

import java.io.IOException;

import server.ServerCore;
import view.GuiIntro;

public class App {
	
	public static void main(String[] args) throws IOException {
		//Debut du serveur sur le port 1234 par defaut
		try{
			ServerCore core = new ServerCore();
		}catch(IOException e){
			System.out.println("Error during initialisation: "+e.toString());
		}
		new GuiIntro();
	}

}
