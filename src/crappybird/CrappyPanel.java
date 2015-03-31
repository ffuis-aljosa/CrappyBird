package crappybird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CrappyPanel extends JPanel implements ActionListener, KeyListener {
    
    private CrappyBird bird;
    private CrappyObstacle obstacle;

    private final int PANEL_HEIGHT = 600;
    private final int PANEL_WIDTH = 400;
    
    private Timer timer;
    
    public CrappyPanel() {
        CrappyBird.loadImages();
        CrappyObstacle.loadImages();
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        
        addKeyListener(this);
        
        bird = new CrappyBird();
        obstacle = new CrappyObstacle(PANEL_WIDTH, 200, PANEL_HEIGHT);
        
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        drawBird(g2d);
        drawObstacles(g2d);
    }
    
    private void drawBird(Graphics2D g2d) {
        g2d.drawImage(CrappyBird.getImage(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
    }
    
    private void drawObstacles(Graphics2D g2d) {
        Rectangle2D.Double upperRect = obstacle.getUpperRectangle();
        Rectangle2D.Double lowerRect = obstacle.getLowerRectangle();
        
        int headHeight = 20;
        
        g2d.drawImage(CrappyObstacle.getLowerPillarHeadImage(), (int)lowerRect.x, (int)lowerRect.y, (int)lowerRect.width, headHeight, null);
        g2d.drawImage(CrappyObstacle.getPillarImage(),(int)lowerRect.x, (int)lowerRect.y + headHeight, (int)lowerRect.width, (int)lowerRect.height - headHeight, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        bird.move();
        obstacle.move();
        
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
