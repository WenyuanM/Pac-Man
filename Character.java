package Character;

import HelpingClass.Coordinate;
import HelpingClass.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Character Class (Super class of Pac Man and Ghost class): to manage a character in this game generally

public class Character {
    // =========================== PRIVATE VARIABLES =====================================
    private char _type; // P = PAC MAN , N = NORMAL GHOST , S = SMART GHOST , - = UNDEFINED
    private Coordinate _speed;
    private int _row;
    private int _col;
    private int _previousRow;
    private int _previousCol;
    private String _status;

    // =========================== PACKAGE-PRIVATE VARIABLES ===============================
    BufferedImage[] _images;
    char _movingDirection;  // L = LEFT || R = RIGHT || U = UP || D = DOWN || - = UNDEFINED
    Coordinate _position;
    boolean _die;

    // ================================ Constructor ========================================

    /**
     * Constructor: to initial the character
     */
    public Character(){
        initialCharacter(new Coordinate(0,0),new Coordinate(0,0),0,0,'-');
    }

    /**
     * Constructor: to initial the character with given position, speed, row, col, and name
     * @param pos the given position in COORDINATE type
     * @param speed the given speed in COORDINATE type
     * @param row the given row in INT type
     * @param col the given col in INT type
     * @param name the given name in CHAR type
     */
    public Character(Coordinate pos,Coordinate speed,int row,int col,char name){
        initialCharacter(pos,speed,row,col,name);
    }

    // =================================== Public Methods ====================================

    /**
     * to get the whole information into a STRING type and return
     * @return all information in STRING type
     */
    public String to_String(){
        return "Type: " +_type + ", Moving Direction: " + _movingDirection + ", Speed: " +
                _speed + ", Position: " + _position;
    }

    // ============================= PACKAGE-PRIVATE Methods ================================

    /**
     * to initial the images based on given image names
     * @param imageNames the image names as STRING type
     */
    void initialImages(String... imageNames){
        int size = imageNames.length;
        try{
            _images = new BufferedImage[size];
            for(int i=0;i<size;i++){
                _images[i] = ImageIO.read(new File(imageNames[i]));

            }
        }
        catch(IOException exception){
            System.out.println("Load Pac man image FAIL!");
        }

    }

    /**
     * to initial the character with given position, speed, row, col, and name
     * @param pos the given position in COORDINATE type
     * @param speed the given speed in COORDINATE type
     * @param row the given row in INT type
     * @param col the given col in INT type
     * @param name the given name in CHAR type
     */
    void initialCharacter(Coordinate pos, Coordinate speed, int row, int col, char name){
        _type = name;
        _movingDirection = '-';
        _status = "-";
        _position = pos;
        _speed = speed;
        _row = row;
        _col = col;
        _previousRow = row;
        _previousCol = col;
        if(_type == 'S'){
            _status = "Pathfinding";
        }
        else{
            _status = "Moving";
        }
        _die = false;
    }

    /**
     * to get the new position if updates the character
     * @param code the input code indicating the next moving direction
     * @return the new position of the character in COORDINATE type
     */
    Coordinate ifUpdate(int code){
        if(updateMovingDirection(code) && updateSpeed()){
            return _position.add(_speed);
        }
        return _position;
    }

    /**
     * to adjust the position of the character after updating in order to make the character always at the center of the road
     * @param newCharacterPos the new character position
     * @param mapCenterPos the correct center position of the current grid in the map
     * @return the adjusted position of the character in COORDINATE type
     */
    Coordinate adjustPos(Coordinate newCharacterPos, Coordinate mapCenterPos){
        if(_movingDirection == 'L' || _movingDirection == 'R' || _movingDirection == '-'){
            newCharacterPos.setY(mapCenterPos.getY());
        }
        if(_movingDirection == 'U' || _movingDirection == 'D' || _movingDirection == '-'){
            newCharacterPos.setX(mapCenterPos.getX());
        }
        return newCharacterPos;
    }

    /**
     * to update the character based on input position, and the related row and col of that grid in the map
     * @param newPosition the new position of the character
     * @param row the row number of the grid related to the position point
     * @param col the col number of the grid related to the position point
     */
    void update(Coordinate newPosition, int row, int col){
        _position = newPosition;
        _previousRow = _row;
        _previousCol = _col;
        _row = row;
        _col = col;
    }

    /**
     * to update the character if it is in a transition grid
     * @param col the col number of the current transition grid
     * @param maxCol the total col in the map
     */
    void updateTransition(int col, int maxCol){
        if(col == Constants.TRANSITION_LEFT_COL){
            _position.setX(Constants.GAMEFRAME_FRAME_WIDTH - Constants.SIDEBAR_WIDTH - Constants.CHARACTER_IMAGE_SIZE / 2);
            _col = maxCol - 1;
        }
        else{
            _position.setX(Constants.CHARACTER_IMAGE_SIZE / 2);
            _col = 0;
        }
    }

    /**
     * to check whether there is any collide between this character and the input character
     * @param other the input character to check collision
     * @return return T if there is a collision between them; otherwise, return F
     */
    boolean checkCollide(Character other){
        double distance = _position.distance(other.get_position());
        return distance <= Constants.CHARACTER_IMAGE_SIZE;
    }

    // ====================================== Accessor =========================================

    /**
     * to set the row number of the inside grid
     * @param row the new row number
     */
    public void set_row(int row){
        _row = row;
    }

    /**
     * to set the col number of the inside grid
     * @param col the new col number
     */
    public void set_col(int col){
        _col = col;
    }

    /**
     * to set the new moving direction
     * @param movingDirection the new moving direction of the character
     */
    public void set_movingDirection(char movingDirection){
        _movingDirection = movingDirection;
    }

    /**
     * to set the die information of the character and also set the moving direction of the character into UNDEFINED
     * @param die the new die info in BOOLEAN type
     */
    void set_die(boolean die){
        _die = die;
        _movingDirection = '-';
        if(die){
            _status = "Die. Go back to the starting position.";
        }
        else{
            if(_type == 'S'){
                _status = "Pathfinding.";
            }
            else{
                _status = "Moving";
            }
        }
    }

    /**
     * to set the new type of the character
     * @param name the new type
     */
    void set_type(char name) {
        _type = name;
        if(_type == 'S'){
            _status = "Pathfinding";
        }
        else{
            _status = "Moving";
        }
    }

    // ====================================== Mutator =========================================

    /**
     * to get the current position of the character
     * @return the current position in COORDINATE type
     */
    private Coordinate get_position(){
        return _position;
    }

    /**
     * to get the current row number that the character is in
     * @return the current row number
     */
    int get_row(){
        return _row;
    }

    /**
     * to get the current col number that the character is in
     * @return the current col number
     */
    int get_col(){
        return _col;
    }

    /**
     * to get the previous row number that the character is in
     * @return the previous row number
     */
    int get_previousRow(){
        return _previousRow;
    }

    /**
     * to get the previous col number that the character is in
     * @return the previous col number
     */
    int get_previousCol(){
        return _previousCol;
    }

    /**
     * to get the current die info of the character
     * @return the current die info in BOOLEAN type
     */
    boolean get_die(){
        return _die;
    }

    /**
     * to get the current moving direction
     * @return the current moving direction
     */
    char get_movingDirection() {return _movingDirection;}

    /**
     * to get the status of the character
     * @return the current status
     */
    String get_status(){return _status;}

    // =================================== Private Methods ===================================

    /**
     * to update the moving direction based on input code
     * @param code the input code indicating the direction (L,R,U,D)
     * @return return T if the movingDirection is updated successfully; otherwise, return F
     */
    private boolean updateMovingDirection(int code){
        switch(code){
            // UP
            case 87:
            case 38:
                _movingDirection = 'U';
                return true;
            // LEFT
            case 65:
            case 37:
                _movingDirection = 'L';
                return true;
            // DOWN
            case 83:
            case 40:
                _movingDirection = 'D';
                return true;
            // RIGHT
            case 68:
            case 39:
                _movingDirection = 'R';
                return true;
            // keep the moving direction as the last one
            case -1:
                return true;
            default:
                _movingDirection = '-';
                return false;
        }
    }

    /**
     * to update the speed based on current movingDirection
     * @return return T if the speed is updated successfully; otherwise, return F
     */
    private boolean updateSpeed(){
        double speed = 0;
        if(_type == 'P'){
            speed = Constants.PACMAN_SPEED;
        }
        else{
            speed = Constants.GHOST_SPEED;
        }
        switch(_movingDirection){
            case '-':
                // UNDEFINED
                _speed.setXY(0,0);
                return true;
            case 'L':
                // LEFT
                _speed.setXY(-speed,0);
                return true;
            case 'R':
                // RIGHT
                _speed.setXY(speed,0);
                return true;
            case 'U':
                // UP
                _speed.setXY(0,-speed);
                return true;
            case 'D':
                // DOWN
                _speed.setXY(0,speed);
                return true;
            default:
                _speed.setXY(0,0);
                System.out.println("The Moving Direction is not valid!");
                return false;
        }
    }
}
