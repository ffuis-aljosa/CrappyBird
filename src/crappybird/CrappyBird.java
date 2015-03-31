package crappybird;

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
    
    private int speedY;
    private final int gravity;

    public CrappyBird() {
        x = 150;
        y = 0;
        
        speedY = 1;
        gravity = 2;
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
        speedY += gravity;
    }
    
    public static BufferedImage getImage() {
        return birdImage;
    }
    
}
