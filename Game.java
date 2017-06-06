package Main;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

import Account.AccountList;
import Frame.GameResultFrame;
import Frame.LogInFrame;
import Frame.GameFrame;
import HelpingClass.Constants;
import Map.Map;

// Game class: to create the game and manage the whole program

public class Game implements Runnable{
    private AccountList _accounts;
    private LogInFrame _logInFrame;
    private Map _map;
    private GameFrame _gameFrame;
    private GameResultFrame _gameResultFrame;
    private boolean _run;
    private String _username;
    private Thread _t;
    private String _threadName;

    /**
     * Constructor: to create and start the game
     * @param name the game thread name
     */
    public Game(String name){
        _threadName = name;
        _run = true;
        _accounts = new AccountList();
        if(Constants.DEBUG)     _accounts.printAccountList();
        _logInFrame = new LogInFrame(_accounts);
        while(_logInFrame.get_frameExist()){}
        _username = _logInFrame.get_username();
    }

    /**
     * the run method to run the thread
     */
    public void run(){
        while(_run){
            _map = new Map();
            _gameFrame = new GameFrame(_map,_username);
            while(_gameFrame.get_frameExist()){}
            _gameResultFrame = new GameResultFrame(_accounts,_username,_gameFrame.get_gameResult(),_gameFrame.get_earnedPoint(),_gameFrame.get_time());
            while(_gameResultFrame.get_restartGame() == 0){}
            if(_gameResultFrame.get_restartGame() == 1){
                _run = true;
            }
            else{
                _run = false;
            }
            try{
                _t.sleep(50);
            }
            catch(InterruptedException ignore){}
        }
    }

    /**
     * to start the thread of the game
     */
    public void start(){
        if(_t == null){
            _t = new Thread(this,_threadName);
            _t.start();
        }
    }
}
