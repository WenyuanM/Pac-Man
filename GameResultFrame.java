package Frame;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

import Account.*;
import HelpingClass.Constants;
import HelpingClass.WriteFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GameResultFrame: to show the game result

public class GameResultFrame {
    private JFrame _gameResultFrame;
    private JTextArea _ratePane;
    private JLabel _gameResultLabel;
    private JLabel _playAgainLabel;
    private JButton _yesButton;
    private JButton _noButton;
    private JPanel _centerPanel;
    private JPanel _southPanel;
    private Font _font;
    private AccountList _accountList;

    private int _restartGame;   // 0 = processing; 1 = play again; 2 = end

    // ================================ CONSTRUCTOR ============================

    /**
     * to create a game result frame
     * @param accountList the read account list
     * @param userName the input username
     * @param gameWin the game result
     * @param earnedPoint the total earned points
     * @param time the spent time
     */
    public GameResultFrame(AccountList accountList,String userName,boolean gameWin,int earnedPoint,String time){
        _font = new Font("TimesNewRoman",0,30);
        _restartGame = 0;
        _gameResultFrame = new JFrame();
        _accountList = accountList;

        updateAccountInfo(userName,_accountList,earnedPoint,time);
        _accountList.sortAccountList();
        createElement(userName,gameWin,earnedPoint,time);
        createPanel();
        updateFile();

        _gameResultFrame.add(_centerPanel,BorderLayout.CENTER);
        _gameResultFrame.add(_southPanel,BorderLayout.SOUTH);
        _gameResultFrame.setBackground(Color.BLACK);
        _gameResultFrame.setTitle("Game Result Window");
        _gameResultFrame.setSize(Constants.GAMERESULTFRAME_FRAME_WIDTH,Constants.GAMERESULTFRAME_FRAME_HEIGHT);
        _gameResultFrame.setLocationRelativeTo(null);
        _gameResultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _gameResultFrame.setVisible(true);
    }

    /**
     * to update the account information
     * @param username the user name
     * @param _accounts the whole account list
     * @param earnedPoint the earned point
     * @param time the time
     */
    private void updateAccountInfo(String username,AccountList _accounts,int earnedPoint,String time){
        Account account = _accounts.getAccount(username);
        String timePart = time.substring(6);
        if(account != null){
            account.updateEarnedPointandTime(earnedPoint,timePart);
        }
    }

    /**
     * to create all the elements in the frame
     * @param gameWin the game result
     * @param earnedPoint the total earned points
     * @param time the spent time
     */
    private void createElement(String username,boolean gameWin,int earnedPoint,String time){
        String gameResult;
        if(gameWin){
            gameResult= username + ", YOU WIN! Earned Point: " + earnedPoint + ", Time Spent: " + time;
        }
        else{
            gameResult = username + ", YOU LOSE. :(";
        }
        _gameResultLabel = new JLabel(gameResult);
        _gameResultLabel.setPreferredSize(new Dimension(Constants.GAMERESULTFRAME_FRAME_WIDTH - 100,100));
        _gameResultLabel.setForeground(Color.WHITE);
        _gameResultLabel.setBackground(Color.BLACK);
        _gameResultLabel.setFont(_font);

        _ratePane = new JTextArea(_accountList.toString());
        _ratePane.setBackground(Color.BLACK);
        _ratePane.setForeground(Color.WHITE);
        _ratePane.setFont(_font);
        _ratePane.setPreferredSize(new Dimension(Constants.GAMERESULTFRAME_FRAME_WIDTH - 100,300));

        _playAgainLabel = new JLabel("Play Again?");
        _playAgainLabel.setPreferredSize(new Dimension(Constants.GAMERESULTFRAME_FRAME_WIDTH - 100,100));
        _playAgainLabel.setForeground(Color.WHITE);
        _playAgainLabel.setBackground(Color.BLACK);
        _playAgainLabel.setFont(_font);

        _yesButton = new JButton("Yes?");
        _yesButton.setForeground(Color.WHITE);
        _yesButton.setBackground(Color.BLACK);
        _yesButton.setFont(_font);
        ActionListener yesListener = new yesButtonListener();
        _yesButton.addActionListener(yesListener);

        _noButton = new JButton("No?");
        _noButton.setForeground(Color.WHITE);
        _noButton.setBackground(Color.BLACK);
        _noButton.setFont(_font);
        ActionListener noListener = new noButtonListener();
        _noButton.addActionListener(noListener);
    }

    /**
     * to create the panels
     */
    private void createPanel(){
        _centerPanel = new JPanel();
        _centerPanel.setBackground(Color.black);
        _centerPanel.add(_gameResultLabel);
        _centerPanel.add(_ratePane);
        _centerPanel.add(_playAgainLabel);

        _southPanel = new JPanel(new GridLayout(1,2));
        _southPanel.setBackground(Color.black);
        _southPanel.add(_yesButton);
        _southPanel.add(_noButton);
    }

    /**
     * to write the new account list into the file
     */
    public void updateFile(){
        WriteFile writeFile = new WriteFile();
        writeFile.writeFile(Constants.ACCOUNT_FILE_NAME,_accountList);
    }

    /**
     * to get the restart game variable
     * @return the restart game variable
     */
    public int get_restartGame(){ return _restartGame;}

    // ============================== INNER CLASS ===============================
    class yesButtonListener implements ActionListener{

        /**
         * when the yes button is clicked, dispose the window and set restartgame = 1
         * @param e the ActionEvent object
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            _restartGame = 1;
            _gameResultFrame.dispose();
        }
    }

    class noButtonListener implements ActionListener{

        /**
         * when the no button is clicked, dispose the window and set restartgame = 2
         * @param e the ActionEvent object
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            _restartGame = 2;
            _gameResultFrame.dispose();
        }
    }
}
