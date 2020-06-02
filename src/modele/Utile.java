package modele;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.FontMetrics;

public class Utile {
	/**
	 * Dessine le texte dans g avec la font, centre dans rect
	 * @param g
	 * @param text
	 * @param rect
	 * @param font
	 */
	public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java
		// 2d 0 is top of the screen)
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(text, x, y);
	}

	/**
	 * renvoie le dernier element de array 
	 * prerequis : array non vide
	 * @param array
	 * @return
	 */
	public static Artefact last(ArrayList<Artefact> array) {
		return array.get(array.size() - 1);
	}
}