package observer;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import modele.Modele;


public class VueModele extends JPanel implements Observer {
    private Modele jeu;
    public final static int TAILLE = 50;

    /** Constructeur. */
    public VueModele(Modele jeu) {
        this.jeu = jeu;
        jeu.addObserver((Observer) this);
        /**
         * Définition et application d'une taille fixe pour cette zone de
         * l'interface, calculée en fonction du nombre de cellules et de la
         * taille d'affichage.
         */
        Dimension dim = new Dimension(TAILLE * jeu.getLine(), TAILLE * jeu.getCol());
        this.setSize(dim);
    }

    /**
     * L'interface [Observer] demande de fournir une méthode [update], qui
     * sera appelée lorsque la vue sera notifiée d'un changement dans le
     * modèle. Ici on se contente de réafficher toute la grille avec la méthode
     * prédéfinie [repaint].
     */
    public void update() {
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
        this.jeu.paint(g, TAILLE);
    }
}

