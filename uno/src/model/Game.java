package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import view.GuiGame;

public class Game implements Runnable {

	public final static int MIN_PLAYERS = 2; // nombre minimal de joueur dans une partie
	public final static int MAX_PLAYERS = 4; // nombre maximal de joueur dans une partie
	public final static int NB_CARDS = 108; // nombre total de cartes
	public int CONNECTED_PLAYERS = 0; // nombre des joueurs connectés
	public int NB_PLAYERS; // nombre des joueurs dans une partie
	private ArrayList<Player> players; // liste des joueurs de la partie
	private ArrayList<Card> deck; // pioche de la partie
	private ArrayList<Card> discard_pile; // pile pour placer les cartes pendant le jeu
	private Color CURRENT_COLOR; // couleur de la pile
	private boolean clockwise; // sens du jeu
	private int winner; // numéro du joueur gagnant de la partie
	private int turn; // numéro du joueur dont c'est le tour (de 0 à 3)
	private GuiGame guiGame;
	private static final int TIME = 500; 

	public Game(int x) {
		
		NB_PLAYERS = x;

		deck = new ArrayList<Card>();
		// Cartes 0 : apparaît une seule fois pour chaque couleur
		deck.add(new NumberedCard(this, Color.RED, 0));
		deck.add(new NumberedCard(this, Color.YELLOW, 0));
		deck.add(new NumberedCard(this, Color.GREEN, 0));
		deck.add(new NumberedCard(this, Color.BLUE, 0));
		// Cartes ROUGES numérotées de 1 à 9 + Wild
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.RED, i));
		}
		//deck.add(new SymbolCard(this, Color.RED, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.RED, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.RED, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD, "WILD"));
		// Cartes JAUNES numérotées de 1 à 9 + Wild
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.YELLOW, i));
		}
		//deck.add(new SymbolCard(this, Color.YELLOW, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.YELLOW, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.YELLOW, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD, "WILD"));
		// Cartes VERTES numérotées de 1 à 9 + Wild
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.GREEN, i));
		}
		//deck.add(new SymbolCard(this, Color.GREEN, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.GREEN, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.GREEN, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD, "WILD"));
		// Cartes BLEUES numérotées de 1 à 9 + Wild
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.BLUE, i));
		}
		//deck.add(new SymbolCard(this, Color.BLUE, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.BLUE, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.BLUE, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD, "WILD"));
		// Cartes ROUGES numérotées de 1 à 9 + Wild Draw
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.RED, i));
		}
		//deck.add(new SymbolCard(this, Color.RED, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.RED, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.RED, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD_DRAW, "WILD DRAW"));
		// Cartes JAUNES numérotées de 1 à 9 + Wild Draw
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.YELLOW, i));
		}
		//deck.add(new SymbolCard(this, Color.YELLOW, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.YELLOW, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.YELLOW, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD_DRAW,"WILD DRAW"));
		// Cartes VERTES numérotées de 1 à 9 + Wild Draw
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.GREEN, i));
		}
		//deck.add(new SymbolCard(this, Color.GREEN, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.GREEN, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.GREEN, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD_DRAW,"WILD DRAW"));
		// Cartes BLEUES numérotées de 1 à 9 + Wild Draw
		for (int i = 1; i <= 9; i++) {
			deck.add(new NumberedCard(this, Color.BLUE, i));
		}
		//deck.add(new SymbolCard(this, Color.BLUE, SymbolType.SKIP));
		//deck.add(new SymbolCard(this, Color.BLUE, SymbolType.REVERSE));
		deck.add(new SymbolCard(this, Color.BLUE, SymbolType.DRAW_TWO));
		deck.add(new WildCard(this, WildType.WILD_DRAW,"WILD DRAW"));

		clockwise = true;

		// TODO : distribuer aléatoirement 7 cartes dans la main des joueurs

		discard_pile = new ArrayList<Card>();
		int n = random(deck.size());
		while(deck.get(n)==new WildCard(this, WildType.WILD_DRAW,"WILD DRAW") || deck.get(n)==new WildCard(this, WildType.WILD_DRAW,"WILD DRAW")) {
			n=random(deck.size());
		}
		discard_pile.add(deck.get(n));
		CURRENT_COLOR=discard_pile.get(discard_pile.size()-1).getColor();
		deck.remove(n);
		System.out.println(getCard_sup().getString());


		turn = 0; // le premier tour revient au premier joueur
		
		connectedPlayers();
	}

	// GENERER UN NOMBRE ALEATOIRE ENTRE X ET Y
	private int random(int x) {
		Random rand = new Random();
		int i = rand.nextInt(x);
		return i;
	}
	
	public boolean game_over() {
		boolean test = true;
		for(Player p : players) {
			test = p.hasCard();
			if(test == false) return test;
		}
		return test;
	}
	
	public Player getPlayerNext() {
		if (NB_PLAYERS == 2) {
			if (turn == 0)
				return players.get(turn + 1);
			else
				return players.get(turn - 1);
		} else if (NB_PLAYERS >= 3) {
			if (clockwise) {
				if (turn <= 1)
					return players.get(turn + 1);
				else
					return players.get(turn - 2);
			} else {
				if (turn >= 1)
					return players.get(turn - 1);
				else if(NB_PLAYERS == 3 && turn == 0)
					return players.get(turn + 2);
				else if(NB_PLAYERS == 4 && turn == 0)
					return players.get(turn + 3);
			}
		}
		return null;
	}
	
	public int getTurnNext() {
		if (NB_PLAYERS == 2) {
			if (turn == 0)
				return turn + 1;
			else
				return turn - 1;
		} else if (NB_PLAYERS >= 3) {
			if (clockwise) {
				if (turn <= 1)
					return turn + 1;
				else
					return turn - 2;
			} else {
				if (turn >= 1)
					return turn - 1;
				else if(NB_PLAYERS == 3 && turn == 0)
					return turn + 2;
				else if(NB_PLAYERS == 4 && turn == 0)
					return turn + 3;
			}
		}
		return -1;
	}

	public Player nextPlayer() {
		if (clockwise) {
			if (turn == NB_PLAYERS - 1) {
				setTurn(0);
				return players.get(turn);
			} else {
				setTurn(turn + 1);
				return players.get(turn);
			}
		} else {
			if (turn == 0) {
				setTurn(3);
				return players.get(turn);
			} else {
				setTurn(turn - 1);
				return players.get(turn);
			}
		}
	}

	private void connectedPlayers() {
		CONNECTED_PLAYERS++;
		initPlayers();
	}

	private void initPlayers() {
		players = new ArrayList<Player>();
		// TODO : ajouter à la liste des joueurs, les personnes connectées
		if (NB_PLAYERS == 2) {
			players.add(new Player(this, "Player 1"));
			players.get(0).setTurn(true);
			players.add(new Player(this, "Player 2"));

		} else if ( NB_PLAYERS== 3) {
			players.add(new Player(this, "Player 3"));
		} else {
			players.add(new Player(this, "Player 4"));
		}
	}
	
	public void playersLblAction() {
		for(int i=0; i < players.size(); i++) {
			players.get(i).getGuiGame().getLblAction(i, "Waiting");
			if(players.get(i).isTurn()==true) {
				players.get(i).getGuiGame().getLblAction(i, "Playing");
			}
		}
	}
	
	public String getColor(Color color) {
		String c = "";
		if (color == Color.BLUE)
			c = "Blue";
		else if (color == Color.RED)
			c = "Red";
		else if (color == Color.YELLOW)
			c = "Yellow";
		else if (color == Color.GREEN)
			c = "Green";

		return "" + c;
	}

	public Card getCard_sup() {
		return discard_pile.get(discard_pile.size() - 1);
	}

	public void setCard_sup(Card c) {
		discard_pile.add(c);
		for(int i=0; i < players.size(); i++) {
			players.get(i).getGuiGame().setLblNewLabel(c.getString());
		}
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int n) {
		turn = n;
	}

	public boolean getDirection() {
		return clockwise;
	}

	public void setDirection(boolean d) {
		clockwise = d;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Card> getDiscard_pile() {
		return discard_pile;
	}

	public void setDiscard_pile(ArrayList<Card> discard_pile) {
		this.discard_pile = discard_pile;
	}

	public Color getCURRENT_COLOR() {
		return CURRENT_COLOR;
	}

	public void setCURRENT_COLOR(Color color) {
		CURRENT_COLOR = color;
	}
	
	public int winner() {
		int n = 0;
		for(Player p : players) {
			if(!p.hasCard()) return n;
			n++;
		}
		return n;
	}

	@Override
	public void run() {
		while(game_over() == false) {
			if(deck.size() == 0) {
				deck = discard_pile;
				discard_pile.clear();
				discard_pile.add(deck.get(deck.size()-1));
				deck.remove(deck.size()-1);
			}
			guiGame.revalidate();
			guiGame.repaint();
			try {
				Thread.sleep(TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "The winner of this game is Player " + winner() + " !",
				"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
	}

	public void setGui(GuiGame gui) {
		guiGame = gui;
	}
}
