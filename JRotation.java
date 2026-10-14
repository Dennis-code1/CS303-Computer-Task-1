import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class JRotation extends JPanel {

    private double angle = 0.0;
    private boolean rotating = true;

    public JRotation() {
        setPreferredSize(new Dimension(600, 450));
        setBackground(Color.BLACK);

        // Allow this panel to receive keyboard input.
        setFocusable(true);

        // Create the rotation timer.
        Timer timer = new Timer(16, e -> {
            if (rotating) {
                angle += Math.toRadians(2);

                if (angle >= 2 * Math.PI) {
                    angle -= 2 * Math.PI;
                }

                repaint();
            }
        });

        timer.start();

        // Press SPACE to pause or resume rotation.
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

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2.setFont(new Font("Arial", Font.BOLD, 220));
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();

        int x = centerX - fm.stringWidth("J") / 2;
        int y = centerY
                + (fm.getAscent() - fm.getDescent()) / 2;

        AffineTransform originalTransform = g2.getTransform();

        g2.rotate(angle, centerX, centerY);
        g2.drawString("J", x, y);

        g2.setTransform(originalTransform);
        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame =
                new JFrame("2D Rotation of Letter J");

            frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
            );

            JRotation panel = new JRotation();

            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);

            // Give keyboard focus to the panel.
            panel.requestFocusInWindow();
        });
    }
}
