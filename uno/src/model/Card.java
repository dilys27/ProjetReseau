package model;

import java.awt.Color;

public interface Card {
	
	public int getPts();
	public boolean action();
	public Color getColor();
	public String getString();
	public SymbolType getSymbolType();
	public String setString(String x);


	
}
