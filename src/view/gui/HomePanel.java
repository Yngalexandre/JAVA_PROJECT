package view.gui;

import controller.GuiController;
import model.media.Morceau;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * HomePanel : L'écran d'accueil chaleureux de l'application.
 * Il affiche des suggestions personnalisées ("Sunset & Cozy") basées sur 
 * les goûts réels de l'abonné.
 */
public class HomePanel extends JPanel {

    private final GuiController guiController;
    private final JPanel cardsContainer;
    private final JLabel newsTitle;

    public HomePanel(GuiController guiController) {
        this.guiController = guiController;
        setLayout(new BorderLayout());
        setOpaque(false);
        
        JPanel welcomeContainer = new JPanel();
        welcomeContainer.setLayout(new BoxLayout(welcomeContainer, BoxLayout.Y_AXIS));
        welcomeContainer.setOpaque(false);
        welcomeContainer.setBorder(BorderFactory.createEmptyBorder(60, 80, 40, 80));
        
        JLabel welcomeTitle = new JLabel("Bienvenue sur JAVAZIC");
        welcomeTitle.setFont(Theme.FONT_TITLE);
        welcomeTitle.setForeground(Theme.TEXT_PRIMARY);
        welcomeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeContainer.add(welcomeTitle);
        
        welcomeContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JLabel subtitle = new JLabel("Détendez-vous au rythme d'un coucher de soleil musical.");
        subtitle.setFont(Theme.FONT_SUBTITLE);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeContainer.add(subtitle);
        
        welcomeContainer.add(Box.createRigidArea(new Dimension(0, 50)));
        
        newsTitle = new JLabel("Recommandés pour vous");
        newsTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        newsTitle.setForeground(Theme.SUNSET_PEACH);
        newsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeContainer.add(newsTitle);
        
        cardsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        cardsContainer.setOpaque(false);
        cardsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        welcomeContainer.add(Box.createRigidArea(new Dimension(0, 25)));
        welcomeContainer.add(cardsContainer);
        
        add(welcomeContainer, BorderLayout.NORTH);
        
        refresh(); // Chargement initial
    }
    
    public void refresh() {
        cardsContainer.removeAll();
        List<Morceau> recommandations = guiController.getRecommandations();
        
        if (recommandations.isEmpty()) {
            newsTitle.setText("Morceaux vedettes du moment");
            // Utiliser des morceaux aléatoires au lieu de mock vides
            List<Morceau> vedettes = guiController.getMorceauxAleatoires(3);
            Color[] colors = {Theme.SUNSET_ORANGE, Theme.BG_PANEL, Theme.SUNSET_YELLOW};
            
            if (vedettes.isEmpty()) {
                // Vraiment rien dans le catalogue
                cardsContainer.add(createAestheticCard(null, "Catalogue vide", Theme.BG_PANEL));
            } else {
                for (int i = 0; i < vedettes.size(); i++) {
                    Morceau m = vedettes.get(i);
                    cardsContainer.add(createAestheticCard(m, m.getTitre(), colors[i % colors.length]));
                }
            }
        } else {
            newsTitle.setText("Spécialement pour vous");
            Color[] colors = {Theme.SUNSET_ORANGE, Theme.BG_PANEL, Theme.SUNSET_YELLOW};
            for (int i = 0; i < recommandations.size(); i++) {
                Morceau m = recommandations.get(i);
                cardsContainer.add(createAestheticCard(m, m.getTitre(), colors[i % colors.length]));
            }
        }
        
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }
    
    private JPanel createAestheticCard(Morceau morceau, String title, Color accent) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Theme.BG_DARK);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                // Petit disque vinyle symbolique
                g2d.setColor(accent);
                g2d.fillOval(75, 50, 50, 50);
                g2d.setColor(Theme.BG_DARK);
                g2d.fillOval(95, 70, 10, 10);
            }
        };
        card.setPreferredSize(new Dimension(200, 250));
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (morceau != null) {
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    guiController.ecouterMorceau(morceau);
                }
            });
        }
        
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setForeground(Theme.TEXT_PRIMARY);
        label.setFont(Theme.FONT_BOLD);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        card.add(label, BorderLayout.SOUTH);
        
        return card;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        GradientPaint gp = new GradientPaint(
                0, 0, Theme.BG_ACCENT, 
                0, getHeight(), Theme.BG_DARK);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(new Color(255, 197, 120, 100)); 
        g2d.fillOval(-100, -100, 400, 400);
    }
}
