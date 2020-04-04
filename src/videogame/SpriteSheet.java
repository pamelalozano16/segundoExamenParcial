/*
 * Pamela Lozano A01176970
 * Javier Sanchez A00517066
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Javier
 */
public class SpriteSheet {

    private BufferedImage sheet;

    /**
     * Create a new sprite sheet.
     *
     * @param sheet the <code>image</code> with the sprites.
     */
    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    /**
     * Crop a sprite from the sprite sheet.
     *
     * @param x an <code>int</code> value with the x coordinate.
     * @param y an <code>int</code> value with the y coordinate.
     * @param width an <code>int</code> value with the width of the sprite.
     * @param height an <code>int</code> value with the height of the sprite.
     * @return
     */
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
