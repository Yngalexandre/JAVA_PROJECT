package view.gui;

import controller.GuiController;
import model.media.Morceau;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * Affiche le catalogue sous forme d'un tableau propre.
 */
public class CataloguePanel extends JPanel {

    private final GuiController guiController;
    private JTable table;
    private MorceauTableModel tableModel;

    public CataloguePanel(GuiController guiController) {
        this.guiController = guiController;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);

        // --- En-tête avec titre + champ de recherche ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        JLabel title = new JLabel("🎵 Catalogue Musical");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        headerPanel.add(title, BorderLayout.WEST);

        // Barre de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setForeground(Theme.TEXT_SECONDARY);
        searchIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));

        JTextField tfRecherche = new JTextField(20);
        tfRecherche.setBackground(Theme.BG_PANEL);
        tfRecherche.setForeground(Theme.TEXT_PRIMARY);
        tfRecherche.setCaretColor(Theme.SUNSET_ORANGE);
        tfRecherche.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.TEXT_SECONDARY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        tfRecherche.setFont(Theme.FONT_NORMAL);
        tfRecherche.putClientProperty("JTextField.placeholderText", "Chercher par titre, artiste...");

        searchPanel.add(searchIcon);
        searchPanel.add(tfRecherche);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Tableau
        tableModel = new MorceauTableModel(guiController.getTousLesMorceaux());
        table = new JTable(tableModel);
        stylerTableau();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG_DARK);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Lier le champ de recherche au filtrage
        tfRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrer(tfRecherche.getText()); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrer(tfRecherche.getText()); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrer(tfRecherche.getText()); }
        });

        // Bouton d'action en bas
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(Theme.BG_DARK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton playButton = new JButton("▶ ÉCOUTER");
        stylerBouton(playButton);
        playButton.addActionListener(e -> ecouterMorceauSelectionne());
        
        JButton addPlaylistButton = new JButton("✚ AJOUTER À LA PLAYLIST");
        stylerBouton(addPlaylistButton);
        addPlaylistButton.setBackground(Theme.BG_PANEL); // Couleur différente pour distinguer
        addPlaylistButton.setForeground(Theme.SUNSET_PEACH);
        addPlaylistButton.setPreferredSize(new Dimension(220, 40));
        addPlaylistButton.addActionListener(e -> ajouterMorceauPlaylistSelectionne());

        bottomPanel.add(addPlaylistButton);
        bottomPanel.add(playButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void stylerTableau() {
        table.setBackground(Theme.BG_DARK);
        table.setForeground(Theme.TEXT_PRIMARY);
        table.setFont(Theme.FONT_NORMAL);
        table.setSelectionBackground(Theme.SUNSET_ORANGE);
        table.setSelectionForeground(Theme.BG_DARK);
        
        // Séparations nettes
        table.setShowGrid(true);
        table.setGridColor(Theme.BG_PANEL); // Séparation subtile mais visible
        table.setIntercellSpacing(new Dimension(0, 1)); // Ligne horizontale uniquement
        
        // Supprimer la grille verticale pour un look plus moderne
        table.setShowVerticalLines(false);

        // Style de l'en-tête
        table.getTableHeader().setBackground(Theme.BG_DARK);
        table.getTableHeader().setForeground(Theme.SUNSET_PEACH);
        table.getTableHeader().setFont(Theme.FONT_BOLD);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BG_PANEL));

        // Centrer les colonnes courtes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }

    private void stylerBouton(JButton btn) {
        btn.setBackground(Theme.SUNSET_ORANGE);
        btn.setForeground(Theme.BG_DARK);
        btn.setFont(Theme.FONT_BOLD);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
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

    private void filtrer(String texte) {
        List<Morceau> tous = guiController.getTousLesMorceaux();
        if (texte == null || texte.isBlank()) {
            tableModel.setMorceaux(tous);
        } else {
            String lower = texte.toLowerCase();
            List<Morceau> filtres = tous.stream()
                    .filter(m -> m.getTitre().toLowerCase().contains(lower)
                            || m.getArtiste().toLowerCase().contains(lower))
                    .toList();
            tableModel.setMorceaux(filtres);
        }
    }

    private void ajouterMorceauPlaylistSelectionne() {
        if (!guiController.isConnected()) {
            guiController.showMessage("Vous devez être connecté (Abonné) pour utiliser les playlists.", true);
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            guiController.showMessage("Veuillez sélectionner un morceau à ajouter.", true);
            return;
        }

        List<String> playlists = guiController.getNomsPlaylists();
        if (playlists.isEmpty()) {
            guiController.showMessage("Vous n'avez aucune playlist. Créez-en une d'abord dans l'onglet Playlists.", true);
            return;
        }

        // Utiliser directement les noms renvoyés par le service (vraiNom désormais)
        String[] options = playlists.toArray(new String[0]);

        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Choisissez la playlist de destination :",
                "Ajouter à la playlist",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choix != null) {
            Morceau m = tableModel.getMorceauAt(selectedRow);
            guiController.ajouterMorceauAPlaylist(m, choix);
        }
    }

    /**
     * Rafraîchit les données du tableau
     */
    public void refresh() {
        tableModel.setMorceaux(guiController.getTousLesMorceaux());
    }

    // --- Modèle de données pour la JTable ---
    private static class MorceauTableModel extends AbstractTableModel {
        private final String[] colonnes = {"Titre", "Artiste", "Genre", "Durée (s)"};
        private List<Morceau> morceaux;

        public MorceauTableModel(List<Morceau> morceaux) {
            this.morceaux = morceaux;
        }

        public void setMorceaux(List<Morceau> morceaux) {
            this.morceaux = morceaux;
            fireTableDataChanged();
        }

        public Morceau getMorceauAt(int row) {
            return morceaux.get(row);
        }

        @Override
        public int getRowCount() { return morceaux.size(); }
        @Override
        public int getColumnCount() { return colonnes.length; }
        @Override
        public String getColumnName(int column) { return colonnes[column]; }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Morceau m = morceaux.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> m.getTitre();
                case 1 -> m.getArtiste();
                case 2 -> m.getGenre() != null ? m.getGenre().name() : "-";
                case 3 -> m.getDuree();
                default -> null;
            };
        }
    }
}
