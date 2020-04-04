/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;

import java.util.TimerTask;

/**
 *
 * @author Javier
 */
public class GoodTask extends TimerTask {

    private Game game;

    GoodTask(Game game) {
        this.game = game;
    }

    public void run() {
        game.good();
    }
}
