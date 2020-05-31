package Vue;

import javax.swing.*;

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

		//Les boutons de controles (fin du tour, deplacement...)
		JButton boutonFDT = new JButton("FDT");
		JButton boutonGauche = new JButton("<");
		JButton boutonDroite = new JButton(">");
		JButton boutonHaut = new JButton("^");
		JButton boutonBas = new JButton("v");
		JButton boutonAsseche = new JButton("asseche");
	
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