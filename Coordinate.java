/**
 * to use as point or speed
 */

public class Coordinate {
    private double _x;
    private double _y;

    // ========================= CONSTRUCTORS FOR THIS CLASS ============================

    /**
     * two dimensional constructor for Coordinate
     * @param x the value of x direction
     * @param y the value of y direction
     */
    public Coordinate(double x, double y){
        _x = x;
        _y = y;
    }

    // ========================= COMMON METHODS USED OUTSIDE THE CLASS ============================

    public double distance(Coordinate p2){
        double inside = Math.pow((_x - p2.getX()),2) + Math.pow((_y - p2.getY()),2);
        return Math.sqrt(inside);
    }

    /**
     * to output the current coordinate with x, y and z
     */
    public void print(){
        System.out.print("Coordinate: (" + _x + "," + _y + ")");
    }

    public Coordinate add(Coordinate p2){
        return new Coordinate(_x + p2.getX(),_y + p2.getY());
    }

    public Coordinate subtract(Coordinate p2){
        return new Coordinate(_x - p2.getX(),_y - p2.getY());
    }

    public Coordinate multiple(double factor){
        return new Coordinate(_x * factor,_y * factor);
    }

    public Coordinate divide(double factor){
        return new Coordinate(_x / factor,_y / factor);
    }


    // ========================= ACCESSOR AND MUTATOR FOR PRIVATE VARIABLES ============================

    public String toString(){
        return "(" + _x + "," + _y + ")";
    }

    /**
     * to set the value of x, y, and z
     * @param x the value of x direction
     * @param y the value of y direction
     */
    public void setXY(double x,double y){
        _x = x;
        _y = y;
    }

    /**
     * to set the value of x
     * @param x the value of x direction
     */
    public void setX(double x){
        _x = x;

    }

    /**
     * to get the value of x direction
     * @return the value of x direction
     */
    public double getX(){
        return _x;
    }

    /**
     * to set the value of y
     * @param y the value of y direction
     */
    public void setY(double y){
        _y = y;
    }

    /**
     * to get the value of y direction
     * @return the value of y direction
     */
    public double getY(){
        return _y;
    }
}
