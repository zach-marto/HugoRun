import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//By Zachary Marto and Jiming Xu
 
public class Main extends JPanel {
 
    //instance fields for the general environment
    public static final int FRAMEWIDTH = 1440, FRAMEHEIGHT = 850;
    private Timer timer;
    private boolean[] keys;
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private double rockSpawnInterval;
    private int score;
    private boolean isPlaying;
    private boolean isInvincible;
    private int invincibleCounter;
    private boolean playing;

    //coins
    private ArrayList<Coin> coins = new ArrayList<Coin>();
    private ArrayList<Star> stars = new ArrayList<Star>();

    private Hugo hugo = new Hugo(0, 500, Sprite.NORTH);
    private Sprite jimingPic = new Sprite(800, 200, Sprite.NORTH);

    private Star counterStar = new Star(10, 50, Sprite.NORTH);
 
    public Main() {

        keys = new boolean[512]; //should be enough to hold any key code.
        score = 0;
        rockSpawnInterval = 0.015;
        isPlaying = true;
        jimingPic.setPic("jiming.png", Sprite.NORTH);
        isInvincible = false;
        invincibleCounter = 30;

        //creates all obstacles in an ArrayList
        for (int i = 0; i < 100; i++) {
            obstacles.add(new Obstacle(-200, -200, Sprite.NORTH));
        }

        //stars
        for (int i = 0; i < 20; i++) {
            stars.add(new Star(-200, -200, Sprite.NORTH));
        }

        //coins
        for (int i = 0; i < 100; i++) {
            coins.add(new Coin(-200, -200, Sprite.NORTH));
        }

        //timer
        timer = new Timer(100, new ActionListener() {
            @Override //code in here executes every tick
            public void actionPerformed(ActionEvent actionEvent) {
                if(isPlaying) {
                    score++;

                    //checks if hugo intersects and obstacle and sets obstacle speed
                    for (int i = 0; i < obstacles.size(); i++) {
                        obstacles.get(i).update();
                        if(hugo.intersects(obstacles.get(i)))
                            gameOver();
                        int speed = score/500 + 10;
                        obstacles.get(i).setSpeed(speed);
                    }

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

                    //coins
                    for (int i = 0; i < coins.size(); i++) {
                        coins.get(i).spin();
                        coins.get(i).update();
                        if(isPlaying) {
                            if (coins.get(i).intersects(hugo)) {
                                coins.get(i).setUpdating(false);
                                coins.get(i).setLoc(new Point(-200, -200));
                                score += 50;
                            }
                        }
                    }

                    if(Math.random() < 0.02){
                        int index = (int)(Math.random()*coins.size());
                        if(!coins.get(index).getIsUpdating())
                            coins.get(index).spawn((int)(Math.random()*5)+1);
                    }

                    //stars
                    for (int i = 0; i < stars.size(); i++) {
                        stars.get(i).spin();
                        stars.get(i).update();
                        if(isPlaying){
                            if(stars.get(i).intersects(hugo)){
                                stars.get(i).setUpdating(false);
                                stars.get(i).setLoc(new Point(-200, -200));
                                goInvincible();
                            }
                        }
                    }

                    if(Math.random() < 0.005){
                        int index = (int)(Math.random()*stars.size());
                        if(!stars.get(index).getIsUpdating())
                            stars.get(index).spawn((int)(Math.random()*5)+1);
                    }

                    //invincible countdown
                    if(isInvincible){
                        invincibleCounter--;
                        if(invincibleCounter < 1){
                            isInvincible = false;
                            invincibleCounter = 30;
                        }
                    }

                    //animates hugo
                    hugo.changeFrame();

                }
                repaint(); //always the last line.  after updating, refresh the graphics.
            }
        });
        timer.start();
 
        setKeyListener();

 
    }
 
    //Our paint method.
    public void paintComponent(Graphics g) {

        if(!playing) {
            String file = "./res/HugoRun.wav";
            InputStream in = null;
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            AudioStream audioStream = null;
            try {
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(audioStream);

            playing = true;
        }


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

        //draws the score
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("ComicSansMS", Font.BOLD, 36));
        g2.drawString("Score: " + score, 10, 50);

        //draws invincibility counter
        if(isInvincible) {
            invincibleCounter = 30;
            counterStar.draw(g2);
            g2.drawString("" + invincibleCounter, 40, 115);
        }


        //updates/draws all obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw(g2);
        }

        //stars
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).draw(g2);
        }

        //draws coins
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).draw(g2);
        }

        hugo.draw(g2);

        //end screen
        if(!isPlaying){
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 1440, 900);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("ComicSansMS", Font.BOLD, 100));
            g2.drawString("Game Over!", 100, 300);
            g2.setFont(new Font("ComicSansMS", Font.BOLD, 75));
            g2.drawString("Your score: " + score, 100, 500);
            jimingPic.draw(g2);

        }

    }

    public void gameOver(){
        if(!isInvincible) {
            isPlaying = false;
            repaint();
        }
    }

    public void goInvincible(){
        isInvincible = true;
    }

    //does actions when keys are pressed
    public void moveHugo() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
            hugo.moveUp();
            keys[KeyEvent.VK_W] = false;
            keys[KeyEvent.VK_UP] = false;
        }
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
            hugo.setLane(hugo.getLane()-1);
            keys[KeyEvent.VK_A] = false;
            keys[KeyEvent.VK_LEFT] = false;
        }
        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
            hugo.moveDown();
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