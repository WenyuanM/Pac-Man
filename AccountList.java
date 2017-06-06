package Account;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

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

    /**
     * to sort the account lists based on their game result
     */
    public void sortAccountList(){
        ArrayList<Account> sortedAccount = new ArrayList<>();
        while(_accountList.size() != 0){
            int index = maxAccount();
            sortedAccount.add(_accountList.get(index));
            _accountList.remove(index);
        }
        _accountList = sortedAccount;
    }

    /**
     * to get the string of the whole account list
     * @return the string of the whole account list
     */
    public String toString(){
        String s = "";
        for(Account account:_accountList){
            s += account.rateDisplay() + "\n";
        }
        return s;
    }

    /**
     * to get the best game result account's index
     * @return the index of the best account
     */
    private int maxAccount(){
        int index = 0;
        Account maxAccount = _accountList.get(0);
        int length = _accountList.size();
        for(int i=1;i<length;i++){
            if(_accountList.get(i).bigger(maxAccount)){
                index = i;
                maxAccount = _accountList.get(i);
            }
        }
        return index;
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
     * to get the account based on the user name
     * @param username the user name
     * @return the account
     */
    public Account getAccount(String username){
        for(Account account:_accountList){
            if(account.getUserName().equals(username)){
                return account;
            }
        }
        return null;
    }

    /**
     * to get the number of accounts in the list so far
     * @return the number of accounts in the list
     */
    public int getNumAccount(){
        return _accountList.size();
    }
}
