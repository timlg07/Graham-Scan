/**
 * 
 */
package de.tim_greller.convex_hull.model;

/**
 * Represents a point in a two-dimensional integer coordinate system.
 */
public class Point {

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
}
