package modele;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/**
 * Enum Cle : les clees possibles
 */
enum Cle {
    //clef possible
    Air, Eau, Feu, Terre;

    /**
     * makeFromInt
     * 
     * @param n : entier pour creer une clef (n dois appartenir a {0, 1, 2, 3})
     * @return : une clef, fonction de n (si n in {0,1,2,3}, sinon leve une
     *         ValueException)
     */
    public static Cle makeFromInt(int n) throws ValueException {
        switch (n) {
            case 0:
                return Cle.Air;
            case 1:
                return Cle.Eau;
            case 2:
                return Cle.Feu;
            case 3:
                return Cle.Terre;
            default:
                throw new ValueException(String.format("%d not in {0,1,2,3} in makeFromInt", n));
        }

    }
}