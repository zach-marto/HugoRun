public class Coin extends Sprite {
    private int sprite;

    public Coin(int x, int y, int dir) {
        super(x, y, dir);
        sprite = 1;
        setPic("coin1.png", Sprite.NORTH);
    }

    public void spin(){
        sprite++;
        if(sprite > 6)
            sprite = 1;
        String name = "coin" + sprite;
        setPic(name, Sprite.NORTH);
    }
}
