/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
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
    private boolean visible;

    public Good(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.animationRight = new Animation(Assets.goodRight, 100);
        this.visible = true;
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

        int azar = (int) (Math.random() * 2 + 1);

        if (this.getY() <= game.getWidth()) {
            this.setY(this.getY() + azar);
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
        if (getY() >= 280) {
            this.die();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void die() {
        this.visible = false;
    }

    @Override
    public void render(Graphics g) {
        if (visible) {
            g.drawImage(animationRight.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
