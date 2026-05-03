package view.gui;

import controller.GuiController;
import model.media.Morceau;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * Affiche la gestion des playlists de l'abonné connecté.
 */
public class PlaylistPanel extends JPanel {

    private final GuiController guiController;
    private DefaultListModel<String> listModel;
    private JList<String> listPlaylists;
    private PlaylistMorceauTableModel tableModel;
    private JTable table;

    public PlaylistPanel(GuiController guiController) {
        this.guiController = guiController;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);

        // -- Titre --
        JLabel title = new JLabel(" Mes Playlists");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        if (guiController.isAdministrateur()) {
            JLabel adminHint = new JLabel(" (Mode Administrateur : les playlists sont réservées aux abonnés)");
            adminHint.setFont(Theme.FONT_NORMAL);
            adminHint.setForeground(Theme.SUNSET_PEACH);
            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            header.setOpaque(false);
            header.add(title);
            header.add(adminHint);
            add(header, BorderLayout.NORTH);
        } else {
            add(title, BorderLayout.NORTH);
        }

        // -- Layout Central splitté (Gauche: Liste, Droite: Contenu) --
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(250);
        splitPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BG_PANEL));

        // Panneau de gauche : La liste des playlists
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Theme.BG_DARK);

        listModel = new DefaultListModel<>();
        listPlaylists = new JList<>(listModel);
        listPlaylists.setBackground(Theme.BG_DARK);
        listPlaylists.setForeground(Theme.TEXT_PRIMARY);
        listPlaylists.setFont(Theme.FONT_BOLD);
        listPlaylists.setSelectionBackground(Theme.SUNSET_ORANGE);
        listPlaylists.setSelectionForeground(Theme.BG_DARK);
        listPlaylists.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        listPlaylists.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) chargerContenuPlaylist();
        });

        JScrollPane scrollList = new JScrollPane(listPlaylists);
        scrollList.setBorder(null);
        leftPanel.add(scrollList, BorderLayout.CENTER);

        // Boutons Créer / Renommer / Supprimer Playlist
        JPanel actionsPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        actionsPanel.setBackground(Theme.BG_DARK);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton btnCreer = new JButton("+ CRÉER");
        stylerBoutonPanel(btnCreer, Theme.SUNSET_ORANGE);
        btnCreer.addActionListener(e -> creerNouvellePlaylist());

        JButton btnRenommer = new JButton("✏ RENOMMER");
        stylerBoutonPanel(btnRenommer, Theme.BG_PANEL);
        btnRenommer.addActionListener(e -> renommerPlaylist());

        JButton btnSupprimer = new JButton("🗑 SUPPR.");
        stylerBoutonPanel(btnSupprimer, new Color(120, 40, 40));
        btnSupprimer.addActionListener(e -> supprimerPlaylist());

        actionsPanel.add(btnCreer);
        actionsPanel.add(btnRenommer);
        actionsPanel.add(btnSupprimer);
        leftPanel.add(actionsPanel, BorderLayout.SOUTH);

        splitPane.setLeftComponent(leftPanel);

        // Panneau de droite : Le tableau des morceaux
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Theme.BG_DARK);

        tableModel = new PlaylistMorceauTableModel(List.of());
        table = new JTable(tableModel);
        stylerTableau();

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.getViewport().setBackground(Theme.BG_DARK);
        scrollTable.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(scrollTable, BorderLayout.CENTER);

        // Bouton d'action bas droite : Écouter + Retirer
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setBackground(Theme.BG_DARK);
        JButton retireButton = new JButton("⊖ RETIRER");
        retireButton.setBackground(new Color(100, 40, 40));
        retireButton.setForeground(Theme.TEXT_DARK);
        retireButton.setFont(Theme.FONT_BOLD);
        retireButton.setFocusPainted(false);
        retireButton.addActionListener(e -> retirerMorceauSelectionne());
        JButton playButton = new JButton("▶ ÉCOUTER");
        playButton.setBackground(Theme.SUNSET_ORANGE);
        playButton.setForeground(Theme.BG_DARK);
        playButton.setFont(Theme.FONT_BOLD);
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> ecouterMorceauSelectionne());
        bottomPanel.add(retireButton);
        bottomPanel.add(playButton);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(250);

        add(splitPane, BorderLayout.CENTER);
    }

    private void stylerBoutonPanel(JButton btn, java.awt.Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Theme.TEXT_PRIMARY);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
    }

    private void stylerTableau() {
        table.setBackground(Theme.BG_DARK);
        table.setForeground(Theme.TEXT_PRIMARY);
        table.setFont(Theme.FONT_NORMAL);
        table.setRowHeight(35);
        table.setSelectionBackground(Theme.SUNSET_ORANGE);
        table.setSelectionForeground(Theme.BG_DARK);
        
        // Séparations nettes
        table.setShowGrid(true);
        table.setGridColor(Theme.BG_PANEL);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setShowVerticalLines(false);
        
        table.getTableHeader().setBackground(Theme.BG_DARK);
        table.getTableHeader().setForeground(Theme.SUNSET_PEACH);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BG_PANEL));
    }

    private void creerNouvellePlaylist() {
        if (!guiController.isConnected()) {
            guiController.showMessage("Vous devez être connecté pour créer une playlist.", true);
            return;
        }
        String nom = JOptionPane.showInputDialog(this, "Nom de la nouvelle playlist :");
        if (nom != null && !nom.trim().isEmpty()) {
            guiController.creerPlaylist(nom.trim());
            refresh();
        }
    }

    private void renommerPlaylist() {
        String selectionne = listPlaylists.getSelectedValue();
        if (selectionne == null) { guiController.showMessage("Sélectionnez une playlist à renommer.", true); return; }
        String vraiNom = selectionne;
        String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom :", vraiNom);
        if (nouveauNom != null && !nouveauNom.isBlank()) {
            guiController.renommerPlaylist(vraiNom, nouveauNom.trim());
            refresh();
        }
    }

    private void supprimerPlaylist() {
        String selectionne = listPlaylists.getSelectedValue();
        if (selectionne == null) { guiController.showMessage("Sélectionnez une playlist à supprimer.", true); return; }
        String vraiNom = selectionne;
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer la playlist '" + vraiNom + "' ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            guiController.supprimerPlaylist(vraiNom);
            refresh();
        }
    }

    private void retirerMorceauSelectionne() {
        String nomPlaylist = listPlaylists.getSelectedValue();
        if (nomPlaylist == null) { guiController.showMessage("Sélectionnez une playlist.", true); return; }
        int row = table.getSelectedRow();
        if (row < 0) { guiController.showMessage("Sélectionnez un morceau à retirer.", true); return; }
        Morceau m = tableModel.getMorceauAt(row);
        String vraiNom = nomPlaylist;
        guiController.retirerMorceauDePlaylist(m, vraiNom);
        chargerContenuPlaylist();
    }

    private void chargerContenuPlaylist() {
        String nomSelectionne = listPlaylists.getSelectedValue();
        if (nomSelectionne != null) {
            // Nettoyer l'éventuel toString() "Playlist : Nom"
            String vraiNom = nomSelectionne;
            tableModel.setMorceaux(guiController.getMorceauxDePlaylist(vraiNom));
        } else {
            tableModel.setMorceaux(List.of());
        }
    }

    private void ecouterMorceauSelectionne() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Morceau m = tableModel.getMorceauAt(selectedRow);
            guiController.ecouterMorceau(m);
        } else {
            guiController.showMessage("Veuillez sélectionner un morceau à écouter.", true);
        }
    }

    public void refresh() {
        listModel.clear();
        if (guiController.isConnected()) {
            List<String> noms = guiController.getNomsPlaylists();
            for (String nom : noms) {
                listModel.addElement(nom);
            }
        }
        chargerContenuPlaylist();
    }

    // --- Modèle de tableau simplifié ---
    private static class PlaylistMorceauTableModel extends AbstractTableModel {
        private final String[] colonnes = {"Titre", "Artiste", "Durée (s)"};
        private List<Morceau> morceaux;

        public PlaylistMorceauTableModel(List<Morceau> morceaux) {
            this.morceaux = morceaux;
        }

        public void setMorceaux(List<Morceau> morceaux) {
            this.morceaux = morceaux;
            fireTableDataChanged();
        }

        public Morceau getMorceauAt(int row) { return morceaux.get(row); }
        @Override public int getRowCount() { return morceaux.size(); }
        @Override public int getColumnCount() { return colonnes.length; }
        @Override public String getColumnName(int column) { return colonnes[column]; }
        @Override public Object getValueAt(int rowIndex, int columnIndex) {
            Morceau m = morceaux.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> m.getTitre();
                case 1 -> m.getArtiste();
                case 2 -> m.getDuree();
                default -> null;
            };
        }
    }
}
