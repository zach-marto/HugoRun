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

    private Hugo hugo = new Hugo(0, 500, Sprite.NORTH);
 
    public Main() {

        keys = new boolean[512]; //should be enough to hold any key code.

        for (int i = 0; i < 6; i++) {
            obstacles.add(new Obstacle(-200, -200, Sprite.NORTH));
            obstacles.get(i).spawn(i);
        }

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                for (int i = 0; i < obstacles.size(); i++) {
                    if(hugo.intersects(obstacles.get(i)))
                        System.out.println("you dead stupid head");
                }

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



        Color color = new Color (100,57,40);
        g2.setColor(color);

        for (int i = 1; i < 6; i++) {

            int constant = 273;
            int[] x1 = {73 + (i-1)*constant, i*constant, i*constant, 73 + (i-1)*constant};
            int[] y1 = {900,900,0,0};
            int a1 = 4;
            g2.fillPolygon(x1, y1, a1);
        }


        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).update();
            obstacles.get(i).draw(g2);
        }


        hugo.draw(g2);

    }
 
    public void moveHugo() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {

            keys[KeyEvent.VK_W] = false;
            keys[KeyEvent.VK_UP] = false;
        }
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
            hugo.setLane(hugo.getLane()-1);
            keys[KeyEvent.VK_A] = false;
            keys[KeyEvent.VK_LEFT] = false;
        }
        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {

            keys[KeyEvent.VK_S] = false;
            keys[KeyEvent.VK_DOWN] = false;
        }
        if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
            hugo.setLane(hugo.getLane()+1);
            keys[KeyEvent.VK_D] = false;
            keys[KeyEvent.VK_RIGHT] = false;
        }
    }
 
    /*
      You probably don't need to modify this keyListener code.
       */
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
    //sets ups the panel and frame.  Probably not much to modify here.
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