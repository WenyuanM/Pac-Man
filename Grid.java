package Map;

import HelpingClass.Coordinate;
import HelpingClass.Constants;

// Grid class: to create a single grid

public class Grid {
    private int _index;
    private String _type;  // . for food, | for blocks, o for big food, - for connecting left and right
    private Coordinate _size;   // x = width; y = height
    private Coordinate _position;
    private int[] _neighbors;
    private boolean[] _hasNeighbors;
    private int _neighborNum;
    private boolean _containFood;

    // Pathfinding elements
    private int _parent;
    private int _Fnum;

    // ========================= CONSTRUCTORS FOR THIS CLASS ============================

    /**
     * Constructor: to initialize the gird with type, size, and pos for 2D
     * @param type the type of the grid T = road, F = block
     */
    public Grid(int index,String type){
        _containFood = false;
        _neighborNum = 0;
        _index = index;
        _type = type;
        if(!type.equals("|")){
            _containFood = true;
        }
        initialNeighborArray();
        initialHasNeighborArray();

        _parent = index;
        _Fnum = 100000000;
    }

    // ========================== PUBLIC METHODS ======================================

    /**
     * to get the information of the grid in string type
     * @return the string
     */
    public String toString(){
        String s = _index + " " + _type + " " + "Neighbor: ";
        int length = _neighbors.length;
        for (int _neighbor : _neighbors) {
            s += _neighbor + ", ";
        }
        return s;
    }

    // ================================== PRIVATE METHODS ==================================

    /**
     * to initialize the neighbor array of the grid
     */
    private void initialNeighborArray(){
        _neighbors = new int[Constants.NEIGHBOR_NUM];
        int length = _neighbors.length;
        for(int i=0;i<length;i++){
            _neighbors[i] = -1;
        }
    }

    /**
     * to initialize the hasNeighbor array of the grid
     */
    private void initialHasNeighborArray(){
        _hasNeighbors = new boolean[Constants.NEIGHBOR_NUM];
        int length = _hasNeighbors.length;
        for(int i=0;i<length;i++){
            _hasNeighbors[i] = false;
        }
    }

    // ============================= PACKAGE PRIVATE METHODS ===============================

    /**
     * to add the hasNeighbor variable into the hasNeighbor array
     * @param num the index of the hasNeighbor variable in the array
     * @param has the actual data
     */
    void addHasNeighbor(int num, boolean has){
        _hasNeighbors[num] = has;
    }

    /**
     * to add a new neighbor into the neighbor array
     * @param neighbor the new neighbor index
     */
    void addNeighbor(int neighbor){
        _neighbors[_neighborNum] = neighbor;
        _neighborNum++;
    }

    // ==================================== MUTATORS ============================================

    /**
     * to set the size of the grid
     * @param size the new size of the grid
     */
    void set_size(Coordinate size){
        _size = size;
    }

    /**
     * to set the left top corner position of the grid
     * @param position the left top corner position of the grid
     */
    void set_position(Coordinate position){
        _position = position;
    }

    /**
     * to set the type of contain food in the grid
     * @param contain the type of contain food in the grid
     */
    void set_containFood(boolean contain){_containFood = contain;}

    /**
     * to set the parent index of the grid (PATHFINDING)
     * @param parentIndex the parent index of the grid
     */
    public void set_parent(int parentIndex){
        _parent = parentIndex;
    }

    /**
     * to set the F number of the grid
     * @param F the F number of the grid
     */
    public void set_Fnum(int F){
        _Fnum = F;
    }

    /**
     * to set the type of the grid
     * @param type the type of the grid
     */
    public void set_type(String type){
        if((_type.equals("o") || _type.equals("O")) && type.equals("-")){
            _type = type;
        }
    }

    // ===================================== ACCESSORS ========================================

    /**
     * to get the grid type
     * @return the grid type
     */
    public String get_type(){return _type;}

    /**
     * to get the index of the grid
     * @return the index of the grid
     */
    public int get_index(){return _index;}

    /**
     * to get the left top position of the grid
     * @return the left top position of the grid
     */
    public Coordinate get_position(){
        return _position;
    }

    /**
     * to get the size of the grid
     * @return the size of the grid
     */
    public Coordinate get_size(){
        return _size;
    }

    /**
     * to get the number of neighbors of the grid
     * @return the number of neighbors of the grid
     */
    public int get_neighborNum() {return _neighborNum;}

    /**
     * to get the array of neighbors of the grid
     * @return the array of neighbors of the grid
     */
    public int[] get_neighbors(){
        return _neighbors;
    }

    /**
     * to get the array of hasNeighbors of the grid
     * @return the array of hasNeighbors of the grid
     */
    public boolean[] get_hasNeighbors() {return _hasNeighbors;}

    /**
     * to get whether the grid contains food or not
     * @return T if the grid contains food; otherwise, F
     */
    public boolean get_containFood(){return _containFood;}

    /**
     * to get the index of the parent of this grid
     * @return the index of the parent of this grid
     */
    public int get_parent(){
        return _parent;
    }

    /**
     * to get the F number of the grid
     * @return the F number of the grid
     */
    public int get_Fnum(){
        return _Fnum;
    }
}
