package modele;

import java.util.ArrayList;
import java.awt.Graphics;

public class Controleur {
	private Jeu jeu;

	private ArrayList<Case> artefactToPickUp = new ArrayList<>();


	private Joueur jActif; // a qui c'est le tour
	private int pos_jActif = 0;
	private boolean InGame = true;
	private boolean asseche = false;
	private boolean echange = false;

	/**
	 * Constructeur
	 */
	public Controleur(Jeu j){
		this.jeu = j;
		this.jActif = this.jeu.getJoueur(0);
		this.artefactToPickUp = this.jeu.artefacts();
		this.jeu.setArtefacts((ArrayList<Case>) this.artefactToPickUp.clone());	//copie les artefacts pour qu'on ai toujours la postion 
	}

	Cle rdKeyEncoreSurLaMap(){
		int n = this.jeu.rd.nextInt(this.nbArtefactToPickUp());
		return this.artefactToPickUp.get(n).getArtefact().toCle();
	}

	ArrayList<Case> artefactOnPlateau(){
		return this.artefactToPickUp;
	}


	public boolean InGame() {
		return this.InGame;
	}

	public boolean isSecheMode() {
		return this.asseche;
	}

	/**
	 * ATTENTION c'est pas la position dans l'array des joueurs mais bien le num du
	 * joueur actif
	 */
	public int getNumJoueur() {
		return this.jActif.num;
	}

	/**
	 * r
	 */
	public int getActionLeft() {
		return this.jActif.actionLeft();
	}

	Joueur getJoueurActif() {
		return this.jActif;
	}

	private void finTour() {
		ArrayList<Case> inonderCeTour = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Case c = this.jeu.inondeRdm(inonderCeTour);
			inonderCeTour.add(c);

		}
		this.jActif.resetActionPerfo();
	}

	public void exitAsseche() {
		this.asseche = false;
	}


	public int nbArtefactToPickUp() {
		return this.artefactToPickUp.size();
	}

	/**
	 * declanche la fin du tour du joueur a qui c'est le tour...
	 */
	public void boutonFDT() {
		if (this.asseche) {
			this.asseche = false;
			this.jeu.notifyObservers();
			return;
		}

		if (!InGame){
			this.jeu.notifyObservers();
			return;
		}

		if (this.jeu.testWin()) {
			this.InGame = false;
			this.jeu.notifyObservers();
			return;
		}

		this.jActif.chercheCle();

		Artefact a = this.jActif.takeArtefact();
		if (a != null) {
			this.artefactToPickUp.remove(this.jActif.getPos());
		}

		this.finTour();
		if (this.jeu.testPerdu()) {
			if (this.jeu.testPerdu()) {
				this.InGame = false;
				this.jeu.notifyObservers();
				return;
			}
		}

		this.pos_jActif = (this.pos_jActif + 1) % this.jeu.getNbj();
		this.jActif = this.jeu.getJoueur(this.pos_jActif);
		this.asseche = false;
		this.jeu.notifyObservers();
	}

	public void bouton_fl_gauche() {
		if (!InGame)
			return;

		if (this.asseche) {
			this.jActif.asseche(this.jActif.caseLeft());
			this.asseche = false;
		} else
			this.jActif.deplaceGauche();
		this.jeu.notifyObservers();

	}

	public void bouton_fl_droite() {
		if (!InGame)
			return;

		if (this.asseche) {
			this.jActif.asseche(this.jActif.caseRight());
			this.asseche = false;
		} else
			this.jActif.deplaceDroite();
		this.jeu.notifyObservers();

	}

	public void bouton_fl_bas() {
		if (!InGame)
			return;

		if (this.asseche) {
			this.jActif.asseche(this.jActif.caseDown());
			this.asseche = false;
		} else
			this.jActif.deplaceBas();
		this.jeu.notifyObservers();

	}

	public void bouton_fl_haut() {
		if (!InGame)
			return;

		if (this.asseche) {
			this.jActif.asseche(this.jActif.caseUp());
			this.asseche = false;
		} else
			this.jActif.deplaceHaut();
		this.jeu.notifyObservers();

	}

	public void bouton_asseche() {
		if (!InGame)
			return;

		if (this.asseche) {
			this.jActif.asseche();
			this.asseche = false;
		} else
			this.asseche = true;
		this.jeu.notifyObservers();

	}

	public void bouton_echange() {
		if (this.asseche || !InGame)
			return;
		
		if (this.echange){
			// this.
		}
		else 
			this.echange = true;
		this.jeu.notifyObservers();

		

		
	}

	public void paint(Graphics g, int TAILLE){
		if (this.isSecheMode()) {
			this.jActif.drawAsseche(g, TAILLE);
		}
	}

	public ArrayList<String> possiblePlayers(){
		ArrayList<String> name = new ArrayList<>();
		ArrayList<Joueur> possible = this.jeu.getJoueur(jActif.getPos());
		possible.remove(this.jActif);
		for(Joueur j : possible) name.add(j.toString());
		return name;
	}

	ArrayList<String> getInfoString() {
        ArrayList<String> strs = new ArrayList<>();
        strs.add("C'est au joueur " + this.getNumJoueur() + " de jouer");
        strs.add("Il vous reste " + this.getActionLeft() + " actions à réaliser avant la fin du tour");
		return strs;
	}
}