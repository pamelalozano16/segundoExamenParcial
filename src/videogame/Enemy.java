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
public class Enemy extends Item {

    private int direction;
    private int width;
    private int height;
    private Game game;
    private int moving;
    private boolean visible;
    private Animation animationStanding;

    public Enemy(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.visible = true;
        this.animationStanding = new Animation(Assets.enemyStanding, 100);
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
        animationStanding.tick();
        if (this.getX() <= 0) {   //Esquina izquierda
            // setX(0);
            //this.direction=1;
            game.border(1);
        }
        if (this.getX() >= game.getWidth() - 12) {   //Esquina izquierda
            this.direction = -1;
            game.border(-1);

        }
        setX(getX() + direction);
        if (getY() >= 300) {
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
            g.drawImage(animationStanding.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
    }

}
