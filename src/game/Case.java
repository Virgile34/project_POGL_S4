package game;

import java.awt.*;
import java.util.ArrayList;

/**
 *      class Case :
 * represente une case de notre plateau de jeu.
 */
public class Case {

    private final int x; //pos x (colone sur representation graphique)
    private final int y; //pos y (ligne sur la representation graphique)
    private Etat e; //Etat de la case
    private Artefact art; //Artefact present sur la case (== null si il n'y en a pas)
    private Cle cle; //Cle presente sur la case (== null si il n'y en a pas)
    private final Jeu jeu; //Jeu associe a la case
    private ArrayList<Joueur> joueurs; //joueur(s) present sur la case


    /**
     *      Constructeur :
     * 
     * @param jeu   : le jeu associe a la case 
     * @param x     : pos x
     * @param y     : pos y
     * @param art   : un artefact
     * @param cle   : une clef
     */
    public Case(Jeu jeu, int x, int y, Artefact art, Cle cle) {
        this.x = x;
        this.y = y;        
        this.art = art;
        this.cle = cle;
        this.jeu = jeu;

        //parametre par default a la creation :
        this.e = Etat.NORMALE;              //etat NORMALE
        this.joueurs = new ArrayList<>();   //pas de joueur
    }

    /**
     *      Constructeur : 
     * pas de clef sur la case cree
     * 
     * @param jeu : le jeu associe a la case
     * @param x   : pos x
     * @param y   : pos y
     * @param art : un artefact
     * 
     */
    public Case(Jeu jeu, int x, int y, Artefact art){
        this(jeu, x, y, art, null);
    }


    /**
     *      Constructeur : 
     * pas de clef ou d'artefact sur la case cree
     * 
     * @param jeu : le jeu associe a la case
     * @param x   : pos x
     * @param y   : pos y
     */
    public Case(Jeu jeu, int x, int y) {
        this(jeu, x, y, null, null);
    }


    @Override
    /**
     *      toString : 
     * renvoie la position et le type (si la case possede un Artefact alors son nom, sinon Normale)
     * 
     * @return : info sur la case (string toujours de meme longueur)
     */
    public String toString() {
        if (this.asArtefact())
            return String.format("%2d %2d : %8s", this.x, this.y, this.getArtefact().toString());
        else 
            return String.format("%2d %2d : %8s", this.x, this.y, "Normal");
    }

    /**
     *      asseche : 
     * change l'etat de la case si possible (pour la secher)
     */
    public void asseche() {
        if (this.e == Etat.INONDE)
            this.e = Etat.NORMALE;
    }

    /**
     *      inonde :
     * change l'etat de la case si possible (pour l'inonder)
     *
     * Attention : cette fonction modifier le nombre de case restante dans le jeu si on passe d'une case INONDE a SUBMERGER (on ne pourra desormais plus y aller)
     */
    public void inonde() {
        if (this.e == Etat.NORMALE)
            this.e = Etat.INONDE;
        else if (this.e == Etat.INONDE){
            this.e = Etat.SUBMERGE;
            this.jeu.tueCase();
        }
    }

    /**
     *      equals :
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
     *      getX :
     *
     * @return : la position en x de la case
     */
    public int getX() {
        return this.x;
    }

    /**
     *      getY :
     *
     * @return : la position en y de la case
     */
    public int getY() {
        return this.y;
    }



    public Etat getEtat() {
        return this.e;
    }

    public boolean isSubmerger() {
        return this.e == Etat.SUBMERGE;
    }

    public boolean asArtefact(){
        return this.art != null;
    }

    public Artefact getArtefact(){
        return this.art;
    }

    public Artefact takeArtefact(){
        Artefact a = this.art;
        this.art = null;
        return a;
    }

    public Color getColor() {
        if (this.asArtefact()) return this.e.makeColor(this.art.getColor());
        else 
            return this.e.makeColor(Color.WHITE);
    }

    public boolean asCle(){
        return this.cle != null;
    }

    public Cle getCle(){
        return this.cle;
    }

    public Cle takeCle() {
        Cle c = this.cle;
        this.cle = null;
        return c;
    }

    public boolean isHeli(){
        return false;
    }

    public void setPlayer(Joueur j){
        this.joueurs.add(j);
    }

    public void takePlayer(Joueur j) { 
        this.joueurs.remove(j);
    }


    public boolean asPlayer() {
        return this.joueurs.size() > 0;
    }
    

    public void paint(Graphics g, int TAILLE) {
        /** Sélection d'une couleur. */
        int sx = this.getY() * TAILLE;
        int sy = this.getX() * TAILLE;

        g.setColor(this.getColor());
        g.fillRect(sx, sy, TAILLE - 1, TAILLE - 1);

        
        if (this.asPlayer()) {
            String str = "";
            for (Joueur j : this.joueurs){
                str = str + " " + (String.format("J%1d", j.num));
            }
            int size = 25 / this.joueurs.size();
            Font fonte = new Font(" TimesRoman ", Font.BOLD, size);
            g.setColor(Color.cyan);
            g.setFont(fonte);
            g.drawString(str, sx + 10, sy + 30);
        }

    }
}



