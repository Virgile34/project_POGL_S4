package observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.Color;

import modele.Jeu;

public class VueCommandes extends JPanel implements Observer {

	private Jeu jeu;

	JButton boutonSeche_posActu;
	JButton boutonFDT_annuler;

	public VueCommandes(Jeu j){
		this.jeu = j;
		jeu.addObserver((Observer) this);

		Dimension dim = new Dimension(200, 100);
		// this.setPreferredSize(dim);
		this.setSize(dim);

		/**
		 * Bouton gauche
		 */
		ImageIcon icon_gauche = new ImageIcon("Image/fleche_gauche.png");
		Image im_gauche = icon_gauche.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_gauche = new ImageIcon(im_gauche);
		JButton boutonGauche = new JButton(icon_gauche);

		/**
		 * Bouton droit
		 */
		ImageIcon icon_droite = new ImageIcon("Image/fleche_droite.png");
		Image im_droite = icon_droite.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_droite = new ImageIcon(im_droite);
		JButton boutonDroite = new JButton(icon_droite);

		/**
		 * Bouton haut
		 */
		ImageIcon icon_haut = new ImageIcon("Image/fleche_haut.png");
		Image im_haut = icon_haut.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_haut = new ImageIcon(im_haut);
		JButton boutonHaut = new JButton(icon_haut);

		/**
		 * Bouton bas
		 */
		ImageIcon icon_bas = new ImageIcon("Image/fleche_bas.png");
		Image im_bas = icon_bas.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_bas = new ImageIcon(im_bas);
		JButton boutonBas = new JButton(icon_bas);

		/**
		 * echange de cles
		 */
		JButton echange = new JButton("switch");
		echange.setBackground(Color.RED);
		echange.setBorder(null);
		
		this.boutonSeche_posActu = new JButton("asseche");

		this.boutonFDT_annuler = new JButton("fin tour");

		

		this.setLayout(new GridLayout(3, 1));

		this.add(boutonFDT_annuler);
		
		JPanel temp = new JPanel(new GridLayout(1, 3));
		temp.add(echange);
		temp.add(boutonHaut);
		temp.add(boutonSeche_posActu);
		
		this.add(temp);

		temp = new JPanel(new GridLayout(1, 3));
		temp.add(boutonGauche);
		temp.add(boutonBas);
		temp.add(boutonDroite);

		this.add(temp);

		boutonFDT_annuler.addActionListener(e -> { this.jeu.getControleur().boutonFDT(); });
        boutonBas.addActionListener(e -> { this.jeu.getControleur().bouton_fl_bas(); });
        boutonGauche.addActionListener(e -> { this.jeu.getControleur().bouton_fl_gauche(); });
        boutonDroite.addActionListener(e -> { this.jeu.getControleur().bouton_fl_droite(); });
        boutonHaut.addActionListener(e -> { this.jeu.getControleur().bouton_fl_haut(); });
        boutonSeche_posActu.addActionListener(e -> { this.jeu.getControleur().bouton_asseche(); });
	}

	@Override
	public void update() {
		if (this.jeu.getControleur().isSecheMode()){
			this.boutonFDT_annuler.setText("annuler");
			this.boutonSeche_posActu.setText("pos actuelle");
		}
		else {
			this.boutonFDT_annuler.setText("fin tour");
			this.boutonSeche_posActu.setText("assecher");			
		}
		repaint();
	}
	
}