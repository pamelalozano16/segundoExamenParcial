/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        this.animationUp = new Animation(Assets.playerUp, 100);
        this.animationLeft = new Animation(Assets.playerLeft, 100);
        this.animationDown = new Animation(Assets.playerDown, 100);
        this.animationRight = new Animation(Assets.playerRight, 100);
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
        // updating animation.
        /*if (animation == "right") {
            this.animationRight.tick();
        }
        if (animation == "left") {
            this.animationLeft.tick();
        }
        if (animation == "up") {
            this.animationUp.tick();
        }
        if (animation == "down") {
            this.animationDown.tick();
        }
        if (animation == "standing") {
            this.animationStanding.tick();
        }*/
        // moving player depending on flags
        /*if (game.getKeyManager().up) {
            setY(getY() - 1);
            animation = "up";
        } else if (game.getKeyManager().down) {
            setY(getY() + 1);
            animation = "down";
        } */
        if(game.getKeyManager().space){
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
       /* if (animation == "right") {
            g.drawImage(animationRight.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
        if (animation == "left") {
            g.drawImage(animationLeft.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
        if (animation == "up") {
            g.drawImage(animationUp.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
        if (animation == "down") {
            g.drawImage(animationDown.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }
        if (animation == "standing") {
            g.drawImage(animationStanding.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
        }*/
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);

    }
}
