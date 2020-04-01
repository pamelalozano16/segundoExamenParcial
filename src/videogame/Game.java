/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import static java.lang.System.load;
import java.util.LinkedList;

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
    private int contadorPerder;
    public int lives;
    public int score;
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
        int vidas = (int) (Math.random() * 3 + 3);
        lives = vidas;
        score = 0;
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
        tamBuenos = (int) (Math.random() * 3 + 8); //b-a+1 NUMERO DE ENEMIGOS
        tamMalos = (int) (Math.random() * 6 + 10); //NUMERO DE PACMANS
        System.out.println("Enemys: " + tamMalos);
        System.out.println("Buenos: " + tamBuenos);
        player = new Player((getWidth() - 100) / 2, 280, 1, 15, 10, this); //Player posicionen medio
        RW = new RandW(this);//Pasarle el game a read y write
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Enemy alien = new Enemy(150 + 18 * j,5 + 18 * i,-1, 15,15,this);
                enemys.add(alien);
            }
        }
      /*  for (int i = 1; i <= tamMalos; i++) { //CREACION DE ENEMIGOS EN EL EXTREMO IZQUIERDO
            Enemy enemy = new Enemy(getWidth() + 50, (int) (Math.random() * getHeight()), 1, 12, 12, this);
            lista.add(enemy);

        }
        for (int i = 1; i <= tamBuenos; i++) { //CREACION DE ENEMIGOS EN EL EXTREMO DERECHO
            Good pacman = new Good(-50, (int) (Math.random() * getHeight()), 1, 12, 12, this);
            pacmans.add(pacman);

        }*/

        display.getJframe().addKeyListener(keyManager);
        System.out.println("Vidas: " + lives);
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

            if (lives <= 0) { //Que se pinte el game over
                gameOver();
            }
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            for (Enemy enemy : enemys) {
                enemy.render(g);
            }
            /*for (Good pacman : pacmans) {
                pacman.render(g);
            }*/
            g.setColor(Color.white);
            g.drawString("Score: " + Integer.toString(score), getWidth() - 80,
                    20); //Pinta score
            g.drawString("Lives: " + Integer.toString(lives), getWidth() - 80,
                    45);//Pinta lives
            g.setColor(Color.green);
            g.drawLine(0, 290, getWidth(), 290);
             
            bs.show();
            g.dispose();

        }

    }
    
    public void border(int direction){
        for(Enemy enemy : enemys){
            enemy.setY(enemy.getY()+15);
            enemy.setDirection(direction);
        }
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
                   /* if (player.colission(enemy)) {
                        contadorPerder += 1; //Contador de veces que toca enemigo
                        if (contadorPerder >= 5) {
                            contadorPerder = 0;
                            beep();
                            lives -= 1; //5 veces resta vida

                            System.out.println("Perdio");
                        }
                        //player.setX(0);
                        //player.setY(getHeight()-100);
                        enemy.setX(getWidth() - 50); //Regresa al enemigo al otro extremo y a random height
                        enemy.setY((int) (Math.random() * getHeight()));
                    }*/
                }
                /*for (Good pacman : pacmans) {
                    pacman.tick(); //Tickea cada pacman de la lista
                    if (player.colission(pacman)) {
                        //player.setX(0);
                        //player.setY(getHeight()-100);
                        win(); //Sonido de score
                        score += 5; //Suma score
                        pacman.setX(getWidth() - 50); //Regresa a los pacman al otro extremo y random height
                        pacman.setY((int) (Math.random() * getHeight()));
                    }
                }*/
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
