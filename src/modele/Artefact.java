package modele;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.awt.*;

/**
 * Enum Artefact : les artefacts possibles
 */
public enum Artefact {
	//Artefact possible et leur couleur associe
	Air(Color.YELLOW, Cle.Air), Eau(Color.BLUE, Cle.Eau), Feu(Color.RED, Cle.Feu), Terre(Color.GREEN, Cle.Terre);

	// couleur associe a l'artefact
	Color c;
	Cle key;
	/**
	 * Constructeur :
	 */
	Artefact(Color c, Cle key) {
		this.c = c;	//initialise la couleur
		this.key = key; 
	}

	/**
	 * 		getColor 
	 * @return la couleur associe a l'artefact
	 */
	public Color getColor() {
		return this.c;
	}

	/**
	 * makeFromInt
	 * 
	 * @param n : entier pour creer un Artefact (n dois appartenir a {0, 1, 2, 3})
	 * @return : un Artefact, fonction de n (si n in {0,1,2,3}, sinon leve une
	 *         ValueException)
	 * @throws ValueException
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

	/**
	 * 
	 * @return : la cle correspondant a l'artefact
	 */
	public Cle toCle(){
		return this.key;
	}
}