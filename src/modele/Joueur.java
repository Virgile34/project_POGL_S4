package modele;

import java.awt.Graphics;
import java.util.ArrayList;

import Exception.HorsLimite;

public class Joueur {
    public Jeu jeu;                        //le joueur est associe a un jeu
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
            this.launchFDT();
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


    public boolean donneCle(Cle c){
        int temp = this.cles.size();
        this.cles.remove(c);
        return temp != this.cles.size();
    }

    /**
     * asseche la case sur laquelle se tient le joueur
     */
    public void asseche(){
        this.asseche(this.position);
    }

    /**
     * Asseche la case c
     * @param c
     */
    public void asseche(Case c){
        if (this.asAction()) {
            if (c != null && c.asseche()){
                this.actionPerformed++;
                this.launchFDT();
            }
        }
    }

    /**
     * cherche une cle a la position du joueur
     */
    public boolean chercheCle(){
        float rd = this.jeu.rd.nextFloat();
        if (rd > this.jeu.level) {
            Cle c = this.jeu.getControleur().rdKeyEncoreSurLaMap();
            this.cles.add(c);
            System.out.println(String.format("J%d a trouve la cle %s", this.num, c.toString()));
            return true;
        }
        return false;
    }



    public Artefact takeArtefact(){
        Artefact a = null;
        if (this.position.asArtefact()){
            Cle necessaire = this.position.getArtefact().toCle();
            if (this.cles.contains(necessaire)) {
                this.cles.remove(necessaire);
                a = this.position.takeArtefact();
                this.artefacts.add(a);
                System.out.println(String.format("J%d a recupere l'artefact : %s", this.num, Utile.last(this.artefacts).toString()));
            }
        }
        return a;
    }




    public void resetActionPerfo() {
        this.actionPerformed = 0;
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

    public boolean canMove(){
        ArrayList<Case> adj = this.adjacents();
        for(Case c: adj) if (!c.isSubmerger()) return true;
        return false;
    }

    private void launchFDT(){
        this.jeu.getControleur().exitAsseche();
        if (!this.asAction()) this.jeu.getControleur().boutonFDT();
    }

    public String getInfoString(){
        String str = String.format("J%d : Art ", this.num);
        if (this.artefacts.size() != 0) {
            str += ": ";
            for (Artefact a : this.artefacts) str += a.toString() + " ";
        }
        else str += "aucun ";
        str += "; key ";
        if (this.cles.size() != 0) {
            str += ": ";
            for (Cle c : unique(this.cles)) str += num(this.cles, c) +"-"+ c.toString() + " ";
        }   
        else str += "aucune"; 
        return str;
    }

    private static ArrayList<Cle> unique(ArrayList<Cle> arr){
        ArrayList<Cle> unique = new ArrayList<>();
        for (Cle c : arr){
            if(!unique.contains(c)) unique.add(c);
        }
        return unique;
    }

    private static int num(ArrayList<Cle> arr, Cle c){
        int n = 0;
        for(Cle cle : arr) if (c.equals(cle)) n++;
        return n;
    }

    public String toString(){
        return String.format("J%d", this.num);
    }

}