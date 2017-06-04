package Frame;

/**
 * to store all the info for the game
 */
public class SideBar {
    private int _difficultyLevel;   // 0 = EASY || 1 = HARD
    private int _totalPoints;
    private int _earnedPoints;
    private int _numEnemies;
    private double _time;
    private int _gameMode;      // 0 = GHOSTS EAT PAC MAN || 1 = PAC MAN EATS GHOSTS
    private String _mapName;
    private String _gameResult;

    // ========================== Constructor =================================
    /**
     * to initialize all the variables
     */
    public SideBar(){
        _difficultyLevel = 0;
        _totalPoints = 0;
        _earnedPoints = 0;
        _numEnemies = 0;
        _time = 0;
        _gameMode = 0;
        _mapName = "";
        _gameResult ="Playing.";
    }

    // ================================= Mutators ======================================

    /**
     * to set the difficulty level of the game
     * @param difficultyLevel the difficulty level of the game
     */
    public void set_difficultyLevel(int difficultyLevel){
        _difficultyLevel = difficultyLevel;
    }

    /**
     * to set the total points users can earn in the game
     * @param totalPoints the total points users can earn
     */
    public void set_totalPoints(int totalPoints){
        _totalPoints = totalPoints;
    }

    /**
     * to set the number of ghosts in the game
     * @param numEnemies the number of ghosts
     */
    public void set_numEnemies(int numEnemies){
        _numEnemies = numEnemies;
    }

    /**
     * to set the game mode
     * @param gameMode the game mode
     */
    public void set_gameMode(int gameMode){
        _gameMode = gameMode;
    }

    /**
     * to set the current using map's name
     * @param mapName the current using map's name
     */
    public void set_mapName(String mapName){
        _mapName = mapName;
    }

    /**
     * to set the game result
     * @param gameResult the game result
     */
    public void set_gameResult(String gameResult){
        _gameResult = gameResult;
    }

    /**
     * to add the earn point when the PAC MAN eats food
     * @param point the point earned
     */
    public void addEarnPoint(int point){
        _earnedPoints += point;
    }

    // ================================== Accessor =====================================

    /**
     * to get the difficulty level of the game
     * @return the difficulty level of the game
     */
    public int get_difficultyLevel(){
        return _difficultyLevel;
    }

    /**
     * to get the total points users can earn in the game
     * @return the total points users can earn
     */
    public int get_totalPoints(){
        return _totalPoints;
    }

    /**
     * to get the points earned so far
     * @return the points earned so far
     */
    public int get_earnedPoints(){
        return _earnedPoints;
    }

    /**
     * to get the number of ghosts in the game so far
     * @return the number of ghosts in the game so far
     */
    public int get_numEnemies(){
        return _numEnemies;
    }

    /**
     * to get the current game mode
     * @return the current game mode
     */
    public int get_gameMode(){
        return _gameMode;
    }

    /**
     * to get the current using map's name
     * @return the current using map's name
     */
    public String get_mapName(){
        return _mapName;
    }

    /**
     * to get the game result
     * @return the game result
     */
    public String get_gameResult(){
        return _gameResult;
    }
}
