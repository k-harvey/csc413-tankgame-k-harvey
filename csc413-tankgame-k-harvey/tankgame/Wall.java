package tankgame;

import java.awt.image.*;

public class Wall extends GameObject {

    private boolean breakable;

    public Wall(BufferedImage img, int x, int y, int type){

        super(x, y, 0, img);
        if (type == 1){
            this.breakable = true;
        }else{
            this.breakable = false;
        }

    }

    @Override
    public void update() {

    }

    public Boolean breakable(){
        return breakable;
    }
}
