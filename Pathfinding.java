import java.util.ArrayList;

public class Pathfinding {
    private ArrayList<Integer> _openSet;
    private ArrayList<Integer> _closedSet;
    private ArrayList<Grid> _path;
    private Map _map;
    private int _startIndex;
    private int _endIndex;

    public Pathfinding(Map map){
        _map = map;
        _openSet = new ArrayList<>();
        _closedSet = new ArrayList<>();
        _path = new ArrayList<>();
    }

    public ArrayList<Grid> findShortestPath(int startIndex,int endIndex){
        _startIndex = startIndex;
        _endIndex = endIndex;
        // add the first node into the open set first
        int currentF = findF(_startIndex);
        _map.get_grid(_startIndex).set_Fnum(currentF);
        _openSet.add(_startIndex);
        // call this recursive method to get the whole path
        findPath(_startIndex);
        setPath();
        return _path;
    }

    private void setPath(){
        ArrayList<Grid> reversedPath = new ArrayList<>();
        int index = _endIndex;
        reversedPath.add(_map.get_grid(index));
        while(index != _startIndex){
            index = _map.get_grid(index).get_parent();
            reversedPath.add(_map.get_grid(index));
        }
        int length = reversedPath.size();
        for(int i=length-1;i>=0;i--){
            _path.add(reversedPath.get(i));
        }
    }

    private void findPath(int current){
        // remove current from OPEN
        int index = insideArray(_openSet,current);
        if(index == -1){
            System.out.println("WRONG 1111111111111111111111111111111");
        }
        _openSet.remove(index);

        // add current to CLOSED
        _closedSet.add(current);

        // if current is the target node
        if(current == _endIndex){
            return;
        }

        int neighborNum = _map.get_grid(current).get_neighborNum();
        int[] neighbors = _map.get_grid(current).get_neighbors();

        //for each neighbor of the current node
        for(int i=0;i<neighborNum;i++){
            Grid neighborGrid = _map.get_grid(neighbors[i]);
            // if neighbor is not traversable or neighbor is in CLOSED
            // SKIP THIS NEIGHBOR
            if(!neighborGrid.get_type().equals("|") && insideArray(_closedSet,neighbors[i]) == -1){
                int currentNeighborF = neighborGrid.get_Fnum();
                int neighborF = findF(neighbors[i]);
                if(neighborF < currentNeighborF || insideArray(_closedSet,neighbors[i]) == -1){
                    neighborGrid.set_Fnum(neighborF);
                    neighborGrid.set_parent(current);
                    if(insideArray(_closedSet,neighbors[i]) == -1){
                        _openSet.add(neighbors[i]);
                    }
                }
            }
        }
        int currentIndex = smallestF();
        findPath(_map.get_grid(_openSet.get(currentIndex)).get_index());
    }

    private int smallestF(){
        int smallest = 100000;
        int smallestIndex = 0;
        int size = _openSet.size();
        for(int i=0;i<size;i++){
            int thisF = _map.get_grid(_openSet.get(i)).get_Fnum();
            if(thisF < smallest){
                smallest = thisF;
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    private int findG(int currentIndex){
        int mapCol = _map.get_mapCol();
        int currentRow = currentIndex / mapCol;
        int currentCol = currentIndex % mapCol;

        int startRow = _startIndex / mapCol;
        int startCol = _startIndex % mapCol;

        return Math.abs(currentRow - startRow) + Math.abs(currentCol - startCol);
    }

    private int findH(int currentIndex){
        int mapCol = _map.get_mapCol();
        int currentRow = currentIndex / mapCol;
        int currentCol = currentIndex % mapCol;

        int endRow = _endIndex / mapCol;
        int endCol = _endIndex % mapCol;

        return Math.abs(currentRow - endRow) + Math.abs(currentCol - endCol);
    }

    private int findF(int currentIndex){
        return findH(currentIndex) + findG(currentIndex);
    }

    private int insideArray(ArrayList<Integer> array,int element){
        int index = 0;
        for(int arrayListElement:array){
            if(arrayListElement == element){
                return index;
            }
            index++;
        }
        return -1;
    }
}
