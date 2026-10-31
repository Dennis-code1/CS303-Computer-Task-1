import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class JRotation extends JPanel {

    private double angle = 0;
    private double t = 0;

    private Path2D.Double jPath;

    public JRotation() {
        setPreferredSize(new Dimension(800, 600)); //size of the shape
        setBackground(Color.red); // background color

        createJPath();

        Timer timer = new Timer(20, e -> { // Higher the num, the slower the object..
            angle += Math.toRadians(2); // Higher the num, the fast the letter

            if (angle >= 2 * Math.PI)
                angle -= 2 * Math.PI;

            t += 0.002; // To make it still at one position using number or 1.000 //
            if (t > 1.0)
                t = 0.0;

            repaint();
        });

        timer.start();
    }

    private void createJPath() {
        jPath = new Path2D.Double();

        jPath.moveTo(400, 100);     // top of J
        jPath.lineTo(400, 400);     // vertical part

        jPath.curveTo(
                400, 500,
                250, 500,
                250, 350);          // bottom hook
    }

    private Point2D getPointOnPath(double t) {

        if (t < 0.6) {
            double y = 100 + (300 * (t / 0.6));
            return new Point2D.Double(400, y);
        } else {
            double s = (t - 0.6) / 0.4;

            double x =
                    Math.pow(1 - s, 2) * 400 +
                    2 * (1 - s) * s * 400 +
                    Math.pow(s, 2) * 250;

            double y =
                    Math.pow(1 - s, 2) * 400 +
                    2 * (1 - s) * s * 500 +
                    Math.pow(s, 2) * 350;

            return new Point2D.Double(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw larger guide path
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.draw(jPath);

        Point2D p = getPointOnPath(t);

        AffineTransform old = g2.getTransform();

        g2.translate(p.getX(), p.getY());
        g2.rotate(angle); // The letter rotates around the center of the letter J rotate in 180 degrees "Math.PI"
        g2.setFont(new Font("Arial", Font.BOLD, 80)); // sizes of J and color
        g2.setColor(Color.BLACK);

        g2.drawString("J", -25, 25);

        g2.setTransform(old);
        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotating J Following J Path");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JRotation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}