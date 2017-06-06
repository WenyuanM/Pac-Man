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

public class Ghost extends Character{
    private int _count;

    // =================================== Constructor ===========================================

    /**
     * to initialize the Ghost with given image names as STRING type
     * @param imageNames the given image names as STRING type
     */
    public Ghost(String... imageNames){
        _count = 0;
        initialImages(imageNames);
        initialCharacter(new Coordinate(0,0),new Coordinate(0,0),0,0,'N');
    }

    /**
     * to initialize the Ghost with given position, speed, row, col, name, and image names as STRING type
     * @param pos the given position
     * @param speed the given speed
     * @param row the given row
     * @param col the given col
     * @param name the given name as CHAR type
     * @param imageNames the given image names as STRING type
     */
    public Ghost(Coordinate pos,Coordinate speed,int row,int col,char name,String...imageNames){
        _count = 0;
        initialImages(imageNames);
        initialCharacter(pos,speed,row,col,name);
    }

    /**
     * to draw the ghost image
     * @param shape the given graphics object
     * @param gameMode the current game mode
     */
    void draw(Graphics shape, boolean gameMode){
        BufferedImage image = get_images(gameMode);
        int size = Constants.CHARACTER_IMAGE_SIZE;
        shape.drawImage(image,(int)(_position.getX()-size/2),(int)(_position.getY()-size/2),size,size,null);
    }

    /**
     * to get the corresponding image of the character based on the current game mode and its moving direction
     * @param gameMode the current game mode
     * @return the corresponding image as BUFFEREDIMAGE type
     */
    private BufferedImage get_images(boolean gameMode){
        int remainder = _count % 2;
        _count++;
        // if the character dies
        if(_die){
            if(remainder == 0){
                return _images[7];
            }
            else{
                return _images[8];
            }
        }
        // if the game mode is F
        if(!gameMode){
            if(remainder == 0){
                return _images[5];
            }
            else{
                return _images[6];
            }
        }
        // Moving Down or Up or stop
        if(_movingDirection == '-' || _movingDirection == 'D' || _movingDirection == 'U'){
            if(remainder == 0){
                return _images[0];
            }
            else{
                return _images[1];
            }
        }
        // Moving Left
        if(_movingDirection == 'L'){
            if(remainder == 0){
                return _images[2];
            }
            else{
                return _images[3];
            }
        }
        // Moving Right
        if(_movingDirection == 'R'){
            return _images[4];
        }
        return null;
    }
}
