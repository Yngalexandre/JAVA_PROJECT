package view.gui;

import controller.GuiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interface de connexion + inscription JAVAZIC "Sunset Edition".
 * Deux modes : Connexion (par défaut) et Inscription (au clic du lien).
 */
public class LoginPanel extends JPanel {

    private final GuiController guiController;

    // Champs Connexion
    private JTextField tfLogin;
    private JPasswordField tfPassword;

    // Champs Inscription
    private JTextField tfRegLogin;
    private JPasswordField tfRegPassword;
    private JTextField tfRegNom;

    // Conteneur principal
    private CardLayout modeLayout;
    private JPanel modeContainer;

    public LoginPanel(GuiController guiController) {
        this.guiController = guiController;
        setLayout(new GridBagLayout());
        setOpaque(false);

        // --- Conteneur de modes (Login / Register) ---
        modeLayout = new CardLayout();
        modeContainer = new JPanel(modeLayout);
        modeContainer.setOpaque(false);

        modeContainer.add(creerPanneauConnexion(), "LOGIN");
        modeContainer.add(creerPanneauInscription(), "REGISTER");

        modeLayout.show(modeContainer, "LOGIN");
        add(modeContainer);
    }

    // ========================
    //  PANNEAU CONNEXION
    // ========================
    private JPanel creerPanneauConnexion() {
        JPanel form = creerFormContainer();

        JLabel title = new JLabel("JAVAZIC");
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(Theme.SUNSET_YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(title);

        JLabel subtitle = new JLabel("Connectez-vous pour écouter");
        subtitle.setFont(Theme.FONT_NORMAL);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(subtitle);
        form.add(Box.createRigidArea(new Dimension(0, 40)));

        addLabel(form, "NOM D'UTILISATEUR");
        tfLogin = new JTextField();
        stylerChampText(tfLogin);
        form.add(tfLogin);
        form.add(Box.createRigidArea(new Dimension(0, 20)));

        addLabel(form, "MOT DE PASSE");
        tfPassword = new JPasswordField();
        stylerChampText(tfPassword);
        form.add(tfPassword);
        form.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton btnLogin = creerBoutonPrincipal("ENTRER DANS L'EXPÉRIENCE");
        btnLogin.addActionListener(e -> {
            guiController.connecter(tfLogin.getText(), new String(tfPassword.getPassword()));
        });
        form.add(btnLogin);
        form.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lienInscription = new JLabel("Pas encore de compte ? Créer un compte");
        styliserLien(lienInscription);
        lienInscription.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { modeLayout.show(modeContainer, "REGISTER"); }
        });
        form.add(lienInscription);

        return form;
    }

    // ========================
    //  PANNEAU INSCRIPTION
    // ========================
    private JPanel creerPanneauInscription() {
        JPanel form = creerFormContainer();

        JLabel title = new JLabel("Créer un compte");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Theme.SUNSET_YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(title);

        JLabel subtitle = new JLabel("Rejoignez JAVAZIC gratuitement");
        subtitle.setFont(Theme.FONT_NORMAL);
        subtitle.setForeground(Theme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.add(subtitle);
        form.add(Box.createRigidArea(new Dimension(0, 35)));

        addLabel(form, "NOM D'AFFICHAGE");
        tfRegNom = new JTextField();
        stylerChampText(tfRegNom);
        form.add(tfRegNom);
        form.add(Box.createRigidArea(new Dimension(0, 15)));

        addLabel(form, "NOM D'UTILISATEUR");
        tfRegLogin = new JTextField();
        stylerChampText(tfRegLogin);
        form.add(tfRegLogin);
        form.add(Box.createRigidArea(new Dimension(0, 15)));

        addLabel(form, "MOT DE PASSE");
        tfRegPassword = new JPasswordField();
        stylerChampText(tfRegPassword);
        form.add(tfRegPassword);
        form.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton btnRegister = creerBoutonPrincipal("CRÉER MON COMPTE");
        btnRegister.addActionListener(e -> {
            guiController.inscrire(
                    tfRegLogin.getText(),
                    new String(tfRegPassword.getPassword()),
                    tfRegNom.getText()
            );
        });
        form.add(btnRegister);
        form.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lienConnexion = new JLabel("Déjà un compte ? Se connecter");
        styliserLien(lienConnexion);
        lienConnexion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { modeLayout.show(modeContainer, "LOGIN"); }
        });
        form.add(lienConnexion);

        return form;
    }

    // ========================
    //  HELPERS
    // ========================
    private JPanel creerFormContainer() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(45, 27, 76, 180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        panel.setPreferredSize(new Dimension(420, 520));
        return panel;
    }

    private void addLabel(JPanel p, String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Theme.TEXT_SECONDARY);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(l);
        p.add(Box.createRigidArea(new Dimension(0, 6)));
    }

    private void stylerChampText(JTextField champ) {
        champ.setMaximumSize(new Dimension(300, 40));
        champ.setPreferredSize(new Dimension(300, 40));
        champ.setBackground(new Color(20, 10, 30));
        champ.setForeground(Theme.TEXT_PRIMARY);
        champ.setCaretColor(Theme.SUNSET_ORANGE);
        champ.setHorizontalAlignment(JTextField.CENTER);
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BG_PANEL, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        champ.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JButton creerBoutonPrincipal(String texte) {
        JButton btn = new JButton(texte);
        btn.setMaximumSize(new Dimension(300, 45));
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(Theme.SUNSET_ORANGE);
        btn.setForeground(Theme.BG_DARK);
        btn.setFont(Theme.FONT_BOLD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(Theme.SUNSET_YELLOW); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Theme.SUNSET_ORANGE); }
        });
        return btn;
    }

    private void styliserLien(JLabel label) {
        label.setFont(Theme.FONT_NORMAL);
        label.setForeground(Theme.SUNSET_PEACH);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { label.setForeground(Theme.SUNSET_YELLOW); }
            public void mouseExited(MouseEvent e) { label.setForeground(Theme.SUNSET_PEACH); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        GradientPaint gp = new GradientPaint(0, 0, Theme.BG_ACCENT, 0, getHeight(), Theme.BG_DARK);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(new Color(255, 126, 103, 50));
        g2d.fillOval(getWidth() - 200, -100, 300, 300);
    }
}
