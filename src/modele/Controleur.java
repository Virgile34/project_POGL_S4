package modele;

import java.util.ArrayList;
import java.awt.Graphics;

public class Controleur {
	private Modele jeu;

	private ArrayList<Case> artefactToPickUp = new ArrayList<>();


	private Joueur jActif; // a qui c'est le tour
	private int pos_jActif = 0;
	private boolean InGame = true;
	// private boolean asseche = false;
	// private boolean echange = false;
	private Cle toGive = null;
	private Mode m = Mode.Deplacement;

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

	public ArrayList<Joueur> joueursMemePos(){
		ArrayList<Joueur> j = this.jActif.getPos().getJoueurs();
		j.remove(this.jActif);
		return j;
	}

	public Cle toGive(){
		return this.toGive;
	}

	public void resetToGive(){
		this.toGive = null;
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
		if (!InGame)
			return;

		switch(this.m){
			case Deplacement :
				//on realise alors la fin du tour
				this.jActif.chercheCle();

				Artefact a = this.jActif.takeArtefact();
				if (a != null) {
					this.artefactToPickUp.remove(this.jActif.getPos());
				}

				//test la fin de la partie
				if (this.jeu.testWin()) {
					this.InGame = false;
					break;
				}

				this.finTour();
				if (this.jeu.testPerdu()) {
					this.InGame = false;
					break;
				}

				//met a jour le joueurActif
				this.pos_jActif = (this.pos_jActif + 1) % this.jeu.getNbj();
				this.jActif = this.jeu.getJoueur(this.pos_jActif);
				break;

			// case Asseche : 
			// case Echange :
			// case ValidEchange :

			//sinon il sert de bouton quitter
			default :
				this.exitMode();
				break;
		}
		this.jeu.notifyObservers();

	}

	/**
	 * action effectue sur le modele en appuant sur la fleche de gauche
	 */
	public void bouton_fl_gauche() {
		if (!InGame)
			return;

		switch (this.m) {
			case Deplacement:
				this.jActif.deplaceGauche();
				break;

			case Asseche:
				this.jActif.asseche(this.jActif.caseLeft());
				this.exitMode();
				break;

			// case Echange:
			// case ValidEchange:
			default :
				return;
		}
		this.jeu.notifyObservers();

	}

	/**
	 * action effectue sur le modele en appuant sur la fleche de droite 
	 */
	public void bouton_fl_droite() {
		if (!InGame)
			return;

		switch (this.m) {
			case Deplacement:
				this.jActif.deplaceDroite();
				break;

			case Asseche:
				this.jActif.asseche(this.jActif.caseRight());
				this.exitMode();
				break;

			// case Echange:
			// case ValidEchange:
			default:
				return;
		}
		this.jeu.notifyObservers();

	}

	/**
	 * action effectue sur le modele en appuant sur la fleche du bas 
	 */
	public void bouton_fl_bas() {
		if (!InGame)
			return;

		switch (this.m) {
			case Deplacement : 
				this.jActif.deplaceBas();
				break;
			case Asseche : 				
				this.jActif.asseche(this.jActif.caseDown());
				this.exitMode();
				break;

			// case Echange :
			// case ValidEchange: ;	
			default : 
				return;	
	
		}
		this.jeu.notifyObservers();

	}

	/**
	 * action effectue sur le modele en appuant sur la fleche du haut
	 */
	public void bouton_fl_haut() {
		if (!InGame)
			return;

		switch (this.m) {
			case Deplacement : 
				this.jActif.deplaceHaut();
				break;

			case Asseche : 				
				this.jActif.asseche(this.jActif.caseUp());
				this.exitMode();
				break;

			// case Echange : ;
			// case ValidEchange: ;	
			default : 
				return;	
		}
		this.jeu.notifyObservers();

	}

	/**
	 * action effectue sur le modele en appuant sur la bouton assecher
	 */
	public void bouton_asseche() {
		if (!InGame)
			return;

		switch (this.m) {
			case Deplacement : 
				this.setMode(Mode.Asseche);
				break;
			
			case Asseche : 
				this.jActif.asseche();
				this.exitMode();
				break;
			
			// case Echange : ;
			// case ValidEchange: ;		
			default : 
				return;

		}
		this.jeu.notifyObservers();
	}

	public void bouton_echange() {
		if (!InGame) return;
		
		switch (this.m){
			case Deplacement : 
				this.setMode(Mode.Echange);
				break;
				
			// case Asseche : ;
			// case Echange : ;
			// case ValidEchange: ;
			default : 
				break;
		}
		this.jeu.notifyObservers();
	}

	public void selectAir() {
		if (!InGame) return;

		if (this.isEchange() && this.jActif.donneCle(Cle.Air)){ 
			this.toGive = Cle.Air;
			this.setMode(Mode.ValidEchange);
			this.jeu.notifyObservers();
		}
	}

	public void selectEau() {
		if (!InGame) return;

		if (this.isEchange() && this.jActif.donneCle(Cle.Eau)){ 
			this.toGive = Cle.Eau;
			this.setMode(Mode.ValidEchange);
			this.jeu.notifyObservers();
		}
	}

	public void selectFeu() {
		if (!InGame) return;

		if (this.isEchange() && this.jActif.donneCle(Cle.Feu)){ 
			this.toGive = Cle.Feu;
			this.setMode(Mode.ValidEchange);
			this.jeu.notifyObservers();
		}
	}

	public void selectTerre() {
		if (!InGame) return;

		if (this.isEchange() && this.jActif.donneCle(Cle.Terre)){ 
			this.toGive = Cle.Terre;
			this.setMode(Mode.ValidEchange);
			this.jeu.notifyObservers();
		}
	}

	/**
	 * 
	 * @param g
	 * @param TAILLE	: peint les case a secher pour le joueur selectionne si on est en mode asseche
	 */
	public void paint(Graphics g, int TAILLE){
		if (this.isAsseche()) {
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

	private void setMode(Mode m){
		if (this.m != Mode.Deplacement && (this.m != Mode.Echange && m != Mode.ValidEchange)){
			System.out.println("error, on set un mode sans venir du mode de base");
			System.exit(1);
		}
		this.m = m;
		this.jeu.notifyObservers();
	}

	public void exitMode(){
		this.m = Mode.Deplacement;
		this.jeu.notifyObservers();
	}

	public boolean isDeplace(){
		return this.m == Mode.Deplacement;
	}

	public boolean isAsseche(){
		return this.m == Mode.Asseche;
	}

	public boolean isEchange(){
		return this.m == Mode.Echange;
	}

	public boolean isValidEchange(){
		return this.m == Mode.ValidEchange;
	}

	private Mode old_state = Mode.Deplacement;
	public boolean stateChanged(){
		boolean b = old_state.equals(this.m);
		if (!b) {
			old_state = this.m;
			return true;
		}
		return false;
	}

	enum Mode{
		Deplacement,
		Asseche,
		Echange,
		ValidEchange;
	}
}