import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//TODO: PUT YOUR NAME HERE
 
public class Main extends JPanel {
 
    //instance fields for the general environment
    public static final int FRAMEWIDTH = 1440, FRAMEHEIGHT = 850;
    private Timer timer;
    private boolean[] keys;
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private double rockSpawnInterval;

    //coins
    private ArrayList<Coin> coins = new ArrayList<Coin>();

    private Hugo hugo = new Hugo(0, 500, Sprite.NORTH);
 
    public Main() {

        keys = new boolean[512]; //should be enough to hold any key code.

        rockSpawnInterval = 0.015;

        //creates all obstacles in an ArrayList
        for (int i = 0; i < 100; i++) {
            obstacles.add(new Obstacle(-200, -200, Sprite.NORTH));
        }

        //coins
        coins.add(new Coin(500, 500, Sprite.NORTH));

        //timer
        timer = new Timer(100, new ActionListener() {
            @Override //code in here executes every tick
            public void actionPerformed(ActionEvent actionEvent) {


                //checks if hugo intersects and obstacle
                for (int i = 0; i < obstacles.size(); i++) {
                    if(hugo.intersects(obstacles.get(i)))
                        System.out.println("you dead stupid head");
                }

                //animates hugo
                hugo.changeFrame();

                repaint(); //always the last line.  after updating, refresh the graphics.
            }
        });
        timer.start();
 
        setKeyListener();

 
    }
 
    //Our paint method.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //this whole section draws the background
        Color background = new Color(64,32,31);
        g2.setColor(background);

        g2.fillRect(0,0,1440,900);

        Color color = new Color (100,57,40);
        g2.setColor(color);

//        for (int i = 1; i < 6; i++) {

            int constant = 273;
            int[] x1 = {73, constant, 315, 165};
            int[] y1 = {900,900,0,0};
            int a1 = 4;
            g2.fillPolygon(x1, y1, a1);

        int[] x2 = {73 + constant, 2 * constant, 555, 395};
        int[] y2 = {900,900,0,0};
        int a2 = 4;
        g2.fillPolygon(x2, y2, a2);


        int[] x3 = {73 + 2*constant, 3 * constant, 795, 645};
        int[] y3 = {900,900,0,0};
        int a3 = 4;
        g2.fillPolygon(x3, y3, a3);

        int[] x4 = {73 + 3*constant, 4 * constant, 1035, 885};
        int[] y4 = {900,900,0,0};
        int a4 = 4;
        g2.fillPolygon(x4, y4, a4);

        int[] x5 = {73 + 4*constant, 5 * constant, 1265, 1115};
        int[] y5 = {900,900,0,0};
        int a5 = 4;
        g2.fillPolygon(x5, y5, a5);


        //spawns in obstacles in random lanes in random increments
        if(Math.random() < rockSpawnInterval){
            int index = (int)(Math.random()*obstacles.size());
            if(!obstacles.get(index).getIsUpdating())
                obstacles.get(index).spawn((int)(Math.random()*5)+1);
        }
        rockSpawnInterval += 0.00001; //slowly makes rocks spawn faster

        //stops rocks from stacking
        for (int i = 0; i < obstacles.size(); i++) {
            int n = i;
            for (int j = 0; j < n; j++) {
                if(obstacles.get(i).intersects(obstacles.get(j))) {
                    obstacles.get(j).setUpdating(false);
                    obstacles.get(j).setLoc(new Point(-200, -200));
                }
            }
            for (int j = n+1; j < obstacles.size(); j++) {
                if(obstacles.get(i).intersects(obstacles.get(j))) {
                    obstacles.get(j).setUpdating(false);
                    obstacles.get(j).setLoc(new Point(-200, -200));
                }
            }
        }

        //updates/draws all obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).update();
            obstacles.get(i).draw(g2);
        }

        //draws coins
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).spin();
            coins.get(i).draw(g2);
        }


        hugo.draw(g2);

    }

    //does actions when keys are pressed
    public void moveHugo() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
            hugo.setUpAndDown(hugo.getUpAndDown()-1);
            keys[KeyEvent.VK_W] = false;
            keys[KeyEvent.VK_UP] = false;
        }
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
            hugo.setLane(hugo.getLane()-1);
            keys[KeyEvent.VK_A] = false;
            keys[KeyEvent.VK_LEFT] = false;
        }
        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
            hugo.setUpAndDown(hugo.getUpAndDown()+1);
            keys[KeyEvent.VK_S] = false;
            keys[KeyEvent.VK_DOWN] = false;
        }
        if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
            hugo.setLane(hugo.getLane()+1);
            keys[KeyEvent.VK_D] = false;
            keys[KeyEvent.VK_RIGHT] = false;
        }



    }

    //detects when keys are pressed
    public void setKeyListener() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {/*intentionally left blank*/ }
 
            //when a key is pressed, its boolean is switch to true.
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = true;
                moveHugo();

            }
 
            //when a key is released, its boolean is switched to false.
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                keys[keyEvent.getKeyCode()] = false;
            }
        });
    }
    //sets ups the panel and frame
    public static void main(String[] args) {
        JFrame window = new JFrame("Hugo Run!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, FRAMEWIDTH, FRAMEHEIGHT + 22); //(x, y, w, h) 22 due to title bar.
 
        Main panel = new Main();
        panel.setSize(FRAMEWIDTH, FRAMEHEIGHT);
 
        panel.setFocusable(true);
        panel.grabFocus();
 
        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }
}