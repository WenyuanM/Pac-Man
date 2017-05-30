import java.awt.*;
import java.util.Random;

/**
 * to store all the characters = PAC MAN + GHOSTS
 */
public class Game_Character {
    private Ghost[] _normalGhosts;
    private Ghost[] _smartGhosts;
    private PacMan _pacMan;
    private Map _map;

    // ============================= Constructor ========================

    public Game_Character(Map map){
        _map = map;
        _pacMan = new PacMan(generateStartPos(Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL),new Coordinate(0,0),
                Constants.PACMAN_GRID_ROW,Constants.PACMAN_GRID_COL);
    }

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

    public void update_NormalGhosts(){
        int size = _normalGhosts.length;
        int code = -1;
        for(int i=0;i<size;i++){
            System.out.println("Ghost " + i + ") ");
            char movingDirection = _normalGhosts[i].get_movingDirection();
            System.out.println("Moving direction: " + movingDirection);
            int currentRow = _normalGhosts[i].get_row();
            int currentCol = _normalGhosts[i].get_col();
            System.out.println("Row: " + currentRow + ", Col: " + currentCol);
            if(checkWhetherGenerateRandomDirection(_normalGhosts[i])){
                System.out.println("Random Direction");
                boolean[] hasNeighbor = _map.get_grid(_normalGhosts[i].get_row(),_normalGhosts[i].get_col()).get_hasNeighbors();
                code = randomDirectionGenerate(movingDirection, hasNeighbor);
                System.out.println("The new code: " + code);
            }
            else if(_map.get_grid(_normalGhosts[i].get_row(),_normalGhosts[i].get_col()).get_neighborNum() == 2
                    && !((_normalGhosts[i].get_row() == _normalGhosts[i].get_previousRow())
                    && (_normalGhosts[i].get_previousCol() == _normalGhosts[i].get_col()))) {
                System.out.println("Choose the other way");
                boolean[] hasNeighbor = _map.get_grid(_normalGhosts[i].get_row(), _normalGhosts[i].get_col()).get_hasNeighbors();
                code = findTheOtherWay(hasNeighbor,movingDirection);
                System.out.println("The new code: " + code);
            }
            Coordinate newPos = _normalGhosts[i].ifUpdate(code);
            int index = _map.findGridIndex(newPos,_normalGhosts[i].get_row(),_normalGhosts[i].get_col());
            int row = index / _map.get_mapCol();
            int col = index % _map.get_mapCol();
            char newMovingDirection = _normalGhosts[i].get_movingDirection();
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
        System.out.println("==================");
    }

    public int update_PacMan(int code){
        int gamePoint = 0;
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
        else{
            gamePoint += _map.updateFood(row,col);
            _pacMan.updateTransition(col,_map.get_mapCol());
        }
        gamePoint += _map.updateFood(_pacMan.get_row(),_pacMan.get_col());
        return gamePoint;
    }

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

    public boolean checkGhostsDies(){
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            if(_pacMan.checkCollide(_normalGhosts[i])){
                _normalGhosts[i].set_die(true);
                return true;
            }
        }
        size = _smartGhosts.length;
        for(int i=0;i<size;i++){
            if(_pacMan.checkCollide(_normalGhosts[i])){
                _normalGhosts[i].set_die(true);
                _pacMan.set_die(true);
                return true;
            }
        }
        return false;   // doesn't die
    }

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

    private Coordinate generateStartPos(int row,int col){
        Grid grid = _map.get_grid(row,col);
        Coordinate pos = grid.get_position();
        Coordinate size = grid.get_size();
        size = size.divide(2);
        return pos.add(size);
    }


}
