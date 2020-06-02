package modele;

import java.awt.Graphics;

import observer.*;
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
    private ArrayList<Case> artefacts = new ArrayList<>();

    private Heliport H;
    public final float level;                               //entre 0 et 1, (1-probabilite) de trouver une cle sur la case (level = 1 => probailite = 0)
    private Controleur ctr;

    public String endString = null;

    /**
     * Constructeur :
     * 
     * @param nbLine    : nombre de lignes
     * @param nbCol     : nombre de colones
     * @param nbPlayers : nombre de joueurs
     * @param level     : niveau choisiS
     */
    public Jeu(int nbLine, int nbCol,int nbPlayers, float level) {
        super();

        //init les attributs
        this.level = level;
        this.nbLine = nbLine;
        this.nbCol = nbCol;
        this.plateau = new Case[nbLine][nbCol];

        this.initPlateau();
        //init les joueurs (doit etre fais apres car le joueur "s'ajoute" dans la case quand il se crer)
        for (int i = 0 ; i < nbPlayers; i++) this.players.add(new Joueur(this, 0, i));

        this.ctr = new Controleur(this);

    }

    ArrayList<Joueur> getJoueur(Case c){
        ArrayList<Joueur> arr = new ArrayList<>();
        for(Joueur j : this.players){
            if(j.getPos().equals(c)) arr.add(j);
        }
        return arr;
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

    public int getNbj(){
        return this.players.size();
    }

    public Controleur getControleur(){
        return this.ctr;
    }





    /**
     * inonde une case aleatoire non submerge
     */
    public Case inondeRdm(ArrayList<Case> inondeCeTour) {
        int r1 = rd.nextInt(nbLine);
        int r2 = rd.nextInt(nbCol);
        if (this.plateau[r1][r2].isSubmerger()) return inondeRdm(inondeCeTour);
        for (Case c : inondeCeTour) {
            if (this.plateau[r1][r2].equals(c)) 
                return inondeRdm(inondeCeTour);
        }
        this.plateau[r1][r2].inonde();
        return this.plateau[r1][r2];
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
    public Joueur getJoueur(int i) {
        Joueur j = null;
        try {
            j = this.players.get(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(String.format("Erreur : bad index in getJoueut(int j) (%d alors qu'il y a %d joueurs", i, this.getNbj()));
            System.out.println(Thread.currentThread().getStackTrace());
            System.exit(1);
        }
        return j;
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
            this.artefacts.add(c);
            this.plateau[c.getX()][c.getY()] = c;
        }
    }

    void setArtefacts(ArrayList<Case> art){
        this.artefacts = art;
    }

    public void initHeliport(){
        Heliport c = null;
        boolean find = false;
        while (!find) {
            int r1 = rd.nextInt(nbLine);
            int r2 = rd.nextInt(nbCol);
            if (this.getCasedev(r1, r2).isNormal()) {
                c = new Heliport(r1, r2);
                find = true;
            }
        }
        this.H = c;
        this.plateau[c.getX()][c.getY()] = c;
    }



    /**
     * 
     * @return : true si le jeu est finis
     */
    boolean testPerdu() {
        //test si l'heliport est pas submerger
        if (this.H.isSubmerger()){
            this.endString = "l'Heliphort est submerger..., perdu";
            return true;
        }

        // test si une des zones artefacts est pas submerger
        for(Case c : this.ctr.artefactOnPlateau()) {
            if (c.isSubmerger()){
                this.endString = String.format("la zone %s est submerger..., perdu", c.getArtefact().toString());
                return true;
            }
        }

        for (Joueur j : this.getJoueurs()) {
            //test si joueur ne c'est pas noye
            if (j.getPos().isSubmerger()) {
                this.endString = String.format("le joueur j%1d est mort..., perdu !", j.num);
                return true;
            }
            //test si le joueur peut bouger
            if (!j.canMove()){
                this.endString =  String.format("le joueur j%1d ne peut plus bouger..., perdu !", j.num);
                return true;
            }
        }
        return false;
    }



    public ArrayList<Case> artefacts() {
        return this.artefacts;
    }

    boolean testWin() {
        if (this.ctr.nbArtefactToPickUp() == 0) {
            for (Joueur j : this.getJoueurs()) {
                if (!j.getPos().isHeli())
                    return false;
            }
            this.endString = "Gagne !";
            return true;
        }
        return false;
    }




    public void paint(Graphics g, int TAILLE){
        for (int i = 0; i < this.getLine(); i++) {
            for (int j = 0; j < this.getCol(); j++) {
                this.plateau[i][j].paint(g, TAILLE);

            }
        }
        this.ctr.paint(g, TAILLE);

    }

    public ArrayList<String> getInfoString() {
        return this.ctr.getInfoString();
    }


}