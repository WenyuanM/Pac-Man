import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Nancy on 2017/5/28.
 */
public class Ghost extends Character{
    public Ghost(String... imageNames){
        initialImages(imageNames);
        initialCharacter(new Coordinate(0,0),new Coordinate(0,0),0,0,'N');
    }
    public Ghost(Coordinate pos,Coordinate speed,int row,int col,char name,String...imageNames){
        initialImages(imageNames);
        initialCharacter(pos,speed,row,col,name);
    }

    public BufferedImage get_images(){
        if(_movingDirection == '-' || _movingDirection == 'D' || _movingDirection == 'U'){
            return _images[0];
        }
        if(_movingDirection == 'L'){
            return _images[2];
        }
        if(_movingDirection == 'R'){
            return _images[4];
        }
        return null;
    }

    public void draw(Graphics shape){
        BufferedImage image = get_images();
        int size = Constants.CHARACTER_IMAGE_SIZE;
        shape.drawImage(image,(int)(_position.getX()-size/2),(int)(_position.getY()-size/2),size,size,null);
    }
}
