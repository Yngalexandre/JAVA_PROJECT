package view.gui;

import controller.GuiController;
import javax.swing.*;
import java.awt.*;

/**
 * AdminPanel : Tableau de bord simplifié avec gestion musique et statistiques Top 3.
 */
public class AdminPanel extends JPanel {

    private final GuiController guiController;
    private JTextArea statArea;

    public AdminPanel(GuiController guiController) {
        this.guiController = guiController;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);

        JLabel title = new JLabel(" Espace Administrateur");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.SUNSET_ORANGE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Theme.FONT_BOLD);
        tabbedPane.setBackground(Theme.BG_DARK);
        tabbedPane.setForeground(Theme.SUNSET_ORANGE);

        tabbedPane.addTab("Gestion Musique", creerOngletMusique());
        tabbedPane.addTab("Statistiques", creerOngletStats());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel creerOngletMusique() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(Theme.BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // -- AJOUT --
        JPanel panelAjout = creerBoiteFormulaire("Ajouter un morceau");
        JTextField tfTitre = stylerChamp(new JTextField());
        JTextField tfArtiste = stylerChamp(new JTextField());
        JTextField tfDuree = stylerChamp(new JTextField());

        panelAjout.add(creerLabel("Titre du morceau :"));
        panelAjout.add(tfTitre);
        panelAjout.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAjout.add(creerLabel("Artiste / Groupe :"));
        panelAjout.add(tfArtiste);
        panelAjout.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAjout.add(creerLabel("Durée (sec) :"));
        panelAjout.add(tfDuree);
        panelAjout.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnAjouter = stylerBoutonAction("+ Ajouter");
        btnAjouter.addActionListener(e -> {
            try {
                guiController.ajouterMorceauAdmin(tfTitre.getText(), Integer.parseInt(tfDuree.getText()), tfArtiste.getText());
                tfTitre.setText(""); tfArtiste.setText(""); tfDuree.setText("");
            } catch (Exception ex) {
                guiController.showMessage("Erreur : " + ex.getMessage(), true);
            }
        });
        panelAjout.add(btnAjouter);

        // -- SUPPRESSION --
        JPanel panelSuppr = creerBoiteFormulaire("Supprimer un morceau");
        JTextField tfIdSuppr = stylerChamp(new JTextField());
        panelSuppr.add(creerLabel("Titre ou ID :"));
        panelSuppr.add(tfIdSuppr);
        panelSuppr.add(Box.createRigidArea(new Dimension(0, 30)));
        
        JButton btnSupprimer = stylerBoutonAction("🗑 Supprimer");
        btnSupprimer.setBackground(new Color(180, 50, 50));
        btnSupprimer.addActionListener(e -> {
            guiController.supprimerMorceauAdmin(tfIdSuppr.getText());
            tfIdSuppr.setText("");
        });
        panelSuppr.add(btnSupprimer);

        panel.add(panelAjout);
        panel.add(panelSuppr);
        return panel;
    }

    private JPanel creerOngletStats() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        statArea = new JTextArea("Chargement des stats...");
        statArea.setFont(new Font("Consolas", Font.BOLD, 14));
        statArea.setBackground(Theme.BG_PANEL);
        statArea.setForeground(Theme.SUNSET_YELLOW);
        statArea.setEditable(false);
        p.add(new JScrollPane(statArea), BorderLayout.CENTER);

        // -- MODÉRATION --
        JPanel panelModeration = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelModeration.setBackground(Theme.BG_PANEL);
        panelModeration.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.SUNSET_ORANGE), "Modération Abonnés"));
        ((javax.swing.border.TitledBorder)panelModeration.getBorder()).setTitleColor(Theme.TEXT_PRIMARY);

        JTextField tfLogin = stylerChamp(new JTextField(12));
        JButton btnBloquer = stylerBoutonAction("Bloquer");
        btnBloquer.setPreferredSize(new Dimension(100, 35));
        btnBloquer.addActionListener(e -> {
            guiController.suspendreAbonne(tfLogin.getText());
            tfLogin.setText("");
            refresh();
        });

        JButton btnActiver = stylerBoutonAction("Débloquer");
        btnActiver.setBackground(new Color(50, 150, 50));
        btnActiver.setPreferredSize(new Dimension(100, 35));
        btnActiver.addActionListener(e -> {
            guiController.activerAbonne(tfLogin.getText());
            tfLogin.setText("");
            refresh();
        });

        JButton btnSuppr = stylerBoutonAction("Supprimer");
        btnSuppr.setBackground(new Color(150, 40, 40));
        btnSuppr.setPreferredSize(new Dimension(100, 35));
        btnSuppr.addActionListener(e -> {
            guiController.supprimerAbonne(tfLogin.getText());
            tfLogin.setText("");
            refresh();
        });

        panelModeration.add(creerLabel("Login : "));
        panelModeration.add(tfLogin);
        panelModeration.add(btnBloquer);
        panelModeration.add(btnActiver);
        panelModeration.add(btnSuppr);

        p.add(panelModeration, BorderLayout.SOUTH);

        return p;
    }

    private JPanel creerBoiteFormulaire(String titre) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Theme.BG_PANEL);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel(titre);
        title.setFont(Theme.FONT_SUBTITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        return p;
    }

    private JLabel creerLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Theme.TEXT_SECONDARY);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        return l;
    }

    private JTextField stylerChamp(JTextField tf) {
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        tf.setBackground(Theme.BG_DARK);
        tf.setForeground(Theme.TEXT_PRIMARY);
        tf.setHorizontalAlignment(JTextField.CENTER);
        return tf;
    }

    private JButton stylerBoutonAction(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(Theme.SUNSET_ORANGE);
        btn.setForeground(Theme.BG_DARK);
        btn.setFont(Theme.FONT_BOLD);
        return btn;
    }

    public void refresh() {
        if (guiController.isAdministrateur()) {
            statArea.setText(guiController.getStatistiques());
        }
    }
}
