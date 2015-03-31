package crappybird;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CrappyObstacle {
    
    static BufferedImage pillar = null;
    static BufferedImage upperPillarHead = null;
    static BufferedImage lowerPillarHead = null;
    
    private int x;
    private int gapY;
    private int maxHeight;
    
    private Rectangle2D.Double upperRectangle;
    private Rectangle2D.Double lowerRectangle;
    
    private final int SPEED_X = 3;
    
    private final int OBSTACLE_WIDTH = 60;
    private final int GAP_HEIGHT = 100;

    public CrappyObstacle(int x, int gapY, int maxHeight) {
        this.x = x;
        this.gapY = gapY;
        this.maxHeight = maxHeight;
        
        int y = gapY + GAP_HEIGHT;
        
        upperRectangle = new Rectangle2D.Double(x, 0, OBSTACLE_WIDTH, gapY);
        lowerRectangle = new Rectangle2D.Double(x, y, OBSTACLE_WIDTH, maxHeight - y);
    }
    
    public static void loadImages() {
        try {
            pillar = ImageIO.read(new File("src/images/pillar-bkg.png"));
            upperPillarHead = ImageIO.read(new File("src/images/upper-pillar-head.png"));
            lowerPillarHead = ImageIO.read(new File("src/images/lower-pillar-head.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void move() {
        x -= SPEED_X;
    }
    
    public static BufferedImage getPillarImage() {
        return pillar;
    }
    
    public static BufferedImage getUpperPillarHeadImage() {
        return upperPillarHead;
    }
    
    public static BufferedImage getLowerPillarHeadImage() {
        return lowerPillarHead;
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
