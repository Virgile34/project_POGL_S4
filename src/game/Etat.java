package game;

import java.awt.*;


/**
 * Enum Etat : les etat possibles d'une case
 */
public enum Etat {
	//les etats possibles d'une case
	NORMALE, INONDE, SUBMERGE;


	//modifie une couleur en fonction le l'etat de this
	Color makeColor(Color c) {
		if (this == NORMALE)
			return c;	//si la case est NORMALE on touche pas
		else if (this == INONDE)
			return c.darker().darker().darker();	//si la case est INONDE on assombrit
		else
			return Color.BLACK; //si la case est SUBMERGE on renvoie noir.
	}
}

