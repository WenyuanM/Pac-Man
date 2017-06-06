package Frame;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

import Character.Game_Character;
import Map.*;
import HelpingClass.Constants;
import HelpingClass.Coordinate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// GameFrame class: implements MouseMotionListener and KeyListener

public class GameFrame implements KeyListener{
    private JFrame _gameFrame;
    private JPanel _insidePanel;

    private JPanel _sideBarBottomPanel;
    private Font _font;
    private SideBar _sideBar;
    private JLabel _usernameLabel;
    private JLabel _gameResultLabel;
    private JLabel _timeLabel;
    private JLabel _pacManLivesLabel;
    private JLabel _pointLabel;
    private JLabel _ghostLabel;

    private JPanel _sideBarRightPanel;
    private JLabel[] _pacManInfo;
    private JLabel[] _ghost1Info;
    private JLabel[] _ghost2Info;
    private JLabel[] _ghost3Info;
    private JLabel[] _ghost4Info;

    private JComponent _mapComponent;
    private BufferedImage _offImg;

    private Date _startTime;
    private ArrayList<Long> _timePeriod;
    private Map _map;
    private Game_Character _characters;
    private Date _modeTime;
    private boolean _gameMode;  // T = Ghosts eat Pac Man || F = Pac Man eats Ghosts
    private boolean _gamePause;
    private boolean _gameResult;
    private boolean _frameExist;

    // ================================ CONSTRUCTOR ===============================================

    /**
     * to create the game frame
     * @param map the generated map
     * @param username the user name
     */
    public GameFrame(Map map,String username){
        _font = new Font("TimesNewRoman",0,25);
        _startTime = new Date();
        _timePeriod = new ArrayList<>();
        _gameFrame = new JFrame();
        _gameMode = true;
        _gamePause = false;
        _gameResult = false;
        _frameExist = true;
        _map = map;
        _sideBar = new SideBar(_map.get_totalPoint(),3,1,3,
                username);

        _characters = new Game_Character(_map,3,1);
        _offImg = new BufferedImage(Constants.GAMEFRAME_FRAME_WIDTH, Constants.GAMEFRAME_FRAME_HEIGHT,BufferedImage.TYPE_INT_RGB);
        drawOffImg();
        createInsidePanel();
        createSideBarBottomPanel();

        _gameFrame.addKeyListener(this);

        createSideBarRightPanel();

        _mapComponent = new MapComponent();
        _insidePanel.add(_mapComponent,BorderLayout.CENTER);

        SimpleDateFormat _timeFormat = new SimpleDateFormat("h:mm a");
        SimpleDateFormat _dateFormat = new SimpleDateFormat("EEE,d MMM yyyy");

        _gameFrame.getContentPane().add(_insidePanel);
        _gameFrame.setTitle("Pac Man Game");
        _gameFrame.pack();
        _gameFrame.setLocationRelativeTo(null);
        _gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _gameFrame.setVisible(true);
    }

    // =================================== PRIVATE METHODS ===================================

    /**
     * to create the inside panel of the frame
     */
    private void createInsidePanel(){
        _insidePanel = new JPanel();
        _insidePanel.setLayout(new BorderLayout());
        _insidePanel.setPreferredSize(new Dimension(Constants.GAMEFRAME_FRAME_WIDTH,Constants.GAMEFRAME_FRAME_HEIGHT));
    }

    /**
     * to create the bottom sidebar panel
     */
    private void createSideBarBottomPanel(){
        createSideBarBottomElement();
        _sideBarBottomPanel = new JPanel(new GridLayout(2,3));
        _sideBarBottomPanel.add(_usernameLabel);
        _sideBarBottomPanel.add(_gameResultLabel);
        _sideBarBottomPanel.add(_timeLabel);
        _sideBarBottomPanel.add(_pacManLivesLabel);
        _sideBarBottomPanel.add(_pointLabel);
        _sideBarBottomPanel.add(_ghostLabel);

        _sideBarBottomPanel.setBackground(Color.black);
        _sideBarBottomPanel.setPreferredSize(new Dimension(Constants.GAMEFRAME_FRAME_WIDTH - Constants.SIDEBAR_WIDTH,
                Constants.SIDEBAR_HEIGHT));
        _insidePanel.add(_sideBarBottomPanel, BorderLayout.SOUTH);
    }

    /**
     * to initialize all the bottom side bar elements
     */
    private void createSideBarBottomElement(){
        _usernameLabel = new JLabel("Username: " + _sideBar.get_playerName());
        _usernameLabel.setFont(_font);
        _usernameLabel.setForeground(Color.WHITE);

        _gameResultLabel = new JLabel("Game: " + _sideBar.get_gameResult());
        _gameResultLabel.setFont(_font);
        _gameResultLabel.setForeground(Color.WHITE);

        Date currentTime = new Date();
        long duration = currentTime.getTime() - _startTime.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        _timeLabel = new JLabel("Time: " + diffInMinutes + ":" + diffInSeconds);
        _timeLabel.setFont(_font);
        _timeLabel.setForeground(Color.WHITE);

        _pacManLivesLabel = new JLabel("Remain lives: " + String.valueOf(_sideBar.get_pacManLives()));
        _pacManLivesLabel.setFont(_font);
        _pacManLivesLabel.setForeground(Color.WHITE);

        _pointLabel = new JLabel("Points: " + _sideBar.get_earnedPoints() + "/" + _sideBar.get_totalPoints());
        _pointLabel.setFont(_font);
        _pointLabel.setForeground(Color.WHITE);

        _ghostLabel = new JLabel("Ghosts: " + _sideBar.get_numNormalEnemies() + "/" + _sideBar.get_numSmartEnemies());
        _ghostLabel.setFont(_font);
        _ghostLabel.setForeground(Color.WHITE);
    }

    /**
     * to create the right side bar panel
     */
    private void createSideBarRightPanel(){
        createSideBarRightElement();
        _sideBarRightPanel = new JPanel();

        for(int i=0;i<4;i++){
            _sideBarRightPanel.add(_pacManInfo[i]);
        }

        for(int i=0;i<4;i++){
            _sideBarRightPanel.add(_ghost1Info[i]);
        }

        for(int i=0;i<4;i++){
            _sideBarRightPanel.add(_ghost2Info[i]);
        }

        for(int i=0;i<4;i++){
            _sideBarRightPanel.add(_ghost3Info[i]);
        }

        for(int i=0;i<4;i++){
            _sideBarRightPanel.add(_ghost4Info[i]);
        }

        _sideBarRightPanel.setBackground(Color.BLACK);
        _sideBarRightPanel.setPreferredSize(new Dimension(Constants.SIDEBAR_WIDTH,Constants.GAMEFRAME_FRAME_HEIGHT));
        _insidePanel.add(_sideBarRightPanel, BorderLayout.EAST);
    }

    /**
     * to create all the elements inside the right sidebar panel
     */
    private void createSideBarRightElement(){
        _pacManInfo = new JLabel[4];
        _ghost1Info = new JLabel[4];
        _ghost2Info = new JLabel[4];
        _ghost3Info = new JLabel[4];
        _ghost4Info = new JLabel[4];

        _pacManInfo[0] = new JLabel("Pac Man: ");
        _pacManInfo[1] = new JLabel("Status: " + _characters.get_status(-1));
        _pacManInfo[2] = new JLabel("Moving Direction: " + _characters.get_movingDirection(-1));
        _pacManInfo[3] = new JLabel("Speed: " + Constants.PACMAN_SPEED);

        _ghost1Info[0] = new JLabel("Ghost Red: ");
        _ghost1Info[1] = new JLabel("Status: " + _characters.get_status(0));
        _ghost1Info[2] = new JLabel("Moving Direction: " + _characters.get_movingDirection(0));
        _ghost1Info[3] = new JLabel("Speed: " + Constants.GHOST_SPEED);

        _ghost2Info[0] = new JLabel("Ghost Pink: ");
        _ghost2Info[1] = new JLabel("Status: " + _characters.get_status(1));
        _ghost2Info[2] = new JLabel("Moving Direction: " + _characters.get_movingDirection(1));
        _ghost2Info[3] = new JLabel("Speed: " + Constants.GHOST_SPEED);

        _ghost3Info[0] = new JLabel("Ghost Blue: ");
        _ghost3Info[1] = new JLabel("Status: " + _characters.get_status(2));
        _ghost3Info[2] = new JLabel("Moving Direction: " + _characters.get_movingDirection(2));
        _ghost3Info[3] = new JLabel("Speed: " + Constants.GHOST_SPEED);

        _ghost4Info[0] = new JLabel("Ghost Orange: ");
        _ghost4Info[1] = new JLabel("Status: " + _characters.get_status(3));
        _ghost4Info[2] = new JLabel("Moving Direction: " + _characters.get_movingDirection(3));
        _ghost4Info[3] = new JLabel("Speed: " + Constants.GHOST_SPEED);

        for(int i=0;i<4;i++){
            _pacManInfo[i].setBackground(Color.black);
            _pacManInfo[i].setFont(_font);
            _pacManInfo[i].setForeground(Color.WHITE);
            _pacManInfo[i].setPreferredSize(new Dimension(Constants.SIDEBAR_WIDTH,50));

            _ghost1Info[i].setBackground(Color.black);
            _ghost1Info[i].setFont(_font);
            _ghost1Info[i].setForeground(Color.WHITE);
            _ghost1Info[i].setPreferredSize(new Dimension(Constants.SIDEBAR_WIDTH,50));

            _ghost2Info[i].setBackground(Color.black);
            _ghost2Info[i].setFont(_font);
            _ghost2Info[i].setForeground(Color.WHITE);
            _ghost2Info[i].setPreferredSize(new Dimension(Constants.SIDEBAR_WIDTH,50));

            _ghost3Info[i].setBackground(Color.black);
            _ghost3Info[i].setFont(_font);
            _ghost3Info[i].setForeground(Color.WHITE);
            _ghost3Info[i].setPreferredSize(new Dimension(Constants.SIDEBAR_WIDTH,50));

            _ghost4Info[i].setBackground(Color.black);
            _ghost4Info[i].setFont(_font);
            _ghost4Info[i].setForeground(Color.WHITE);
            _ghost4Info[i].setPreferredSize(new Dimension(Constants.SIDEBAR_WIDTH,50));
        }
    }

    /**
     * to create all the elements inside the right sidebar panel
     */
    private void updateSideBarRightElement(){
        _pacManInfo[0].setText("Pac Man: ");
        _pacManInfo[1].setText("Status: " + _characters.get_status(-1));
        _pacManInfo[2].setText("Moving Direction: " + _characters.get_movingDirection(-1));
        _pacManInfo[3].setText("Speed: " + Constants.PACMAN_SPEED);

        _ghost1Info[0].setText("Ghost Red: ");
        _ghost1Info[1].setText("Status: " + _characters.get_status(0));
        _ghost1Info[2].setText("Moving Direction: " + _characters.get_movingDirection(0));
        _ghost1Info[3].setText("Speed: " + Constants.GHOST_SPEED);

        _ghost2Info[0].setText("Ghost Pink: ");
        _ghost2Info[1].setText("Status: " + _characters.get_status(1));
        _ghost2Info[2].setText("Moving Direction: " + _characters.get_movingDirection(1));
        _ghost2Info[3].setText("Speed: " + Constants.GHOST_SPEED);

        _ghost3Info[0].setText("Ghost Blue: ");
        _ghost3Info[1].setText("Status: " + _characters.get_status(2));
        _ghost3Info[2].setText("Moving Direction: " + _characters.get_movingDirection(2));
        _ghost3Info[3].setText("Speed: " + Constants.GHOST_SPEED);

        _ghost4Info[0].setText("Ghost Orange: ");
        _ghost4Info[1].setText("Status: " + _characters.get_status(3));
        _ghost4Info[2].setText("Moving Direction: " + _characters.get_movingDirection(3));
        _ghost4Info[3].setText("Speed: " + Constants.GHOST_SPEED);
    }

    /**
     * to update the bottom side bar element
     */
    private void updateSideBarBottom(){
        _gameResultLabel.setText("Game: " + _sideBar.get_gameResult());
        long duration = 0;
        if(!_gamePause){
            Date currentTime = new Date();
            duration = currentTime.getTime() - _startTime.getTime();
        }
        for(long timePeriod:_timePeriod) duration += timePeriod;
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        _timeLabel.setText("Time: " + diffInMinutes + ":" + diffInSeconds);
        _pacManLivesLabel.setText("Remain lives: " + _sideBar.get_pacManLives());
        _pointLabel.setText("Points: " + _sideBar.get_earnedPoints() + "/" + _sideBar.get_totalPoints());
    }

    /**
     * to draw the off image of the map
     */
    private void drawOffImg(){
        Graphics offShape = _offImg.createGraphics();
        drawMap(offShape);
    }

    /**
     * to draw all the grid in the map to the off image
     * @param shape the off image Graphics object
     */
    private void drawMap(Graphics shape){
        int mapSize = _map.get_mapSize();
        for(int i=0;i<mapSize;i++){
            Grid g = _map.get_grid(i);
            Coordinate leftTop = g.get_position();
            Coordinate size = g.get_size();
            String type = g.get_type();
            drawSingleGrid(shape,leftTop,size,type);
        }
    }

    /**
     * to draw the single grid in the map onto the off image
     * @param shape the off image Graphics object
     * @param leftTop the left top position of the grid
     * @param size the size of the grid
     * @param type the type of the grid
     */
    private void drawSingleGrid(Graphics shape,Coordinate leftTop,Coordinate size,String type){
        switch (type) {
            // if the grid is a block
            case "|":
                shape.setColor(Color.BLUE);
                shape.fillRect((int) leftTop.getX() + Constants.DRAWING_ADJUST, (int) leftTop.getY() + Constants.DRAWING_ADJUST,
                        (int) size.getX() + Constants.DRAWING_ADJUST, (int) size.getY() + Constants.DRAWING_ADJUST);
                break;
            // if the grid is a transition grid
            case "x":
                shape.setColor(Color.ORANGE);
                shape.fillRect((int) leftTop.getX() + Constants.DRAWING_ADJUST, (int) leftTop.getY() + Constants.DRAWING_ADJUST,
                        (int) size.getX() + Constants.DRAWING_ADJUST, (int) size.getY() + Constants.DRAWING_ADJUST);
                break;
            // if the grid is a road
            default:
                shape.setColor(Color.BLACK);
                shape.fillRect((int) leftTop.getX() + Constants.DRAWING_ADJUST, (int) leftTop.getY() + Constants.DRAWING_ADJUST,
                        (int) size.getX() + Constants.DRAWING_ADJUST, (int) size.getY() + Constants.DRAWING_ADJUST);
                break;
        }
    }


    /**
     * to update the pac man based on the given moving direciton
     * @param code the number indicates the moving direction of the pac man
     */
    private void updatePacMan(int code){
        int earnPoint = _characters.update_PacMan(code);
        _sideBar.add_earnedPoints(earnPoint);
        if(earnPoint >= 2){
            // eat the big dot
            _gameMode = false;
            Calendar currentCalendar = Calendar.getInstance();
            _modeTime = currentCalendar.getTime();
        }
    }

    /**
     * for pausing the game, to store the current game time
     */
    private void storeGameTime(){
        Date current = new Date();
        long duration = current.getTime() - _startTime.getTime();
        _timePeriod.add(duration);
    }

    /**
     * to get game result
     * @return the game result
     */
    public boolean get_gameResult(){ return _gameResult;}

    /**
     * to get the total earned point
     * @return the total earned point
     */
    public int get_earnedPoint() {return _sideBar.get_earnedPoints();}

    /**
     * to get the total time
     * @return the total time
     */
    public String get_time(){return _timeLabel.getText();}

    /**
     * to get whether the frame is existing
     * @return _frameExist variable
     */
    public boolean get_frameExist(){return _frameExist;}

    // ============================== KeyListener =====================================

    /**
     * LEAVE BLANK
     * @param e the keyEvent object
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // LEAVE BLANK
    }

    /**
     * to get the pressed key and update the character or game mode
     * @param e the keyEvent object
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // to pause the game, P
        if(code == 80){
            _gamePause = !_gamePause;
            if(_gamePause){
                _sideBar.set_gameResult("Game Paused");
                storeGameTime();
            }
            else{
                _sideBar.set_gameResult("Game Playing");
                _startTime = new Date();
            }
            updateSideBarBottom();
        }
        // to end the game, Z
        else if(code == 90){
            _map.endGame();
            _mapComponent.repaint();
        }
        else{
            updatePacMan(code);
        }
    }

    /**
     * LEAVE BLANK
     * @param e the keyEvent object
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // LEAVE BLANK
    }

    // =================================== INNER CLASS ========================================
    private class MapComponent extends JComponent{
        /**
         * Constructor of MapComponent: to draw characters and update the game in a thread
         */
        MapComponent(){
            Thread animationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(_sideBar.get_pacManLives() > 0 && _map.gameContinue()){
                        if(!_gamePause){
                            updateSideBarBottom();
                            updateSideBarRightElement();
                            _characters.update_NormalGhosts();
                            _characters.update_SmartGhosts(_gameMode);
                            if(_gameMode){
                                if(_characters.checkPacManDies()){
                                    _sideBar.decre_pacManLives();
                                    _characters.restartCharacters();
                                }
                            }
                            else{
                                int number = _characters.checkGhostsDies();
                                if(number != 0){
                                    _sideBar.add_earnedPoints(number * 100);
                                }
                                Calendar currentCalendar = Calendar.getInstance();
                                Date time = currentCalendar.getTime();
                                if((time.getTime() - _modeTime.getTime()) > Constants.MODE_PERIOD){
                                    _gameMode = true;
                                }
                            }
                            repaint();
                            try{
                                Thread.sleep(50);
                            }
                            catch(Exception ignored){}
                        }
                    }
                    if(_sideBar.get_pacManLives() == 0){
                        _gameResult = false;
                    }
                    else{
                        _gameResult = true;
                    }
                    _frameExist = false;
                    _gameFrame.dispose();
                }
            });
            animationThread.start();
        }

        /**
         * to draw the images
         * @param shape the Graphics object
         */
        public void paintComponent(Graphics shape){
            shape.drawImage(_offImg,0,0,null);
            drawCharacters(shape);
            drawFood(shape);
        }

        /**
         * to draw the characters
         * @param shape the Graphics object
         */
        private void drawCharacters(Graphics shape){
            _characters.drawCharacters(shape,_gameMode);
        }

        /**
         * to draw all the food dots
         * @param shape the Graphics object
         */
        private void drawFood(Graphics shape){
            int mapSize = _map.get_mapSize();
            if(Constants.DEBUG) System.out.println("Map Size: " + mapSize);
            for(int i=0;i<mapSize;i++){
                Grid g = _map.get_grid(i);
                if(g.get_containFood()){
                    Coordinate leftTop = g.get_position();
                    Coordinate size = g.get_size();
                    String type = g.get_type();
                    drawSingleFood(shape,leftTop,size,type);
                }
            }
        }

        /**
         * to draw a single food dot
         * @param shape the Graphics object
         * @param leftTop the leftTop position of the grid
         * @param size the size of the grid
         * @param type the type of the grid
         */
        private void drawSingleFood(Graphics shape,Coordinate leftTop,Coordinate size,String type){
            shape.setColor(Color.YELLOW);
            if(type.equals(".") || type.equals("-")){
                int x = (int)(leftTop.getX() + size.getX() / 2);
                int y = (int)(leftTop.getY() + size.getY() / 2);
                shape.fillOval(x,y,Constants.SMALL_FOOD_SIZE,Constants.SMALL_FOOD_SIZE);
            }
            if(type.equals("o")){
                int x = (int)(leftTop.getX() + size.getX() / 2);
                int y = (int)(leftTop.getY() + size.getY() / 2);
                shape.fillOval(x,y,Constants.BIG_FOOD_SIZE,Constants.BIG_FOOD_SIZE);
            }
        }
    }
}
