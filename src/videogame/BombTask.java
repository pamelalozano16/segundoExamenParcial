/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.util.TimerTask;

/**
 *
 * @author mac
 */
public class BombTask extends TimerTask {
    private Game game;
    BombTask(Game game){
        this.game=game;
    }
    public void run(){
        game.bomb();
    }
}
