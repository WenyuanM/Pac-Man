public class Game {
    private AccountList _accounts;
    private LogInFrame _logInFrame;
    private Map _map;
    private GameFrame _gameFrame;

    public Game(){
        _accounts = new AccountList();
        if(Constants.DEBUG)     _accounts.printAccountList();
        _logInFrame = new LogInFrame(_accounts);
        _map = new Map();
        while(_logInFrame.get_frameExist()){}
        _gameFrame = new GameFrame(_map);
    }

    public void run(){

    }
}
