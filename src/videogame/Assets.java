/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Pamela Lozano
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage player;
    public static BufferedImage shot;
    public static BufferedImage bomb;
    public static BufferedImage corazón;

    public static BufferedImage enemySprites;
    public static BufferedImage enemyStanding[];

    public static BufferedImage goodSprites;
    public static BufferedImage goodRight[];

    public static BufferedImage scoreSprites;
    public static BufferedImage extraScore[];

    public static BufferedImage playerSprites; // to store the sprites.
    public static BufferedImage playerStanding[];

    public static BufferedImage enemy;
    public static BufferedImage gameOver;
    public static BufferedImage gameWon;
    public static SoundClip loose;
    public static SoundClip end;
    public static SoundClip win;
    public static SoundClip música;
    public static SoundClip disparo;

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        shot = ImageLoader.loadImage("/images/shot.png");
        bomb = ImageLoader.loadImage("/images/bomb.png");
        gameOver = ImageLoader.loadImage("/images/game-over.png");
        gameWon = ImageLoader.loadImage("/images/game-won.png");
        playerSprites = ImageLoader.loadImage("/images/Nave.png");
        enemySprites = ImageLoader.loadImage("/images/Enemigo.png");
        corazón = ImageLoader.loadImage("/images/Corazón.png");

        //PLAYER
        //creating array of images before animations
        SpriteSheet playerSheet = new SpriteSheet(playerSprites);
        playerStanding = new BufferedImage[3];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 3; i++) {
            playerStanding[i] = playerSheet.crop(i * 108 + 34, 34, 99, 108);
        }

        //ENEMY
        //creating array of images before animations
        SpriteSheet enemySheet = new SpriteSheet(enemySprites);
        enemyStanding = new BufferedImage[3];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 3; i++) { //Tres animaciones
            enemyStanding[i] = enemySheet.crop(i * 108, 1, 87, 130);
        }

        //Vidas
        goodSprites = ImageLoader.loadImage("/images/corazon.png");
        SpriteSheet goodSheet = new SpriteSheet(goodSprites);
        goodRight = new BufferedImage[6];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 6; i++) { //6 Animaciones
            goodRight[i] = goodSheet.crop(i * 299, 0, 299, 258);
        }

        //MONEDA
        scoreSprites = ImageLoader.loadImage("/images/buenos.png");
        SpriteSheet scoreSheet = new SpriteSheet(scoreSprites);
        extraScore = new BufferedImage[6];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 6; i++) { //6 Animaciones
            extraScore[i] = scoreSheet.crop(i * 73, 0, 72, 90);
        }

        loose = new SoundClip("/sounds/loose.wav");
        end = new SoundClip("/sounds/gameOver.wav");
        win = new SoundClip("/sounds/winning.wav");
        música = new SoundClip("/sounds/Música.wav");
        disparo = new SoundClip("/sounds/Disparo.wav");
    }

    public static void musicDie() { //Game over sobre todos los personajes 
        música = null;
    }
}
