package view.gui;

import controller.GuiController;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panneau affichant l'historique d'écoute de l'abonné connecté.
 */
public class HistoriquePanel extends JPanel {

    private final GuiController guiController;
    private HistoriqueTableModel tableModel;
    private JLabel lblConnexionRequise;
    private JScrollPane scrollPane;

    public HistoriquePanel(GuiController guiController) {
        this.guiController = guiController;
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);

        // Titre
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JLabel title = new JLabel("📋 Historique d'écoute");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_PRIMARY);
        headerPanel.add(title, BorderLayout.WEST);

        JButton btnEffacer = new JButton("🗑 Effacer l'historique");
        btnEffacer.setBackground(new Color(100, 45, 45));
        btnEffacer.setForeground(Theme.TEXT_PRIMARY);
        btnEffacer.setFont(Theme.FONT_NORMAL);
        btnEffacer.setFocusPainted(false);
        btnEffacer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEffacer.addActionListener(e -> {
            guiController.showMessage("Fonctionnalité disponible en console (réinitialisation du compte).", false);
        });
        headerPanel.add(btnEffacer, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Message si non connecté
        lblConnexionRequise = new JLabel("Connectez-vous en tant qu'Abonné pour voir votre historique.", SwingConstants.CENTER);
        lblConnexionRequise.setFont(Theme.FONT_SUBTITLE);
        lblConnexionRequise.setForeground(Theme.TEXT_SECONDARY);
        lblConnexionRequise.setVisible(false);
        add(lblConnexionRequise, BorderLayout.CENTER);

        // Tableau
        tableModel = new HistoriqueTableModel(List.of());
        JTable table = new JTable(tableModel);
        stylerTableau(table);

        scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG_DARK);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BG_PANEL));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        if (!guiController.isConnected() || guiController.isAdministrateur()) {
            scrollPane.setVisible(false);
            lblConnexionRequise.setVisible(true);
        } else {
            lblConnexionRequise.setVisible(false);
            scrollPane.setVisible(true);
            tableModel.setEntrees(guiController.getHistorique());
        }
        revalidate();
        repaint();
    }

    private void stylerTableau(JTable table) {
        table.setBackground(Theme.BG_DARK);
        table.setForeground(Theme.TEXT_PRIMARY);
        table.setFont(Theme.FONT_NORMAL);
        table.setRowHeight(38);
        table.setSelectionBackground(Theme.SUNSET_ORANGE);
        table.setSelectionForeground(Theme.BG_DARK);
        table.setShowGrid(true);
        table.setGridColor(Theme.BG_PANEL);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setShowVerticalLines(false);

        table.getTableHeader().setBackground(Theme.BG_DARK);
        table.getTableHeader().setForeground(Theme.SUNSET_PEACH);
        table.getTableHeader().setFont(Theme.FONT_BOLD);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.SUNSET_ORANGE));
    }

    // --- Modèle de données ---
    private static class HistoriqueTableModel extends AbstractTableModel {
        private final String[] colonnes = {"#", "Morceau écouté"};
        private List<String> entrees;

        public HistoriqueTableModel(List<String> entrees) {
            this.entrees = entrees;
        }

        public void setEntrees(List<String> entrees) {
            this.entrees = entrees;
            fireTableDataChanged();
        }

        @Override public int getRowCount() { return entrees.size(); }
        @Override public int getColumnCount() { return colonnes.length; }
        @Override public String getColumnName(int col) { return colonnes[col]; }
        @Override public Object getValueAt(int row, int col) {
            return col == 0 ? (row + 1) : entrees.get(row);
        }
    }
}
