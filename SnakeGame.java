
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {

    // Screen settings
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;

    // Number of units that fit on the screen
    private static final int GAME_UNITS =
            (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    // Snake
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];

    private int bodyParts = 3;

    // Food
    private int foodX;
    private int foodY;

    // Direction
    private char direction = 'R';

    // Game state
    private boolean running = false;
    private boolean gameOver = false;

    // Score
    private int score = 0;

    // Timer
    private Timer timer;

    // Random food position
    private final Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new MyKeyAdapter());

        startGame();
    }

    // Start or restart the game
    public void startGame() {

        bodyParts = 3;
        score = 0;
        direction = 'R';
        gameOver = false;

        // Starting position of snake
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 100 - (i * UNIT_SIZE);
            y[i] = 100;
        }

        newFood();

        running = true;

        timer = new Timer(100, this);
        timer.start();

        requestFocusInWindow();
    }

    // Generate food at a random position
    public void newFood() {

        foodX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

        // Make sure food does not appear inside the snake
        for (int i = 0; i < bodyParts; i++) {
            if (foodX == x[i] && foodY == y[i]) {
                newFood();
                return;
            }
        }
    }

    // Draw everything
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }
    }

    private void drawGame(Graphics g) {

        // Draw food
        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

        // Draw snake
        for (int i = 0; i < bodyParts; i++) {

            if (i == 0) {
                // Snake head
                g.setColor(Color.GREEN);
            } else {
                // Snake body
                g.setColor(new Color(0, 180, 0));
            }

            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        String scoreText = "Score: " + score;

        FontMetrics metrics = g.getFontMetrics();
        int scoreX = (SCREEN_WIDTH - metrics.stringWidth(scoreText)) / 2;

        g.drawString(scoreText, scoreX, 25);
    }

    // Move the snake
    public void move() {

        // Move body from tail to head
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Move head
        switch (direction) {

            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // Check if snake eats food
    public void checkFood() {

        if (x[0] == foodX && y[0] == foodY) {

            bodyParts++;
            score++;

            newFood();
        }
    }

    // Check collisions
    public void checkCollisions() {

        // Check if snake hits its own body
        for (int i = bodyParts - 1; i > 0; i--) {

            if (x[0] == x[i] && y[0] == y[i]) {
                gameOver = true;
            }
        }

        // Check left wall
        if (x[0] < 0) {
            gameOver = true;
        }

        // Check right wall
        if (x[0] >= SCREEN_WIDTH) {
            gameOver = true;
        }

        // Check top wall
        if (y[0] < 0) {
            gameOver = true;
        }

        // Check bottom wall
        if (y[0] >= SCREEN_HEIGHT) {
            gameOver = true;
        }

        if (gameOver) {
            running = false;
            timer.stop();
        }
    }

    // Timer repeatedly calls this method
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {

            move();
            checkFood();
            checkCollisions();

            repaint();
        }
    }

    // Game Over screen
    private void drawGameOver(Graphics g) {

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));

        String gameOverText = "GAME OVER";

        FontMetrics metrics = g.getFontMetrics();
        int textX =
                (SCREEN_WIDTH - metrics.stringWidth(gameOverText)) / 2;

        int textY = SCREEN_HEIGHT / 2 - 30;

        g.drawString(gameOverText, textX, textY);

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 25));

        String scoreText = "Score: " + score;

        int scoreX =
                (SCREEN_WIDTH - g.getFontMetrics().stringWidth(scoreText)) / 2;

        g.drawString(scoreText, scoreX, textY + 50);

        // Restart instruction
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        String restartText = "Press ENTER to restart";

        int restartX =
                (SCREEN_WIDTH - g.getFontMetrics().stringWidth(restartText)) / 2;

        g.drawString(restartText, restartX, textY + 90);
    }

    // Keyboard controls
    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

                case KeyEvent.VK_ENTER:
                    if (gameOver) {
                        startGame();
                    }
                    break;
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Game");

        SnakeGame game = new SnakeGame();

        frame.add(game);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        game.requestFocusInWindow();
    }
}