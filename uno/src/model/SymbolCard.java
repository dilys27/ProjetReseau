package model;

import java.awt.Color;

import javax.swing.JOptionPane;

public class SymbolCard implements Card {

	private Game game;
	private Color color; // couleur de la carte
	private SymbolType type; // type de symbole de la carte
	private String s;

	public SymbolCard(Game g, Color color, SymbolType type) {
		this.game = g;
		this.color = color;
		this.type = type;
		this.s= getString();
	}

	@Override
	public int getPts() {
		return 20;
	}

	public Color getColor() {
		return color;
	}

	public SymbolType getType() {
		return type;
	}

	@Override
	public SymbolType getSymbolType() {
		return type;
	}
	
	private void skip() {
		int n = game.getTurnNext(); // je passe au joueur suivant qui est interdit de jouer
		game.setTurn(n); 
		n = game.getTurnNext(); // puis je passe encore pour le joueur suivant
		game.setTurn(n);
	}

	@Override
	public boolean action() {
		Card sup = game.getCard_sup();
		if (((color == sup.getColor()) && (color == game.getCURRENT_COLOR())) || (type == sup.getSymbolType())) {
			if (type == SymbolType.SKIP) { // joueur suivant doit passer son tour
				skip();
			} else if (type == SymbolType.REVERSE) { // le sens du jeu change
				game.setDirection(!game.getDirection());
			} else if (type == SymbolType.DRAW_TWO) { // joueur suivant doit piocher deux cartes et passer son tour
				game.getPlayerNext().addCard(2);
				JOptionPane.showMessageDialog(null, "+2 cards", "Draw two", JOptionPane.INFORMATION_MESSAGE);
				skip();
			}
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "You can't play this card", "Inane warning", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	@Override
	public String getString() {
		String c="";
		if (color == Color.BLUE)
			c="Blue";
		else if (color == Color.RED)
			c="Red";
		else if (color == Color.YELLOW)
			c="Yellow";
		else if (color == Color.GREEN)
			c="Green";
		
		return "" + c + " " + type;
	}

	@Override
	public String setString(String x) {
		// TODO Auto-generated method stub
		return x;
	}

}
