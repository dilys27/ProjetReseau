package server;

import java.io.IOException;

public class ServerMain {
	public static void main(String[] args) {
		try{
			ServerCore core = new ServerCore();
		}catch(IOException e){
			System.out.println("Error during initialisation: "+e.toString());
		}
	}
}
