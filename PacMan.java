import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * to store all the information of the character, such as image, moving direction, position, speed, name and type
 */
public class PacMan extends Character {
    // =========================== Constructor =====================
    public PacMan() {
        initialImages(Constants.PAC_MAN_FACING_UP, Constants.PAC_MAN_FACING_DOWN,
                Constants.PAC_MAN_FACING_LEFT, Constants.PAC_MAN_FACING_RIGHT);
        initialCharacter(new Coordinate(0, 0), new Coordinate(0, 0), 0, 0, 'P');
    }

    public PacMan(Coordinate pos, Coordinate speed, int row, int col) {
        initialImages(Constants.PAC_MAN_FACING_UP, Constants.PAC_MAN_FACING_DOWN,
                Constants.PAC_MAN_FACING_LEFT, Constants.PAC_MAN_FACING_RIGHT);
        initialCharacter(pos, speed, row, col, 'P');
    }

    public BufferedImage get_images(){
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

    public void draw(Graphics shape){
        BufferedImage image = get_images();
        int size = Constants.CHARACTER_IMAGE_SIZE;
        shape.drawImage(image,(int)(_position.getX()-size/2),(int)(_position.getY()-size/2),size,size,null);
    }
}