/**
 * to store one single grid on the map
 */

public class Grid {
    private int _index;
    private String _type;  // . for food, | for blocks, O for big food, - for connecting left and right
    private Coordinate _size;   // x = width; y = height
    private Coordinate _position;
    private int[] _neighbors;
    private int _neighborNum;
    private boolean _containFood;

    // ========================= CONSTRUCTORS FOR THIS CLASS ============================

    /**
     * to initialize the gird with type, size, and pos for 2D
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
//        _slope = 0;
//        _occupation = "None";
    }

    // ========================= COMMON METHODS USED OUTSIDE THE CLASS ============================
    public void set_size(Coordinate size){
        _size = size;
    }

    public void set_position(Coordinate position){
        _position = position;
    }

    public void set_type(String type){
        if((_type.equals("o") || _type.equals("O")) && type.equals("-")){
            _type = type;
        }
    }

    public void addNeighbor(int neighbor){
        _neighbors[_neighborNum] = neighbor;
        _neighborNum++;
    }

    public String get_type(){return _type;}

    public int get_index(){return _index;}

    public Coordinate get_position(){
        return _position;
    }

    public Coordinate get_size(){
        return _size;
    }

    public void set_containFood(boolean contain){_containFood = contain;}

    public boolean get_containFood(){return _containFood;}

    public String toString(){
        String s = _index + " " + _type + " " + "Neighbor: ";
        int length = _neighbors.length;
        for(int i=0;i<length;i++){
            s += _neighbors[i] + ", ";
        }
        return s;
    }

    private void initialNeighborArray(){
        _neighbors = new int[Constants.NEIGHBOR_NUM];
        int length = _neighbors.length;
        for(int i=0;i<length;i++){
            _neighbors[i] = -1;
        }
    }
}
