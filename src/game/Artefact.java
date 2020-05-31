package game;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.awt.*;

/**
 * Enum Artefact : les artefacts possibles
 */
public enum Artefact {
	//Artefact possible et leur couleur associe
	Air(Color.YELLOW), Eau(Color.BLUE), Feu(Color.RED), Terre(Color.GREEN);

	// couleur associe a l'artefact
	Color c;

	/**
	 * Constructeur :
	 */
	Artefact(Color c) {
		this.c = c;	//initialise la couleur 
	}

	/**
	 * 		getColor 
	 * @return la couleur associe a l'artefact
	 */
	Color getColor() {
		return this.c;
	}

	/**
	 * 		makeFromInt
	 * @param n : entier pour creer un Artefact (n dois appartenir a {0, 1, 2, 3})
	 * @return : un	Artefact, fonction de n (si n in {0,1,2,3}, sinon leve une ValueException)
	 */
	public static Artefact makeFromInt(int n) throws ValueException {
		switch (n) {
			case 0 : return Artefact.Air;
			case 1 : return Artefact.Eau;
			case 2 : return Artefact.Feu;
			case 3 : return Artefact.Terre;
			default : throw new ValueException(String.format("%d not in {0,1,2,3} in makeFromInt", n));
		}

	}
}