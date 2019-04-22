package model;

import java.awt.Color;

public class NumberedCard implements Card {

	private Game game;
	private Color color; // couleur de la carte
	private int number;
	private String s;

	public NumberedCard(Game g, Color color, int number) {
		this.game = g;
		this.color = color;
		this.number = number;
		this.s= getString();
	}

	@Override
	public int getPts() {
		return number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public boolean action() {
		Card sup = game.getCard_sup(); // rcupre la carte suprieur de la pile
		if (((color == sup.getColor()) && (color == game.getCURRENT_COLOR())) || (number == sup.getPts())) { // si mme couleur ou mme numro
			game.setCard_sup(this); // ajoute  la pile la carte
			return true;
		}
		return false;
	}

	@Override
	public SymbolType getSymbolType() {
		// TODO Auto-generated method stub
		return null;
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
		
		return "" + c + " " + number;
	}

	@Override
	public String setString(String x) {
		return x;
	}

}
