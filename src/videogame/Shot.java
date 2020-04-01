/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;
import java.awt.Graphics;

/**
 *
 * @author mac
 */
public class Shot extends Item {

    private int direction;
    private int width;
    private int height;
    private Game game;
    private int moving;
    public boolean visible;
   // private Animation animationLeft;

    public Shot(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.visible=true;
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

        setY(getY()-2);
        
        if(getY()==0){
            this.die();
        }
    }
    public boolean isVisible(){
        return visible;
    }
    
    public void die(){
        this.visible=false;
    }

    @Override
    public void render(Graphics g) {
        //  g.drawImage(Assets.enemy, getX(), getY(), getWidth(), getHeight(), null);
        if(visible){
         g.drawImage(Assets.shot, getX(), getY(), getWidth(), getHeight(), null);
        }
    }

}
