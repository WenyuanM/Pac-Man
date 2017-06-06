package Frame;

// SideBar class: to store all the sidebar information

public class SideBar {
    private int _totalPoints;
    private int _earnedPoints;
    private int _numSmartEnemies;
    private int _numNormalEnemies;
    private int _pacManLives;
    private String _gameResult;
    private String _playerName;

    // ========================== Constructor =================================

    /**
     * to initialize all the variables
     * @param totalPoints the total score earned
     * @param numNormalEnemies the number of normal enemies in the game
     * @param numSmartEnemies the number of smart enemies in the game
     * @param pacManLives the lives of pac man remaining
     * @param playerName the player name
     */
    public SideBar(int totalPoints,int numNormalEnemies,int numSmartEnemies,int pacManLives,String playerName){
        _totalPoints = totalPoints;
        _numSmartEnemies = numSmartEnemies;
        _numNormalEnemies = numNormalEnemies;
        _playerName = playerName;
        _earnedPoints = 0;
        _pacManLives = pacManLives;
        _gameResult ="Game Playing";
    }

    // ================================= Mutators ======================================

    /**
     * to set the game result
     * @param gameResult the game result
     */
    public void set_gameResult(String gameResult){
        _gameResult = gameResult;
    }

    /**
     * to set the earn point when the PAC MAN eats food
     * @param point the point earned
     */
    public void set_earnedPoints(int point){
        _earnedPoints = point;
    }

    /**
     * to add the earned points
     * @param addPoint the add points
     */
    void add_earnedPoints(int addPoint){
        _earnedPoints += addPoint;
    }

    /**
     * to decrease the pacman Lives
     */
    void decre_pacManLives(){
        _pacManLives --;
    }

    // ================================== Accessor =====================================

    /**
     * to get the total points users can earn in the game
     * @return the total points users can earn
     */
    int get_totalPoints(){
        return _totalPoints;
    }

    /**
     * to get the points earned so far
     * @return the points earned so far
     */
    int get_earnedPoints(){
        return _earnedPoints;
    }

    /**
     * to get the game result
     * @return the game result
     */
    String get_gameResult(){
        return _gameResult;
    }

    /**
     * to get the number of smart enemies
     * @return the number of smart enemies
     */
    int get_numSmartEnemies(){ return _numSmartEnemies;}

    /**
     * to get the number of normal enemies
     * @return the number of normal enemies
     */
    int get_numNormalEnemies(){ return _numNormalEnemies;}

    /**
     * to get the player name
     * @return the player name
     */
    String get_playerName(){ return _playerName;}

    /**
     * to get the current remaining pacman lives
     * @return the current remaining pacman lives
     */
    int get_pacManLives(){ return _pacManLives;}
}
