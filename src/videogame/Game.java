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
        lives = 3;
        score=0;
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
     //   tamBuenos = (int) (Math.random() * 3 + 8); //b-a+1 NUMERO DE ENEMIGOS
     
        tamMalos = 24; //NUMERO DE ENEMIGOS
        player = new Player((getWidth() - 100) / 2, 280, 1, 15, 10, this); //Player posicionen medio
        RW = new RandW(this);//Pasarle el game a read y write

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                //Se agregan los enemigos con las coordenadas de un cuadro 6x4
                Enemy alien = new Enemy(150 + 18 * j, 5 + 18 * i, 1, 15, 15, this);
                enemys.add(alien);
            }
        }

        display.getJframe().addKeyListener(keyManager);
        System.out.println("Vidas: " + lives);

        //Timer para que cada 0.4s se disparen dos bombas

        Timer timer = new Timer();
        timer.schedule(new BombTask(this), 0, 400);
        //Se puede disminuir la dificultad aumentando el 400
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
   private boolean noEnemys(){ //Checa si se acabaron los enemigos cada tick
        for(Enemy enemy : enemys){
            if(enemy.isVisible()){
                return false;
            }
        }
        return true;
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

            if (lives > 0&&!noEnemys()) { //Si el player esta vivo pintar todo
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

                g.setColor(Color.white);
                g.drawString("Score: " + Integer.toString(score), getWidth() - 80,
                    20); //Pinta score                
                g.drawString("Lives: " + Integer.toString(lives), getWidth() - 80,
                        45);//Pinta lives
                g.setColor(Color.green);
                g.drawLine(0, 290, getWidth(), 290);//Pinta la linea
            } else {
                if(noEnemys()){
                 g.drawImage(Assets.gameWon, 0, 0, width, height, null);
                } else {
                g.drawImage(Assets.gameOver, 0, 0, width, height, null);
                }
                //Pinta game over
            }

            bs.show();
            g.dispose();

        }

    }
    
 
    public void border(int direction) { 
    //El enemigo llego al borde cambia de dirección

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
        
        //Numero random del 1 al 23
        int rand = (int) (Math.random() * 22 + 1);

        //  Enemigos Random que disparan
        Enemy enemy = enemys.get(rand);
      
        while(!enemy.isVisible()){ //Si ya no es visible que escoja otro
        rand = (int) (Math.random() * 22 + 1);
        enemy = enemys.get(rand);
        }
        //Bombas 
        Bomb bomb = new Bomb(enemy.getX(), enemy.getY(), 1, 3, 5, this);


        bombs.add(bomb);
        }
    }

    public void shoot() {//Disparo del player
        Shot last=null;
        if(shots.size()!=0){
        last = shots.get(shots.size()-1);  
        } else{
        Shot shot = new Shot(player.getX() + 6, player.getY(), 1, 3, 7, this);
        shots.add(shot); 
        }
        if(last!=null&&!last.isVisible()){
        Shot shot = new Shot(player.getX() + 6, player.getY(), 1, 3, 7, this);
        shots.addLast(shot); 
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {
        if (lives > 0&& !noEnemys()) { //Si se acaba que todo se deje de mover
            keyManager.tick();
            this.pause = keyManager.pause;

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
                }

                for (Bomb bomb : bombs) {
                    bomb.tick();
                    //Si le cae una bomba al player
                    if (player.colission(bomb) && bomb.isVisible()) {
                        //La bomba ya no es visible
                        bomb.die();
                        //Vida menos
                        lives--;
                    }
                }

                for (Shot shot : shots) {
                    for (Enemy enemy : enemys) {
                        //Checa si el shot choco con algun enemigo
                        //Si estan visibles siguen en el juego
                        if (shot.colission(enemy) && shot.isVisible() && enemy.isVisible()) {
                            //Si chocan ya no se pintan
                            enemy.die();
                            shot.die();
                            //Se suma score
                            score+=5;
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
