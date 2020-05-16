/**
 * 
 */
package de.tim_greller.graham_scan.model;

/**
 * Represents a point in a two-dimensional integer coordinate system.
 */
public class Point implements Comparable<Point> {

    /** The x and y coordinates of the point. */
    private int x, y;
    
    /**
     * Creates a new Point with the given position.
     * 
     * @param x The first coordinate.
     * @param y The second coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns the x coordinate.
     * 
     * @return The first coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate.
     * 
     * @return The second coordinate.
     */
    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Point p) {
        int deltaX = getX() - p.getX();
        if (deltaX == 0) {
            return getY() - p.getY();
        } else {
            return deltaX;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof Point && compareTo((Point) o) == 0;
    }
}
