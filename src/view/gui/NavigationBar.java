package view.gui;

import controller.GuiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Barre de navigation latérale "Sunset & Cozy".
 */
public class NavigationBar extends JPanel {

    private final MainFrame parent;
    private final GuiController guiController;
    private JPanel buttonContainer;

    public NavigationBar(MainFrame parent, GuiController guiController) {
        this.parent = parent;
        this.guiController = guiController;
        
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);
        setPreferredSize(new Dimension(240, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BG_PANEL));

        // Logo avec icône (simulation du disque vinyle de l'image)
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 40));
        logoPanel.setOpaque(false);
        
        JLabel logoIcon = new JLabel("💿"); // Icône disque
        logoIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 40));
        logoIcon.setForeground(Theme.SUNSET_ORANGE);
        
        JLabel logoText = new JLabel(" JAVAZIC");
        logoText.setForeground(Theme.TEXT_PRIMARY);
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        logoPanel.add(logoIcon);
        logoPanel.add(logoText);
        add(logoPanel, BorderLayout.NORTH);

        buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setOpaque(false);
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        add(buttonContainer, BorderLayout.CENTER);
        
        refresh();
    }

    public void refresh() {
        buttonContainer.removeAll();
        
        addNavButton(" Accueil", "HOME", "🏠");
        addNavButton(" Catalogue", "CATALOGUE", "🔍");
        addNavButton(" Playlists", "PLAYLISTS", "📚");
        
        // Historique uniquement pour les abonnés connectés
        if (guiController != null && guiController.isConnected() && !guiController.isAdministrateur()) {
            addNavButton(" Historique", "HISTORIQUE", "📊");
        }
        
        // Administration en haut avec les autres onglets pour plus de visibilité
        if (guiController != null && guiController.isConnected() && guiController.isAdministrateur()) {
            addNavButton(" Administration", "ADMIN", "⚙");
        }
        
        buttonContainer.add(Box.createVerticalGlue());

        if (guiController != null && guiController.isConnected()) {
            addActionButton(" Se Déconnecter", () -> guiController.deconnecter(), "🚪");
        } else {
            addNavButton(" Se Connecter", "LOGIN", "👤");
        }
        
        buttonContainer.revalidate();
        buttonContainer.repaint();
    }

    private void addNavButton(String text, String target, String icon) {
        JButton btn = createStyledButton(icon + text);
        btn.addActionListener(e -> {
            parent.showPanel(target);
            resetButtonState(btn);
        });
        buttonContainer.add(btn);
        buttonContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    private void addActionButton(String text, Runnable action, String icon) {
        JButton btn = createStyledButton(icon + text);
        btn.addActionListener(e -> {
            action.run();
            resetButtonState(btn);
        });
        buttonContainer.add(btn);
        buttonContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (hovered) {
                    g2d.setColor(new Color(255, 255, 255, 15)); // Survol léger
                    g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);
                    setForeground(Theme.SUNSET_PEACH);
                } else {
                    setForeground(Theme.TEXT_SECONDARY);
                }
                super.paintComponent(g);
            }
        };

        btn.setMaximumSize(new Dimension(210, 50));
        btn.setPreferredSize(new Dimension(210, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        return btn;
    }
    
    private void resetButtonState(JButton btn) {
        // Cette méthode devient obsolète car le bouton gère son état
        btn.repaint();
    }
}
