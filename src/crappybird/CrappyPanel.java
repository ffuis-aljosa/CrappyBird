package crappybird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CrappyPanel extends JPanel implements ActionListener, KeyListener {
    
    private boolean inGame;
    private Font mainFont;
    
    private CrappyBird bird;
    private ArrayList<CrappyObstacle> obstacles;
    
    private Image background;
    private int backgroundPosition;
    private final int BACKGROUND_WIDTH = 1000;

    private final int PANEL_HEIGHT = 600;
    private final int PANEL_WIDTH = 400;
    
    private long frames;
    
    private Timer timer;
    
    private Random random;
    
    public CrappyPanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        
        loadImages();
        CrappyBird.loadImages();
        CrappyObstacle.loadImages();
        
        backgroundPosition = 0;
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        
        addKeyListener(this);
        
        resetBird();
        
        obstacles = new ArrayList<>();
        
        frames = 0;
        
        timer = new Timer(30, this);
        timer.start();
        
        random = new Random();
        
        inGame = false;
    }
    
    private void resetBird() {
        bird = new CrappyBird((int)PANEL_WIDTH/4, (int)PANEL_HEIGHT/5);
    }
    
    private void loadImages() {
        try {
            background = ImageIO.read(new File("src/images/background.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void gameOver() {
        obstacles.clear();
        resetBird();
        inGame = false;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        drawBackground(g2d);
        drawBird(g2d);
        
        if (inGame) {
            drawObstacles(g2d);
        } else {
            drawMessage(g2d);
        }
    }
    
    private void drawBackground(Graphics2D g2d) {
        g2d.drawImage(background, backgroundPosition, 0, BACKGROUND_WIDTH, PANEL_HEIGHT, null);
        
        if (backgroundPosition < -(BACKGROUND_WIDTH - PANEL_WIDTH)) {
            g2d.drawImage(background, backgroundPosition + BACKGROUND_WIDTH, 0, BACKGROUND_WIDTH, PANEL_HEIGHT, null);
        }
    }
    
    private void drawBird(Graphics2D g2d) {
        g2d.drawImage(CrappyBird.getImage(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
    }
    
    private void drawObstacles(Graphics2D g2d) {
        for (CrappyObstacle obstacle : obstacles) {
            Rectangle2D.Double upperRect = obstacle.getUpperRectangle();
            Rectangle2D.Double lowerRect = obstacle.getLowerRectangle();

            int headHeight = 20;

            g2d.drawImage(CrappyObstacle.getLowerPillarHeadImage(), (int)lowerRect.x, (int)lowerRect.y, (int)lowerRect.width, headHeight, null);
            g2d.drawImage(CrappyObstacle.getPillarImage(),(int)lowerRect.x, (int)lowerRect.y + headHeight, (int)lowerRect.width, (int)lowerRect.height - headHeight, null);

            g2d.drawImage(CrappyObstacle.getUpperPillarHeadImage(), (int)upperRect.x, (int)(upperRect.y + upperRect.height - headHeight), (int)upperRect.width, headHeight, null);
            g2d.drawImage(CrappyObstacle.getPillarImage(), (int)upperRect.x, (int)upperRect.y, (int)upperRect.width, (int)(upperRect.height - headHeight), null);
        }
    }
    
    private void drawMessage(Graphics2D g2d) {
        g2d.setFont(mainFont);
            
        String message = "Press SPACE to start the game";

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);

        g2d.drawString(message, (PANEL_WIDTH - stringWidth) / 2, PANEL_HEIGHT/2);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            frames++;
        
            moveObjects();
            handleCollisions();
            generateObstacles();
            cleanObstacles();
        }
        
        repaint();
    }
    
    private void handleCollisions() {
        int birdY = bird.getY();
        
        if (birdY <= 0 || birdY + bird.getHeight() >= PANEL_HEIGHT || hasBirdHitObstacle()) {
            gameOver();
        }
    }
    
    private boolean hasBirdHitObstacle() {
        Rectangle2D.Double birdBounds = bird.getBounds();
        
        for (CrappyObstacle obstacle : obstacles) {
            if (obstacle.getUpperRectangle().intersects(birdBounds) || obstacle.getLowerRectangle().intersects(birdBounds))
                return true;
        }
        
        return false;
    }
    
    private void moveObjects() {
        bird.move();
        
        for (CrappyObstacle obstacle : obstacles) {
            obstacle.move();
        }
        
        backgroundPosition--;
        
        if (backgroundPosition < -BACKGROUND_WIDTH) {
            backgroundPosition = 0;
        }
    }
    
    private void cleanObstacles() {
        int d = obstacles.size();
        for (int i = d - 1; i >= 0; i--) {
            if (obstacles.get(i).isOutOfPanel())
                obstacles.remove(i);
        }
    }
    
    private void generateObstacles() {
        if (frames % 100 == 0) {
            int gapY = random.nextInt(PANEL_HEIGHT / 2) + PANEL_HEIGHT / 4;
            obstacles.add(new CrappyObstacle(PANEL_WIDTH, gapY, PANEL_HEIGHT));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (inGame)
                bird.jump();
            else
                inGame = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
