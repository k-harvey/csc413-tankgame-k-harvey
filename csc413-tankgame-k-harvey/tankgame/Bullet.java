package tankgame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends MovingObject {

    private int initialFrame;
    private int range;

    public Bullet(BufferedImage img, int x, int y, int angle, int range){
        super(img, x, y, angle);
        this.range = range;
        this.speed = 8;
        this.initialFrame = TRE.framecount;

    }
    @Override
    public void checkBorder() {
        if (this.getX() <= 30) {
            this.setX(30);
            this.exists = false;
        }
        if (this.getX() >= TRE.WORLD_WIDTH - 88) {
            this.setX(TRE.WORLD_WIDTH - 88);
            this.exists = false;
        }
        if (this.getY() <= 30) {
            this.setY(30);
            this.exists = false;
        }
        if (this.getY() >= TRE.WORLD_HEIGHT - 80) {
            this.setY(TRE.WORLD_HEIGHT - 80);
            this.exists = false;
        }
    }

    @Override

    public void moveForwards(){
        super.moveForwards();
        if (TRE.framecount - initialFrame == range){
            this.setExists(false);
        }
    }

    public void update(){
        moveForwards();
        checkBorder();
    }



}