package Frame;

import Account.AccountList;
import HelpingClass.Constants;
import HelpingClass.WriteFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// LogInFrame class: make a Log in frame connecting with Sign up Frame

public class LogInFrame {
    // ===================================== PRIVATE VARIABLES =================================
    private JFrame _logInFrame;
    private SignUpFrame _signUpFrame;
    private JLabel _infoLabel;
    private JLabel _usernameLabel;
    private JLabel _passwordLabel;
    private JTextField _usernameField;
    private JPasswordField _passwordField;
    private JButton _logInButton;
    private JButton _registerButton;
    private JProgressBar _pbProgress;
    private Font _font;

    private boolean _frameExist;
    private AccountList _accounts;

    // ===================================== CONSTRUCTOR =======================================

    /**
     * Constructor: to create the log in frame
     * @param accountList the account list getting from reading file
     */
    public LogInFrame(AccountList accountList){
        _accounts = accountList;
        _frameExist = true;
        _font = new Font("TimesNewRoman",0,30);
        _logInFrame = new JFrame();

        createBackground();
        createLabelAndField();
        createButton();
        createPanels();

        _logInFrame.setTitle("Pac Man Log In Window");
        _logInFrame.setSize(Constants.LOGINFRAME_FRAME_WIDTH,Constants.LOGINFRAME_FRAME_HEIGHT);
        _logInFrame.setLocationRelativeTo(null);
        _logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _logInFrame.setVisible(true);
    }

    // ============================ PRIVATE METHODS ==========================================

    /**
     * to create the ContentPanel of the frame
     */
    private void createBackground(){
        _logInFrame.add(new ContentPanel());
    }

    /**
     * to create all the labels and fields in the frame
     */
    private void createLabelAndField(){
        _usernameLabel = new JLabel("Username");
        _usernameLabel.setForeground(Color.WHITE);
        _usernameLabel.setFont(_font);
        _usernameField = new JTextField(Constants.FIELD_WIDTH);
        _usernameField.setFont(_font);

        if(Constants.DEBUG) _usernameField.setText("Alice");

        _passwordLabel = new JLabel("Password");
        _passwordLabel.setForeground(Color.WHITE);
        _passwordLabel.setFont(_font);
        _passwordField = new JPasswordField(Constants.FIELD_WIDTH);
        _passwordField.setFont(_font);
        if(Constants.DEBUG) _passwordField.setText("123890");

        _infoLabel = new JLabel("Please log in / sign up");
        _infoLabel.setForeground(Color.WHITE);
        _infoLabel.setFont(_font);

        _pbProgress = new JProgressBar();
        _pbProgress.setBackground(Color.BLACK);
        _pbProgress.setForeground(Color.YELLOW);
        _pbProgress.setPreferredSize(new Dimension(500,30));
    }

    /**
     * to create all the buttons in the frame
     */
    private void createButton(){
        ActionListener logInListener = new LogInButtonListener();
        _logInButton = new JButton("Log In");
        _logInButton.setFont(_font);
        _logInButton.addActionListener(logInListener);

        ActionListener registerListener = new RegisterButtonListener();
        _registerButton = new JButton("Sign Up");
        _registerButton.setFont(_font);
        _registerButton.addActionListener(registerListener);
    }

    /**
     * to create the panels in the frame
     */
    private void createPanels(){
        JPanel infoPanel = new JPanel();
        infoPanel.add(_infoLabel);
        infoPanel.setBackground(new Color(0,0,0,255));

        JPanel usernamePanel = new JPanel();
        usernamePanel.add(_usernameLabel);
        usernamePanel.add(_usernameField);
        usernamePanel.setBackground(new Color(0,0,0,255));

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(_passwordLabel);
        passwordPanel.add(_passwordField);
        passwordPanel.setBackground(new Color(0,0,0,255));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(_logInButton);
        buttonPanel.add(_registerButton);
        buttonPanel.setBackground(new Color(0,0,0,255));

        JPanel progressBarPanel = new JPanel();
        progressBarPanel.add(_pbProgress);
        progressBarPanel.setBackground(new Color(0,0,0,255));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(usernamePanel,BorderLayout.NORTH);
        centerPanel.add(passwordPanel,BorderLayout.CENTER);
        centerPanel.add(buttonPanel,BorderLayout.SOUTH);
        centerPanel.setBackground(new Color(0,0,0,255));

        JPanel labelAndFieldPanel = new JPanel(new BorderLayout());
        labelAndFieldPanel.add(infoPanel, BorderLayout.NORTH);
        labelAndFieldPanel.add(centerPanel,BorderLayout.CENTER);
        labelAndFieldPanel.add(progressBarPanel,BorderLayout.SOUTH);
        labelAndFieldPanel.setBackground(new Color(0,0,0,255));

        _logInFrame.add(labelAndFieldPanel,BorderLayout.SOUTH);
    }

    /**
     * to set up the SignUpFrame
     */
    private void setUpSignUpFrame(){
        _signUpFrame = new SignUpFrame(_accounts,this);
    }

    // =========================== PACKAGE PRIVATE METHODS ===================================

    /**
     * to update the AccountList once there is a new registered account
     */
    void updateAccountList() {
        _accounts = _signUpFrame.getAccountList();
        WriteFile writeFile = new WriteFile();
        writeFile.writeFile(Constants.ACCOUNT_FILE_NAME,_accounts);
    }

    /**
     * to set the frame visible or not
     * @param visible a boolean to indicate the frame is visible or not
     */
    void setFrameVisible(boolean visible) {
        _logInFrame.setVisible(visible);
        if(Constants.DEBUG) System.out.println("Updated AccountList: ");
        if(Constants.DEBUG) _accounts.printAccountList();
    }

    // =============================== PUBLIC METHODS ==========================================

    /**
     * to get whether the frame is still existing or not
     * @return T if the frame exists; F if the frame doesn't exist anymore
     */
    public boolean get_frameExist(){return _frameExist;}


    // =============================== INNER CLASSES ===========================================

    class LogInButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = _usernameField.getText();
            String password = String.valueOf(_passwordField.getPassword());
            if(_accounts.logIn(username,password)){
                // username and password are correct
                _infoLabel.setText("Welcome back, " + username);
                _logInButton.setEnabled(false);
                _pbProgress.setStringPainted(true);
                ProgressWorker pw = new ProgressWorker();
                pw.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        String name = evt.getPropertyName();
                        if(name.equals("progress")){
                            int progress = (int) evt.getNewValue();
                            _pbProgress.setString(progress + "%");
                            _pbProgress.setValue(progress);
                        }
                        else if(name.equals("state")){
                            SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
                            switch(state){
                                case DONE:
                                    _logInButton.setEnabled(true);
                                    break;
                            }
                        }
                    }
                });
                pw.execute();
            }
            else{
                _infoLabel.setText("Invalid username / password. Please enter again.");
            }
        }
    }

    class RegisterButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            _logInFrame.setVisible(false);

            setUpSignUpFrame();
        }
    }

    class ProgressWorker extends SwingWorker<Object,Object>{

        @Override
        protected Object doInBackground() throws Exception {
            int i = 0;
            while(i < Constants.LOADING_MAXIMUM){
                i += 10;
                int progress = Math.round(((float)i / (float)Constants.LOADING_MAXIMUM) * 100f);
                setProgress(progress);
                try {
                    Thread.sleep(25);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            _logInFrame.dispose();
            _frameExist = false;
            return null;
        }
    }

    class ContentPanel extends JPanel{
        Image bgimage = null;

        ContentPanel(){
            MediaTracker mt = new MediaTracker(_logInFrame);
            bgimage = Toolkit.getDefaultToolkit().getImage(Constants.LOG_IN_WINDOW_BACKGROUND);
            mt.addImage(bgimage,0);
            try{
                mt.waitForAll();
            }
            catch(InterruptedException exception){
                exception.printStackTrace();
            }
        }

        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(bgimage,1,1,null);
        }
    }
}
