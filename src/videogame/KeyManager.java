package videogame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class KeyManager implements KeyListener {

    public boolean up;      // flag to move up the player
    public boolean down;    // flag to move down the player
    public boolean left;    // flag to move left the player
    public boolean right;   // flag to move right the player
    public boolean pause;
    public boolean space;
    public boolean guardar;
    public boolean load;

    private boolean keys[];  // to store all the flags for every key

    public KeyManager() {
        keys = new boolean[256];
        pause = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        /*   if(e.getKeyCode()==80){
            if(pause){keys[80]=false;}
            else{keys[80]=true;}
        }*/
        // sb.append(e.getKeyChar());
    }

    public void releasePause() { //Pausa no tiene release, se renuda cuando le vuelve a dar click
        keys[80] = false;
    }
    
   public void releaseSpace() { //Pausa no tiene release, se renuda cuando le vuelve a dar click
        keys[KeyEvent.VK_SPACE] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
        if (pause && (e.getKeyCode() == 80)) { //Que se renude si le da click a p
            releasePause();
        }

        // System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        if (e.getKeyCode() != 80) { //Todas menos pausa
            keys[e.getKeyCode()] = false;
        }
    }

    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
        pause = keys[80];
        guardar = keys[71];
        load = keys[67];
    }
}
