//import java.util.ArrayList;
//
///**
// * to store all the characters = PAC MAN + GHOSTS
// */
//public class Game_Character {
//    private ArrayList<Character> _ghosts;
//    private Character _pacman;
//
//    // ============================= Constructor ========================
//
//    /**
//     * to initialize pac man character with zero ghost
//     */
//    public Game_Character(){
//        _ghosts = new ArrayList<>();
//
//        Coordinate pos = new Coordinate(0,0);
//        Coordinate speed = new Coordinate(0,0);
//        char name = 'P';
//        _pacman.initialCharacter(pos,name);
//    }
//
//    /**
//     * to initialize pac man and given number of ghosts in the arrayList
//     * @param numOfGhosts the number of ghosts to create in the game
//     */
//    public Game_Character(int numOfGhosts){
//        Coordinate pos = new Coordinate(0,0);         // THESE INITIALIZATIONS NEED TO BE CHANGED LATER!!! (MAYBE RANDOM GENERATE)
//        Coordinate speed = new Coordinate(0,0);
//        char name = 'P';
//        _pacman.initialCharacter(pos,name);
//
//        _ghosts = new ArrayList<>();
//
//        name = 'N';
//        for(int i=0;i<numOfGhosts;i++){
//            Character ghost = new Character(pos, speed,name);
//            _ghosts.add(ghost);
//        }
//    }
//
//    // ========================== Public Methods ===========================
//
//    /**
//     * to move all the characters (PAC MAN + GHOSTS)
//     * @return if any of the characters fails to move, return false; otherwise, return true
//     */
//    public boolean update(){
//        Coordinate currentSpeed = new Coordinate(0,0);    // THIS NEEDS TO BE CHANGED LATER FOR CORRECT CURRENT SPEED)
//        for(Character ghost:_ghosts){
//            if(!ghost.update()){
//                return false;
//            }
//        }
//        if(_pacman.update()){
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * to print a certain character's information
//     * @param index the index of the character (-1 = PAC MAN, >= 0 GHOSTS)
//     */
//    public void printCertainCharacter(int index){
//        if(index == -1){
//            _pacman.printCharacterInfo();
//        }
//        else if(index >= 0 && index < _ghosts.size()){
//            Character printCharacter = _ghosts.get(index);
//            printCharacter.printCharacterInfo();
//        }
//        else{
//            System.out.println("The index is invalid.");
//        }
//    }
//
//    /**
//     * to get the number of ghosts in the game so far
//     * @return the number of ghost
//     */
//    public int getNumOfGhosts(){
//        return _ghosts.size();
//    }
//
//}
