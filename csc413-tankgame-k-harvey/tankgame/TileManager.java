package tankgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;


public class TileManager {

    BufferedImage background;
    BufferedImage wall1;
    BufferedImage wall2;
    private BufferedImage healthUp;
    private BufferedImage shieldUp;
    private BufferedImage rangeUp;
    private BufferedImage heatUp;
    ArrayList<ArrayList<Integer>> map = new ArrayList<>();

    public TileManager(BufferedImage background, BufferedImage wall1, BufferedImage wall2, BufferedImage healthUp, BufferedImage shieldUp, BufferedImage rangeUp, BufferedImage heatUp){

        this.background = background;
        this.wall1 = wall1;
        this.wall2 = wall2;
        this.healthUp = healthUp;
        this.shieldUp = shieldUp;
        this.rangeUp = rangeUp;
        this.heatUp = heatUp;

    }

    public void setUpMap(String fileName){

        try(BufferedReader br = new BufferedReader((new FileReader(fileName)))){

            String currentLine;

            while((currentLine = br.readLine()) != null){

                if(currentLine.isEmpty()){
                    continue;
                }

                ArrayList<Integer> row = new ArrayList<>();

                String[] values = currentLine.trim().split(" ");

                for(String string : values){
                    System.out.println(string);
                    if(!string.isEmpty()){
                        int id = Integer.parseInt(string);
                        row.add(id);

                    }
                }
                map.add(row);

            }



        }catch(IOException e){

        }

        for (int y = 0; y < map.size(); y++ ){
            for (int x = 0; x <map.get(y).size(); x++){
                if (!(map.get(y).get(x).equals(0))){
                    switch (map.get(y).get(x)){
                        case 1: GameWorld.addGameObject(new Wall(this.wall1, x*64, y*64, 1));
                            break;
                        case 2: GameWorld.addGameObject(new Wall(this.wall2, x*64, y*64, 2));
                            break;
                        case 3: GameWorld.addGameObject(new Powerup(this.healthUp, x*64, y*64, 1));
                            break;
                        case 4: GameWorld.addGameObject(new Powerup(this.shieldUp, x*64, y*64, 2));
                            break;
                        case 5: GameWorld.addGameObject(new Powerup(this.rangeUp, x*64, y*64, 3));
                            break;
                        case 6: GameWorld.addGameObject(new Powerup(this.heatUp, x*64, y*64, 4));
                            break;
                    }
                }
            }
        }


    }

    public void drawLayout(Graphics2D buffer){
        buffer.clearRect(0,0, TRE.WORLD_WIDTH, TRE.WORLD_HEIGHT);
        for (int y = 0; y < TRE.WORLD_HEIGHT; y+=TRE.WORLD_HEIGHT/5 ){
            for (int x = 0; x < TRE.WORLD_WIDTH; x+=TRE.WORLD_WIDTH/5){
                buffer.drawImage(background, x, y, null);
            }
        }
    }



}
