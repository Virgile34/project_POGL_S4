package game;


import javax.swing.*;

public class ControleJs extends JPanel {
	private Jeu j;
	private int tourj = 0;

	public ControleJs(Jeu j) {
		this.j=j;

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

		boutonFDT.addActionListener(e -> { this.FDT(); });
        boutonBas.addActionListener(e -> { this.deplaceBas(); });
        boutonGauche.addActionListener(e -> { this.deplaceGauche(); });
        boutonDroite.addActionListener(e -> { this.deplaceDroite(); });
        boutonHaut.addActionListener(e -> { this.deplaceHaut(); });
        boutonAsseche.addActionListener(e -> { this.asseche(); });
	}


	public void FDT(){
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