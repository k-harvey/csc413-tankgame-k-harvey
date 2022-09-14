package tankgame;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.*;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int angle;
    protected BufferedImage img;

    protected boolean exists;

    public GameObject(int x, int y, int angle, BufferedImage img){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;
        this.exists = true;
    }

    protected int getX(){
        return this.x;
    }

    protected void setX(int x){
        this.x = x;
    }

    protected int getY(){ return this.y;
    }
    protected void setY(int y){this.y = y;}

    protected int getAngle() {return this.angle;}

    protected void setAngle(int angle){this.angle = angle;}

    protected BufferedImage getImg(){
        return this.img;
    }

    protected Boolean exists(){
        return this.exists;
    }

    protected void setExists(Boolean b){
        this.exists = b;
    }

    public void drawImage(Graphics g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    abstract public void update();



}