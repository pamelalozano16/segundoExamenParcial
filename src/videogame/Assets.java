/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public static BufferedImage enemySprites;
    public static BufferedImage enemyLeft[];

    public static BufferedImage goodSprites;
    public static BufferedImage goodRight[];

    public static BufferedImage playerSprites; // to store the sprites.
    public static BufferedImage playerUp[]; // pictures to go up.
    public static BufferedImage playerLeft[]; // pictures to go left.
    public static BufferedImage playerDown[]; // pictures to go down.
    public static BufferedImage playerRight[]; // pictures to go right.
    public static BufferedImage playerStanding[];

    public static BufferedImage enemy;
    public static BufferedImage gameOver;
    public static BufferedImage gameWon;
    public static SoundClip loose;
    public static SoundClip end;
    public static SoundClip win;

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        player = ImageLoader.loadImage("/images/player.png");
        enemy = ImageLoader.loadImage("/images/alien.png");
        shot = ImageLoader.loadImage("/images/shot.png");
        bomb = ImageLoader.loadImage("/images/bomb.png");
        gameOver = ImageLoader.loadImage("/images/game-over.png");
        gameWon = ImageLoader.loadImage("/images/game-won.png");
        /* playerSprites = ImageLoader.loadImage("/images/player.png");*/

        //PLAYER
        // creating array of images before animations
        /* SpriteSheet playerSheet = new SpriteSheet(playerSprites);
        playerUp = new BufferedImage[3];
        playerLeft = new BufferedImage[3];
        playerDown = new BufferedImage[3];
        playerRight = new BufferedImage[3];
        playerStanding = new BufferedImage[3];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 3; i++) {
            playerDown[i] = playerSheet.crop(i * 42, 0, 42, 51);
            playerLeft[i] = playerSheet.crop(i * 42, 51, 42, 51);
            playerRight[i] = playerSheet.crop(i * 42, 103, 42, 50);
            //Los sprites de la derecha y de standing estan muy cerca a los de arriba, 
            //entonces le agregue un pixel a la "y" y le quite uno a la altura
            playerUp[i] = playerSheet.crop(i * 42, 153, 42, 51);
            playerStanding[i] = playerSheet.crop(i * 42, 204, 42, 50);
        }*/

 /*ENEMY
        enemySprites = ImageLoader.loadImage("/images/enemy.png");
        SpriteSheet enemySheet = new SpriteSheet(enemySprites);
        enemyLeft = new BufferedImage[2];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 2; i++) { //Dos animaciones
            enemyLeft[i] = enemySheet.crop(i * 135, 0, 133, 221);
        }*/
        //MONEDA
        goodSprites = ImageLoader.loadImage("/images/buenos.png");
        SpriteSheet goodSheet = new SpriteSheet(goodSprites);
        goodRight = new BufferedImage[6];
        // cropping the pictures from the sheet into the array
        for (int i = 0; i < 6; i++) { //6 Animaciones
            goodRight[i] = goodSheet.crop(i * 73, 0, 72, 90);
        }

        loose = new SoundClip("/sounds/loose.wav");
        end = new SoundClip("/sounds/gameOver.wav");
        win = new SoundClip("/sounds/winning.wav");
    }

    public static void changeBackground() { //Game over sobre todos los personajes 
        playerSprites = null;
        goodSprites = null;
        enemySprites = null;
    }
}
