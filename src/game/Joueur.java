package game;

// import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Joueur {
    private Jeu jeu;
    private Case position;
    private ArrayList<Cle> cles;
    private ArrayList<Artefact> artefacts;
    public int num;
    private static int nb = 0;
    private int actionPerformed;



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

    public Joueur(Jeu jeu, Case pos) {
        this(jeu, pos.getX(), pos.getY());
    }


    private void moveto(Case pos) {
        if (this.asAction() && !pos.isSubmerger()){
            this.position.takePlayer(this);
            this.position = pos;
            this.position.setPlayer(this);
            this.actionPerformed++;   
            System.out.println(String.format("Case de J%1d = %s, action av FDT = %1d ", this.num, this.getPos().toString(), 3-this.actionPerformed));
        }
    }

    private boolean asAction(){
        return 3-this.actionPerformed > 0;
    }

    public void deplaceHaut() {
        int x = this.position.getX();
        if (x > 0){
            Case newC = this.jeu.getCase(x-1, this.position.getY());
            this.moveto(newC);
        }
    }

    public void deplaceBas() {
        int x = this.position.getX();
        if (x+1 < this.jeu.getM()) {
            Case newC = this.jeu.getCase(x + 1, this.position.getY());
            this.moveto(newC);
        }
    }

    public void deplaceGauche() {
        int y = this.position.getY();
        if (y > 0){
            Case newC = this.jeu.getCase(this.position.getX(), y-1);
            this.moveto(newC);
        }
    }

    public void deplaceDroite() {
        int y = this.position.getY();
        if (y+1 < this.jeu.getN()){
            Case newC = this.jeu.getCase(this.position.getX(), y + 1);
            this.moveto(newC);
        }
    }

    public void asseche(){
        if (this.asAction()){
            this.actionPerformed++;
            this.position.asseche();
            // System.out.println("Position : [" + this.position.getX() + " : " + this.position.getY() + "], action avant FDT : " + (3 -this.actionPerformed));
        }
    }

    // public void ZoneArtefact(){            //moche mais intelliJ veut pas de switch avec une constante d'enum
    //     for(Cle c : this.cles){
    //         if (c.sameAs(this.position.getTypeCase())) {
    //             if (c.name().equals(Cle.Air.name())) {
    //                 this.artefacts.add(Artefact.Air);
    //                 break;
    //             }

    //             if (c.name().equals(Cle.Eau.name())) {
    //                 this.artefacts.add(Artefact.Eau);
    //                 break;
    //             }

    //             if (c.name().equals(Cle.Terre.name())) {
    //                 this.artefacts.add(Artefact.Eau);
    //                 break;
    //             }

    //             this.artefacts.add(Artefact.Feu);
    //         }
    //     }
    // }

    public void chercheCle(){
        if (this.position.asCle() && this.asAction()) {
            float rd = this.jeu.Rdm.nextFloat();
            if (rd > this.jeu.level) {
                this.cles.add(this.position.takeCle());
            }
        }
    }


    public void finDuTour(){
        this.jeu.FDT();
        this.actionPerformed = 0;
        // System.out.println("Position : [" + this.position.getX() + " : " + this.position.getY() + "], action avant FDT : " + (3 -this.actionPerformed));

    }

    public Case getPos(){
        return this.position;
    }
}