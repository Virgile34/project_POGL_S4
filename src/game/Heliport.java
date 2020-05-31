package game;

import java.awt.*;

/**
 * classe heliport, on redefinit quelque fonction
 */
public class Heliport extends Case {

	public Heliport(Jeu plateau, int x, int y) {
		super(plateau, x, y);
	}

	public Color getColor() {
		return this.getEtat().makeColor(Color.GRAY);
	}

	public String toString(){
		return String.format("%2d %2d : %8s", this.getX(), this.getY(), "Heliport");
	}

	public boolean isHeli() {
		return true;
	}
	
}