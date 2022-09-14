package tankgame;

public class Camera {
    Tank tank;

    int x;
    int y;

    public Camera(Tank tank){
        this.tank = tank;
        this.x = tank.getX() - TRE.SCREEN_WIDTH/4;
        this.y = tank.getY() - TRE.SCREEN_HEIGHT/2;
        this.verify();
    }

    public void verify(){
        if (this.x > TRE.WORLD_WIDTH - TRE.SCREEN_WIDTH/2){
            this.x = TRE.WORLD_WIDTH - TRE.SCREEN_WIDTH/2;
        }else if (this.x < 0){
            this.x = 0;
        }
        if (this.y > TRE.WORLD_HEIGHT - TRE.SCREEN_HEIGHT ){
            this.y = TRE.WORLD_HEIGHT - TRE.SCREEN_HEIGHT;
        }else if(this.y < 0){
            this.y = 0;
        }
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
