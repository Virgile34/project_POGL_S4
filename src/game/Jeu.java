package game;


import Vue.Observable;

import java.util.ArrayList;
import java.util.Random;

public class Jeu extends Observable {

    public Random Rdm = new Random();

    private final Case[][] plateau;
    // private final boolean[][] submerger;
    private final int m;
    private final int n;
    private ArrayList<Case> arts = new ArrayList<>();
    private ArrayList<Joueur> players = new ArrayList<>();
    private int nbCaseRest;
    public final float level; //plus grand plus dur (pour trouver les cles)
    // private final Case air, eau, terre, feu, Heli;

    public Jeu(int w, int h,int nbPlayers, float level) {
        this.level = level;
        this.m = w;
        this.n = h;
        this.plateau = new Case[m][n];
        this.nbCaseRest = m*n;
        // this.submerger = new boolean[m][n];
        this.initArtefact();
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                Case c = new Case(this, i,j);
                for (Case art : this.arts) {
                    if (c.equals(art)){
                        c = art;
                        break;
                    }
                }
                this.plateau[i][j] = c;
                // this.submerger[i][j] = false;
            }
        }
        for (int i = 0 ; i < nbPlayers; i++) this.players.add(new Joueur(this, 0, i));
        // System.out.println(this.toString());

    }



    private void initArtefact() {
        ArrayList<Integer> intArts = new ArrayList<>();        
        for (int i = 0 ; i < 5 ; i++){
            int art = this.Rdm.nextInt(5-i);
            for (int val = 0 ; val <= art ; val++){
                if (intArts.contains(val)) art++;
            }
            intArts.add(art);
        }



        this.arts = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            boolean find = false;
            Case c = null;

            while (!find){
                int r1 = Rdm.nextInt(m);
                int r2 = Rdm.nextInt(n);
                if (intArts.get(i) != 4)
                    c = new Case(this, r1, r2, Artefact.makeFromInt(intArts.get(i)));
                else c = new Heliport(this, r1, r2);

                boolean temp = true;
                for (Case art : this.arts){
                    if (c.equals(art)) {
                        temp = false;
                        break;
                    }
                }
                
                find = temp;
            }
            this.arts.add(c);
        }
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                s = s + String.format("%s  ;  ", this.plateau[i][j].toString());
            }
            s = s + "\n";
        }
        return s;
    }

    public int getM() {
        return this.m;
    }

    public int getN() {
        return this.n;
    }

    public Case getCase(int i, int j) {
        return this.plateau[i][j];
    }
    
    public Case getCase(Case c) {
        return this.getCase(c.getX(), c.getY());
    }

    public void FDT() {
        for(int i = 0 ; i < 3 ; i++){
            if(this.finDeJeu()) break;
            this.inondeRdm();
        }
        notifyObservers();
    }


    private boolean finDeJeu() {
        if (this.nbCaseRest == 0) {
            System.out.println("tout les cases sont submerge, perdu !");
            return true;
        }
        if (!this.getJoueurs().isEmpty()) {
            for (Joueur j : this.getJoueurs()) {
                if (j.getPos().isSubmerger()){
                    System.out.println(String.format("le joueur j%1d est mort...", j.num));
                    return true;
                }
            }
        }
        return false;
    }

    public void inondeRdm() {
        int r1 = Rdm.nextInt(m);
        int r2 = Rdm.nextInt(n);
        if (this.plateau[r1][r2].isSubmerger()) inondeRdm();
        this.plateau[r1][r2].inonde();
    }



    public void tueCase(){
        this.nbCaseRest--;
    }

    public ArrayList<Joueur> getJoueurs(){
        return this.players;
    }

    public Joueur getJoueur(int j) {
        return this.players.get(j);
    }

}
