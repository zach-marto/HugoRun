import java.awt.*;

public class Obstacle extends Sprite {
    private boolean isUpdating = false;
    private int speed = 10;

    public Obstacle(int x, int y, int dir) {
        super(x, y, dir);
        setPic("rock.png", Sprite.NORTH);
        isUpdating = false;
    }

    public void spawn(int lane){
        int x = 273*(lane-1) + 73 + ((200-getPic().getWidth())/2);
        int y = -200;
        setLoc(new Point(x, y));
        isUpdating = true;
    }

    public void update(){
        if(isUpdating) {
            int y = (int) (getLoc().getY());
            int x = (int) (getLoc().getX());
            y += speed;

            setLoc(new Point(x, y));

            if(y > 950)
                isUpdating = false;
        }
    }

    public boolean getIsUpdating() {
        return isUpdating;
    }

    public void setUpdating(boolean updating) {
        isUpdating = updating;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
