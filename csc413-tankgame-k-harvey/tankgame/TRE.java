package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;
import java.io.File;
/**
 *
 * @author anthony-pc
 */
public class TRE<frameCount> extends JPanel  {


    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 450;
    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 1600;

    private GameWorld gameWorld;
    public Player player1;
    public Player player2;

    private BufferedImage forwardHeart;
    private BufferedImage reverseHeart;
    private BufferedImage reverseTank;


    //Added for StartmenuPanel
    private JPanel mainPanel;
    /*
     * start panel will be used to view the start menu. It will contain
     * two buttons start and exit.
     */
    private JPanel startPanel;
    /*
     * game panel is used to show our game to the screen. inside this panel
     * also contains the game loop. This is where out objects are updated and
     * redrawn. This panel will execute its game loop on a separate thread.
     * This is to ensure responsiveness of the GUI. It is also a bad practice to
     * run long running loops(or tasks) on Java Swing's main thread. This thread is
     * called the event dispatch thread.
     */
    private TRE gamePanel;
    /*
     * end panel is used to show the end game panel.  it will contain
     * two buttons restart and exit.
     */
    private JPanel endPanel;
    /*
     * JFrame used to store our main panel. We will also attach all event
     * listeners to this JFrame.
     */
    private CardLayout cl;

    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2;
    private BufferedImage background;
    private Camera cam1;
    private Camera cam2;
    public boolean GameOver = false;

    private CollisionDetector CD;

    public static int framecount = 0;




    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        try {

            while (!(trex.GameOver)) {
                trex.player1.getTank().update();
                trex.player2.getTank().update();
                trex.CD.playerVsbullet();
                trex.CD.bulletVswall();
                trex.CD.playerVsobject();
                trex.repaint();
                framecount++;
                Thread.sleep(1000 / 144);
                if(trex.player1.getLives() == 0 || trex.player2.getLives() == 0){
                    trex.GameOver = true;
                }
            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Wars Game");

        /*
        this.mainPanel = new JPanel(); // create a new main panel
        this.startPanel = new StartMenuPanel(this); // create a new start panel
        //this.gamePanel = new TRE(this); // create a new game panel
        //this.gamePanel.gameInitialize(); // initialize game, but DO NOT start game
        this.endPanel = new EndGamePanel(this); // create a new end game pane;
        cl = new CardLayout(); // creating a new CardLayout Panel
        //this.jf.setResizable(false); //make the JFrame not resizable
        this.mainPanel.setLayout(cl); // set the layout of the main panel to our card layout
        this.mainPanel.add(startPanel, "start"); //add the start panel to the main panel
        this.mainPanel.add(gamePanel, "game");   //add the game panel to the main panel
        this.mainPanel.add(endPanel, "end");    // add the end game panel to the main panel
        this.jf.add(mainPanel); // add the main panel to the JFrame
        this.setFrame("start"); // set the current panel to start panel
*/
        this.world = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null, bulletimg = null;
        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            forwardHeart = read(new File("forwardHeart.png"));
            reverseHeart = read(new File("reverseHeart.png"));
            reverseTank = read(new File("reverseTank.png"));



        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        gameWorld = new GameWorld();

        player1 = new Player(gameWorld.getPlayer(1));
        player2 = new Player(gameWorld.getPlayer(2));

        CD = new CollisionDetector(player1, player2);

        //t1 = new Tank(WORLD_WIDTH/4 - 25, WORLD_HEIGHT/4 - 25, 0, 0, 0, 3, 2, t1img, bulletimg);
        //t2 = new Tank(3*WORLD_WIDTH/4 - 25, 3*WORLD_HEIGHT/4 - 25, 180, 0, 0, 3, 2, t2img, bulletimg);


        TankControl tc1 = new TankControl(player1.getTank(), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(player2.getTank(), KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


        this.jf.setSize(TRE.SCREEN_WIDTH+1, TRE.SCREEN_HEIGHT + 275);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        this.setBackground(Color.black);
        this.setForeground(Color.GREEN);

    }

    /*
    // Added setFrame for StartMenuPanel
    public void setFrame(String type){
        this.jf.setVisible(false); // hide the JFrame
        switch(type){
            case "start":
                // set the size of the jFrame to the expected size for the start panel
                this.jf.setSize(GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "game":
                // set the size of the jFrame to the expected size for the game panel
                this.jf.setSize(GameConstants.SCREEN_WIDTH,GameConstants.SCREEN_HEIGHT);
                //start a new thread for the game to run. This will ensure our JFrame is responsive and
                // not stuck executing the game loop.
                (new Thread((Runnable) this.gamePanel)).start();
                break;
            case "end":
                // set the size of the jFrame to the expected size for the end panel
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
        }
        this.cl.show(mainPanel, type); // change current panel shown on main panel tp the panel denoted by type.
        this.jf.setVisible(true); // show the JFrame
    }

    public void closeGame(){
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }
*/
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);
        gameWorld.drawWorld(buffer);

        cam1 = new Camera(this.player1.getTank());
        cam2 = new Camera(this.player2.getTank());

        g2.drawImage((world.getSubimage(cam1.getX(), cam1.getY(), SCREEN_WIDTH/2, SCREEN_HEIGHT)),0,0,null);
        g2.drawImage((world.getSubimage(cam2.getX(), cam2.getY(),  SCREEN_WIDTH/2, SCREEN_HEIGHT)), SCREEN_WIDTH/2+1, 0, null);
        g2.drawImage((world.getSubimage(0, 0, WORLD_WIDTH, WORLD_HEIGHT)), SCREEN_WIDTH/2-SCREEN_WIDTH/8, SCREEN_HEIGHT + 1, SCREEN_WIDTH/4, 245, null);
        g2.drawImage(gameWorld.getTankImg(), SCREEN_WIDTH/32 - 15, 33*SCREEN_HEIGHT/32, null);
        g2.drawImage(reverseTank, SCREEN_WIDTH - 75, 33*SCREEN_HEIGHT/32, null);


        for (int i = 0; i < player1.getLives(); i++){
            g2.drawImage(forwardHeart, SCREEN_WIDTH/32 - 17, 33*SCREEN_HEIGHT/32 + 55*(1 + i), null);
        }
        for (int i = 0; i < player2.getLives(); i++){
            g2.drawImage(reverseHeart, SCREEN_WIDTH - 68, 33*SCREEN_HEIGHT/32 + 55*(1 + i), null);
        }

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        g2.setColor(Color.BLUE);
        g2.drawString(("Shields: "), SCREEN_WIDTH/30 + 47, 34*SCREEN_HEIGHT/32 + 75);
        g2.drawOval(SCREEN_WIDTH/30 + 180, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH/30 + 230, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH/30 + 280, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH/30 + 330, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH - 410, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH - 360, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH - 310, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        g2.drawOval(SCREEN_WIDTH - 260, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);
        for (int i = 0; i < player1.getShields(); i++){

            g2.fillOval(SCREEN_WIDTH/30 + 180 + 50*i, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);

        }
        for (int i = 0; i < player2.getShields(); i++){

            g2.fillOval(SCREEN_WIDTH - 410 + 50 * i, 34*SCREEN_HEIGHT/32 + 75 - 30, 40, 40);

        }
        g2.drawString((":Shields "), 2*SCREEN_WIDTH/3 + 192, 34*SCREEN_HEIGHT/32 + 75);

        g2.setColor(Color.WHITE);

        g2.drawString(("Range:"), SCREEN_WIDTH/30 +46, 34*SCREEN_HEIGHT/32 + 125 );
        if (player1.getRange() == 50) {
            g2.setColor(Color.GRAY);
        } else if (player1.getRange() > 50 && player1.getRange() < Player.MAX_RANGE){
            g2.setColor(Color.YELLOW);
        }else{
            g2.setColor(Color.GREEN);
        }
        //g2.drawLine(SCREEN_WIDTH/30 + 178, 34*SCREEN_HEIGHT/32 + 115, SCREEN_WIDTH/30 + 178 + player1.getRange()*2,34*SCREEN_HEIGHT/32 + 115 );
        g2.drawRect(SCREEN_WIDTH/30 + 182, 34*SCREEN_HEIGHT/32 + 107, 3*player1.MAX_RANGE / 2 + 37,25 );
        g2.fillRect(SCREEN_WIDTH/30 + 182, 34*SCREEN_HEIGHT/32 + 107, 3*player1.getTank().getRange()/2 + 37,25 );

        g2.setColor(Color.WHITE);

        g2.drawString((":Range"), 2*SCREEN_WIDTH/3 + 205, 34*SCREEN_HEIGHT/32 + 125 );
        if (player2.getRange() == 50) {
            g2.setColor(Color.GRAY);
        } else if (player2.getRange() > 50 && player2.getRange() < Player.MAX_RANGE){
            g2.setColor(Color.YELLOW);
        }else{
            g2.setColor(Color.GREEN);
        }
        //g2.drawLine(2*SCREEN_WIDTH/3 + 185, 34*SCREEN_HEIGHT/32 + 115, 2*SCREEN_WIDTH/3 + 180 - player2.getRange()*2,34*SCREEN_HEIGHT/32 + 115 );
        g2.drawRect(2*SCREEN_WIDTH/3 - 8, 34*SCREEN_HEIGHT/32 + 105, 3*player2.MAX_RANGE / 2 + 38,25 );
        g2.fillRect(2*SCREEN_WIDTH/3 + 3*player2.MAX_RANGE/2 - 3*player2.getRange()/2 - 8, 34*SCREEN_HEIGHT/32 + 105, 3*player2.getTank().getRange()/2 + 38,25 );
        g2.setColor(Color.RED);

        g2.drawString(("Temp:"), SCREEN_WIDTH/30 + 52, 34*SCREEN_HEIGHT/32 + 175 );

        if (player1.getTank().getHeat() >= 0 && player1.getTank().getHeat() < 250){
            g2.setColor(Color.WHITE);
        }else if(player1.getTank().getHeat() >= 25 && player1.getTank().getHeat() < 500){
            g2.setColor(Color.YELLOW);
        }else if(player1.getTank().getHeat() >= 50 && player1.getTank().getHeat() < 750){
            g2.setColor(new Color(255, 102, 0));
        }

        //g2.drawLine(SCREEN_WIDTH/30 + 178, 34*SCREEN_HEIGHT/32 + 165, SCREEN_WIDTH/30 + 178 + player1.getTank().getHeat()/5,34*SCREEN_HEIGHT/32 + 165 );

        g2.drawRect(SCREEN_WIDTH/30 + 179, 34*SCREEN_HEIGHT/32 + 155, player1.MAX_HEAT/5 - 10,25 );
        g2.fillRect(SCREEN_WIDTH/30 + 179, 34*SCREEN_HEIGHT/32 + 155, player1.getTank().getHeat()/5 - 10,25 );

        g2.setColor(Color.RED);

        g2.drawString((":Temp"), SCREEN_WIDTH - 175, 34*SCREEN_HEIGHT/32 + 175 );

        if (player2.getTank().getHeat() >= 0 && player2.getTank().getHeat() < 250){
            g2.setColor(Color.WHITE);
        }else if(player2.getTank().getHeat() >= 25 && player2.getTank().getHeat() < 500){
            g2.setColor(Color.YELLOW);
        }else if(player2.getTank().getHeat() >= 50 && player2.getTank().getHeat() < 750){
            g2.setColor(new Color(255, 102, 0));
        }

        //g2.drawLine(SCREEN_WIDTH/30 + 178, 34*SCREEN_HEIGHT/32 + 165, SCREEN_WIDTH/30 + 178 + player1.getTank().getHeat()/5,34*SCREEN_HEIGHT/32 + 165 );

        g2.drawRect(SCREEN_WIDTH/2 + 190, 34*SCREEN_HEIGHT/32 + 155, player2.MAX_HEAT/5 - 10,25 );
        g2.fillRect(SCREEN_WIDTH/2 + 190 + Player.MAX_HEAT/5 - player2.getTank().getHeat()/5, 34*SCREEN_HEIGHT/32 + 155, player2.getTank().getHeat()/5 - 10,25 );

        g2.setColor(Color.RED);



        if (player1.getLives() == 0 || player2.getLives() == 0){
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 350));
            g2.drawString("GAME", 75, 350);
            g2.drawString("OVER", 90, 620);
            return;
        } else {
            g2.drawRect(SCREEN_WIDTH / 30 + 50, 33 * SCREEN_HEIGHT / 32, Player.MAX_HEALTH, 50);
            g2.fillRect(SCREEN_WIDTH / 30 + 50, 33 * SCREEN_HEIGHT / 32, player1.getHealth(), 50);
            g2.drawRect(SCREEN_WIDTH - 415, 33 * SCREEN_HEIGHT / 32, Player.MAX_HEALTH, 50);
            g2.fillRect(SCREEN_WIDTH - 415 + Player.MAX_HEALTH - player2.getHealth(), 33 * SCREEN_HEIGHT / 32, player2.getHealth(), 50);
        }


    }


}
