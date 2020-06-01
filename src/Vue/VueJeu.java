package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.Jeu;


public class VueJeu extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Jeu jeu;
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    public final static int TAILLE = 50;

    /** Constructeur. */
    public VueJeu(Jeu jeu) {
        this.jeu = jeu;
        /** On enregistre la vue [this] en tant qu'observateur de [modele]. */
        jeu.addObserver((Observer) this);
        /**
         * Définition et application d'une taille fixe pour cette zone de
         * l'interface, calculée en fonction du nombre de cellules et de la
         * taille d'affichage.
         */
        Dimension dim = new Dimension(TAILLE * jeu.getLine() + 150, TAILLE * jeu.getCol() + 200);

        this.setPreferredSize(dim);
    }

    /**
     * L'interface [Observer] demande de fournir une méthode [update], qui
     * sera appelée lorsque la vue sera notifiée d'un changement dans le
     * modèle. Ici on se contente de réafficher toute la grille avec la méthode
     * prédéfinie [repaint].
     */
    public void update() {
        // JButton button = new JButton("Mon premier bouton");
        // this.add(button);
        repaint();
    }

    /**
     * Les éléments graphiques comme [JPanel] possèdent une méthode
     * [paintComponent] qui définit l'action à accomplir pour afficher cet
     * élément. On la redéfinit ici pour lui confier l'affichage des cellules.
     *
     * La classe [Graphics] regroupe les éléments de style sur le dessin,
     * comme la couleur actuelle.
     */
    public void paintComponent(Graphics g) {
        g.setColor(this.getBackground());
        g.fillRect(0, 0, TAILLE * jeu.getLine() + 150, TAILLE * jeu.getCol() + 200);
        super.repaint();
        this.jeu.paint(g, TAILLE);


        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        String s = "C'est au joueur " + jeu.getNumJoueur() + " de jouer";
        g.drawString(s, 10, jeu.getLine() * TAILLE + 100);

        int resteAction = jeu.getActionLeft();
        String s1 = "Il vous reste " + resteAction + " actions à réaliser avant la fin du tour";
        g.drawString(s1, 10, jeu.getLine() * TAILLE + 140);
        /** Pour chaque cellule... */
        // for(int i=0; i< jeu.getLine(); i++) {
        //     for(int j=0; j< jeu.getCol(); j++) {
        //         try {                    
        //             this.jeu.getCase(i, j).paint(g, TAILLE);
        //         } catch (HorsLimite e) {
        //             System.out.println("impossible error in VueJeu.paintComponent");
        //         }
        //     }
        // }
    }
}

