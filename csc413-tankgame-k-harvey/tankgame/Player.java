package tankgame;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Player {

    private int lives;
    private int health;
    private int shields;
    private int score;
    public final static int MAX_HEALTH = 325;
    public final static int MAX_RANGE = 100;
    public final static int MAX_HEAT = 1000;


    private BufferedImage tankImg;

    private Tank tank;

    public Player(Tank tank){
        this.lives = 3;
        this.health = MAX_HEALTH;
        this.shields = 0;
        this.score = 0;

        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            tankImg = read(new File("tank.png"));
            //background = read(new File("Background.bmp"));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        this.tank = tank;
    }

    public Tank getTank(){
        return this.tank;
    }
    public void setHealth(int amount){
        if (amount <= MAX_HEALTH && amount > 0){
            this.health = amount;
            return;
        } else if (amount <= 0){
            this.health = MAX_HEALTH;
            this.loseLife();
            return;
        } else {
            this.health = MAX_HEALTH;
        }
    }
    public void checkDamage(int damage) {
        if (this.shields > 0) {
            this.setShields(this.shields - 1);
        } else {
            this.setHealth(this.health - damage);
        }
    }
    public int getHealth(){
        return this.health;
    }

    public int getLives(){
        return this.lives;
    }

    public void loseLife(){
        if (this.lives != 1){
            this.lives--;
        }else{
            this.lives = 0;
            this.getTank().setExists(false);
        }
    }

    public void setShields(int amount){
        if (amount > 4){
            this.shields = 4;
        } else if (amount < 0) {
            this.shields = 0;
        } else {
            this.shields = amount;
        }
    }

    public int getShields(){
        return this.shields;
    }

    public void setRange(int amount){
        if (amount > MAX_RANGE){
            this.getTank().setRange(MAX_RANGE);
        }else{
            this.getTank().setRange(amount);
        }
    }

    public int getRange(){
        return this.getTank().getRange();
    }




}
