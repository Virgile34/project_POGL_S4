package observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import modele.Modele;
import modele.Joueur;

public class VueInformation extends JPanel implements Observer {
	private Modele jeu;
	private ArrayList<InfoPlayer> infos = new ArrayList<>();
	private ArrayList<String> strsInfoTour = new ArrayList<>();
	private ArrayList<JLabel> infoTour = new ArrayList<>(); //a qui c'est de jouer, comment de mouvement il reste..

	public VueInformation(Modele jeu) {
		this.jeu = jeu;
		jeu.addObserver((Observer) this);

		strsInfoTour = this.jeu.getInfoString();

		this.setLayout(new GridLayout(this.jeu.getJoueurs().size() + strsInfoTour.size(), 1));

		for (String str : strsInfoTour) {
			this.infoTour.add(new JLabel(str));
			this.add(infoTour.get(infoTour.size() - 1));
		}

		for(Joueur j : jeu.getJoueurs()) {
			infos.add(new InfoPlayer(j));
			this.add(infos.get(infos.size()- 1));
		}
		// this.update();
	}

	@Override
	public void update() {
		this.strsInfoTour = this.jeu.getInfoString();
		for (int i = 0 ; i < this.strsInfoTour.size() ; i++){
			this.infoTour.get(i).setText(this.strsInfoTour.get(i));
		}
		for (InfoPlayer i : infos) i.update();
		repaint();
	}

	class InfoPlayer extends JLabel {
		private Joueur j;

		private InfoPlayer(Joueur j) {
			super();
			this.j = j;
		}

		public void update() {
			this.setText(j.getInfoString());
		}
	}


	
}