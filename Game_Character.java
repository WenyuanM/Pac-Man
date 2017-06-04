package Character;

import HelpingClass.Coordinate;
import HelpingClass.Constants;
import HelpingClass.Pathfinding;
import Map.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// Game Character class: to create and manage all the characters in the game

public class Game_Character {
    private Ghost[] _normalGhosts;
    private Ghost[] _smartGhosts;
    private PacMan _pacMan;
    private Map _map;

    // ============================= Constructor ========================

    /**
     * Constructor: to create PacMan
     * @param map the generated map
     */
    public Game_Character(Map map){
        _map = map;
        _pacMan = new PacMan(generateStartPos(Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL),new Coordinate(0,0),
                Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL);
    }

    /**
     * to create Pac Man and all the ghosts
     * @param map the generated map
     * @param numOfNormalGhosts the number of normal ghosts to generate
     * @param numofSmartGhost the number of smart ghosts to generate
     */
    public Game_Character(Map map,int numOfNormalGhosts,int numofSmartGhost){
        _map = map;
        _pacMan = new PacMan(generateStartPos(Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL),new Coordinate(0,0),
                Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL);

        Ghost[] ghostList = initialGhosts();
        _normalGhosts = new Ghost[numOfNormalGhosts];
        for(int i=0;i<numOfNormalGhosts;i++){
            _normalGhosts[i] = ghostList[i];
        }
        _smartGhosts = new Ghost[numofSmartGhost];
        for(int i=0;i<numofSmartGhost;i++){
            _smartGhosts[i] = ghostList[i + numOfNormalGhosts];
            _smartGhosts[i].set_type('S');
        }
    }

    // ================================== PRIVATE VARIABLES ======================================

    /**
     * to initialize all the ghosts as a Ghost array
     * @return an Ghost array
     */
    private Ghost[] initialGhosts(){
        Ghost[] ghosts = new Ghost[4];
        Ghost ghost1 = new Ghost(generateStartPos(Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL),new Coordinate(0,0),Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL,'N',
                Constants.RED_GHOST_DOWN_1,Constants.RED_GHOST_DOWN_2,Constants.RED_GHOST_LEFT_1,Constants.RED_GHOST_LEFT_2,
                Constants.RED_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        Ghost ghost2 = new Ghost(generateStartPos(Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL),new Coordinate(0,0),Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL,'N',
                Constants.PINK_GHOST_DOWN_1,Constants.PINK_GHOST_DOWN_2,Constants.PINK_GHOST_LEFT_1,Constants.PINK_GHOST_LEFT_2,
                Constants.PINK_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        Ghost ghost3 = new Ghost(generateStartPos(Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL),new Coordinate(0,0),Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL,'N',
                Constants.BLUE_GHOST_DOWN_1,Constants.BLUE_GHOST_DOWN_2,Constants.BLUE_GHOST_LEFT_1,Constants.BLUE_GHOST_LEFT_2,
                Constants.BLUE_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        Ghost ghost4 = new Ghost(generateStartPos(Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL),new Coordinate(0,0),Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL,'N',
                Constants.ORANGE_GHOST_DOWN_1,Constants.ORANGE_GHOST_DOWN_2,Constants.ORANGE_GHOST_LEFT_1,Constants.ORANGE_GHOST_LEFT_2,
                Constants.ORANGE_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        ghosts[0] = ghost1;
        ghosts[1] = ghost2;
        ghosts[2] = ghost3;
        ghosts[3] = ghost4;
        return ghosts;
    }

    /**
     * to random generate a direction
     * @param currentMovingDirection the current moving direction of the character
     * @param hasNeighbor whether the current node has neighbors or not
     * @return the new direction
     */
    private int randomDirectionGenerate(char currentMovingDirection,boolean[] hasNeighbor){
        Random generator = new Random();
        int number = 0;
        int notComingDirection = notComingDirection(currentMovingDirection);
        while(true){
            number = generator.nextInt(3);
            if(hasNeighbor[number] && (number != notComingDirection)){
                return findMatchingCode(number);
            }
        }
    }

    /**
     * to find the direction that is not same as the coming direction
     * @param currentMovingDirection the current moving direction of the character
     * @return the not coming direction
     */
    private int notComingDirection(char currentMovingDirection){
        switch(currentMovingDirection){
            case 'L':
                return 3;
            case 'R':
                return 2;
            case 'U':
                return 1;
            case 'D':
                return 0;
        }
        return -1;
    }

    /**
     * to check whether this character needs to generate a new direction or not
     * @param ghost the ghost that is checking
     * @return T if it needs a new direction; otherwise, F
     */
    private boolean checkWhetherGenerateRandomDirection(Ghost ghost){
        if(ghost.get_movingDirection() == '-'){
            // There is no moving direction been assigned
            return true;
        }
        if((ghost.get_row() == ghost.get_previousRow()) && (ghost.get_previousCol() == ghost.get_col())){
            return false;
        }
        if(_map.get_grid(ghost.get_row(),ghost.get_col()).get_neighborNum() > 2){
            return true;
        }
        return false;
    }

    /**
     * to find the other direction that is not same as the coming direction
     * @param hasNeighbor an boolean array indicates that whether the current node has neighbors
     * @param currentMovingDirection the current moving direction of the character
     * @return the other direction
     */
    private int findTheOtherWay(boolean[] hasNeighbor,char currentMovingDirection){
        int notComingDirection = notComingDirection(currentMovingDirection);
        int i=0;
        for(i=0;i<Constants.NEIGHBOR_NUM;i++){
            if(hasNeighbor[i] && (i != notComingDirection)){
                break;
            }
        }
        return findMatchingCode(i);
    }

    /**
     * to find the new direction by pathfinding the shortest path
     * @param startIndex the start position's grid index
     * @param endIndex the end position's grid index
     * @return the next moving direction
     */
    private int pathfindingCode(int startIndex,int endIndex){
        if(startIndex == endIndex){
            return -1;
        }
        Pathfinding pathfinding = new Pathfinding(_map);
        ArrayList<Grid> path = pathfinding.findShortestPath(startIndex,endIndex);
        Grid nextGrid = path.get(1);
        int nextIndex = nextGrid.get_index();
        int i = 0;
        if(startIndex == nextIndex - 1 || (startIndex == _map.get_mapCol() && nextIndex == 0)){
            i = 3;
        }
        else if(startIndex == nextIndex + 1 || (startIndex == 0 && nextIndex == _map.get_mapCol())){
            i = 2;
        }
        else if(startIndex == nextIndex + _map.get_mapCol()){
            i = 0;
        }
        else if(startIndex == nextIndex - _map.get_mapCol()){
            i = 1;
        }
        return findMatchingCode(i);
    }

    /**
     * to find the actual moving code number based on the direction
     * @param i the indicated direction
     * @return the actual moving code number
     */
    private int findMatchingCode(int i){
        switch(i){
            case 0: // up
                return 87;
            case 1: // down
                return 83;
            case 2: // left
                return 65;
            case 3: // right
                return 68;
        }
        return -1;
    }

    /**
     * to generate the start position based on input row and col
     * @param row the grid's row number
     * @param col the grid's col number
     * @return the center position of the grid
     */
    private Coordinate generateStartPos(int row,int col){
        Grid grid = _map.get_grid(row,col);
        Coordinate pos = grid.get_position();
        Coordinate size = grid.get_size();
        size = size.divide(2);
        return pos.add(size);
    }

    // ============================ PUBLIC METHODS ========================================

    /**
     * to restart all the characters
     */
    public void restartCharacters(){
        _pacMan.initialCharacter(generateStartPos(Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL),new Coordinate(0,0),Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL,'P');
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            _normalGhosts[i].initialCharacter(generateStartPos(Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL),new Coordinate(0,0),Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL,'N');
        }
        size = _smartGhosts.length;
        for(int i=0;i<size;i++){
            _smartGhosts[i].initialCharacter(generateStartPos(Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL),new Coordinate(0,0),Constants.GHOST_GRID_ROW,Constants.GHOST_GRID_COL,'S');
        }

    }

    /**
     * to update the normal ghost for each iteration
     */
    public void update_NormalGhosts(){
        int size = _normalGhosts.length;
        int code = -1;
        for(int i=0;i<size;i++){
            if(_normalGhosts[i].get_row() == Constants.GHOST_GRID_ROW && _normalGhosts[i].get_col() == Constants.GHOST_GRID_COL){
                // at the ghost starting point
                _normalGhosts[i].set_die(false);
            }
            // if the ghost is died
            if(_normalGhosts[i].get_die()){
                if(checkWhetherGenerateRandomDirection(_normalGhosts[i]) ||
                        (_map.get_grid(_normalGhosts[i].get_row(),_normalGhosts[i].get_col()).get_neighborNum() == 2
                                && !((_normalGhosts[i].get_row() == _normalGhosts[i].get_previousRow())
                                && (_normalGhosts[i].get_previousCol() == _normalGhosts[i].get_col())))) {
                    code = pathfindingCode(_normalGhosts[i].get_row() * _map.get_mapCol() + _normalGhosts[i].get_col(),
                            Constants.GHOST_GRID_ROW * _map.get_mapCol() + Constants.GHOST_GRID_COL);
                }
            }
            // if the ghost is alive
            else{
                // find out the next moving direction of the ghost
                char movingDirection = _normalGhosts[i].get_movingDirection();
                if(checkWhetherGenerateRandomDirection(_normalGhosts[i])){
                    boolean[] hasNeighbor = _map.get_grid(_normalGhosts[i].get_row(),_normalGhosts[i].get_col()).get_hasNeighbors();
                    code = randomDirectionGenerate(movingDirection, hasNeighbor);
                }
                else if(_map.get_grid(_normalGhosts[i].get_row(),_normalGhosts[i].get_col()).get_neighborNum() == 2
                        && !((_normalGhosts[i].get_row() == _normalGhosts[i].get_previousRow())
                        && (_normalGhosts[i].get_previousCol() == _normalGhosts[i].get_col()))) {
                    boolean[] hasNeighbor = _map.get_grid(_normalGhosts[i].get_row(), _normalGhosts[i].get_col()).get_hasNeighbors();
                    code = findTheOtherWay(hasNeighbor,movingDirection);
                }
            }
            // update the next position of the ghost based on the next moving direction
            Coordinate newPos = _normalGhosts[i].ifUpdate(code);
            int index = _map.findGridIndex(newPos,_normalGhosts[i].get_row(),_normalGhosts[i].get_col());
            int row = index / _map.get_mapCol();
            int col = index % _map.get_mapCol();
            char newMovingDirection = _normalGhosts[i].get_movingDirection();
            // consider if it moves at the transition grid
            if(!(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_LEFT_COL && newMovingDirection == 'L') &&
                    !(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_RIGHT_COL && newMovingDirection == 'R')){
                if(_map.updateValid(newPos,row,col)){
                    Coordinate gridLeftTop = _map.get_grid(row,col).get_position();
                    Coordinate gridSize = _map.get_grid(row,col).get_size();
                    Coordinate gridCenter = new Coordinate(gridLeftTop.getX() + gridSize.getX() / 2,
                            gridLeftTop.getY() + gridSize.getY() / 2);
                    newPos = _normalGhosts[i].adjustPos(newPos,gridCenter);
                    _normalGhosts[i].update(newPos,row,col);
                }
            }
            else{
                _normalGhosts[i].updateTransition(col,_map.get_mapCol());
            }
            code = -1;
        }
    }

    /**
     * to update the smart ghost for each iteration
     * @param gameMode the current game mode
     */
    public void update_SmartGhosts(boolean gameMode){
        int size = _smartGhosts.length;
        int code = -1;
        for(int i=0;i<size;i++){
            if(_smartGhosts[i].get_row() == Constants.GHOST_GRID_ROW && _smartGhosts[i].get_col() == Constants.GHOST_GRID_COL){
                // at the ghost starting point
                _smartGhosts[i].set_die(false);
            }
            // if the ghost dies
            if(_smartGhosts[i].get_die()){
                if(checkWhetherGenerateRandomDirection(_smartGhosts[i]) ||
                        (_map.get_grid(_smartGhosts[i].get_row(),_smartGhosts[i].get_col()).get_neighborNum() == 2
                                && !((_smartGhosts[i].get_row() == _smartGhosts[i].get_previousRow())
                                && (_smartGhosts[i].get_previousCol() == _smartGhosts[i].get_col())))) {
                    code = pathfindingCode(_smartGhosts[i].get_row() * _map.get_mapCol() + _smartGhosts[i].get_col(),
                            Constants.GHOST_GRID_ROW * _map.get_mapCol() + Constants.GHOST_GRID_COL);
                }
            }
            // if the game mode is the ghosts are tracing Pac Man
            else if(gameMode){
                if(checkWhetherGenerateRandomDirection(_smartGhosts[i]) ||
                        (_map.get_grid(_smartGhosts[i].get_row(),_smartGhosts[i].get_col()).get_neighborNum() == 2
                                && !((_smartGhosts[i].get_row() == _smartGhosts[i].get_previousRow())
                                && (_smartGhosts[i].get_previousCol() == _smartGhosts[i].get_col())))) {
                    code = pathfindingCode(_smartGhosts[i].get_row() * _map.get_mapCol() + _smartGhosts[i].get_col(),
                            _pacMan.get_row() * _map.get_mapCol() + _pacMan.get_col());
                }
            }
            // if the game mode is Pac Man is tracing the ghosts
            else{
                char movingDirection = _smartGhosts[i].get_movingDirection();
                if(checkWhetherGenerateRandomDirection(_smartGhosts[i])){
                    boolean[] hasNeighbor = _map.get_grid(_smartGhosts[i].get_row(),_smartGhosts[i].get_col()).get_hasNeighbors();
                    code = randomDirectionGenerate(movingDirection, hasNeighbor);
                }
                else if(_map.get_grid(_smartGhosts[i].get_row(),_smartGhosts[i].get_col()).get_neighborNum() == 2
                        && !((_smartGhosts[i].get_row() == _smartGhosts[i].get_previousRow())
                        && (_smartGhosts[i].get_previousCol() == _smartGhosts[i].get_col()))) {
                    boolean[] hasNeighbor = _map.get_grid(_smartGhosts[i].get_row(), _smartGhosts[i].get_col()).get_hasNeighbors();
                    code = findTheOtherWay(hasNeighbor,movingDirection);
                }
            }
            // to update the ghost's moving direction
            Coordinate newPos = _smartGhosts[i].ifUpdate(code);
            int index = _map.findGridIndex(newPos,_smartGhosts[i].get_row(),_smartGhosts[i].get_col());
            int row = index / _map.get_mapCol();
            int col = index % _map.get_mapCol();
            char newMovingDirection = _smartGhosts[i].get_movingDirection();
            // if the ghost is at transition grid
            if(!(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_LEFT_COL && newMovingDirection == 'L') &&
                    !(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_RIGHT_COL && newMovingDirection == 'R')){
                if(_map.updateValid(newPos,row,col)){
                    Coordinate gridLeftTop = _map.get_grid(row,col).get_position();
                    Coordinate gridSize = _map.get_grid(row,col).get_size();
                    Coordinate gridCenter = new Coordinate(gridLeftTop.getX() + gridSize.getX() / 2,
                            gridLeftTop.getY() + gridSize.getY() / 2);
                    newPos = _smartGhosts[i].adjustPos(newPos,gridCenter);
                    _smartGhosts[i].update(newPos,row,col);
                }
            }
            else{
                _smartGhosts[i].updateTransition(col,_map.get_mapCol());
            }
            code = -1;
        }
    }

    /**
     * to update the Pac Man in each iteration
     * @param code the inputting moving direction from users
     * @return the new earned game points
     */
    public int update_PacMan(int code){
        int gamePoint = 0;
        // update the pacman's moving direction
        Coordinate newPosPacMan = _pacMan.ifUpdate(code);
        int index = _map.findGridIndex(newPosPacMan,_pacMan.get_row(),_pacMan.get_col());
        int row = index / _map.get_mapCol();
        int col = index % _map.get_mapCol();
        char movingDirection = _pacMan.get_movingDirection();
        if(!(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_LEFT_COL && movingDirection == 'L') &&
                !(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_RIGHT_COL && movingDirection == 'R')){
            if(_map.updateValid(newPosPacMan,row,col)){
                Coordinate gridLeftTop = _map.get_grid(row,col).get_position();
                Coordinate gridSize = _map.get_grid(row,col).get_size();
                Coordinate gridCenter = new Coordinate(gridLeftTop.getX() + gridSize.getX() / 2,
                        gridLeftTop.getY() + gridSize.getY() / 2);
                newPosPacMan = _pacMan.adjustPos(newPosPacMan,gridCenter);
                _pacMan.update(newPosPacMan,row,col);
            }
        }
        // if pacman is at transition grid
        else{
            gamePoint += _map.updateFood(row,col);
            _pacMan.updateTransition(col,_map.get_mapCol());
        }
        gamePoint += _map.updateFood(_pacMan.get_row(),_pacMan.get_col());
        return gamePoint;
    }

    /**
     * to check whether the pacman is collided with any other characters (death)
     * @return T if it dies; F if it is alive
     */
    public boolean checkPacManDies(){
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            if(_pacMan.checkCollide(_normalGhosts[i]) && !_normalGhosts[i].get_die()){
                _pacMan.set_die(true);
                return true;
            }
        }
        size = _smartGhosts.length;
        for(int i=0;i<size;i++){
            if(_pacMan.checkCollide(_smartGhosts[i]) && !_smartGhosts[i].get_die()){
                _pacMan.set_die(true);
                return true;
            }
        }
        return false;   // doesn't die
    }

    /**
     * to check all the ghosts die or alive
     * @return the number of died ghosts in this iteration
     */
    public int checkGhostsDies(){
        int number = 0;
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            if(_pacMan.checkCollide(_normalGhosts[i])){
                _normalGhosts[i].set_die(true);
                number++;
            }
        }
        size = _smartGhosts.length;
        for(int i=0;i<size;i++){
            if(_pacMan.checkCollide(_smartGhosts[i])){
                _smartGhosts[i].set_die(true);
                number++;
            }
        }
        return number;
    }

    /**
     * to draw all the characters
     * @param shape the Graphics object
     * @param gameMode the game mode
     */
    public void drawCharacters(Graphics shape,boolean gameMode){
        _pacMan.draw(shape);
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            _normalGhosts[i].draw(shape,gameMode);
        }
        size = _smartGhosts.length;
        for(int i=0;i<size;i++) {
            _smartGhosts[i].draw(shape,gameMode);
        }
    }
}
