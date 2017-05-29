import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


public class GameFrame implements MouseMotionListener,KeyListener{
    private JFrame _gameFrame;
    private JPanel _insidePanel;
    private JComponent _mapComponent;
    private BufferedImage _offImg;

    private Map _map;
    private Game_Character _characters;
    private int _gamePoint;
    private boolean _gameMode;  // T = Ghosts eat Pac Man || F = Pac Man eats Ghosts

    public GameFrame(Map map){
        _gameFrame = new JFrame();
        _gamePoint = 0;
        _map = map;
        _characters = new Game_Character(_map,4,0);
        _offImg = new BufferedImage(Constants.GAMEFRAME_FRAME_WIDTH, Constants.GAMEFRAME_FRAME_HEIGHT,BufferedImage.TYPE_INT_RGB);
        drawOffImg();
        _mapComponent = new MapComponent();
        createInsidePanel();

        _gameFrame.addMouseMotionListener(this);
        _gameFrame.addKeyListener(this);

        _gameFrame.getContentPane().add(_insidePanel);
        _gameFrame.setTitle("Pac Man Game");
        _gameFrame.pack();
        _gameFrame.setLocationRelativeTo(null);
        _gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _gameFrame.setVisible(true);
    }

    private void createInsidePanel(){
        _insidePanel = new JPanel();
        _insidePanel.setLayout(new BorderLayout());
        _insidePanel.setPreferredSize(new Dimension(Constants.GAMEFRAME_FRAME_WIDTH,Constants.GAMEFRAME_FRAME_HEIGHT));
        _insidePanel.add(_mapComponent,BorderLayout.CENTER);
    }

    private void drawOffImg(){
        Graphics offShape = _offImg.createGraphics();
        drawMap(offShape);
    }

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

    private void drawSingleGrid(Graphics shape,Coordinate leftTop,Coordinate size,String type){
        if(type.equals("|")){
            shape.setColor(Color.BLUE);
            shape.fillRect((int)leftTop.getX() + Constants.DRAWING_ADJUST,(int)leftTop.getY() + Constants.DRAWING_ADJUST,
                    (int)size.getX() + Constants.DRAWING_ADJUST,(int)size.getY() + Constants.DRAWING_ADJUST);
        }
        else{
            shape.setColor(Color.BLACK);
            shape.fillRect((int)leftTop.getX() + Constants.DRAWING_ADJUST,(int)leftTop.getY() + Constants.DRAWING_ADJUST,
                    (int)size.getX() + Constants.DRAWING_ADJUST,(int)size.getY() + Constants.DRAWING_ADJUST);
        }
    }

    // ========================== MouseMotionListener ================================
    @Override
    public void mouseDragged(MouseEvent e) {
        // LEAVE BLANK
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("(" + e.getX() + "," + e.getY() + ")");
    }

    // ============================== KeyListener =====================================
    @Override
    public void keyTyped(KeyEvent e) {
        // LEAVE BLANK
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        updatePacMan(code);
//        _characters.update_NormalGhosts();
//        _mapComponent.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // LEAVE BLANK
    }

    private void updatePacMan(int code){
        _gamePoint += _characters.update_PacMan(code);
    }

    private class MapComponent extends JComponent{
        public MapComponent(){
            Thread animationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        _characters.update_NormalGhosts();
                        repaint();
                        try{
                            Thread.sleep(50);
                        }
                        catch(Exception exception){}
                    }
                }
            });
            animationThread.start();
        }

        public void paintComponent(Graphics shape){
            shape.drawImage(_offImg,0,0,null);
            drawCharacters(shape);
            drawFood(shape);
        }

        private void drawCharacters(Graphics shape){
            _characters.drawCharacters(shape);
        }

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

        private void drawSingleFood(Graphics shape,Coordinate leftTop,Coordinate size,String type){
            shape.setColor(Color.YELLOW);
            if(type.equals(".") || type.equals("-")){
                int x = (int)(leftTop.getX() + size.getX() / 2);
                int y = (int)(leftTop.getY() + size.getY() / 2);
                shape.fillOval(x,y,Constants.SMALL_FOOD_SIZE,Constants.SMALL_FOOD_SIZE);
            }
        }
    }
}
