package modele;

import java.util.ArrayList;
import java.awt.Graphics;

public class Controleur {
	private Modele jeu;

	private ArrayList<Case> artefactToPickUp = new ArrayList<>();


	private Joueur jActif; // a qui c'est le tour
	private int pos_jActif = 0;
	private boolean InGame = true;
	private boolean asseche = false;
	private boolean echange = false;

	/**
	 * Constructeur
	 */
	public Controleur(Modele j){
		this.jeu = j;
		this.jActif = this.jeu.getJoueur(0);
		this.artefactToPickUp = this.jeu.artefacts();
		this.jeu.setArtefacts((ArrayList<Case>) this.artefactToPickUp.clone());	
		//change les artefacts du jeu, (sans les modifiers dans le plateau) pour retenir les positions pour le retry
	}

	/**
	 * 
	 * @return : une clef, parmi celles correspondant a un des artefact d'encore present sur le plateau
	 */
	Cle rdKeyEncoreSurLaMap(){
		int n = this.jeu.rd.nextInt(this.nbArtefactToPickUp());
		return this.artefactToPickUp.get(n).getArtefact().toCle();
	}

	/**
	 * 
	 * @return : les Case contenant les artefacts encore present sur le plateau
	 */
	ArrayList<Case> artefactOnPlateau(){
		return this.artefactToPickUp;
	}


	/**
	 * @return : true si le jeux est en cours
	 */
	public boolean InGame() {
		return this.InGame;
	}

	/**
	 * 
	 * @return true le mode secher a etait selectionner (prochaine fleche pour secher pas se deplacer)
	 */
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
	 * @return : le nombre d'actions restantes au joueur actif.
	 */
	public int getActionLeft() {
		return this.jActif.actionLeft();
	}

	/**
	 * 
	 * @return le joueur actir
	 */
	Joueur getJoueurActif() {
		return this.jActif;
	}

	/**
	 * fonction auxilliaires : termine le tour (inonde 3 zone aleatoire)
	 */
	private void finTour() {
		ArrayList<Case> inonderCeTour = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Case c = this.jeu.inondeRdm(inonderCeTour);
			inonderCeTour.add(c);

		}
		this.jActif.resetActionPerfo();
	}

	/**
	 * quit le mode pour assecher une case
	 */
	public void exitAsseche() {
		this.asseche = false;
	}


	/**
	 * 
	 * @return : le nombre d'artefacts a ramasser
	 */
	public int nbArtefactToPickUp() {
		return this.artefactToPickUp.size();
	}

	/**
	 * declanche la fin du tour du joueur a qui c'est le tour...
	 */
	public void boutonFDT() {
		if (!InGame){
			//verifie q'on a pas quitte le jeu
			this.jeu.notifyObservers();
			return;
		}

		if (this.asseche) {
			//fonction du bouton en mode asseche
			this.asseche = false;
			this.jeu.notifyObservers();
			return;
		}

		//sinon on effectue une fin de tour normale
		this.jActif.chercheCle();

		Artefact a = this.jActif.takeArtefact();
		if (a != null) {
			this.artefactToPickUp.remove(this.jActif.getPos());
		}


		//test la fin de la partie
		if (this.jeu.testWin()) {
			this.InGame = false;
			this.jeu.notifyObservers();
			return;
		}

		this.finTour();
		if (this.jeu.testPerdu()) {
			if (this.jeu.testPerdu()) {
				this.InGame = false;
				this.jeu.notifyObservers();
				return;
			}
		}

		//met a jour le joueurActif
		this.pos_jActif = (this.pos_jActif + 1) % this.jeu.getNbj();
		this.jActif = this.jeu.getJoueur(this.pos_jActif);
		this.asseche = false;
		this.jeu.notifyObservers();
	}

	/**
	 * action effectue sur le modele en appuant sur la fleche de gauche
	 */
	public void bouton_fl_gauche() {
		if (!InGame)
			return;

		if (this.asseche) {
			//en mode asseche
			this.jActif.asseche(this.jActif.caseLeft());
			this.asseche = false;
		} else
			this.jActif.deplaceGauche();
			//sinon on se deplace
	
		this.jeu.notifyObservers();

	}

	/**
	 * action effectue sur le modele en appuant sur la fleche de droite 
	 */
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

	/**
	 * action effectue sur le modele en appuant sur la fleche du bas 
	 */
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

	/**
	 * action effectue sur le modele en appuant sur la fleche du haut
	 */
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

	/**
	 * action effectue sur le modele en appuant sur la bouton assecher
	 */
	public void bouton_asseche() {
		if (!InGame)
			return;

		if (this.asseche) {
			//en mode assech
			this.jActif.asseche();
			this.asseche = false;

		} else	//pour l'activer sinon
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

	/**
	 * 
	 * @param g
	 * @param TAILLE	: peint les case a secher pour le joueur selectionne si on est en mode asseche
	 */
	public void paint(Graphics g, int TAILLE){
		if (this.isSecheMode()) {
			this.jActif.drawAsseche(g, TAILLE);
		}
	}


	/**
	 * 
	 * @return : une liste de string, les infos de la partie en cours
	 */
	ArrayList<String> getInfoString() {
        ArrayList<String> strs = new ArrayList<>();
        strs.add("C'est au joueur " + this.getNumJoueur() + " de jouer");
        strs.add("Il vous reste " + this.getActionLeft() + " actions à réaliser avant la fin du tour");
		return strs;
	}
}