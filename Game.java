package Main;

import Account.AccountList;
import Frame.LogInFrame;
import Frame.GameFrame;
import HelpingClass.Constants;
import Map.Map;

// Game class: to create the game and manage the whole program

public class Game {
    private AccountList _accounts;
    private LogInFrame _logInFrame;
    private Map _map;
    private GameFrame _gameFrame;

    /**
     * Constructor: to create and start the game
     */
    public Game(){
        _accounts = new AccountList();
        if(Constants.DEBUG)     _accounts.printAccountList();
        _logInFrame = new LogInFrame(_accounts);
        _map = new Map();
        while(_logInFrame.get_frameExist()){}
        _gameFrame = new GameFrame(_map);
    }
}
