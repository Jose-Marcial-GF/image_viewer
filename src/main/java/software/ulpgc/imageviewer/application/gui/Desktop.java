package software.ulpgc.imageviewer.application.gui;

import software.ulpgc.imageviewer.architecture.control.Command;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.awt.BorderLayout.*;
import static java.awt.Color.*;

public class Desktop extends JFrame {
    private final Map<String, Command> commands;
    private final JButton prevButton = button(" \u25C0 ", "prev");
    private final JButton nextButton = button(" \u25B6 ", "next");

    public static Desktop create(SwingImageDisplay imageDisplay) {
        return new Desktop(imageDisplay);
    }

    private Desktop(SwingImageDisplay imageDisplay) throws HeadlessException {
        this.commands = new HashMap<>();
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.createToolbar();
        this.addNavigationArrows();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(BLACK);
        this.getContentPane().add(imageDisplay);
        insertCommands();

    }

    private void insertCommands() {
        commands.put("view",
                () -> {
                    Color newColor = isWhite(prevButton) ? Color.BLACK : Color.WHITE;

                    prevButton.setForeground(newColor);
                    nextButton.setForeground(newColor);
                    commands.get("present").execute();
                });
    }

    private boolean isWhite(JButton prevButton) {
        return prevButton.getForeground().equals(Color.WHITE);
    }

    private void addNavigationArrows() {
        getContentPane().add(leftArrowPanel(), WEST);
        getContentPane().add(rightArrowPanel(), EAST);
    }

    private Component rightArrowPanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setBackground(null);
        panel.add(nextButton);
        return panel;
    }

    private Component leftArrowPanel() {
        Panel panel = new Panel(new BorderLayout());
        panel.setBackground(null);
        panel.add(prevButton);
        return panel;
    }

    private void createToolbar() {
        this.getContentPane().add(toolbar(), SOUTH);
    }

    private JPanel toolbar() {
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(null);
        southPanel.add(button("\uD83D\uDC41", "view"), EAST);
        southPanel.add(zoomPanel(), CENTER);
        return southPanel;
        }

    private JPanel zoomPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(null);
        panel.setBorder(null);
        panel.add(button("  +  ", "zoomIn"), CENTER);
        panel.add(button("  -  ", "zoomOut"), CENTER);
        return panel;
    }

    private JButton button(String name, String command) {
        JButton button = new JButton(name);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(WHITE);
        button.addActionListener(_ -> commands.get(command).execute());
        return button;
    }

    public Desktop put(String name, Command command) {
        commands.put(name, command);
        return this;
    }
}


