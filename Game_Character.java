import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
        _pacMan = new PacMan(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL);
    }

    public Game_Character(Map map,int numOfNormalGhosts,int numofSmartGhost){
        _map = map;
        _pacMan = new PacMan(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL);

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

    // ========================== Public Methods ===========================
    private Ghost[] initialGhosts(){
        Ghost[] ghosts = new Ghost[4];
        Ghost ghost1 = new Ghost(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL,'N',
                Constants.RED_GHOST_DOWN_1,Constants.RED_GHOST_DOWN_2,Constants.RED_GHOST_LEFT_1,Constants.RED_GHOST_LEFT_2,
                Constants.RED_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        Ghost ghost2 = new Ghost(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL,'N',
                Constants.PINK_GHOST_DOWN_1,Constants.PINK_GHOST_DOWN_2,Constants.PINK_GHOST_LEFT_1,Constants.PINK_GHOST_LEFT_2,
                Constants.PINK_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        Ghost ghost3 = new Ghost(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL,'N',
                Constants.BLUE_GHOST_DOWN_1,Constants.BLUE_GHOST_DOWN_2,Constants.BLUE_GHOST_LEFT_1,Constants.BLUE_GHOST_LEFT_2,
                Constants.BLUE_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        Ghost ghost4 = new Ghost(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL,'N',
                Constants.ORANGE_GHOST_DOWN_1,Constants.ORANGE_GHOST_DOWN_2,Constants.ORANGE_GHOST_LEFT_1,Constants.ORANGE_GHOST_LEFT_2,
                Constants.ORANGE_GHOST_RIGHT,Constants.TRACE_GHOST_1,Constants.TRACE_GHOST_2,Constants.DIE_GHOST_1,Constants.DIE_GHOST_2);
        ghosts[0] = ghost1;
        ghosts[1] = ghost2;
        ghosts[2] = ghost3;
        ghosts[3] = ghost4;
        return ghosts;
    }

    private int randomDirectionGenerate(){
        int n = ThreadLocalRandom.current().nextInt(1,4 + 1);
        switch(n){
            case 1: // up
                return 87;
            case 2: // down
                return 83;
            case 3: // left
                return 65;
            case 4: // right
                return 68;
        }
        return -1;
    }

    public void update_NormalGhosts(){
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            int code = randomDirectionGenerate();
            Coordinate newPos = _normalGhosts[i].ifUpdate(code);
            int index = _map.findGridIndex(newPos,_normalGhosts[i].get_row(),_normalGhosts[i].get_col());
            int row = index / _map.get_mapCol();
            int col = index % _map.get_mapCol();
            char movingDirection = _normalGhosts[i].get_movingDirection();
            if(!(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_LEFT_COL && movingDirection == 'L') &&
                    !(row == Constants.TRANSITION_ROW && col == Constants.TRANSITION_RIGHT_COL && movingDirection == 'R')){
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
        }
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

    public void drawCharacters(Graphics shape){
        _pacMan.draw(shape);
        int size = _normalGhosts.length;
        for(int i=0;i<size;i++){
            _normalGhosts[i].draw(shape);
        }
        size = _smartGhosts.length;
        for(int i=0;i<size;i++) {
            _smartGhosts[i].draw(shape);
        }
    }

    private Coordinate generatePacManStartPos(){
        Grid grid = _map.get_grid(Constants.GRID_ROW,Constants.GRID_COL);
        Coordinate pos = grid.get_position();
        Coordinate size = grid.get_size();
        size = size.divide(2);
        return pos.add(size);
    }
}
