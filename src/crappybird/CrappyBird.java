package crappybird;

import java.awt.geom.Ellipse2D;

public class CrappyBird {
    
    private final int x;
    private int y;
    
    private final int BIRD_WIDTH = 60;
    private final int BIRD_HEIGHT = 60;
    
    private int speedY;
    private final int gravity;

    public CrappyBird() {
        x = 150;
        y = 0;
        
        speedY = 1;
        gravity = 2;
    }
    
    public void jump() {
        speedY = -20;
    }
    
    public void move() {
        y += speedY;
        speedY += gravity;
    }
    
    public Ellipse2D.Double getCircle() {
        return new Ellipse2D.Double(x, y, BIRD_WIDTH, BIRD_HEIGHT);
    }
    
}
