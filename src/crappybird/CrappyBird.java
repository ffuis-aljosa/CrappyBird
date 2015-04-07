package crappybird;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CrappyBird {
    
    static BufferedImage birdImage = null;
    
    private final int x;
    private int y;
    
    private final int BIRD_WIDTH = 50;
    private final int BIRD_HEIGHT = 40;
    
    private int speedY = 1;
    private final int GRAVITY = 2;

    public CrappyBird(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return BIRD_WIDTH;
    }

    public int getHeight() {
        return BIRD_HEIGHT;
    }
    
    public static void loadImages() {
        try {
            birdImage = ImageIO.read(new File("src/images/flappy-base.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void jump() {
        speedY = -20;
    }
    
    public void move() {
        y += speedY;
        speedY += GRAVITY;
    }
    
    public static BufferedImage getImage() {
        return birdImage;
    }
    
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, BIRD_WIDTH, BIRD_HEIGHT);
    }
}
