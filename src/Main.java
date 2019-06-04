import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//TODO: PUT YOUR NAME HERE
 
public class Main extends JPanel {
 
    //instance fields for the general environment
    public static final int FRAMEWIDTH = 1440, FRAMEHEIGHT = 850;
    private Timer timer;
    private boolean[] keys;

    private Hugo hugo = new Hugo(0, 500, Sprite.NORTH);
 
    public Main() {
        keys = new boolean[512]; //should be enough to hold any key code.

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

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

        hugo.draw(g2);

    }
 
    public void moveHugo() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {

            keys[KeyEvent.VK_W] = false;
            keys[KeyEvent.VK_UP] = false;
        }
        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {

            keys[KeyEvent.VK_A] = false;
            keys[KeyEvent.VK_LEFT] = false;
        }
        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {

            keys[KeyEvent.VK_S] = false;
            keys[KeyEvent.VK_DOWN] = false;
        }
        if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {

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