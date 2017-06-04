package HelpingClass;

import Map.*;
import java.util.ArrayList;

// Pathfinding class: a helping class to find shortest path based on passed in map

public class Pathfinding {
    private ArrayList<Integer> _openSet;
    private ArrayList<Integer> _closedSet;
    private ArrayList<Grid> _path;
    private Map _map;
    private int _startIndex;
    private int _endIndex;

    // ===================================== Constructor ========================================

    /**
     * Constructor: to initialize the map variable in the class
     * @param map the given map in MAP type
     */
    public Pathfinding(Map map){
        _map = map;
        _openSet = new ArrayList<>();
        _closedSet = new ArrayList<>();
        _path = new ArrayList<>();
    }

    /**
     * to find the shortest path from startIndex to endIndex
     * @param startIndex the start index
     * @param endIndex the end index
     * @return the path in ARRAYLIST<GRID> type
     */
    public ArrayList<Grid> findShortestPath(int startIndex,int endIndex){
        resetFValue();
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

    // ===================================== PRIVATE METHODS ==================================

    /**
     * to reset the F Values of all the grids in the map to 10000
     */
    private void resetFValue(){
        int length = _map.get_mapSize();
        for(int i=0;i<length;i++){
            _map.get_grid(i).set_Fnum(10000);
        }
    }

    /**
     * after finding the path, from the endIndex to reversely store the whole path into _path (private variable)
     */
    private void setPath(){
        // get the reversed path
        ArrayList<Grid> reversedPath = new ArrayList<>();
        int index = _endIndex;
        reversedPath.add(_map.get_grid(index));
        while(index != _startIndex){
            index = _map.get_grid(index).get_parent();
            reversedPath.add(_map.get_grid(index));
        }

        // get the final path in the correct order
        int length = reversedPath.size();
        for(int i=length-1;i>=0;i--){
            _path.add(reversedPath.get(i));
        }
    }

    /**
     * Recursive method: to find the path
     * @param current the current checking node
     */
    private void findPath(int current){
        // remove current from OPEN
        int index = insideArray(_openSet,current);
        if(index == -1){
            System.out.println("findPath method returns -1.");
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
        // RECURSIVE CALL
        findPath(_map.get_grid(_openSet.get(currentIndex)).get_index());
    }

    /**
     * to find the index of the grid holding the smallest F value in the openSet now
     * @return the index of the grid holding the smallest F value
     */
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

    /**
     * G value: the straight distance from the startIndex node to the current node
     * to find the G value of the current node from the startIndex
     * @param currentIndex the current node's index
     * @return the G value
     */
    private int findG(int currentIndex){
        int mapCol = _map.get_mapCol();
        int currentRow = currentIndex / mapCol;
        int currentCol = currentIndex % mapCol;

        int startRow = _startIndex / mapCol;
        int startCol = _startIndex % mapCol;

        return Math.abs(currentRow - startRow) + Math.abs(currentCol - startCol);
    }

    /**
     * H value: the straight distance from the current node to the endIndex node
     * to find the H value of the current node from the endIndex
     * @param currentIndex the current node's index
     * @return the H value
     */
    private int findH(int currentIndex){
        int mapCol = _map.get_mapCol();
        int currentRow = currentIndex / mapCol;
        int currentCol = currentIndex % mapCol;

        int endRow = _endIndex / mapCol;
        int endCol = _endIndex % mapCol;

        return Math.abs(currentRow - endRow) + Math.abs(currentCol - endCol);
    }

    /**
     * F value: the sum of its G value and H value
     * to find the F value of the current node
     * @param currentIndex the current node's index
     * @return the F value
     */
    private int findF(int currentIndex){
        return findH(currentIndex) + findG(currentIndex);
    }

    /**
     * to check whether the given element is inside the given array
     * @param array the given array
     * @param element the given element
     * @return return -1 if there is no this element in the array;
     * return its index if the element is inside the array
     */
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
