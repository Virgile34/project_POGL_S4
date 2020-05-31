package game;

// import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Joueur {
    private Jeu jeu;                        //le joueur est associe a un jeu
    private Case position;                  //la position du joueur
    private ArrayList<Cle> cles;            //les cles en possesion du joueur
    private ArrayList<Artefact> artefacts;  //les artefacts en possesion du joueur
    public int num;                         //le numero du joueur (le 1er cree est le 1)
    private static int nb = 0;              //nombre de joueur cree
    private int actionPerformed;            //nombre d'action restante effectue depuis le debut du tour


    /**
     * Constructeur :
     * 
     * @param jeu   : le jeu a associe au joueur
     * @param i     : la position en x dans le plateau (lignes)
     * @param j     : la position en y dans le plateau (colones)
     */
    public Joueur(Jeu jeu, int i, int j) {
        nb++;
        this.num = nb;
        this.jeu = jeu;
        this.position = this.jeu.getCase(i, j);
        this.position.setPlayer(this);
        this.cles = new ArrayList<Cle>();
        this.artefacts = new ArrayList<Artefact>();
        this.actionPerformed = 0;
    }

    /**
     * Constructeur :
     * 
     * @param jeu   : le jeu associe
     * @param pos   : la cas ou place le joueur (utilise en realite la case du plateau de coordonnes pos)
     */
    public Joueur(Jeu jeu, Case pos) {
        this(jeu, pos.getX(), pos.getY());
    }


    /**
     * renvoie la case du joueur
     * 
     * @return
     */
    public Case getPos() {
        return this.position;
    }


    /**
     * place la joueur dans la case du plateau de coordonnes de celle de pos 
     * @param pos
     */
    private void moveto(Case pos) {
        if (this.asAction() && !pos.isSubmerger()){
            this.position.takePlayer(this);
            this.position = pos;
            this.position.setPlayer(this);
            this.actionPerformed++;   
            System.out.println(String.format("Case de J%1d = %s, action av FDT = %1d ", this.num, this.getPos().toString(), 3-this.actionPerformed));
        }
    }

    /**
     * 
     * @return true si le joueur peut encore effectue des actions pendant le tour (3 possible au total)
     */
    private boolean asAction(){
        return 3-this.actionPerformed > 0;
    }

    /**
     * deplace le joueur vers le haut (donc a la ligne "d'au dessus", la ligne precedente)
     */
    public void deplaceHaut() {
        int x = this.position.getX();
        if (x > 0){
            Case newC = this.jeu.getCase(x-1, this.position.getY());
            this.moveto(newC);
        }
    }

    /**
     * deplace le joueur vers le bas
     */
    public void deplaceBas() {
        int x = this.position.getX();
        if (x+1 < this.jeu.getLine()) {
            Case newC = this.jeu.getCase(x + 1, this.position.getY());
            this.moveto(newC);
        }
    }

    /**
     * deplace le joueur vers la gauche
     */
    public void deplaceGauche() {
        int y = this.position.getY();
        if (y > 0){
            Case newC = this.jeu.getCase(this.position.getX(), y-1);
            this.moveto(newC);
        }
    }

    /**
     * deplace le joueur vers la droite
     */
    public void deplaceDroite() {
        int y = this.position.getY();
        if (y+1 < this.jeu.getCol()){
            Case newC = this.jeu.getCase(this.position.getX(), y + 1);
            this.moveto(newC);
        }
    }

    /**
     * asseche la case sur laquelle se tient le joueur
     */
    public void asseche(){
        if (this.asAction()){
            this.actionPerformed++;
            this.position.asseche();
        }
    }

    /**
     * Asseche la case c
     * @param c
     */
    public void asseche(Case c){
        if (this.asAction()) {
            this.actionPerformed++;
            c.asseche();
        }
    }

    /**
     * cherche une cle a la position du joueur
     */
    public void chercheCle(){
        if (this.position.asCle() && this.asAction()) {
            float rd = this.jeu.rd.nextFloat();
            if (rd > this.jeu.level) {
                this.cles.add(this.position.takeCle());
            }
            this.actionPerformed++;
        }
    }

    /**
     * declanche la fin du tour du joueur actif
     */
    private void FDT() {
        for (int i = 0; i < 3; i++) {
            if (this.jeu.finDeJeu()) break;
            this.jeu.inondeRdm();
        }
        this.jeu.notifyObservers();
    }

    public void finDuTour(){
        this.FDT();
        this.actionPerformed = 0;
    }
}