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

    private Character _pacMan;
    private Map _map;
    private int _gamePoint;

    public GameFrame(Map map){
        _gameFrame = new JFrame();
        _gamePoint = 0;
        _map = map;
        _pacMan = new Character(generatePacManStartPos(),new Coordinate(0,0),Constants.GRID_ROW,Constants.GRID_COL,'P');
        _offImg = new BufferedImage(Constants.GAMEFRAME_FRAME_WIDTH,
                (Constants.GAMEFRAME_FRAME_HEIGHT - Constants.SIDEBAR_HEIGHT),BufferedImage.TYPE_INT_RGB);
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

    private Coordinate generatePacManStartPos(){
        Grid grid = _map.get_grid(Constants.GRID_ROW,Constants.GRID_COL);
        Coordinate pos = grid.get_position();
        Coordinate size = grid.get_size();
        size = size.divide(2);
        Coordinate position = pos.add(size);
        position = position.add(new Coordinate(0,Constants.SIDEBAR_HEIGHT));
        return position;
    }

    private void drawOffImg(){
        Graphics offShape = _offImg.createGraphics();
        drawMap(offShape);
    }

    private void drawMap(Graphics shape){
        int mapSize = _map.get_mapSize();
        if(Constants.DEBUG) System.out.println("Map Size: " + mapSize);
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
//            shape.fillRoundRect((int)leftTop.getX() + Constants.DRAWING_ADJUST,(int)leftTop.getY() + Constants.DRAWING_ADJUST,
//                    (int)size.getX() + Constants.DRAWING_ADJUST,(int)size.getY() + Constants.DRAWING_ADJUST,
//                    -(int)(size.getX()/2),-(int)(size.getX()/2));
            shape.fillRect((int)leftTop.getX() + Constants.DRAWING_ADJUST,(int)leftTop.getY() + Constants.DRAWING_ADJUST,
                    (int)size.getX() + Constants.DRAWING_ADJUST,(int)size.getY() + Constants.DRAWING_ADJUST);
        }
        else{
            shape.setColor(Color.BLACK);
//            shape.fillRoundRect((int)leftTop.getX() + Constants.DRAWING_ADJUST,(int)leftTop.getY() + Constants.DRAWING_ADJUST,
//                    (int)size.getX() + Constants.DRAWING_ADJUST,(int)size.getY() + Constants.DRAWING_ADJUST,
//                    (int)(size.getX()/2),(int)(size.getX()/2));
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
        System.out.println("Key: " + code);
        _pacMan.update(code);
        System.out.println(_pacMan.get_position().add(new Coordinate(0,Constants.SIDEBAR_HEIGHT)));
        int index = _map.findGridIndex(_pacMan.get_position(),_pacMan.get_row(),_pacMan.get_col());
        _pacMan.set_row(index / _map.get_mapCol());
        _pacMan.set_col(index % _map.get_mapCol());
        _map.updateFood(_pacMan.get_position(),_pacMan.get_row(),_pacMan.get_col());
        _mapComponent.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // LEAVE BLANK
    }


    private class MapComponent extends JComponent{
        public void paintComponent(Graphics shape){
            shape.drawImage(_offImg,0,Constants.SIDEBAR_HEIGHT,null);
            drawCharacters(shape);
            drawFood(shape);
        }

        private void drawCharacters(Graphics shape){
            BufferedImage image = _pacMan.get_images();
            Coordinate position = _pacMan.get_position();
//            int row = _pacMan.get_row();
//            int col = _pacMan.get_col();
//            Coordinate size = _map.get_grid(row,col).get_size();
            int size = Constants.CHARACTER_IMAGE_SIZE;
            shape.drawImage(image,(int)(position.getX()-size/2),(int)(position.getY()-size/2),size,size,null);
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
            if(type.equals(".")){
                int x = (int)(leftTop.getX() + size.getX() / 2);
                int y = (int)(leftTop.getY() + Constants.SIDEBAR_HEIGHT + size.getY() / 2);
                shape.fillOval(x,y,Constants.SMALL_FOOD_SIZE,Constants.SMALL_FOOD_SIZE);
            }
        }

    }
}
