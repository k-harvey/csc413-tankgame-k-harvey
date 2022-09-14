package tankgame;

import java.awt.image.BufferedImage;

public class Powerup extends GameObject{

    private int ID;

    public Powerup(BufferedImage img, int x, int y, int ID)   {
        super(x, y, 0, img);
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    @Override
    public void update() {

    }
}
