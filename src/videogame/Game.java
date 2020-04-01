/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import static java.lang.System.load;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Pamela Lozano
 */
public class Game implements Runnable {

    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    public Player player;
    private KeyManager keyManager;
    private RandW RW;
    public LinkedList<Enemy> enemys; //LISTA = MALOS
    public LinkedList<Good> pacmans;// PACMANS = BUENOS
    public LinkedList<Bomb> bombs;
    public LinkedList<Shot> shots;
    private int contadorPerder;
    public int lives;
    private boolean pause;
    public int tamBuenos;
    public int tamMalos;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.pause = false;
        running = false;
        keyManager = new KeyManager();
        contadorPerder = 0;
        lives = 3;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
//

    public Player getPlayer() {
        return player;
    }

    public void beep() {
        Assets.loose.play();
    }

    public void win() {
        Assets.win.play();
    }

    public void gameOver() {
        Assets.changeBackground();
        Assets.end.play();
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        enemys = new LinkedList<Enemy>();
        pacmans = new LinkedList<Good>();
        bombs = new LinkedList<Bomb>();
        shots = new LinkedList<Shot>();
        tamBuenos = (int) (Math.random() * 3 + 8); //b-a+1 NUMERO DE ENEMIGOS
        tamMalos = (int) (Math.random() * 6 + 10); //NUMERO DE PACMANS
        System.out.println("Enemys: " + tamMalos);
        System.out.println("Buenos: " + tamBuenos);
        player = new Player((getWidth() - 100) / 2, 280, 1, 15, 10, this); //Player posicionen medio
        RW = new RandW(this);//Pasarle el game a read y write

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Enemy alien = new Enemy(150 + 18 * j, 5 + 18 * i, 1, 15, 15, this);
                enemys.add(alien);
            }
        }

        display.getJframe().addKeyListener(keyManager);
        System.out.println("Vidas: " + lives);

        //Timer para que cada 0.6s se disparen dos bombas
        Timer timer = new Timer();
        timer.schedule(new BombTask(this), 0, 600);

    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the
        buffer strategy element.
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            //Que se pinte el game over
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);

            if (lives > 0) {
                player.render(g);
                for (Enemy enemy : enemys) {
                    enemy.render(g);
                }
                for (Bomb bomb : bombs) {
                    bomb.render(g);
                }
                for (Shot shot : shots) {
                    shot.render(g);
                }
                /*for (Good pacman : pacmans) {
                pacman.render(g);
            }*/
                g.setColor(Color.white);
                g.drawString("Lives: " + Integer.toString(lives), getWidth() - 80,
                        45);//Pinta lives
                g.setColor(Color.green);
                g.drawLine(0, 290, getWidth(), 290);
            } else {
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
            }

            bs.show();
            g.dispose();

        }

    }

    public void border(int direction) {
        /*if(direction==1){
            Enemy enemy=enemys.get(0);
            enemy.setX(1);
        }*/
        for (Enemy enemy : enemys) {
            enemy.setY(enemy.getY() + 15);
            //Bajan 15 px
            if (enemy.getDirection() != direction) {
                enemy.setDirection(direction);
                //Cambia de dirección
                enemy.setX(enemy.getX() + direction);
                //Para que no se corran los de la esquina

            }

        }
    }

    public void bomb() { //Bombas de los enemigos
        if(!pause){
                //Dos bombas al mismo tiempo
        //Dos numeros random del 1 al 24
        int rand = (int) (Math.random() * 23 + 1);
        int rand2 = (int) (Math.random() * 23 + 1);

        //  Enemigos Random que disparan
        Enemy enemy = enemys.get(rand);
        Enemy enemy2 = enemys.get(rand2);

        //Bombas 
        Bomb bomb = new Bomb(enemy.getX(), enemy.getY(), 1, 3, 5, this);
        Bomb bomb2 = new Bomb(enemy2.getX(), enemy2.getY(), 1, 3, 5, this);

        //Se añaden dos bombas
        bombs.addLast(bomb);
        bombs.addLast(bomb2);
        }
    }

    public void shoot() {//Disparo del player
        Shot shot = new Shot(player.getX() + 6, player.getY(), 1, 3, 5, this);
        shots.add(shot);
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {
        if (lives > 0) { //Si se acaba que todo se deje de mover
            keyManager.tick();
            this.pause = keyManager.pause;
            /*   if (pause) {
                    System.out.println("pausar");
                } else {

                    System.out.println("despausar");
                }*/

            if (keyManager.guardar) {
                this.bomb();
                System.out.println("guardar");
                RW.Save("./load.txt");
            }
            if (keyManager.load) {
                System.out.println("load");
                enemys.clear(); //Resetear lista de malos
                pacmans.clear(); //Resetear lista de buenos
                RW.Load("./load.txt");
            }

            if (!pause) { //Si hay pausa deja de tickear
                player.tick();

                for (Enemy enemy : enemys) {
                    enemy.tick();
                }

                for (Bomb bomb : bombs) {
                    bomb.tick();
                    if (player.colission(bomb) && bomb.isVisible()) {
                        bomb.die();
                        lives--;
                    }
                }

                for (Shot shot : shots) {
                    for (Enemy enemy : enemys) {
                        if (shot.colission(enemy) && shot.isVisible() && enemy.isVisible()) {
                            //Si estan visibles siguen en el juego
                            enemy.die();
                            shot.die();
                        }
                    }
                    shot.tick();
                }
            }

        }
    }

    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
