package modele;

import java.awt.*;

// import javax.print.attribute.Size2DSyntax;

/**
 * classe heliport, on redefinit quelque fonction
 */
public class Heliport extends Case {

	/**
	 * Constructeur
	 * @param x
	 * @param y
	 */
	public Heliport(int x, int y) {
		//un Heliport est comme une case (en memoire au moins)
		super(x, y);
	}
	
	@Override
	public Color getColor() {
		return Color.BLACK;
	}

	@Override
	public String toString(){
		return String.format("%2d %2d : %8s", this.getX(), this.getY(), "Heliport");
	}

	@Override
	public boolean isHeli() {
		return true;
	}

	@Override
	public boolean isNormal() {
		return false;
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
		g.setColor(this.getEtat().makeColor(Color.white));
		Utile.drawCenteredString(g, "H", this.rect, font);

		this.paintJoueur(g, TAILLE, Color.BLUE);
	}
	
}