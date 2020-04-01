package videogame;
import java.awt.Graphics;

/**
 *
 * @author Pamela Lozano
 */
public class Good extends Item {

    private int direction;
    private int width;
    private int height;
    private Game game;
    private Animation animationRight;

    public Good(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.animationRight = new Animation(Assets.goodRight, 100);
    }

    public int getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public void tick() {

        this.animationRight.tick();

        int azar = (int) (Math.random() * 3 + 1);

        if (this.getX() <= game.getWidth()) {
            this.setX(this.getX() + azar);
        }
        /*  else
        {
           this.setX(this.getX() - 1);
        }*/

 /* if(this.getY() >0){
            this.setY(this.getY() -azar);
        } 
        else
        {
           this.setY(this.getY() + azar);
        }*/
        if (getX() >= game.getWidth()) { //Esquina derecha
            setX(0);
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animationRight.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
