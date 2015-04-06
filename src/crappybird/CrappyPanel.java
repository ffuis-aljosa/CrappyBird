package crappybird;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CrappyPanel extends JPanel implements ActionListener, KeyListener {
    
    private CrappyBird bird;
    private ArrayList<CrappyObstacle> obstacles;
    
    private Image background;
    private int backgroundPosition;
    private final int BACKGROUND_WIDTH = 1000;

    private final int PANEL_HEIGHT = 600;
    private final int PANEL_WIDTH = 400;
    
    private Timer timer;
    
    public CrappyPanel() {
        loadImages();
        CrappyBird.loadImages();
        CrappyObstacle.loadImages();
        
        backgroundPosition = 0;
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        
        addKeyListener(this);
        
        bird = new CrappyBird();
        
        obstacles = new ArrayList<>();
        obstacles.add(new CrappyObstacle(PANEL_WIDTH, 200, PANEL_HEIGHT));
        
        timer = new Timer(30, this);
        timer.start();
    }
    
    private void loadImages() {
        try {
            background = ImageIO.read(new File("src/images/background.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        drawBackground(g2d);
        drawObstacles(g2d);
        drawBird(g2d);
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        bird.move();
        
        for (CrappyObstacle obstacle : obstacles) {
            obstacle.move();
        }
        
        backgroundPosition--;
        
        if (backgroundPosition < -BACKGROUND_WIDTH) {
            backgroundPosition = 0;
        }
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            bird.jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
