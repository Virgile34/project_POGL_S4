package game;

// import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Exception.HorsLimite;

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
        try {
            this.position = this.jeu.getCase(i, j);
        }
        catch (HorsLimite e) {
            System.out.println("ERREUR : position du joueur lors de sa creation...");
        }
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
     * 
     * @return : la case du plateau au dessus de celle du joueur, null si il n'y en a pas
     */
    public Case caseUp() {
        try {
            return this.jeu.getCase(this.getPos().getX() - 1, this.getPos().getY());
        } catch (HorsLimite e) {
            return null;
        }
    }

    /**
     * 
     * @return : la case du plateau en bas de celle du joueur, null si il n'y en a pas
     */
    public Case caseDown() {
        try {
            return this.jeu.getCase(this.getPos().getX() + 1, this.getPos().getY());
        } catch (HorsLimite e) {
            return null;
        }
    }

    /**
     * 
     * @return : la case du plateau a droite de celle du joueur, null si il n'y en a pas
     */
    public Case caseRight() {
        try {
            return this.jeu.getCase(this.getPos().getX(), this.getPos().getY() + 1);
        } catch (HorsLimite e) {
            return null;
        }
    }

    /**
     * 
     * @return : la case du plateau a gauche de celle du joueur, null si il n'y en a pas
     */
    public Case caseLeft() {
        try {
            return this.jeu.getCase(this.getPos().getX(), this.getPos().getY() - 1);
        } catch (HorsLimite e) {
            return null;
        }
    }

    /**
     * 
     * @return true si le joueur peut encore effectue des actions pendant le tour (3
     *         possible au total)
     */
    private boolean asAction() {
        return 3 - this.actionPerformed > 0;
    }



    /**
     * place la joueur dans la case du plateau de coordonnes de celle de pos 
     * @param pos
     */
    private void moveto(Case pos) {

        if (pos != null && this.asAction() && !pos.isSubmerger()){
            this.position.takePlayer(this);
            this.position = pos;
            this.position.setPlayer(this);
            this.actionPerformed++;   
            System.out.println(String.format("J%d : action av FDT = %1d ", this.num, 3-this.actionPerformed));
        }
    }



    /**
     * deplace le joueur vers le haut (donc a la ligne "d'au dessus", la ligne precedente)
     */
    public void deplaceHaut() {
        this.moveto(this.caseUp());
    }

    /**
     * deplace le joueur vers le bas
     */
    public void deplaceBas() {
        this.moveto(this.caseDown());
    }

    /**
     * deplace le joueur vers la gauche
     */
    public void deplaceGauche() {
        this.moveto(this.caseLeft());
    }

    /**
     * deplace le joueur vers la droite
     */
    public void deplaceDroite() {
        this.moveto(this.caseRight());
    }














    /**
     * asseche la case sur laquelle se tient le joueur
     */
    public void asseche(){
        if (this.asAction()){
            if (this.position.asseche()){
                this.actionPerformed++;
                System.out.println(String.format("J%d : action av FDT = %1d ", this.num, 3-this.actionPerformed));
            }
        }
    }

    /**
     * Asseche la case c
     * @param c
     */
    public void asseche(Case c){
        if (this.asAction()) {
            if (c.asseche()){
                this.actionPerformed++;
                System.out.println(String.format("J%d : action av FDT = %1d ", this.num, 3-this.actionPerformed));
            }
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


    public void finDuTour(){
        for (int i = 0; i < 3; i++) {
            if (this.jeu.finDeJeu())
                break;
            this.jeu.inondeRdm();
        }
        this.jeu.notifyObservers();
        this.actionPerformed = 0;
    }
}