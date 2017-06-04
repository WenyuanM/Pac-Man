package Frame;

import Account.*;
import HelpingClass.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// SignUpFrame class: to create a sign up frame

public class SignUpFrame {
    private JFrame _signUpFrame;
    private LogInFrame _loginFrame;
    private JLabel _infoLabel;
    private JLabel _usernameLabel;
    private JLabel _passwordLabel1;
    private JLabel _passwordLabel2;
    private JTextField _usernameField;
    private JPasswordField _passwordField1;
    private JPasswordField _passwordField2;
    private JButton _registerButton;
    private JProgressBar _pbProgress;
    private Font _font;

    private AccountList _accounts;

    // ================================== CONSTRUCTOR ========================================

    /**
     * to create the sign up frame
     * @param accountList the generated account list and the created logIn frame
     * @param loginFrame the created LogIn frame
     */
    SignUpFrame(AccountList accountList, LogInFrame loginFrame){
        _accounts = accountList;
        _loginFrame = loginFrame;
        _signUpFrame = new JFrame();
        _font = new Font("TimesNewRoman",0,30);

        createBackground();
        createLabelAndField();
        createButton();
        createPanels();

        _signUpFrame.setTitle("Sign Up Window");
        _signUpFrame.setSize(Constants.SIGNUPFRAME_FRAME_WIDTH, Constants.SIGNUPFRAME_FRAME_HEIGHT);
        _signUpFrame.setLocationRelativeTo(null);
        _signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _signUpFrame.setVisible(true);
    }

    // =============================== PRIVATE METHODS =========================================

    /**
     * to create the ContentPanel of the frame
     */
    private void createBackground(){
        _signUpFrame.add(new ContentPanel());
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

        _passwordLabel1 = new JLabel("Password");
        _passwordLabel1.setForeground(Color.WHITE);
        _passwordLabel1.setFont(_font);

        _passwordLabel2= new JLabel("Confirm Password");
        _passwordLabel2.setFont(_font);
        _passwordLabel2.setForeground(Color.WHITE);

        _passwordField1 = new JPasswordField(Constants.FIELD_WIDTH);
        _passwordField1.setFont(_font);
        _passwordField2 = new JPasswordField(Constants.FIELD_WIDTH);
        _passwordField2.setFont(_font);

        _infoLabel = new JLabel("Please sign up");
        _infoLabel.setForeground(Color.WHITE);
        _infoLabel.setFont(_font);

        _pbProgress = new JProgressBar();
        _pbProgress.setForeground(Color.YELLOW);
        _pbProgress.setBackground(Color.BLACK);
        _pbProgress.setPreferredSize(new Dimension(500,30));
    }

    /**
     * to create the buttons in the frame
     */
    private void createButton(){
        ActionListener registerListener = new RegisterButtonListener();
        _registerButton = new JButton("Sign Up");
        _registerButton.setFont(_font);
        _registerButton.addActionListener(registerListener);
    }

    /**
     * to create the panels to organize all the elements in the frame
     */
    private void createPanels(){
        JPanel infoPanel = new JPanel();
        infoPanel.add(_infoLabel);
        infoPanel.setBackground(new Color(0,0,0,255));

        JPanel gridPanel = new JPanel(new GridLayout(3,2,1,1));
        gridPanel.add(_usernameLabel);
        gridPanel.add(_usernameField);
        gridPanel.add(_passwordLabel1);
        gridPanel.add(_passwordField1);
        gridPanel.add(_passwordLabel2);
        gridPanel.add(_passwordField2);
        gridPanel.setBackground(new Color(0,0,0,255));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(_registerButton);
        buttonPanel.setBackground(new Color(0,0,0,255));

        JPanel processBarPanel = new JPanel();
        processBarPanel.add(_pbProgress);
        processBarPanel.setBackground(new Color(0,0,0,255));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel,BorderLayout.NORTH);
        southPanel.add(processBarPanel,BorderLayout.CENTER);
        southPanel.setBackground(new Color(0,0,0,255));

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setBackground(new Color(0,0,0,255));

        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setBackground(new Color(0,0,0,255));

        JPanel labelAndFieldPanel = new JPanel(new BorderLayout());
        labelAndFieldPanel.add(infoPanel, BorderLayout.NORTH);
        labelAndFieldPanel.add(gridPanel,BorderLayout.CENTER);
        labelAndFieldPanel.add(southPanel,BorderLayout.SOUTH);
        labelAndFieldPanel.add(emptyPanel1,BorderLayout.WEST);
        labelAndFieldPanel.add(emptyPanel2,BorderLayout.EAST);
        labelAndFieldPanel.setBackground(new Color(0,0,0,255));

        _signUpFrame.add(labelAndFieldPanel,BorderLayout.SOUTH);
    }

    // ========================== PACKAGE PRIVATE METHODS =================================

    /**
     * to get the current accountList
     * @return the current accountList
     */
    AccountList getAccountList() {return _accounts;}


    // =================================== INNER CLASSES ==================================

    class RegisterButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = _usernameField.getText();
            String password1 = String.valueOf(_passwordField1.getPassword());
            String password2 = String.valueOf(_passwordField2.getPassword());
            if(!password1.equals(password2)){
                _infoLabel.setText("Two input passwords are different! Please enter again.");
                _usernameField.setText("");
                _passwordField1.setText("");
                _passwordField2.setText("");
            }
            else{
                Account newAccount = new Account();
                if(newAccount.createAccount(username,password1)){
                    _accounts.addAccount(newAccount);
                    _infoLabel.setText("Signed up successfully!");
                    _registerButton.setEnabled(false);
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
                                        _registerButton.setEnabled(true);
                                        break;
                                }
                            }
                        }
                    });
                    pw.execute();
                }
                else{
                    _infoLabel.setText("The input username or password is not valid. Length must be between 1 - 10.");
                }
            }
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
            _loginFrame.updateAccountList();
            _loginFrame.setFrameVisible(true);
            _signUpFrame.dispose();
            return null;
        }
    }

    class ContentPanel extends JPanel{
        Image bgimage = null;

        ContentPanel(){
            MediaTracker mt = new MediaTracker(_signUpFrame);
            bgimage = Toolkit.getDefaultToolkit().getImage(Constants.SIGN_UP_WINDOW_BACKGROUND);
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
