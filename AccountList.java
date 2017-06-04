package Account;

import HelpingClass.ReadFile;
import HelpingClass.Constants;
import java.util.ArrayList;

// AccountList Class: to manage an AccountList with multiple Accounts (reading from a file)

public class AccountList {
    private ArrayList<Account> _accountList;

    // ===================================== Constructor ====================================

    /**
     * to initialize the accountList and get all the account information from reading file
     */
    public AccountList(){
        ReadFile readFile = new ReadFile();
        _accountList = readFile.readAccountFile(Constants.ACCOUNT_FILE_NAME);
    }

    // =================================== Public Methods ====================================

    /**
     * to add a new account to the accountList
     * @param newAccount the new account needs to be added into the AccountList
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

    /**
     * to print the AccountList information
     */
    public void printAccountList(){
        System.out.println(_accountList);
    }

    // ======================================= Accessor ===================================
    /**
     * to get the account based on input index
     * @param index the index of the getting account
     * @return the account in the ACCOUNT type
     */
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
}
