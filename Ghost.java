import java.awt.*;
import java.awt.image.BufferedImage;

public class Ghost extends Character{
    private int _count;
    public Ghost(String... imageNames){
        _count = 0;
        initialImages(imageNames);
        initialCharacter(new Coordinate(0,0),new Coordinate(0,0),0,0,'N');
    }
    public Ghost(Coordinate pos,Coordinate speed,int row,int col,char name,String...imageNames){
        _count = 0;
        initialImages(imageNames);
        initialCharacter(pos,speed,row,col,name);
    }

    public BufferedImage get_images(boolean gameMode){
        int remainder = _count % 2;
        _count++;
        if(_die){
            if(remainder == 0){
                return _images[7];
            }
            else{
                return _images[8];
            }
        }
        if(!gameMode){
            if(remainder == 0){
                return _images[5];
            }
            else{
                return _images[6];
            }
        }
        if(_movingDirection == '-' || _movingDirection == 'D' || _movingDirection == 'U'){
            if(remainder == 0){
                return _images[0];
            }
            else{
                return _images[1];
            }
        }
        if(_movingDirection == 'L'){
            if(remainder == 0){
                return _images[2];
            }
            else{
                return _images[3];
            }
        }
        if(_movingDirection == 'R'){
            return _images[4];
        }
        return null;
    }

    public void draw(Graphics shape,boolean gameMode){
        BufferedImage image = get_images(gameMode);
        int size = Constants.CHARACTER_IMAGE_SIZE;
        shape.drawImage(image,(int)(_position.getX()-size/2),(int)(_position.getY()-size/2),size,size,null);
    }
}
