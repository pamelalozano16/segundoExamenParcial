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
public class ScoreTask extends TimerTask {

    private Game game;

    ScoreTask(Game game) {
        this.game = game;
    }

    public void run() {
        game.score();
    }
}
