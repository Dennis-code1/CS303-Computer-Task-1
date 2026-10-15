import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JRotation extends JPanel {

    // ===== SETTINGS YOU CAN CHANGE =====

    private double angle = 0;             // Starting angle, in degrees
    private final double rotationSpeed = 2; // Degrees to rotate each timer tick Speed
    private boolean rotating = true;      // Change to false to start paused

    // Change the window/panel size here
    private final int panelWidth = 600;
    private final int panelHeight = 450;

    // Change the letter, font, size, and color here
    private final String letter = "J";
    private final String fontName = "Arial";
    private final int fontSize = 220;
    private final Color letterColor = Color.WHITE;

    // Change the fixed background color here
    private final Color backgroundColor = Color.BLACK;

    public JRotation() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(backgroundColor);
        setFocusable(true);

        // This timer updates the angle about 60 times per second.
        // Change 16 to a larger number for slower updates.
        Timer timer = new Timer(16, e -> {
            if (rotating) {
                angle += rotationSpeed;

                // Keep the angle between 0 and 360 degrees.
                if (angle >= 360) {
                    angle -= 360;
                }

                repaint(); // Redraw the letter at its new angle
            }
        });
        timer.start();

        // Press Space to pause or resume the rotation.
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    rotating = !rotating;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Make a copy of the drawing settings so we can safely change them.
        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth the edges of the letter.
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Find the center of the panel.
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Set the letter's font and color.
        g2.setFont(new Font(fontName, Font.BOLD, fontSize));
        g2.setColor(letterColor);

        // Position the letter so it appears centered.
        FontMetrics metrics = g2.getFontMetrics();

        //int x = 50; // Distance from the left edge; increase or decrease as needed
        int x = centerX - metrics.stringWidth(letter) / 2; 

        int y = centerY + (metrics.getAscent() - metrics.getDescent()) / 2;

        // Rotate around the center of the panel.
        // Math.toRadians converts the angle from degrees to radians.
        g2.rotate(Math.toRadians(angle), centerX, centerY);

        // Draw the letter.
        g2.drawString(letter, x, y);

        // Release the copied drawing settings.
        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Change the window title here.
            JFrame frame = new JFrame("Rotating Letter");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JRotation panel = new JRotation();
            frame.setContentPane(panel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);

            // Give the panel keyboard focus so Space can pause/resume.
            panel.requestFocusInWindow();
        });
    }
}