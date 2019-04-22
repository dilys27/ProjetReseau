package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import server.Client;
import view.GuiGame;

public class Player {
	private Client c;
	private Game game;
	private String name;
	private GuiGame guiGame;
	private ArrayList<Card> hand; // liste des cartes dans la main du joueur
	private boolean turn; // true ou false si c'est le tours du joueur
	private int pts; // nombre total des points du joueur
	private boolean uno;

	public Player(Game game, String name) {
		this.name = name;
		this.game = game;
		hand = new ArrayList<Card>();
		addCard(7);
		guiGame = new GuiGame(game, this, c);
		game.setGui(guiGame);
		turn = false;
		uno = false;
		pts = 0;
	}

	// GENERER UN NOMBRE ALEATOIRE ENTRE X ET Y
	private int random(int x) {
		Random rand = new Random();
		int i = rand.nextInt(x);
		return i;
	}

	// Ajoute dans la main du joueur un nombre i de cartes
	public void addCard(int i) {
		ArrayList<Card> deck = game.getDeck();
		System.out.println(deck.size());
		for (int j = 0; j < i; j++) {
			int n = random(deck.size());
			hand.add(deck.get(n));
			System.out.println(deck.get(n).getString());
			deck.remove(n);
		}

		uno = false;
	}
	
	public boolean hasCard() {
		return hand.isEmpty();
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
		game.playersLblAction();
	}

	public void plyLblAction() {
		String a = "";
		if (isTurn() == true) {
			a = "Playing";
			if (name.equals("Player 1"))
				guiGame.getLblAction(1, a);
			else if (name.equals("Player 2"))
				guiGame.getLblAction(2, a);
			else if (name.equals("Player 3"))
				guiGame.getLblAction(3, a);
			else if (name.equals("Player 4"))
				guiGame.getLblAction(4, a);
		} else {
			a = "Waiting";
			if (name.equals("Player 1"))
				guiGame.getLblAction(1, a);
			else if (name.equals("Player 2"))
				guiGame.getLblAction(2, a);
			else if (name.equals("Player 3"))
				guiGame.getLblAction(3, a);
			else if (name.equals("Player 4"))
				guiGame.getLblAction(4, a);
		}
	}

	public boolean isUno() {
		return uno;
	}

	public void setUno() {
		if (hand.size() == 1)
			uno = true;
		else
			JOptionPane.showMessageDialog(null, "You can't declare UNO, you have more that one card at hand.", "UNO",
					JOptionPane.ERROR_MESSAGE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void pass() { // PASS TURN TO NEXT PLAYER
		if (turn == true) {
			setTurn(false);
			game.nextPlayer().setTurn(true);
		}
	}

	public synchronized boolean playCard(Card card) { // PLAYING A CARD
		if (turn == true) {
			if (card.action()) {
				pts = pts + card.getPts();
				hand.remove(card);
				setTurn(false);
				game.setCard_sup(card);
				game.nextPlayer().setTurn(true);
				guiGame.cardsOnHand(guiGame.getPanel_1());
				guiGame.repaint();
				System.out.println("dnsj");
				return true;
			}
		}
		return false;
	}

	public GuiGame getGuiGame() {
		return guiGame;
	}

	public Game getGame() {
		return game;
	}

}
