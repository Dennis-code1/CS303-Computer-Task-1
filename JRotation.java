
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JRotation extends JPanel {

    // ==================================================
    // SECTION 1: SETTINGS YOU CAN CHANGE IN AN EXAM
    // ==================================================

    private double angle = 0;
    // CHANGE: Starting angle of the letter.
    // 0 = normal starting position
    // 90 = starts rotated by 90 degrees
    // 180 = starts upside down
    // 270 = starts rotated by 270 degrees

    private final double rotationSpeed = 3;
    // CHANGE: Rotation speed.
    // 1 = slow, 2 = normal, 5 = faster, 10 = very fast.

    private boolean rotating = true;
    // true = rotation starts automatically.
    // false = rotation starts paused.

    private final int panelWidth = 600;
    private final int panelHeight = 560;
    // CHANGE: Window/panel size.
    // Increase these values to make the drawing area bigger.

    private final String letter = "J";
    // CHANGE: Letter to display. Example: "A", "B", "K".

    private final String fontName = "Arial";
    // CHANGE: Font type. Example: "Serif", "Monospaced".

    private final int fontSize = 520;
    // CHANGE: Letter size.
    // Increase to make the letter bigger; decrease to make it smaller.

    private final Color letterColor = Color.BLACK;
    // CHANGE: Letter color. Examples: Color.RED, Color.BLUE, Color.GREEN.

    private final Color backgroundColor = Color.green;
    // CHANGE: Background color. Examples: Color.WHITE, Color.GRAY.

    // ==================================================
    // SECTION 2: CONSTRUCTOR AND ANIMATION TIMER
    // ==================================================

    public JRotation() {

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        // Sets the size of the drawing panel.

        setBackground(backgroundColor);
        // Sets the background color.

        setFocusable(true);
        // Allows the panel to receive keyboard input.

        Timer timer = new Timer(16, e -> {
            // CHANGE: 16 milliseconds controls update frequency.
            // Larger value = slower updates.
            // Smaller value = more frequent updates.

            if (rotating) {
                angle += rotationSpeed -5;
                // Adds the rotation speed to the current angle.

                if (angle >= 180) {
                    angle -= 180;
                }
                // Keeps the angle within the 0–359 degree range.

                repaint();
                // Requests the letter to be drawn again.
            }
        });

        timer.start();
        // Starts the animation timer.

        // SPACEBAR: Pause or resume rotation.
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    rotating = !rotating;
                    // true becomes false; false becomes true.
                }
            }
        });
    }

    // ==================================================
    // SECTION 3: DRAWING THE LETTER
    // ==================================================

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        // Clears the previous drawing before redrawing.

        Graphics2D g2 = (Graphics2D) g.create();
        // Creates a copy of the drawing tools.

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        // Smooths the edges of the letter.

        // FIND THE CENTER OF THE PANEL.
        int centerX = getWidth() / 2;
    // int centerX = getWidth() *3/4; :: For  right side rotation
        int centerY = getHeight() / 2;
        // CHANGE: These values control the rotation center.
        // / 2 finds the middle of the panel.

        // SET FONT AND LETTER COLOR.
        g2.setFont(new Font(fontName, Font.BOLD, fontSize));
        g2.setColor(letterColor);
        // CHANGE: Font type, BOLD style, font size and color.

        FontMetrics metrics = g2.getFontMetrics();
        // Gets information about the letter's dimensions.

        // POSITION THE LETTER.
        int x = centerX - metrics.stringWidth(letter) / 2;
        // CHANGE: x controls horizontal position.
        // Smaller x = move left; larger x = move right.
        // This formula automatically centers the letter.

        int y = centerY
                + (metrics.getAscent() - metrics.getDescent()) / 2;
        // CHANGE: y controls vertical position.
        // Smaller y = move up; larger y = move down.
        // This formula automatically centers the letter.

        // ROTATE THE LETTER.
        g2.rotate(Math.toRadians(angle), centerX, centerY);
        // CHANGE: angle controls rotation in degrees.
        // Java converts degrees to radians for the rotate method.
        // centerX and centerY are the rotation pivot.

        // DRAW THE LETTER.
        g2.drawString(letter, x, y);
        // Displays the letter at position x, y.

        g2.dispose();
        // Releases the copied drawing resources.
    }

    // ==================================================
    // SECTION 4: MAIN METHOD AND WINDOW
    // ==================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Rotating Letter");
            // CHANGE: Window title.

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Closes the application when the window is closed.

            JRotation panel = new JRotation();
            // Creates the rotating-letter panel.

            frame.setContentPane(panel);
            // Places the panel inside the window.

            frame.pack();
            // Sizes the window to fit the panel.

            frame.setLocationRelativeTo(null);
            // Places the window in the center of the screen.

            frame.setResizable(false);
            // Prevents resizing the window.
            // Change to true if resizing is required.

            frame.setVisible(true);
            // Displays the window.

            panel.requestFocusInWindow();
            // Requests keyboard focus so Space can pause/resume.
        });
    }
}
