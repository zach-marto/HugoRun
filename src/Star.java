public class Star extends Coin {
    private int sprite = 1;
    public Star(int x, int y, int dir) {
        super(x, y, dir);
        setPic("star1.png", Sprite.NORTH);
    }

    @Override
    public void spin(){
        sprite++;
        if(sprite > 2)
            sprite = 1;
        String name = "star" + sprite + ".png";
        setPic(name, Sprite.NORTH);
    }
}
