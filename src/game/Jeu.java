package game;


import Vue.Observable;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;
import java.util.Random;

/**
 * notre modele (ici c'est un jeu)
 */
public class Jeu extends Observable {
    
    /********** ATTRIBUTS **********/

    public final Random rd = new Random();   // un generateur de nombre aleatoire, toujours utile

    private final Case[][] plateau;                         //notre plateau de jeu, les cases
    private final int nbLine;                               //nombre de lignes
    private final int nbCol;                                //nombres de colones
    private ArrayList<Case> artefacts = new ArrayList<>();  //les case contenant les artefacts (pas sur que soit vraiment utile)
    private ArrayList<Joueur> players = new ArrayList<>();  //les joueurs de la partie
    private int nbCaseRest;                                 //le nombre de case non submerge (pour pouvoir arrete le jeu dans un premier temps)
    public final float level;                               //entre 0 et 1, (1-probabilite) de trouver une cle sur la case (level = 1 => probailite = 0)



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

        this.initArtefact();    //init le artefacts
        //init les cases du plateau
        for (int i = 0; i < this.nbLine; i++) {
            for (int j = 0; j < this.nbCol; j++) {
                Case c = new Case(this, i,j);
                for (Case art : this.artefacts) {
                    if (c.equals(art)){
                        c = art;
                        this.artefacts.remove(c);
                        break;
                    }
                }
                this.plateau[i][j] = c;
            }
        }
        //init les joueurs (doit etre fais apres car le joueur "s'ajoute" dans la case quand il se crer)
        for (int i = 0 ; i < nbPlayers; i++) this.players.add(new Joueur(this, 0, i));
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
    public Case getCase(int i, int j) throws ValueException {
        try { 
            return this.plateau[i][j];
        }
        catch (ValueException e) {
            throw new ValueException(String.format("(%d, %d) est en dehors du plateau de jeu de taille (%d, %d)", i, j, this.getLine(), this.getCol()));
        }
    }
    
    /**
     * 
     * @param c 
     * @return  : la case du plateau ayant les coordonnes de c
     * @throws ValueException
     */
    public Case getCase(Case c) throws ValueException {
        return this.getCase(c.getX(), c.getY());
    }


    /**
     * 
     * @return  : true si le jeu est finis
     */
    public boolean finDeJeu() {
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


    /**
     * inonde une case aleatoire non submerge
     */
    public void inondeRdm() {
        int r1 = rd.nextInt(nbLine);
        int r2 = rd.nextInt(nbCol);
        if (this.plateau[r1][r2].isSubmerger()) inondeRdm();
        if (this.plateau[r1][r2].inonde()) this.tueCase();
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
     * initialise les artefacts dans l'attributs art
     */
    private void initArtefact() {
        ArrayList<Integer> intArts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int art = this.rd.nextInt(5 - i);
            for (int val = 0; val <= art; val++) {
                if (intArts.contains(val))
                    art++;
            }
            intArts.add(art);
        }

        this.artefacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            boolean find = false;
            Case c = null;

            while (!find) {
                int r1 = rd.nextInt(nbLine);
                int r2 = rd.nextInt(nbCol);
                if (intArts.get(i) != 4)
                    c = new Case(this, r1, r2, Artefact.makeFromInt(intArts.get(i)));
                else
                    c = new Heliport(this, r1, r2);

                boolean temp = true;
                for (Case art : this.artefacts) {
                    if (c.equals(art)) {
                        temp = false;
                        break;
                    }
                }

                find = temp;
            }
            this.artefacts.add(c);
        }
    }

}
