package view.gui;

import javax.swing.*;
import java.awt.*;

/**
 * PlayerBar : Barre de lecture simple avec bouton STOP.
 */
public class PlayerBar extends JPanel {

    private final JLabel lblTrack;
    private final JLabel lblArtist;
    private final JLabel lblTime;
    private final JProgressBar progressBar;
    private final JLabel lblPlayIcon;
    private final JLabel lblStopIcon;

    private Timer animationTimer;
    private int dureeTotale = 0;
    private int secondesEcoulees = 0;
    private boolean isPlaying = false;

    public PlayerBar() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_DARK);
        setPreferredSize(new Dimension(0, 100));
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BG_PANEL));

        // -- Infos --
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        infoPanel.setPreferredSize(new Dimension(320, 0));
        lblTrack = new JLabel("Aucune lecture");
        lblTrack.setForeground(Theme.TEXT_PRIMARY);
        lblTrack.setFont(Theme.FONT_BOLD);
        lblArtist = new JLabel("Choisissez un titre");
        lblArtist.setForeground(Theme.TEXT_SECONDARY);
        infoPanel.add(lblTrack);
        infoPanel.add(lblArtist);
        add(infoPanel, BorderLayout.WEST);

        // -- Contrôles --
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setOpaque(false);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttons.setOpaque(false);

        lblPlayIcon = new JLabel("▶");
        lblPlayIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        lblPlayIcon.setForeground(Theme.TEXT_PRIMARY);
        lblPlayIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblPlayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { togglePlayPause(); }
        });
        buttons.add(lblPlayIcon);

        lblStopIcon = new JLabel("⏹");
        lblStopIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        lblStopIcon.setForeground(Theme.TEXT_PRIMARY);
        lblStopIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblStopIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { stop(); }
        });
        buttons.add(lblStopIcon);
        controlsPanel.add(buttons);

        progressBar = new JProgressBar(0, 1000);
        progressBar.setPreferredSize(new Dimension(350, 5));
        progressBar.setForeground(Theme.SUNSET_ORANGE);
        progressBar.setBackground(new Color(60, 40, 80));
        progressBar.setBorderPainted(false);
        
        lblTime = new JLabel("0:00 / 0:00");
        lblTime.setForeground(Theme.TEXT_SECONDARY);
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel barPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        barPanel.setOpaque(false);
        barPanel.add(lblTime);
        barPanel.add(progressBar);
        controlsPanel.add(barPanel);
        
        add(controlsPanel, BorderLayout.CENTER);
    }

    public void setTrackInfo(String track, String artist, int duree) {
        if (animationTimer != null) animationTimer.stop();
        this.dureeTotale = duree;
        this.secondesEcoulees = 0;
        this.isPlaying = true;
        lblTrack.setText(track);
        lblArtist.setText(artist);
        lblPlayIcon.setText("⏸");
        
        animationTimer = new Timer(1000, e -> {
            secondesEcoulees++;
            updateProgressBar();
        });
        animationTimer.start();
    }

    private void togglePlayPause() {
        if (dureeTotale == 0) return;
        if (isPlaying) {
            if (animationTimer != null) animationTimer.stop();
            isPlaying = false;
            lblPlayIcon.setText("▶");
        } else {
            if (animationTimer != null) animationTimer.start();
            isPlaying = true;
            lblPlayIcon.setText("⏸");
        }
    }

    private void updateProgressBar() {
        if (dureeTotale == 0) return;
        int val = (int) ((double) secondesEcoulees / dureeTotale * 1000);
        progressBar.setValue(val);
        int m = secondesEcoulees / 60;
        int s = secondesEcoulees % 60;
        int tm = dureeTotale / 60;
        int ts = dureeTotale % 60;
        lblTime.setText(String.format("%d:%02d / %d:%02d", m, s, tm, ts));
        
        if (secondesEcoulees >= dureeTotale) stop();
    }

    public void stop() {
        if (animationTimer != null) animationTimer.stop();
        isPlaying = false;
        secondesEcoulees = 0;
        lblTrack.setText("Lecture arrêtée");
        lblPlayIcon.setText("▶");
        progressBar.setValue(0);
        lblTime.setText("0:00 / 0:00");
    }
}
