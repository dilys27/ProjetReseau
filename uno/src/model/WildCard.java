package model;

import java.awt.Color;
import javax.swing.JOptionPane;

public class WildCard implements Card {

	private Game game;
	private Color color; // couleur demandée
	private WildType type;
	private String card_string;

	public WildCard(Game g, WildType type, String s) {
		this.game = g;
		this.type = type;
		this.card_string = s;
	}

	@Override
	public int getPts() {
		return 50;
	}

	public WildType getType() {
		return type;
	}

	@Override
	public Color getColor() {
		return color;
	}

	// Demande à l'utilisateur de choisir une couleur
	private String choice() {
		String[] c = { "RED", "YELLOW", "BLUE", "GREEN" };
		// JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
		int col = JOptionPane.showOptionDialog(null, "Choose a color !", "Color choice !",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, c, c[3]);
		JOptionPane.showMessageDialog(null, "The new color requested is: " + c[col], "New Color",
				JOptionPane.INFORMATION_MESSAGE);
		return c[col];
	}

	// Passe le tour du joueur
	private void skip() {
		int n = game.getTurnNext(); // je passe au joueur suivant qui est interdit de jouer
		game.setTurn(n);
		n = game.getTurnNext(); // puis je passe encore pour le joueur suivant
		game.setTurn(n);
	}

	@Override
	public boolean action() {
		String col = choice();
		if (col == "RED") {
			color = Color.RED;
		} else if (col == "YELLOW") {
			color = Color.YELLOW;
		} else if (col == "GREEN") {
			color = Color.GREEN;
		} else if (col == "BLUE") {
			color = Color.BLUE;
		}
		game.setCURRENT_COLOR(color);
		if (type == WildType.WILD_DRAW) {
			game.getPlayerNext().addCard(4);
			JOptionPane.showMessageDialog(null, "+4 cards", "Wild draw", JOptionPane.INFORMATION_MESSAGE);
			skip();
		}
		return true;
	}

	@Override
	public SymbolType getSymbolType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString() {
		return card_string;
	}

	@Override
	public String setString(String x) {
		// TODO Auto-generated method stub
		return x;
	}

}
