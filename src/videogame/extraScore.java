package videogame;

import java.awt.Graphics;

/**
 *
 * @author Pamela
 */
public class extraScore extends Item {

    private int direction;
    private int width;
    private int height;
    private Game game;
    private boolean visible;
    private Animation animationRight;

    public extraScore(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.visible=true;
        this.animationRight = new Animation(Assets.extraScore, 100);
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

    public boolean isVisible() {
        return visible;
    }

    public void die() {
        this.visible = false;
    }


    public void tick() {

        this.animationRight.tick();

        int azar = (int) (Math.random() * 2 + 1);

        if (this.getY() <= game.getWidth()) {
            this.setY(this.getY() + azar);
        }
     
        if (getY() >= 280) {
            this.die();
        }

    }

    @Override
    public void render(Graphics g) {
        if (isVisible()) {
            g.drawImage(animationRight.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);

        }
    }

}
