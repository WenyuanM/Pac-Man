package Map;

import HelpingClass.Constants;
import HelpingClass.Coordinate;
import HelpingClass.ReadFile;

// Map class: to create the map and store all the useful information

public class Map {
    private Grid[][] _map;
    private int _mapRow;
    private int _mapCol;
    private int _mapSize;
    private int _totalPoint;

    // ================================ Constructor ================================

    /**
     * Constructor: to initialize the map variables (SIDE BAR + GRIDS)
     */
    public Map(){
        generateGrids();
        _totalPoint = findPoints();
    }

    // =========================== PRIVATE METHODS ============================

    /**
     * to generate grids with proper position, slope, type
     * @return return true, if the process is successful; otherwise, return false
     */
    private boolean generateGrids(){
        ReadFile readFile = new ReadFile();
        _map = readFile.readCharacterMapFile(Constants.MAP_FILE_NAME);
        if(_map == null){
            return false;
        }
        _mapRow = readFile.get_row();
        _mapCol = readFile.get_col();
        _mapSize = _mapRow * _mapCol;
        generateCoordinates();
        generateNeighbor();
        return true;
    }

    /**
     * to generate the coordinates for all the grids
     */
    private void generateCoordinates(){
        double windowWidth = (double) (Constants.GAMEFRAME_FRAME_WIDTH - Constants.SIDEBAR_WIDTH);
        double windowHeight = (double) (Constants.GAMEFRAME_FRAME_HEIGHT - Constants.SIDEBAR_HEIGHT);
        double gridWidth = windowWidth / (double) _mapCol;
        double gridHeight = windowHeight / (double) _mapRow;
        Coordinate size = new Coordinate(gridWidth,gridHeight);
        for(int i=0;i<_mapRow;i++){
            for(int j=0;j<_mapCol;j++){
                Coordinate position = new Coordinate(j*gridWidth,i*gridHeight);
                _map[i][j].set_size(size);
                _map[i][j].set_position(position);
            }
        }
    }

    /**
     * to generate the neighbors for all the grids
     */
    private void generateNeighbor(){
        for(int i=0;i<_mapRow;i++){
            for(int j=0;j<_mapCol;j++){
                generateSingleGridNeighbor(i,j);
            }
        }
    }

    /**
     * to generate the neighbor for a single grid
     * @param i the row number of the grid
     * @param j the col number of the grid
     */
    private void generateSingleGridNeighbor(int i,int j){
        if(i > 0){
            // up neighbor
            if(!_map[i-1][j].get_type().equals("|")){
                _map[i][j].addHasNeighbor(0,true);
                _map[i][j].addNeighbor(_map[i-1][j].get_index());
            }
        }
        if(i < _mapRow - 1){
            // down neighbor
            if(!_map[i+1][j].get_type().equals("|")){
                _map[i][j].addHasNeighbor(1,true);
                _map[i][j].addNeighbor(_map[i+1][j].get_index());
            }
        }
        if(j > 0){
            // left neighbor
            if(!_map[i][j-1].get_type().equals("|")){
                _map[i][j].addHasNeighbor(2,true);
                _map[i][j].addNeighbor(_map[i][j-1].get_index());
            }
        }
        if(j < _mapCol - 1){
            // right neighbor
            if(!_map[i][j+1].get_type().equals("|")){
                _map[i][j].addHasNeighbor(3,true);
                _map[i][j].addNeighbor(_map[i][j+1].get_index());
            }
        }
    }

    /**
     * to check whether the point is in the grid
     * @param point the current point
     * @param row the row of the checking grid
     * @param col the col of the checking grid
     * @return T if the point is inside the grid; otherwise, F
     */
    private boolean insideThisGrid(Coordinate point,int row,int col){
        Coordinate leftTop = _map[row][col].get_position();
        Coordinate size = _map[row][col].get_size();
        Coordinate rightBottom = leftTop.add(size);
        point.subtract(new Coordinate(0,Constants.SIDEBAR_HEIGHT));
        return (point.getX() >= leftTop.getX() && point.getX() <= rightBottom.getX()) &&
                (point.getY() >= leftTop.getY() && point.getY() <= rightBottom.getY());
    }

    /**
     * to find the total points that can be earned from this map
     * @return the total points
     */
    private int findPoints(){
        int point = 0;
        for(int i=0;i<_mapRow;i++){
            for(int j=0;j<_mapCol;j++){
                if(_map[i][j].get_type().equals(".")){
                    point++;
                }
                else if(_map[i][j].get_type().equals("o")){
                    point += 2;
                }
            }
        }
        return point;
    }

    // =============================== PUBLIC METHODS =======================================

    /**
     * to find the index of the grid
     * @param point the point
     * @param row the current row of the grid
     * @param col the current col of the grid
     * @return the index of the grid
     */
    public int findGridIndex(Coordinate point,int row,int col){
        if(insideThisGrid(point,row,col)){
            return row * _mapCol + col;
        }
        if(row > 0){
            if(insideThisGrid(point,row - 1,col)) {
                return (row - 1) * _mapCol + col;
            }
        }
        if(row < _mapRow - 1){
            if(insideThisGrid(point,row + 1,col)){
                return (row + 1) * _mapCol + col;
            }
        }
        if(col > 0){
            if(insideThisGrid(point,row,col - 1)){
                return row * _mapCol + (col - 1);
            }
        }
        if(col < _mapCol - 1){
            if(insideThisGrid(point,row,col + 1)){
                return row * _mapCol + (col + 1);
            }
        }
        return 0;
    }

    /**
     * to update whether the food is eaten or not
     * @param row the row of the grid
     * @param col the col of the grid
     * @return if the eaten food is big, return 2; otherwise, return 1
     */
    public int updateFood(int row,int col){
        int index = row * _mapCol + col;
        Grid grid = get_grid(index);
        if(grid.get_containFood()){
            grid.set_containFood(false);
            if(grid.get_type().equals("o")){
                // big food
                return 2;
            }
            else{
                return 1;
            }
        }
        return 0;
    }

    /**
     * to check whether the current position is valid for a character to stay
     * @param position the character's position
     * @param row the row of the inside grid
     * @param col the col of the inside grid
     * @return T if it is valid to stay; otherwise, F
     */
    public boolean updateValid(Coordinate position,int row,int col){
        // character left top point
        Coordinate point = position.subtract(new Coordinate((double)(Constants.CHARACTER_IMAGE_SIZE/2),
                (double)(Constants.CHARACTER_IMAGE_SIZE/2)));
        int index = findGridIndex(point,row,col);
        int newRow = index / _mapCol;
        int newCol = index % _mapCol;
        if(_map[newRow][newCol].get_type().equals("|")){
            return false;
        }

        // character left bottom point
        point = position.add(new Coordinate((double)(-Constants.CHARACTER_IMAGE_SIZE/2),
                (double)(Constants.CHARACTER_IMAGE_SIZE/2)));
        index = findGridIndex(point,row,col);
        newRow = index / _mapCol;
        newCol = index % _mapCol;
        if(_map[newRow][newCol].get_type().equals("|")){
            return false;
        }

        // character right top point
        point = position.add(new Coordinate((double)(Constants.CHARACTER_IMAGE_SIZE/2),
                (double)(-Constants.CHARACTER_IMAGE_SIZE/2)));
        index = findGridIndex(point,row,col);
        newRow = index / _mapCol;
        newCol = index % _mapCol;
        if(_map[newRow][newCol].get_type().equals("|")){
            return false;
        }

        // character right bottom point
        point = position.add(new Coordinate((double)(Constants.CHARACTER_IMAGE_SIZE/2),
                (double)(Constants.CHARACTER_IMAGE_SIZE/2)));
        index = findGridIndex(point,row,col);
        newRow = index / _mapCol;
        newCol = index % _mapCol;
        return !_map[newRow][newCol].get_type().equals("|");
    }

    /**
     * to check whether there is any more food to eat
     * @return T if there is more food; Otherwise, F
     */
    public boolean gameContinue(){
        for(int i=0;i<_mapRow;i++){
            for(int j=0;j<_mapCol;j++){
                if(_map[i][j].get_containFood()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * to set all the food to be eaten
     */
    public void endGame(){
        for(int i=0;i<_mapRow;i++) {
            for (int j = 0; j < _mapCol; j++) {
                _map[i][j].set_containFood(false);
            }
        }
    }
    // ================================== ACCESSORS =========================================

    /**
     * to get the grid in the map
     * @param index the grid index
     * @return the grid
     */
    public Grid get_grid(int index){
        int row = index / _mapCol;
        int col = index % _mapCol;
        return _map[row][col];
    }

    /**
     * to get the grid in the map
     * @param row the row of the grid
     * @param col the col of the grid
     * @return the grid
     */
    public Grid get_grid(int row,int col){
        return _map[row][col];
    }

    /**
     * to get the map size
     * @return the total map size
     */
    public int get_mapSize(){
        return _mapSize;
    }

    /**
     * to get the total row in the map
     * @return the total row number
     */
    public int get_mapRow() {return _mapRow;}

    /**
     * to get the total col in the map
     * @return the total col number
     */
    public int get_mapCol() {return _mapCol;}

    /**
     * to get the total points that can be earned from this map
     * @return the total points that can be earned from this map
     */
    public int get_totalPoint() { return _totalPoint;}
}
