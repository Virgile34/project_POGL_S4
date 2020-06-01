package Vue;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.Jeu;


/**
 * class ControleJs :
 * 
 * controleur de notre jeu de l'ile interdite
 */
public class ControleJs extends JPanel {
	private Jeu j;			//le jeu qui lui est associe
	private int tourj = 0;	//a qui c'est le tour

	/**
	 * 
	 * @param j : le jeu 
	 */
	public ControleJs(Jeu j) {
		this.j=j;

		/**
		 * Bouton gauche
		 */
		ImageIcon icon_gauche = new ImageIcon("Image/fleche_gauche.png");
		Image im_gauche = icon_gauche.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_gauche = new ImageIcon(im_gauche);
		JButton boutonGauche = new JButton(icon_gauche);

		/**
		 * Bouton droit
		 */
		ImageIcon icon_droite = new ImageIcon("Image/fleche_droite.png");
		Image im_droite = icon_droite.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_droite = new ImageIcon(im_droite);
		JButton boutonDroite = new JButton(icon_droite);

		/**
		 * Bouton haut
		 */
		ImageIcon icon_haut = new ImageIcon("Image/fleche_haut.png");
		Image im_haut = icon_haut.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_haut = new ImageIcon(im_haut);
		JButton boutonHaut = new JButton(icon_haut);

		/**
		 * Bouton bas
		 */
		ImageIcon icon_bas = new ImageIcon("Image/fleche_bas.png");
		Image im_bas = icon_bas.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_bas = new ImageIcon(im_bas);
		JButton boutonBas = new JButton(icon_bas);

		JButton boutonAsseche = new JButton("asseche");

		//Les boutons de controles (fin du tour, deplacement...)
		JButton boutonFDT = new JButton("FDT");
		// JButton boutonGauche = new JButton("<");
		// JButton boutonDroite = new JButton(">");
		// JButton boutonHaut = new JButton("^");
		// JButton boutonBas = new JButton("v");
		// JButton boutonAsseche = new JButton("asseche");
	
		this.add(boutonFDT);
		this.add(boutonBas);
		this.add(boutonDroite);
		this.add(boutonGauche);
		this.add(boutonHaut);	
		this.add(boutonAsseche);

		boutonFDT.addActionListener(e -> { this.j.boutonFDT(); });
        boutonBas.addActionListener(e -> { this.j.bouton_fl_bas(); });
        boutonGauche.addActionListener(e -> { this.j.bouton_fl_gauche(); });
        boutonDroite.addActionListener(e -> { this.j.bouton_fl_droite(); });
        boutonHaut.addActionListener(e -> { this.j.bouton_fl_haut(); });
        boutonAsseche.addActionListener(e -> { this.j.bouton_asseche(); });
	}


         
}