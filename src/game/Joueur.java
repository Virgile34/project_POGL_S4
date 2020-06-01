package game;

// import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Exception.HorsLimite;
import Vue.Environment;
import java.awt.Graphics;

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

    public int actionLeft() {
        return 3 - this.actionPerformed;
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
            if (c != null && c.asseche()){
                this.actionPerformed++;
                System.out.println(String.format("J%d : action av FDT = %1d ", this.num, 3-this.actionPerformed));
            }
        }
    }

    /**
     * cherche une cle a la position du joueur
     */
    public boolean chercheCle(){
        float rd = this.jeu.rd.nextFloat();
        if (rd > this.jeu.level) {
            Cle c = Cle.makeFromInt(this.jeu.rd.nextInt(4));
            this.cles.add(c);
            System.out.println(String.format("J%d a trouve la cle %s", this.num, c.toString()));
            return true;
        }
        return false;
    }



    public Artefact takeArtefact(){
        Artefact a = null;
        if (this.position.asArtefact() && 
            this.cles.contains(this.position.getArtefact().toCle())) {
                a = this.position.takeArtefact();
                this.artefacts.add(a);
                System.out.println(String.format("J%d a recupere l'artefact : %s", this.num, Utile.last(this.artefacts).toString()));
        }
        return a;
    }


    public boolean finDuTour(){
        ArrayList<Case> inonderCeTour = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            inonderCeTour.add(this.jeu.inondeRdm(inonderCeTour));
            if (this.jeu.testFinDeJeu())
                return true;
        }
        this.jeu.notifyObservers();
        this.actionPerformed = 0;
        return false;
    }


    public void drawAsseche(Graphics g, int TAILLE) {
        this.getPos().drawRect(g, TAILLE);
        for (Case c : this.adjacents()){
            c.drawRond(g, TAILLE);
        }
    }

    public ArrayList<Case> adjacents() {
        ArrayList<Case> a = new ArrayList<>();
        if (this.caseDown() != null) a.add(this.caseDown());
        if (this.caseUp() != null) a.add(this.caseUp());
        if (this.caseRight() != null) a.add(this.caseRight());
        if (this.caseLeft() != null) a.add(this.caseLeft());
        return a;
    }
}