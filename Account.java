package Account;

// Account Class: to manage a single account

import HelpingClass.Constants;

public class Account {
    private String _username;
    private String _password;

    //================================== Constructor ===================================

    /**
     * Constructor: initialize the private variables
     */
    public Account(){
        _username = "";
        _password = "";
    }

    // ================================= Public Methods ===================================
    /**
     * to check whether the input username and password are valid, if they are valid, create a new account
     * @param username the new username
     * @param password the new password
     * @return if the account is created successfully, return true; otherwise, return false
     */
    public boolean createAccount(String username,String password){
        if(newInputValid(username) && newInputValid(password)){
            _username = username;
            _password = password;
            return true;
        }
        return false;
    }

    /**
     * to check whether the log-in username and password are matching with this existing account
     * @param username the log-in username
     * @param password the log-in password
     * @return if the log-in information is matching the existing account, return true; otherwise, return false
     */
    public boolean matchAccount(String username,String password){
        if(username.equals(_username) && password.equals(_password)){
            return true;
        }
        return false;
    }

    /**
     * to change the password
     * @param username the user name of this account
     * @param oldPassword the old password of this account
     * @param newPassword the new password want to use
     * @return if the username and old password are correct, change the password to the new one and return true.
     * Otherwise, the password remains the old one and return false
     */
    public boolean changePassword(String username,String oldPassword,String newPassword){
        if(matchAccount(username,oldPassword)){
            _password = newPassword;
            return true;
        }
        return false;
    }

    /**
     * to get the account information as a string type
     */
    public String toString(){
        return  _username + " " + _password;
    }

    // ================================ Accessor ========================================

    /**
     * to return the username as a string type
     */
    public String getUserName(){
        return _username;
    }

    /**
     * to return the password as a string type
     */
    public String getPassword(){
        return _password;
    }

    // ================================ Mutator =========================================

    /**
     * to set the username if the username is valid
     * @param username the new username
     */
    public void set_username(String username){
        if(newInputValid(username)){
            _username = username;
        }
    }

    /**
     * to set the password if the password is valid
     * @param password the new password
     */
    public void set_password(String password){
        if(newInputValid(password)){
            _password = password;
        }
    }

    // ================================= Private methods ========================================

    /**
     * to check whether the input username or password is valid
     * @param input the input username or password
     * @return if the input username or password is valid, return true; otherwise, return false
     */
    private boolean newInputValid(String input){
        // the maximum length is 10 with any input characters
        if(input.length() < 1 || input.length() > Constants.INPUT_MAX_LENGTH){
            return false;
        }
        return true;
    }
}
