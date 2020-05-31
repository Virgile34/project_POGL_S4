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

		boutonFDT.addActionListener(e -> { this.finDuTour(); });
        boutonBas.addActionListener(e -> { this.deplaceBas(); });
        boutonGauche.addActionListener(e -> { this.deplaceGauche(); });
        boutonDroite.addActionListener(e -> { this.deplaceDroite(); });
        boutonHaut.addActionListener(e -> { this.deplaceHaut(); });
        boutonAsseche.addActionListener(e -> { this.asseche(); });
	}

	/**
	 * declanche la fin du tour du joueur a qui c'est le tour...
	 */
	public void finDuTour(){
		this.j.getJoueur(tourj).finDuTour();
		tourj = (tourj + 1) % this.j.getJoueurs().size();
		System.out.println("fin du tour, c'est a " + (tourj+1));
	}


	public void deplaceGauche(){
		this.j.getJoueur(tourj).deplaceGauche();
	}
	
	public void deplaceDroite() {
		this.j.getJoueur(tourj).deplaceDroite();
	}

	public void deplaceBas() {
		this.j.getJoueur(tourj).deplaceBas();
	}
	

	public void deplaceHaut() {
		this.j.getJoueur(tourj).deplaceHaut();
	}

	public void asseche() {
		this.j.getJoueur(tourj).asseche();
	}
         
}