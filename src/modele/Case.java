package modele;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import observer.VueModele;

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
    private ArrayList<Joueur> joueurs; // joueur(s) present sur la case


    protected Rectangle rect; //utilise pour dessiner joliment les string dans les cases

    /********** CONSTRUCTEUR **********/

    /**
     * Constructeur :
     * 
     * @param x   : pos x
     * @param y   : pos y
     * @param art : un artefact
     */
    public Case(int x, int y, Artefact art) {
        this.x = x;
        this.y = y;
        this.art = art;
        this.rect = new Rectangle(VueModele.TAILLE * y, VueModele.TAILLE * x, VueModele.TAILLE, VueModele.TAILLE);

        // parametre par default a la creation :
        this.e = Etat.NORMALE; // etat NORMALE
        this.joueurs = new ArrayList<>(); // pas de joueur
    }


    // ArrayList<Joueur> getJoueurs() {
    //     return (ArrayList<Joueur>) this.joueurs.clone();
    // }

    /**
     * Constructeur :
     * 
     * pas d'artefact sur la case cree
     * 
     * @param x   : pos x
     * @param y   : pos y
     */
    public Case(int x, int y) {
        this(x, y, null);
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

    /**
     * 
     * @return : true si la case ne contient pas d'ertefact et n'est pas l'heliport
     */
    public boolean isNormal() {
        return !this.asArtefact();
    }

    



    /********** METHODES POUR AGIR SUR LA CASE **********/
    /**
     * 
     * change l'etat de la case si possible (pour la secher)
     * @return : true si la case a pu etre secher
     */
    public boolean asseche() {
        if (this.e == Etat.INONDE){
            this.e = Etat.NORMALE;
            return true;
        }
        return false;
    }



    //  * @return  : true si la case passe a l'etat submerge (plus utilisable)

    /**
     * 
     * change l'etat de la case si possible (pour l'inonder) 
     * 
     * @return : true si la case en question est noye(Inonde a submerge)
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
     * retire et renvoie l'artefact present sur la case (null si rien n'est ramasse)
     * 
     * @return : l'artefact present sur la case
     */
    public Artefact takeArtefact() {
        Artefact a = this.art;
        this.art = null;
        return a;
    }

    // /**
    //  * retire et renvoie la clef present sur la case (peut renvoyer null)
    //  * 
    //  * @return : la clef presente sur la case
    //  */
    // public Cle takeCle() {
    //     Cle c = this.cle;
    //     this.cle = null;
    //     return c;
    // }

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
     * Dessine un rectangle centre sur la case
     * @param g
     * @param TAILLE    : La taille assose a une case (en pixels)
     */
    public void drawRect(Graphics g, int TAILLE){
        int Xactu = this.getY() * TAILLE + TAILLE / 2;
        int Yactu = this.getX() * TAILLE + TAILLE / 2;

        g.setColor(Color.RED);
        g.drawRect(Xactu, Yactu, TAILLE / 6, TAILLE / 6);
    }

    /**
     * Dessine un rond centre dans la case
     * @param g
     * @param TAILLE : La taille assose a une case (en pixels)
     */    
    public void drawRond(Graphics g, int TAILLE) {
        int Xactu = this.getY() * TAILLE + TAILLE / 2;
        int Yactu = this.getX() * TAILLE + TAILLE / 2;

        g.setColor(Color.RED);
        g.drawOval(Xactu, Yactu, TAILLE / 4, TAILLE / 4);
    }




    /**
     * methode pour dessiner une case dans un Graphics de java.awt
     * 
     * @param g
     * @param TAILLE : La taille assose a une case (en pixels)
     */
    public void paint(Graphics g, int TAILLE) {

        int size_x = this.getY() * TAILLE; // utilise pour dessiner
        int size_y = this.getX() * TAILLE;


        /** Sélection d'une couleur. */
        g.setColor(this.getColor());
        g.fillRect(size_x, size_y, TAILLE - 1, TAILLE - 1); //dessine la case avec la couleur souhaite
        
        this.paintJoueur(g, TAILLE, Color.BLACK);

    }

    /**
     * fonction auxiliaire : dessine les joueurs dans la case si ils sont presents
     * 
     * @param g
     * @param TAILLE : La taille assose a une case (en pixels)
     * @param c : la couleur du texte
     */
    protected void paintJoueur(Graphics g, int TAILLE, Color c){
        //si il y a des joueurs fait dessine grossierement un string
        if (this.asPlayer()) {
            int size_x = this.getY() * TAILLE; // utilise pour dessiner
            int size_y = this.getX() * TAILLE;

            String str = "";
            for (Joueur j : this.joueurs) {
                str = str + " " + (String.format("J%1d", j.num));
            }
            int size = TAILLE *7/10 / this.joueurs.size();
            Font fonte = new Font(" TimesRoman ", Font.BOLD, size);
            g.setColor(c);
            g.setFont(fonte);
            Utile.drawCenteredString(g, str, this.rect, fonte);
        }
    }


}
