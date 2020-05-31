package Vue;

import game.Jeu;


import javax.swing.*;
import java.awt.*;

public class VueJeu extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Jeu jeu;
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    private final static int TAILLE = 50;

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
        Dimension dim = new Dimension(TAILLE* jeu.getM(),
                TAILLE* jeu.getN());
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
        super.repaint();
        /** Pour chaque cellule... */
        for(int i=0; i< jeu.getM(); i++) {
            for(int j=0; j< jeu.getN(); j++) {
                this.jeu.getCase(i, j).paint(g, TAILLE);
            }
        }
    }
}

