package crappybird;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CrappyPanel extends JPanel implements ActionListener, KeyListener {
    
    private CrappyBird bird;
    private CrappyObstacle obstacle;

    private final int PANEL_HEIGHT = 600;
    private final int PANEL_WIDTH = 400;
    
    private Timer timer;
    
    public CrappyPanel() {
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
        
        g2d.draw(bird.getCircle());
        g2d.draw(obstacle.getUpperRectangle());
        g2d.draw(obstacle.getLowerRectangle());
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
