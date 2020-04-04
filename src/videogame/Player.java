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
public class Player extends Item {

    private int direction;
    private Game game;
    private Animation animationUp; // to store the animation for going up.
    private Animation animationLeft; // to store the animation for going left.
    private Animation animationDown; // to store the animation for going down.
    private Animation animationRight; // to store the animation for going right.
    private Animation animationStanding;
    private String animation;

    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.animationStanding = new Animation(Assets.playerStanding, 100);
        this.animation = "standing"; //Empieza standing
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void tick() {

        if (animation == "standing") {
            this.animationStanding.tick();
        }
        // moving player depending on flags
        /*if (game.getKeyManager().up) {
            setY(getY() - 1);
            animation = "up";
        } else if (game.getKeyManager().down) {
            setY(getY() + 1);
            animation = "down";
        } */
        if (game.getKeyManager().space) {
            game.shoot();
            game.getKeyManager().releaseSpace();
        }
        if (game.getKeyManager().left) {
            setX(getX() - 2);
            animation = "left";
        } else if (game.getKeyManager().right) {
            setX(getX() + 2);
            animation = "right";
        } else { //Si no se esta moviendo
            animation = "standing";
        };
        // reset x position and y position if colision
        if (getX() + 15 >= game.getWidth()) {
            setX(game.getWidth() - 15);
        } else if (getX() <= 0) {
            setX(0);
        }
        if (getY() + 48 >= game.getHeight()) {
            setY(game.getHeight() - 48);
        } else if (getY() <= 0) {
            setY(0);
        }
    }

    @Override
    public void render(Graphics g) {
            g.drawImage(animationStanding.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
           
    }
}
