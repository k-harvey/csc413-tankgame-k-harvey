package tankgame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class MovingObject extends GameObject {

    protected int vx = 0;
    protected int vy = 0;
    protected int speed = 0;
    protected int rotationalSpeed = 0;

    public MovingObject(BufferedImage img, int x, int y, int angle){
        super(x, y, angle, img);

    }

    protected void moveForwards() {
        vx = (int) Math.round(this.speed * Math.cos(Math.toRadians(this.getAngle())));
        vy = (int) Math.round(this.speed * Math.sin(Math.toRadians(this.getAngle())));
        this.setX(this.getX() + this.vx);
        this.setY(this.getY() + this.vy);
        checkBorder();
    }

    protected void rotateLeft() {
        this.setAngle(this.getAngle() - this.rotationalSpeed);
    }

    protected void rotateRight() {
        this.setAngle(this.getAngle() + this.rotationalSpeed);
    }

    protected void moveBackwards() {
        this.vx = (int) Math.round(this.speed * Math.cos(Math.toRadians(this.getAngle())));
        this.vy = (int) Math.round(this.speed * Math.sin(Math.toRadians(this.getAngle())));
        this.setX(this.getX() - this.vx);
        this.setY(this.getY() - this.vy);
        checkBorder();
    }

    abstract public void checkBorder();


    abstract public void update();

}
