package view.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * Thème JAVAZIC "Sunset & Cozy" basé sur les retours visuels.
 */
public class Theme {
    
    // --- Couleurs de fond (Violets & Crépuscule) ---
    public static final Color BG_DARK = new Color(38, 25, 62);      // Purple profond des ombres
    public static final Color BG_PANEL = new Color(60, 42, 92);     // Violet de transition
    public static final Color BG_ACCENT = new Color(255, 245, 220); // Crème/Pêche doux du fond d'image
    
    // --- Couleurs Sunset (Accents) ---
    public static final Color SUNSET_ORANGE = new Color(255, 126, 103); // Orange couché de soleil
    public static final Color SUNSET_PEACH = new Color(255, 180, 140);  // Pêche clair
    public static final Color SUNSET_YELLOW = new Color(255, 210, 120); // Jaune soleil lointain
    
    // --- Couleurs de texte ---
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(200, 180, 230); // Mauve clair aérien
    public static final Color TEXT_DARK = new Color(45, 27, 76);        // Texte foncé pour contrastes sur jaune
    
    // --- Polices ---
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 36);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 20);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
}
