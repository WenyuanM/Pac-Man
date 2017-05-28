import java.util.ArrayList;

/**
 * to store a list of Account variables for log-in and register
 */
public class AccountList {
    private ArrayList<Account> _accountList;

    // ======================== Constructor =====================
    /**
     * to initialize the accountList
     */
    public AccountList(){
        ReadFile readFile = new ReadFile();
        _accountList = readFile.readAccountFile(Constants.ACCOUNT_FILE_NAME);
    }

    // ======================== Public Methods =========================

    /**
     * to add a new account to the accountList
     * @param newAccount
     */
    public void addAccount(Account newAccount){
        _accountList.add(newAccount);
    }

    /**
     * to try to log in with the given username and password
     * @param username the log-in username
     * @param password the log-in password
     * @return if it logs in successfully, return true; otherwise, return false
     */
    public boolean logIn(String username, String password){
        for(Account account:_accountList){
            if(account.matchAccount(username,password)){
                return true;
            }
        }
        return false;
    }

    public Account getAccount(int index){
        return _accountList.get(index);
    }

    /**
     * to get the number of accounts in the list so far
     * @return the number of accounts in the list
     */
    public int getNumAccount(){
        return _accountList.size();
    }

    public void printAccountList(){
        System.out.println(_accountList);
    }
}
