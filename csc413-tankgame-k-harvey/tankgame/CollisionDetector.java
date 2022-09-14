package tankgame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class CollisionDetector {

    Player player1, player2;

    public CollisionDetector(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playerVsbullet(){
        Rectangle tankOneBox = new Rectangle(player1.getTank().getX(), player1.getTank().getY(), player1.getTank().getImg().getWidth(), player1.getTank().getImg().getHeight());
        Rectangle tankTwoBox = new Rectangle(player2.getTank().getX(), player2.getTank().getY(), player2.getTank().getImg().getWidth(), player2.getTank().getImg().getHeight());
        Rectangle bulletBox;
        for (int i = 0; i < player1.getTank().bulletList.size(); i++){
            Bullet b = player1.getTank().bulletList.get(i);
            bulletBox = new Rectangle(b.getX(), b.getY(), b.getImg().getWidth(), b.getImg().getHeight());
            if(tankTwoBox.intersects(bulletBox)){
                b.setExists(false);
                player2.checkDamage(25);
            }
        }
        for (int i = 0; i < player2.getTank().bulletList.size(); i++){
            Bullet b = player2.getTank().bulletList.get(i);
            bulletBox = new Rectangle(b.getX(), b.getY(), b.getImg().getWidth(), b.getImg().getHeight());
            if(tankOneBox.intersects(bulletBox)){
                b.setExists(false);
                player1.checkDamage(25);
            }
        }
    }

    public void bulletVswall(){
        Rectangle bulletbox;
        Rectangle wallbox;

        for (int i = 0; i < player1.getTank().bulletList.size(); i++){
            Bullet b = player1.getTank().bulletList.get(i);
            bulletbox = new Rectangle(b.getX(), b.getY(), b.getImg().getWidth(), b.getImg().getHeight());
            for (int j = 0; j < GameWorld.getWorldList().size(); j++) {
                GameObject obj = GameWorld.getWorldList().get(j);
                if (obj instanceof Wall) {
                    wallbox = new Rectangle(obj.getX(), obj.getY(), obj.getImg().getWidth(), obj.getImg().getHeight());
                    if (bulletbox.intersects(wallbox)) {
                        b.setExists(false);
                        if (((Wall) obj).breakable()) {
                            obj.setExists(false);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < player2.getTank().bulletList.size(); i++){
            Bullet b = player2.getTank().bulletList.get(i);
            bulletbox = new Rectangle(b.getX(), b.getY(), b.getImg().getWidth(), b.getImg().getHeight());
            for (int j = 0; j < GameWorld.getWorldList().size(); j++) {
                GameObject obj = GameWorld.getWorldList().get(j);
                if (obj instanceof Wall) {
                    wallbox = new Rectangle(obj.getX(), obj.getY(), obj.getImg().getWidth(), obj.getImg().getHeight());
                    if (bulletbox.intersects(wallbox)) {
                        b.setExists(false);
                        if (((Wall) obj).breakable()) {
                            obj.setExists(false);
                        }
                    }
                }
            }
        }
    }

    public void playerVsobject(){

        Rectangle tankOneBox = new Rectangle(player1.getTank().getX(), player1.getTank().getY(), player1.getTank().getImg().getWidth(), player1.getTank().getImg().getHeight());
        Rectangle tankTwoBox = new Rectangle(player2.getTank().getX(), player2.getTank().getY(), player2.getTank().getImg().getWidth(), player2.getTank().getImg().getHeight());
        Rectangle objBox;
        Rectangle2D intersection;
        if (tankOneBox.intersects(tankTwoBox)){
            intersection = tankOneBox.createIntersection(tankTwoBox);
            if (intersection.getMaxX() >= tankOneBox.getMaxX()){
                player1.getTank().setX(player1.getTank().getX() - 1);
                player2.getTank().setX(player2.getTank().getX() + 1);
            }
            if(intersection.getMaxX() >= tankTwoBox.getMaxX()){
                player1.getTank().setX(player1.getTank().getX() + 1);
                player2.getTank().setX(player2.getTank().getX() - 1);
            }
            if(intersection.getMaxY() >= tankOneBox.getMaxY()){
                player1.getTank().setY(player1.getTank().getY() - 1);
                player2.getTank().setY(player2.getTank().getY() + 1);
            }
            if(intersection.getMaxY() >= tankTwoBox.getMaxY()){
                player1.getTank().setY(player1.getTank().getY() + 1);
                player2.getTank().setY(player2.getTank().getY() - 1);
            }
        }

        for (int i = 0; i < GameWorld.getWorldList().size(); i++) {
            GameObject obj = GameWorld.getWorldList().get(i);
            objBox = new Rectangle(obj.getX(), obj.getY(), obj.getImg().getWidth(), obj.getImg().getHeight());
            if(obj instanceof Wall){
                if (tankOneBox.intersects(objBox)) {
                    intersection = tankOneBox.createIntersection(objBox);
                    if (intersection.getMaxX() >= tankOneBox.getMaxX()){
                        player1.getTank().setX(player1.getTank().getX() - 2);
                    }
                    if(intersection.getMaxX() >= objBox.getMaxX()){
                        player1.getTank().setX(player1.getTank().getX() + 2);
                    }
                    if(intersection.getMaxY() >= tankOneBox.getMaxY()){
                        player1.getTank().setY(player1.getTank().getY() - 2);
                    }
                    if(intersection.getMaxY() >= objBox.getMaxY()){
                        player1.getTank().setY(player1.getTank().getY() + 2);
                    }

                }
                if (tankTwoBox.intersects(objBox)){
                    intersection = tankTwoBox.createIntersection(objBox);
                    if (intersection.getMaxX() >= tankTwoBox.getMaxX()){
                        player2.getTank().setX(player2.getTank().getX() - 2);
                    }
                    if(intersection.getMaxX() >= objBox.getMaxX()){
                        player2.getTank().setX(player2.getTank().getX() + 2);
                    }
                    if(intersection.getMaxY() >= tankTwoBox.getMaxY()){
                        player2.getTank().setY(player2.getTank().getY() - 2);
                    }
                    if(intersection.getMaxY() >= objBox.getMaxY()){
                        player2.getTank().setY(player2.getTank().getY() + 2);
                    }
                }
            }
            if (obj instanceof Powerup){
                switch (((Powerup) obj).getID()) {
                    case 1:
                        if (tankOneBox.intersects(objBox)) {
                            obj.setExists(false);
                            player1.setHealth(player1.getHealth() + 30);
                        }
                        if (tankTwoBox.intersects(objBox)) {
                            obj.setExists(false);
                            player2.setHealth(player2.getHealth() + 30);
                        }
                        break;
                    case 2:
                        if (tankOneBox.intersects(objBox)) {
                            obj.setExists(false);
                            player1.setShields(player1.getShields() + 1);
                        }
                        if (tankTwoBox.intersects(objBox)) {
                            obj.setExists(false);
                            player2.setShields(player2.getShields() + 1);
                        }
                        break;
                    case 3:
                        if (tankOneBox.intersects(objBox)) {
                            obj.setExists(false);
                            player1.setRange(player1.getRange() + 25);
                        }
                        if (tankTwoBox.intersects(objBox)) {
                            obj.setExists(false);
                            player2.setRange(player2.getRange() + 25);
                        }
                        break;
                    case 4:
                        if (tankOneBox.intersects(objBox)) {
                            obj.setExists(false);
                            player1.getTank().setHeat(player1.getTank().getHeat() - 500);
                        }
                        if (tankTwoBox.intersects(objBox)) {
                            obj.setExists(false);
                            player2.getTank().setHeat(player2.getTank().getHeat() - 500);
                        }



                }
            }
        }

    }

}
