package crappybird;

import java.awt.geom.Rectangle2D;

public class CrappyObstacle {
    private int x;
    private int gapY;
    private int maxHeight;
    
    private Rectangle2D.Double upperRectangle;
    private Rectangle2D.Double lowerRectangle;
    
    private final int SPEED_X = 3;
    
    private final int OBSTACLE_WIDTH = 40;
    private final int GAP_HEIGHT = 100;

    public CrappyObstacle(int x, int gapY, int maxHeight) {
        this.x = x;
        this.gapY = gapY;
        this.maxHeight = maxHeight;
        
        int y = gapY + GAP_HEIGHT;
        
        upperRectangle = new Rectangle2D.Double(x, 0, OBSTACLE_WIDTH, gapY);
        lowerRectangle = new Rectangle2D.Double(x, y, OBSTACLE_WIDTH, maxHeight - y);
    }
    
    public void move() {
        x -= SPEED_X;
    }
    
    public Rectangle2D.Double getUpperRectangle() {
        upperRectangle.x = x;
        return upperRectangle;
    }
    
    public Rectangle2D.Double getLowerRectangle() {
        lowerRectangle.x = x;
        return lowerRectangle; 
    }
    
}
