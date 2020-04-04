/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author mac
 */
public class Bomb extends Item {

    private int direction;
    private int width;
    private int height;
    private Game game;
    private boolean visible;
    //Cuando choque dejaran de ser visibles

    public Bomb(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.visible = true;
        // this.animationLeft = new Animation(Assets.enemyLeft, 100);
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

        setY(getY() + 1);

        if (getY() == 285) {
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
        //  g.drawImage(Assets.enemy, getX(), getY(), getWidth(), getHeight(), null);
        if (visible) {
            g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
        }
    }

}
