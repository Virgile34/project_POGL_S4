package game;

import java.awt.Graphics;

import Vue.Environment;
import Vue.Observable;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;
import java.util.Random;

import Exception.HorsLimite;


/**
 * notre modele (ici c'est un jeu)
 */
public class Jeu extends Observable {
    
    /********** ATTRIBUTS **********/

    public final Random rd = new Random();   // un generateur de nombre aleatoire, toujours utile

    private final Case[][] plateau;                         //notre plateau de jeu, les cases
    private final int nbLine;                               //nombre de lignes
    private final int nbCol;                                //nombres de colones
    
    private ArrayList<Joueur> players = new ArrayList<>();  //les joueurs de la partie
    private ArrayList<Case> artefactsToPickUp = new ArrayList<>();
    // private ArrayList<Case> artefacts;
    private Heliport H;
    private int nbCaseRest;                                 //le nombre de case non submerge (pour pouvoir arrete le jeu dans un premier temps)
    public final float level;                               //entre 0 et 1, (1-probabilite) de trouver une cle sur la case (level = 1 => probailite = 0)
    private Joueur jActif; // a qui c'est le tour
    private int pos_jActif = 0;
    private boolean InGame = true;;

    /**
     * Constructeur :
     * 
     * @param nbLine    : nombre de lignes
     * @param nbCol     : nombre de colones
     * @param nbPlayers : nombre de joueurs
     * @param level     : niveau choisiS
     */
    public Jeu(int nbLine, int nbCol,int nbPlayers, float level) {
        //init les attributs
        this.level = level;
        this.nbLine = nbLine;
        this.nbCol = nbCol;
        this.plateau = new Case[nbLine][nbCol];
        this.nbCaseRest = nbLine*nbCol;

        this.initPlateau();
        //init les joueurs (doit etre fais apres car le joueur "s'ajoute" dans la case quand il se crer)
        for (int i = 0 ; i < nbPlayers; i++) this.players.add(new Joueur(this, 0, i));
        try {this.jActif = this.players.get(0);}
        catch (Exception e){
            System.out.println("erreur en creant le JEU, pas de joueurs...");
            System.exit(1);
        }
    }



    /********** METHODE POUR OBTENIR DES INFOS SUR LE JEU **********/
   
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.nbLine; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                s = s + String.format("%s  ;  ", this.plateau[i][j].toString());
            }
            s = s + "\n";
        }
        return s;
    }

    public boolean InGame(){
        return this.InGame;
    }

    /**
     * ATTENTION c'est pas la position dans l'array des joueurs mais bien le num du joueur actif
     */
    public int getNumJoueur(){
        return this.jActif.num;
    }

    /**
     * r
     */
    public int getActionLeft() {
        return this.jActif.actionLeft();
    }

    /**
     * 
     * @return  : nombre de lignes du plateau
     */
    public int getLine() {
        return this.nbLine;
    }

    /**
     * @return : nombre de colones du plateau
     */
    public int getCol() {
        return this.nbCol;
    }

    /**
     * 
     * @param i
     * @param j 
     * @return : la case a la postion (i, j) dans le plateau
     * @throws ValueException
     */
    public Case getCase(int i, int j) throws HorsLimite {
        try { 
            return this.plateau[i][j];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new HorsLimite(String.format("(%d, %d) est en dehors du plateau de jeu de taille (%d, %d)", i, j, this.getLine(), this.getCol()));
        }
    }
    
    /**
     * 
     * @param c 
     * @return  : la case du plateau ayant les coordonnes de c
     * @throws ValueException
     */
    public Case getCase(Case c) throws HorsLimite {
        return this.getCase(c.getX(), c.getY());
    }

    /**
     * 
     * @param i
     * @param j
     * @return : la case a la postion (i, j) dans le plateau
     * @throws ArrayIndexOutOfBoundsException
     * ATTENTION : version dev, soyez certrain que les coordonnes sont dans le plateau
     */
    public Case getCasedev(int i, int j) throws ArrayIndexOutOfBoundsException{
        return this.plateau[i][j];
    }

    /**
     * 
     * @param c
     * @return : la case du plateau ayant les coordonnes de c
     * @throws ValueException
     * ATTENTION : version dev, soyez certrain que les coordonnes sont dans le plateau
     */
    public Case getCasedev(Case c) throws ArrayIndexOutOfBoundsException {
        return this.getCasedev(c.getX(), c.getY());
    }



    /**
     * 
     * @return  : true si le jeu est finis
     */
    public boolean testFinDeJeu() {
        if (this.nbCaseRest == 0) {
            System.out.println("tout les cases sont submerge, perdu !");
            return true;
        }
        if (!this.getJoueurs().isEmpty()) {
            for (Joueur j : this.getJoueurs()) {
                if (j.getPos().isSubmerger()){
                    System.out.println(String.format("le joueur j%1d est mort..., perdu !", j.num));
                    return true;
                }
            }
        }
        return false;
    }

    private int nbArtefactToPickUp() {
        return this.artefactsToPickUp.size();
    }

    public boolean testWin() {
        if (this.nbArtefactToPickUp() == 0) {
            for (Joueur j : this.getJoueurs()) {
                if (!j.getPos().isHeli())
                    return false;
            }
            return true;
        }
        return false;
    }


    /**
     * inonde une case aleatoire non submerge
     */
    public Case inondeRdm(ArrayList<Case> inondeCeTour) {
        int r1 = rd.nextInt(nbLine);
        int r2 = rd.nextInt(nbCol);
        if (this.plateau[r1][r2].isSubmerger()) inondeRdm(inondeCeTour);
        for (Case c : inondeCeTour) if (this.plateau[r1][r2].equals(c)) inondeRdm(inondeCeTour);
        if (this.plateau[r1][r2].inonde()) this.tueCase();
        return this.plateau[r1][r2];
    }



    /**
     * decremente de 1 le nombres de cases encore non submerger
     */
    private void tueCase(){
        this.nbCaseRest--;
    }

    /**
     * 
     * @return : les joueurs de la parties
     */
    public ArrayList<Joueur> getJoueurs(){
        return this.players;
    }

    /**
     * 
     * @param j
     * @return : le ieme joueur dans l'array list des joueurs
     */
    public Joueur getJoueur(int j) {
        return this.players.get(j);
    }


    /**
     * initialise toute les cases du plateau, place les artefacts et l'Heliport
     */
    private void initPlateau(){
        this.initCase();
        this.initArtefact();
        this.initHeliport();
    }

    /**
     * init les cases du plateau (vides)
     */
    private void initCase() {
        for (int i = 0; i < this.nbLine; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                Case c = new Case(i, j);
                this.plateau[i][j] = c;
            }
        }
    }



    /**
     * initialise les cases contenant les artefacts et en renvoie le tableau
     */
    private void initArtefact() {
        ArrayList<Integer> intArts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int art = this.rd.nextInt(4 - i);
            for (int val = 0; val <= art; val++) {
                if (intArts.contains(val))
                    art++;
            }
            intArts.add(art);
        }


        for (int i = 0; i < 4; i++) {
            boolean find = false;
            Case c = null;

            while (!find) {
                int r1 = rd.nextInt(nbLine);
                int r2 = rd.nextInt(nbCol);
                if(this.getCasedev(r1, r2).isNormal()){
                    c = new Case(r1, r2, Artefact.makeFromInt(intArts.get(i)));
                    find = true;
                }
            }
            this.artefactsToPickUp.add(c);
            this.plateau[c.getX()][c.getY()] = c;
        }
    }

    public void initHeliport(){
        Case c = null;
        boolean find = false;
        while (!find) {
            int r1 = rd.nextInt(nbLine);
            int r2 = rd.nextInt(nbCol);
            if (this.getCasedev(r1, r2).isNormal()) {
                c = new Heliport(r1, r2);
                find = true;
            }
        }
        this.artefactsToPickUp.add(c);
        this.plateau[c.getX()][c.getY()] = c;
    }



    private boolean asseche = false;
    private Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
    };
    /**
     * declanche la fin du tour du joueur a qui c'est le tour...
     */
    public void boutonFDT() {
        if (!InGame) return;

        if (this.testWin()) {
            this.InGame = false;
            this.env.set_endFrame(true);
        }
        this.jActif.chercheCle();

        Artefact a = this.jActif.takeArtefact();
        if (a != null) {
            this.artefactsToPickUp.remove(a);
        }


        if (this.jActif.finDuTour()) {
            this.InGame = false;
            this.env.set_endFrame(false);
        }
        this.pos_jActif = (this.pos_jActif + 1) % this.getJoueurs().size();
        this.jActif = this.getJoueur(this.pos_jActif);
        this.asseche = false;
        System.out.println("fin du tour, c'est a " + (this.jActif.num));
    }

    public void bouton_fl_gauche() {
        if (!InGame) return;

        if (this.asseche){
            this.jActif.asseche(this.jActif.caseLeft());
            this.asseche = false;
        }
        else this.jActif.deplaceGauche();
    }

    public void bouton_fl_droite() {
        if (!InGame) return;

        if (this.asseche) {
            this.jActif.asseche(this.jActif.caseRight());
            this.asseche = false;
        } 
        else this.jActif.deplaceDroite();
    }

    public void bouton_fl_bas() {
        if (!InGame) return;

        if (this.asseche) {
            this.jActif.asseche(this.jActif.caseDown());
            this.asseche = false;
        } 
        else this.jActif.deplaceBas();
    }

    public void bouton_fl_haut() {
        if (!InGame) return;

        if (this.asseche) {
            this.jActif.asseche(this.jActif.caseUp());
            this.asseche = false;
        } else
            this.jActif.deplaceHaut();
    }

    public void bouton_asseche() {
        if (!InGame) return;

        if (this.asseche) {
            this.jActif.asseche();
            this.asseche = false;
        }
        else
            this.asseche = true;
    }




    public void paint(Graphics g, int TAILLE){
        for (int i = 0; i < this.getLine(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                this.plateau[i][j].paint(g, TAILLE);

            }
        }

        if (this.asseche) {
            this.jActif.drawAsseche(g, TAILLE);
        }
    }


}