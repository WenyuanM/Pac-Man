import java.util.ArrayList;

/**
 * to store all the grids into an arrayList and sidebar
 */
public class Map {
    private SideBar _sidebar;
    private Grid[][] _map;
    private int _mapRow;
    private int _mapCol;
    private int _mapSize;

    // ================================ Constructor ================================
    /**
     * to initialize the map variables (SIDE BAR + GRIDS)
     */
    public Map(){
//        _sidebar = new SideBar();
        generateGrids();
        if(Constants.DEBUG) printMap();
    }

    // =========================== Public Methods ============================
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

    private void generateCoordinates(){
        double windowWidth = (double) Constants.GAMEFRAME_FRAME_WIDTH;
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

    private void generateNeighbor(){
        for(int i=0;i<_mapRow;i++){
            for(int j=0;j<_mapCol;j++){
                generateSingleGridNeighbor(i,j);
            }
        }
    }

    private void generateSingleGridNeighbor(int i,int j){
        if(i > 0){
            // up neighbor
            if(!_map[i-1][j].get_type().equals("|")){
                _map[i][j].addNeighbor(_map[i-1][j].get_index());
            }
        }
        if(i < _mapRow - 1){
            // down neighbor
            if(!_map[i+1][j].get_type().equals("|")){
                _map[i][j].addNeighbor(_map[i+1][j].get_index());
            }
        }
        if(j > 0){
            // left neighbor
            if(!_map[i][j-1].get_type().equals("|")){
                _map[i][j].addNeighbor(_map[i][j-1].get_index());
            }
        }
        if(j < _mapCol - 1){
            // right neighbor
            if(!_map[i][j+1].get_type().equals("|")){
                _map[i][j].addNeighbor(_map[i][j+1].get_index());
            }
        }
    }

    private boolean insideThisGrid(Coordinate point,int row,int col){
        Coordinate leftTop = _map[row][col].get_position();
        Coordinate size = _map[row][col].get_size();
        Coordinate rightBottom = leftTop.add(size);
        point.subtract(new Coordinate(0,Constants.SIDEBAR_HEIGHT));
        if((point.getX() >= leftTop.getX() && point.getX() <= rightBottom.getX()) &&
                (point.getY() >= leftTop.getY() && point.getY() <= rightBottom.getY())){
            return true;
        }
        return false;
    }

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
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!return -1");
        return -1;
    }

    public int updateFood(Coordinate pacManPosition,int row,int col){
        int index = row * _mapCol + col;
        Grid grid = get_grid(index);
        if(grid.get_containFood()){
            grid.set_containFood(false);
            return 1;
        }
        return 0;
    }

    public Grid get_grid(int index){
        int row = index / _mapCol;
        int col = index % _mapCol;
        return _map[row][col];
    }

    public Grid get_grid(int row,int col){
        return _map[row][col];
    }

    public int get_mapSize(){
        return _mapSize;
    }

    public int get_mapRow() {return _mapRow;}

    public int get_mapCol() {return _mapCol;}

    public void printMap(){
        for(int i=0;i<_mapRow;i++){
            for(int j=0;j<_mapCol;j++){
                System.out.println(_map[i][j]);
            }
            System.out.println("=================================");
        }
    }

    /**
     * to update all the grid occupation
     * @return return true, if the process is successful; otherwise, return false
     */
    public boolean update(){
        return true;
    }
}
