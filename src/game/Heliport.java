package game;

import java.awt.*;

// import javax.print.attribute.Size2DSyntax;

/**
 * classe heliport, on redefinit quelque fonction
 */
public class Heliport extends Case {

	public Heliport(Jeu plateau, int x, int y) {
		super(plateau, x, y);
	}

	public Color getColor() {
		return this.getEtat().makeColor(Color.BLACK);
		// return null;
	}

	public String toString(){
		return String.format("%2d %2d : %8s", this.getX(), this.getY(), "Heliport");
	}

	public boolean isHeli() {
		return true;
	}

	@Override
	public void paint(Graphics g, int TAILLE) {
		int size_x = this.getY() * TAILLE; // utilise pour dessiner
		int size_y = this.getX() * TAILLE;

		/** Sélection d'une couleur. */
		g.setColor(this.getColor());
		g.fillRect(size_x, size_y, TAILLE - 1, TAILLE - 1); // dessine la case avec la couleur souhaite

		Font font = new Font(" TimesRoman ", Font.BOLD, TAILLE);
		g.setFont(font);
		g.setColor(Color.white);
		Utile.drawCenteredString(g, "H", this.rect, font);

		this.paintJoueur(g, TAILLE, Color.BLUE);
	}
	
}