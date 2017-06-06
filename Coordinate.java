package HelpingClass;

/**
 * Project: Pac Man Game (Final Project of CS 3B Java Course in Pasadena City College)
 * Author: Wenyuan Ma
 * Final Edit Date: June 6 2017
 */

// Coordinate Class: to manage a point in a coordinate system (x,y)

public class Coordinate {
    private double _x;
    private double _y;

    // ====================================== CONSTRUCTORS ======================================

    /**
     * Constructor: to initialize the point with given x and y
     * @param x the value of x direction
     * @param y the value of y direction
     */
    public Coordinate(double x, double y){
        _x = x;
        _y = y;
    }

    // ==================================== PUBLIC METHODS ========================================

    /**
     * to find the distance between two points (this point and the input point)
     * @param p2 the new input point
     * @return the distance in DOUBLE type
     */
    public double distance(Coordinate p2){
        double inside = Math.pow((_x - p2.getX()),2) + Math.pow((_y - p2.getY()),2);
        return Math.sqrt(inside);
    }

    /**
     * to add two coordinates
     * @param p2 the second coordinate
     * @return the sum coordinate
     */
    public Coordinate add(Coordinate p2){
        return new Coordinate(_x + p2.getX(),_y + p2.getY());
    }

    /**
     * to subtract two coordinates
     * @param p2 the second coordinate
     * @return the result coordinate
     */
    public Coordinate subtract(Coordinate p2){
        return new Coordinate(_x - p2.getX(),_y - p2.getY());
    }

    /**
     * to multiple the coordinate and the factor
     * @param factor the number needed to be multipled
     * @return the result coordinate
     */
    public Coordinate multiple(double factor){
        return new Coordinate(_x * factor,_y * factor);
    }

    /**
     * to divide the coordinate and the factor
     * @param factor the number needed to be divided
     * @return the result coordinate
     */
    public Coordinate divide(double factor){
        return new Coordinate(_x / factor,_y / factor);
    }

    /**
     * to get the coordinate as a STRING type
     * @return the info as String type
     */
    public String toString(){
        return "(" + _x + "," + _y + ")";
    }

    // ======================================= Mutator ========================================

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
     * to set the value of y
     * @param y the value of y direction
     */
    public void setY(double y){
        _y = y;
    }

    // =========================================== Accessor =========================================

    /**
     * to get the value of x direction
     * @return the value of x direction
     */
    public double getX(){
        return _x;
    }

    /**
     * to get the value of y direction
     * @return the value of y direction
     */
    public double getY(){
        return _y;
    }
}
