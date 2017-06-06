package Account;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

// Account Class: to manage a single account

import HelpingClass.Constants;

public class Account {
    private String _username;
    private String _password;
    private int _earnedPoint;
    private String _time;

    //================================== Constructor ===================================

    /**
     * Constructor: initialize the private variables
     */
    public Account(){
        _username = "";
        _password = "";
        _earnedPoint = 0;
        _time = "0:0";
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
            _earnedPoint = 0;
            _time = "0:0";
            return true;
        }
        return false;
    }

    /**
     * to
     * to check whether the input username and password are valid, if they are valid, create a new account
     * @param username the new username
     * @param password the new password
     * @param earnedPoint the earned point
     * @param time the time
     * @return if the account is created successfully, return true; otherwise, return false
     */
    public boolean createAccount(String username,String password,int earnedPoint,String time){
        if(newInputValid(username) && newInputValid(password)){
            _username = username;
            _password = password;
            _earnedPoint = earnedPoint;
            _time = time;
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
        return  _username + " " + _password + " " + _earnedPoint + " " + _time;
    }

    /**
     * to get the display info on the game result panel
     * @return the string of the game result info
     */
    public String rateDisplay(){
        return "Username: " + _username + "\t" + "Earned Point: " + _earnedPoint + "\t" + "Time: " + _time;
    }
    /**
     * to check whether this account is better than the account
     * @param account the comparing account
     * @return T if this is better than the comparing account; otherwise, return F
     */
    public boolean bigger(Account account){
        if(_earnedPoint > account._earnedPoint){
            return true;
        }
        else if(_earnedPoint == account._earnedPoint){
            String[] times = _time.split(":");
            int minute = Integer.parseInt(times[0]);
            int second = Integer.parseInt(times[1]);
            times = account.get_time().split(":");
            int other_minute = Integer.parseInt(times[0]);
            int other_second = Integer.parseInt(times[1]);
            if(minute < other_minute){
                return true;
            }
            else if(minute == other_minute){
                if(second < other_second){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * to check whether the new record is better than the last record
     * @param earnedPoint the new earned point
     * @param time the new time
     * @return T if updates; otherwise, return F
     */
    public boolean updateEarnedPointandTime(int earnedPoint,String time){
        if(earnedPoint > _earnedPoint){
            _earnedPoint = earnedPoint;
            _time = time;
            return true;
        }
        else if(earnedPoint == _earnedPoint){
            String[] times = _time.split(":");
            int minute = Integer.parseInt(times[0]);
            int second = Integer.parseInt(times[1]);
            times = time.split(":");
            int other_minute = Integer.parseInt(times[0]);
            int other_second = Integer.parseInt(times[1]);
            if(minute > other_minute){
                _time = time;
                return true;
            }
            else if(minute == other_minute){
                if(second > other_second){
                    _time = time;
                    return true;
                }
            }
        }
        return false;
    }

    // ================================ Accessor ========================================

    /**
     * to return the username as a string type
     * @return the username
     */
    public String getUserName(){
        return _username;
    }

    /**
     * to return the password as a string type
     * @return the password
     */
    public String getPassword(){
        return _password;
    }

    /**
     * to return the time string
     * @return the time string
     */
    public String get_time() { return _time;}
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
