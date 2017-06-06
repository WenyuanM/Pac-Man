package Character;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

import HelpingClass.Constants;
import HelpingClass.Coordinate;
import java.awt.*;
import java.awt.image.BufferedImage;

// Pac Man Class (Subclass of Character): to manage some specific features of Pac Man

public class PacMan extends Character {
    // ====================================== Constructor =========================================

    /**
     * Constructor: to initialize the Pac Man character
     */
    public PacMan() {
        initialImages(Constants.PAC_MAN_FACING_UP, Constants.PAC_MAN_FACING_DOWN,
                Constants.PAC_MAN_FACING_LEFT, Constants.PAC_MAN_FACING_RIGHT);
        initialCharacter(new Coordinate(0, 0), new Coordinate(0, 0), 0, 0, 'P');
    }

    /**
     * Constructor (package-private): to initialize the Pac Man character with given position, speed, row and col
     * @param pos the given position in COORDINATE type
     * @param speed the given speed in COORDINATE type
     * @param row the given row
     * @param col the given col
     */
    PacMan(Coordinate pos, Coordinate speed, int row, int col) {
        initialImages(Constants.PAC_MAN_FACING_UP, Constants.PAC_MAN_FACING_DOWN,
                Constants.PAC_MAN_FACING_LEFT, Constants.PAC_MAN_FACING_RIGHT);
        initialCharacter(pos, speed, row, col, 'P');
    }

    /**
     * to draw the character image
     * @param shape the given graphics object
     */
    void draw(Graphics shape){
        BufferedImage image = get_images();
        int size = Constants.CHARACTER_IMAGE_SIZE;
        shape.drawImage(image,(int)(_position.getX()-size/2),(int)(_position.getY()-size/2),size,size,null);
    }

    /**
     * to get the corresponding image based on the current moving direction
     * @return the corresponding image in BUFFEREDIMAGE type
     */
    private BufferedImage get_images(){
        if(_movingDirection == '-' || _movingDirection == 'R'){
            return _images[3];
        }
        if(_movingDirection == 'L'){
            return _images[2];
        }
        if(_movingDirection == 'U'){
            return _images[0];
        }
        if(_movingDirection == 'D'){
            return _images[1];
        }
        return null;
    }
}