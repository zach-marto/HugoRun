import java.awt.*;

public class Coin extends Sprite {
    private int sprite;
    private boolean isUpdating;

    public Coin(int x, int y, int dir) {
        super(x, y, dir);
        sprite = (int)(Math.random()*5)+1;
        setPic("coin1.png", Sprite.NORTH);
        isUpdating = false;
    }

    public void spin(){
        sprite++;
        if(sprite > 6)
            sprite = 1;
        String name = "coin" + sprite + ".png";
        setPic(name, Sprite.NORTH);
    }

    public void spawn(int lane){ //work on coords
        int x = 273*(lane-1) + 73 + ((200-getPic().getWidth())/2);
        int y = -200;
        setLoc(new Point(x, y));
        isUpdating = true;
    }

    public void update(){
        if(isUpdating) {
            int y = (int) (getLoc().getY());
            int x = (int) (getLoc().getX());
            y += 10;

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
}
