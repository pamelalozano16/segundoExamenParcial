/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;

import java.util.TimerTask;

/**
 *
 * @author mac
 */
public class BombTask extends TimerTask {

    private Game game;

    BombTask(Game game) {
        this.game = game;
    }

    public void run() {
        game.bomb();
    }
}
