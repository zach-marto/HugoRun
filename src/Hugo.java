import java.awt.*;

public class Hugo extends Sprite {
    private int lane;
    private int upAndDown;
    boolean frame1 = true;
    public Hugo(int x, int y, int dir) {
        super(x, y, dir);
        setLane(3);
        setUpAndDown(2);
        setPic("hugo frame 1.png", Sprite.NORTH);
    }

//    public void moveLeft(){
//        if(lane <= 2) {
//            setLoc(new Point((int) (getLoc().getX() - 447), (int) (getLoc().getY())));
//            lane--;
//        }
//    }
//
//    public void moveRight(){
//        if(lane >= 2) {
//            setLoc(new Point((int) (getLoc().getX() + 447), (int) (getLoc().getY())));
//            lane++;
//        }
//    }

    //for later dog
    public void moveUp(){
        setLoc(new Point((int)(getLoc().getX()), (int)(getLoc().getY()+500)));
    }

    public void changeFrame(){
        if(frame1)
            setPic("hugo frame 2.png", Sprite.NORTH);
        else
            setPic("hugo frame 1.png", Sprite.NORTH);
        frame1 = !frame1;
    }

    public int getLane() {
        return lane;
    }

    public int getUpAndDown() {
        return upAndDown;
    }



    public void setLane(int lane) {
        if(lane < 1)
            lane = 1;
        if(lane > 5)
            lane = 5;

        this.lane = lane;

        int x = 273*(lane-1)+98;
        int y = (int)(getLoc().getY());

        setLoc(new Point(x, y));
    }


    public void setUpAndDown(int upAndDown) {
        if(upAndDown < 1)
            upAndDown = 1;
        if(upAndDown > 2)
            upAndDown = 2;

        this.upAndDown = upAndDown;

        int x = (int)(getLoc().getX());
        int y = 50+ (200*(upAndDown));

        setLoc(new Point(x,y));
    }


}
