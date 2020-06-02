package observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Image;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.Color;
import java.awt.Component;

import modele.Modele;
import modele.Artefact;
import modele.Joueur;

public class VueCommandes extends JPanel implements Observer {

	private Modele jeu;

	JButton boutonSeche_posActu;
	JButton boutonFDT_annuler;
	JButton boutonEchange;
	// JButton boutonGauche;
	// JButton boutonDroite;
	// JButton boutonHaut;
	// JButton boutonBas;

	// JButton artAir;
	// JButton artFeu;
	// JButton artTerre;
	// JButton artEau;

	ImageIcon icon_bas;
	ImageIcon icon_haut;
	ImageIcon icon_droite;
	ImageIcon icon_gauche;
	final static Dimension dim = new Dimension(200, 100);

	JPanel deplassement, echange, validationEch;
	// JPanel actif;
	
	public VueCommandes(Modele j){
		
		this.jeu = j;
		jeu.addObserver((Observer) this);


		/**
		 * Bouton gauche
		 */
		Image im_gauche = new ImageIcon("Image/fleche_gauche.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_gauche = new ImageIcon(im_gauche);

		/**
		 * Bouton droit
		 */
		Image im_droite = new ImageIcon("Image/fleche_droite.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_droite = new ImageIcon(im_droite);

		/**
		 * Bouton haut
		 */
		Image im_haut = new ImageIcon("Image/fleche_haut.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_haut = new ImageIcon(im_haut);

		/**
		 * Bouton bas
		 */
		Image im_bas = new ImageIcon("Image/fleche_bas.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon_bas = new ImageIcon(im_bas);

		this.init_depl();
		this.init_boutonEch();
		this.init_validEch();
		// this.actif = this.deplassement;
		this.add(this.deplassement);

	}

	@Override
	public void update() {
		if (this.jeu.getControleur().stateChanged()){
			if (this.jeu.getControleur().isDeplace()){
				this.removeAll();
				this.add(this.deplassement);
				this.boutonSeche_posActu.setText("Assecher");
				this.boutonFDT_annuler.setText("Fin Tour");
				this.boutonEchange.setText("Switch");
				// this.actif = this.deplassement;
			}			
			else if (this.jeu.getControleur().isAsseche()){
				this.boutonSeche_posActu.setText("pos actuelle");
				this.boutonFDT_annuler.setText("annuler");
				this.boutonEchange.setText("");
				// JPanel pan = (JPanel) this.getComponents()[0];
				// Component[] compo = pan.getComponents();
				// JButton temp = (JButton) compo[0];
				// temp.setText("annuler");

				// JPanel line1 = (JPanel) compo[1];
				// Component[] sub = line1.getComponents();
				// temp = (JButton) sub[2];
				// temp.setText("pos actuelle");

				// temp = (JButton) sub[0];
				// temp.setText("");

			}
			else if (this.jeu.getControleur().isEchange()) {
				this.removeAll();
				this.add(this.echange);
				// this.actif = this.echange;
			}
			else if (this.jeu.getControleur().isValidEchange()){
				this.removeAll();
				this.add(this.validationEch);
				// this.actif = this.validationEch;
			}
			this.repaint();
		}
	}

	private void init_depl(){
		// JButton boutonSeche_posActu;
		// JButton boutonFDT_annuler;
		// JButton boutonEchange;
		JButton boutonGauche;
		JButton boutonDroite;
		JButton boutonHaut;
		JButton boutonBas;


		boutonGauche = new JButton(icon_gauche);

		boutonDroite = new JButton(icon_droite);

		boutonHaut = new JButton(icon_haut);

		boutonBas = new JButton(icon_bas);

		/**
		 * ceux sans image
		 */
		boutonEchange = new JButton("Switch");
		boutonSeche_posActu = new JButton("Assecher");
		boutonFDT_annuler = new JButton("Fin Tour");


		/**CONTROLEUR, les actions listener */
		boutonEchange.addActionListener(r -> { this.jeu.getControleur().bouton_echange(); });
		boutonFDT_annuler.addActionListener(e -> { this.jeu.getControleur().boutonFDT(); });
        boutonBas.addActionListener(e -> { this.jeu.getControleur().bouton_fl_bas(); });
        boutonGauche.addActionListener(e -> { this.jeu.getControleur().bouton_fl_gauche(); });
        boutonDroite.addActionListener(e -> { this.jeu.getControleur().bouton_fl_droite(); });
        boutonHaut.addActionListener(e -> { this.jeu.getControleur().bouton_fl_haut(); });
		boutonSeche_posActu.addActionListener(e -> { this.jeu.getControleur().bouton_asseche(); });







		deplassement = new JPanel(new GridLayout(3,1));
		deplassement.setSize(dim);

		deplassement.add(boutonFDT_annuler);
		
		JPanel temp = new JPanel(new GridLayout(1, 3));
		temp.add(boutonEchange);
		temp.add(boutonHaut);
		temp.add(boutonSeche_posActu);
		
		deplassement.add(temp);

		temp = new JPanel(new GridLayout(1, 3));
		temp.add(boutonGauche);
		temp.add(boutonBas);
		temp.add(boutonDroite);

		deplassement.add(temp);
	}

	private void init_boutonEch() {
		JButton artAir = new JButton("Air");
		artAir.setBorder(null);
		artAir.setBackground(Artefact.Air.getColor());

		JButton artFeu = new JButton("Feu");
		artFeu.setBorder(null);
		artFeu.setBackground(Artefact.Feu.getColor());

		JButton artEau = new JButton("Eau");
		artEau.setBorder(null);
		artEau.setBackground(Artefact.Eau.getColor());

		JButton artTerre = new JButton("Terre");
		artTerre.setBorder(null);
		artTerre.setBackground(Artefact.Terre.getColor());

		artAir.addActionListener(e -> { this.jeu.getControleur().selectAir(); } );
		artTerre.addActionListener(e -> { this.jeu.getControleur().selectTerre(); } );
		artEau.addActionListener(e -> { this.jeu.getControleur().selectEau(); } );
		artFeu.addActionListener(e -> { this.jeu.getControleur().selectFeu(); } );


		JButton boutonFDT_annuler = new JButton("annuler");
		boutonFDT_annuler.addActionListener(e -> { this.jeu.getControleur().boutonFDT(); });



		echange = new JPanel(new GridLayout(3, 1));
		echange.setSize(dim);

		echange.add(boutonFDT_annuler);

		JPanel temp = new JPanel(new GridLayout(1, 2));
		temp.add(artAir);
		temp.add(artFeu);
		echange.add(temp);

		temp = new JPanel(new GridLayout(1, 2));
		temp.add(artTerre);
		temp.add(artEau);
		echange.add(temp);
	}

	private void init_validEch() {


		JButton boutonFDT_annuler = new JButton("annuler");
		boutonFDT_annuler.addActionListener(e -> { this.jeu.getControleur().boutonFDT(); });


		this.validationEch = new JPanel(new GridLayout(2,1));
		validationEch.setSize(dim);
		validationEch.add(boutonFDT_annuler);

		ArrayList<Joueur> possible = this.jeu.getControleur().joueursMemePos();

		JPanel temp = new JPanel(new GridLayout(1, possible.size()));
		for (int i = 0 ; i < possible.size(); i++){
			Joueur j = possible.get(i);

			JButton add = new JButton(this.jeu.getJoueur(i).toString());
			add.addActionListener(e -> { 
				j.getCle(this.jeu.getControleur().toGive());
				this.jeu.getControleur().resetToGive();
				this.jeu.getControleur().exitMode();
				this.jeu.notifyObservers();
			} );
			temp.add(add);
		}
		validationEch.add(temp);
	}
	
}