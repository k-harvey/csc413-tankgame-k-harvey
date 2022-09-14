package tankgame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import static javax.imageio.ImageIO.read;
import java.io.File;

/**
 *
 * @author anthony-pc
 */
public class Tank extends MovingObject{



    private BufferedImage bulletImg;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private int range = 50;
    private int heat = 0;
    private boolean overHeated;

    public ArrayList<Bullet> bulletList;


    Tank(BufferedImage img, int x, int y, int angle) {
        super(img, x, y, angle);
        this.bulletList = new ArrayList<Bullet>();
        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            bulletImg = read(new File("bullet.png"));
            //background = read(new File("Background.bmp"));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.speed = 2;
        this.rotationalSpeed = 2;

    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {this.ShootPressed = true;}

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {this.ShootPressed = false;}


    @Override
    public void update() {
        this.checkHeat();
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed) {
            if (!(this.overHeated)){
                this.shoot();
            }
            this.unToggleShootPressed();

        }

        for (int i = 0; i < this.bulletList.size(); i ++){
            this.bulletList.get(i).update();
        }
        this.heat = this.heat - 1;
        if (this.heat < 0){
            this.heat = 0;
        }
    }

    private void shoot() {
        if (!(this.overHeated)) {
            Bullet pBullet = new Bullet(bulletImg, this.getX() + 30, this.getY() + 15, this.getAngle(), this.range);
            this.bulletList.add(pBullet);
            System.out.println("Fire!");
            if (this.heat + 100 >= Player.MAX_HEAT) {
                this.heat = Player.MAX_HEAT;
            } else {
                this.heat += 100;
            }
        }
    }


    @Override

    public void checkBorder(){
        if (this.getX() < 30) {
            this.setX(30);
        }
        if (this.getX() >= TRE.WORLD_WIDTH - 88) {
            this.setX(TRE.WORLD_WIDTH - 88);
        }
        if (this.getY() < 30) {
            this.setY(30);
        }
        if (this.getY() >= TRE.WORLD_HEIGHT - 80) {
            this.setY(TRE.WORLD_HEIGHT - 80);
        }
    }

    public Boolean getUpPressed(){
        return this.UpPressed;
    }
    public Boolean getDownPressed(){
        return this.DownPressed;
    }


    @Override

    public void drawImage(Graphics g){
        super.drawImage(g);
        g.drawRect(this.getX()-5, this.getY()-5, this.img.getWidth()+10, this.img.getHeight()+10);
        for (int i = 0; i < this.bulletList.size(); i++){
            if (this.bulletList.get(i).exists()) {
                this.bulletList.get(i).drawImage(g);
            } else {
                this.bulletList.remove(i);
            }
        }
    }

    public int getRange(){return this.range;}
    public void setRange(int amount){this.range = amount;}
    public int getHeat(){return this.heat;}
    public boolean getHeated(){return this.overHeated;}
    public void checkHeat(){
        if (this.heat == Player.MAX_HEAT-1){
            this.overHeated = true;
            System.out.println("OVERHEATED");
        }
        if(this.heat == 0){
            this.overHeated = false;
            System.out.println("FINE");
        }
    }

    public void setHeat(int amount){
        this.heat  = amount;
        if (this.heat < 0){
            this.heat = 0;
        }
    }




}
