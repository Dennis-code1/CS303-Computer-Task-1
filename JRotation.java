import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class JRotation extends JPanel {

    private double angle = 0.0;

    public JRotation() {
        setPreferredSize(new Dimension(600, 450));
        setBackground(Color.WHITE);

        // Update the rotation approximately every 16 milliseconds.
        Timer timer = new Timer(16, e -> {
            angle += Math.toRadians(2);

            // Complete one revolution and start again.
            if (angle >= 2 * Math.PI) {
                angle -= 2 * Math.PI;
            }

            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Smooth the edges of the letter.
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Create a bold, solid capital letter J.
        g2.setFont(new Font("Arial", Font.BOLD, 220));
        g2.setColor(Color.BLACK);

        FontMetrics fm = g2.getFontMetrics();

        int x = centerX - fm.stringWidth("J") / 2;
        int y = centerY + (fm.getAscent() - fm.getDescent()) / 2;

        // Save the original graphics transformation.
        AffineTransform originalTransform = g2.getTransform();

        // Rotate clockwise around the center of the panel.
        g2.rotate(angle, centerX, centerY);

        // Draw the letter.
        g2.drawString("J", x, y);

        // Restore the original transformation.
        g2.setTransform(originalTransform);

        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("2D Rotation of Letter J");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new JRotation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
