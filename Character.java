import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * to store all the information of the character, such as image, moving direction, position, speed, name and type
 */
public class Character {
    private char _type; // P = PAC MAN || N = NORMAL GHOST || S = SMART GHOST || - = UNDEFINED
    private BufferedImage _images;
    private char _movingDirection;  // L = LEFT || R = RIGHT || U = UP || D = DOWN || - = UNDEFINED
    private Coordinate _position;
    private Coordinate _speed;
    private int _row;
    private int _col;

    // =========================== Constructor =====================
    public Character(){
        initialCharacter(new Coordinate(0,0),new Coordinate(0,0),0,0,'-');
    }

    public Character(Coordinate pos,Coordinate speed,int row,int col,char name){
        initialCharacter(pos,speed,row,col,name);
    }

    // ============================ Public Methods =========================

    public void initialCharacter(Coordinate pos,Coordinate speed,int row,int col,char name){
        try{
            _images = ImageIO.read(new File(Constants.PAC_MAN_FACING_RIGHT));
        }
        catch(IOException exception){
            System.out.println("Load Pac man image FAIL!");
        }
        _type = name;
        _movingDirection = '-';
        _position = pos;
        _speed = speed;
        _row = row;
        _col = col;
    }

    public boolean update(int code){
        if(updateMovingDirection(code) && updateSpeed()){
            _position = _position.add(_speed);
            return true;
        }
        return false;
    }

    public BufferedImage get_images(){
        return _images;
    }

    public Coordinate get_position(){
        return _position;
    }

    public int get_row(){
        return _row;
    }

    public int get_col(){
        return _col;
    }

    public void set_row(int row){
        _row = row;
    }

    public void set_col(int col){
        _col = col;
    }

    public void printCharacterInfo(){
        System.out.println("Type: " +_type);
        System.out.println("Moving Direction: " + _movingDirection);
        System.out.print("Speed: " + _speed);
        System.out.print("Position: " + _position);
    }

    // ========================== Private Methods =========================
    private boolean updateMovingDirection(int code){
        switch(code){
            case 87:
            case 38:
                _movingDirection = 'U';
                return true;
            case 65:
            case 37:
                _movingDirection = 'L';
                return true;
            case 83:
            case 40:
                _movingDirection = 'D';
                return true;
            case 68:
            case 39:
                _movingDirection = 'R';
                return true;
            default:
                _movingDirection = '-';
                return false;
        }
    }

    private boolean updateSpeed(){
        switch(_movingDirection){
            case '-':
                // UNDEFINED
                _speed.setXY(0,0);
                return true;
            case 'L':
                // LEFT
                _speed.setXY(-Constants.SPEED,0);
                return true;
            case 'R':
                // RIGHT
                _speed.setXY(Constants.SPEED,0);
                return true;
            case 'U':
                // UP
                _speed.setXY(0,-Constants.SPEED);
                return true;
            case 'D':
                // DOWN
                _speed.setXY(0,Constants.SPEED);
                return true;
            default:
                _speed.setXY(0,0);
                System.out.println("The Moving Direction is not valid!!!!!!!!!!!!");
                return false;
        }
    }
}
