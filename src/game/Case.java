package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * class Case :
 * 
 * represente une case de notre plateau de jeu.
 */
public class Case {

    /********** ATTRIBUTS **********/

    private final int x; // pos x (colone sur representation graphique)
    private final int y; // pos y (ligne sur la representation graphique)
    private Etat e; // Etat de la case
    private Artefact art; // Artefact present sur la case (== null si il n'y en a pas)
    private Cle cle; // Cle presente sur la case (== null si il n'y en a pas)
    // private final Jeu jeu; // Jeu associe a la case
    private ArrayList<Joueur> joueurs; // joueur(s) present sur la case


    /********** CONSTRUCTEUR **********/

    /**
     * Constructeur :
     * 
     * @param jeu : le jeu associe a la case
     * @param x   : pos x
     * @param y   : pos y
     * @param art : un artefact
     * @param cle : une clef
     */
    public Case(Jeu jeu, int x, int y, Artefact art, Cle cle) {
        this.x = x;
        this.y = y;
        this.art = art;
        this.cle = cle;
        // this.jeu = jeu;

        // parametre par default a la creation :
        this.e = Etat.NORMALE; // etat NORMALE
        this.joueurs = new ArrayList<>(); // pas de joueur
    }

    /**
     * Constructeur :
     * 
     * pas de clef sur la case cree
     * 
     * @param jeu : le jeu associe a la case
     * @param x   : pos x
     * @param y   : pos y
     * @param art : un artefact
     * 
     */
    public Case(Jeu jeu, int x, int y, Artefact art) {
        this(jeu, x, y, art, null);
    }

    /**
     * Constructeur :
     * 
     * pas de clef ou d'artefact sur la case cree
     * 
     * @param jeu : le jeu associe a la case
     * @param x   : pos x
     * @param y   : pos y
     */
    public Case(Jeu jeu, int x, int y) {
        this(jeu, x, y, null, null);
    }


    /********** METHODE POUR OBTENIR DES INFOS SUR LA CASE **********/
    /**
     * 
     * @return : la position en x de la case
     */
    public int getX() {
        return this.x;
    }

    /**
     *
     * @return : la position en y de la case
     */
    public int getY() {
        return this.y;
    }

    /**
     * 
     * @return : true si la case est submerger
     */
    public boolean isSubmerger() {
        return this.e == Etat.SUBMERGE;
    }

    /**
     *
     * @return : l'etat actuelle de la case
     */
    public Etat getEtat() {
        return this.e;
    }

    /**
     * 
     * @return : true si la case a un artefact
     */
    public boolean asArtefact() {
        return this.art != null;
    }

    /**
     * 
     * @return : l'artefact present sur la case (peut etre null)
     */
    public Artefact getArtefact() {
        return this.art;
    }

    /**
     * 
     * @return : true si la case possede une clef
     */
    public boolean asCle() {
        return this.cle != null;
    }

    /**
     * 
     * @return : renvoie la cle presente sur la case
     */
    public Cle getCle() {
        return this.cle;
    }

    @Override
    /**
     * 
     * @return : la position et le type (si la case possede un Artefact alors son
     *         nom, sinon Normale)
     */
    public String toString() {
        if (this.asArtefact())
            return String.format("%2d %2d : %8s", this.x, this.y, this.getArtefact().toString());
        else
            return String.format("%2d %2d : %8s", this.x, this.y, "Normal");
    }


    /**
     * calcul et renvoie la couleur associe a la case, fonction de son artefact et
     * de son etat
     * 
     * @return la couleur associe a la case
     */
    public Color getColor() {
        if (this.asArtefact())
            return this.e.makeColor(this.art.getColor());
        else
            return this.e.makeColor(Color.WHITE);
    }

    /**
     * 
     * test l'egalite de 2 case, fonctions de leur positions uniquement.
     * 
     * @param c : a tester
     * @return : true si c et this on la meme position
     */
    public boolean equals(Case c) {
        try {
            return (this.x == c.x && this.y == c.y);
        } catch (NullPointerException nul) {
            return false;
        }
    }


    /**
     * 
     * @return : true si un ou des joueurs sont sur la case
     */
    public boolean asPlayer() {
        return this.joueurs.size() > 0;
    }


    /**
     * 
     * @return : true si la case est un heliport
     */
    public boolean isHeli() {
        return false;
    }

    



    /********** METHODES POUR AGIR SUR LA CASE **********/
    /**
     * 
     * change l'etat de la case si possible (pour la secher)
     */
    public void asseche() {
        if (this.e == Etat.INONDE)
            this.e = Etat.NORMALE;
    }



    //  * @return  : true si la case passe a l'etat submerge (plus utilisable)

    /**
     * 
     * change l'etat de la case si possible (pour l'inonder) 
     * 
     * Attention : cette fonction modifier le nombre de case restante dans 
     * le jeu si on passe d'une case INONDE a SUBMERGER (on ne pourra desormais plus y aller)
     */
    public boolean inonde() {
        if (this.e == Etat.NORMALE)
            this.e = Etat.INONDE;
        else if (this.e == Etat.INONDE) {
            this.e = Etat.SUBMERGE;
            // this.jeu.tueCase();
            return true;
        }
        return false;
    }

    /**
     * retire et renvoie l'artefact present sur la case (peut renvoyer null)
     * 
     * @return : l'artefact present sur la case
     */
    public Artefact takeArtefact() {
        Artefact a = this.art;
        this.art = null;
        return a;
    }

    /**
     * retire et renvoie la clef present sur la case (peut renvoyer null)
     * 
     * @return : la clef presente sur la case
     */
    public Cle takeCle() {
        Cle c = this.cle;
        this.cle = null;
        return c;
    }

    /**
     * 
     * @param j : place le joueur j sur la case
     */
    public void setPlayer(Joueur j) {
        this.joueurs.add(j);
    }

    /**
     * 
     * @param j : enleve le joueur j de la case si il est present
     */
    public void takePlayer(Joueur j) {
        this.joueurs.remove(j);
    }







    /**
     * methode pour dessiner une case dans un Graphics de java.awt
     * @param g     
     * @param TAILLE
     */
    public void paint(Graphics g, int TAILLE) {

        int size_x = this.getY() * TAILLE;  //utilise pour dessiner
        int size_y = this.getX() * TAILLE;  


        /** Sélection d'une couleur. */
        g.setColor(this.getColor());
        g.fillRect(size_x, size_y, TAILLE - 1, TAILLE - 1); //dessine la case avec la couleur souhaite

        //si il y a des joueurs fait dessine grossierement un string
        if (this.asPlayer()) {
            String str = "";
            for (Joueur j : this.joueurs) {
                str = str + " " + (String.format("J%1d", j.num));
            }
            int size = 25 / this.joueurs.size();
            Font fonte = new Font(" TimesRoman ", Font.BOLD, size);
            g.setColor(Color.cyan);
            g.setFont(fonte);
            g.drawString(str, size_x + 10, size_y + 30);
        }

    }
}
