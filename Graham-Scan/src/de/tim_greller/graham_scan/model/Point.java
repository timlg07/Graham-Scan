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
    
    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        return o instanceof Point && compareTo((Point) o) == 0;
    }
    
    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        // Avoid losing information due to overflow while still using shifts for
        // better performance is possible with prime = 31: x * 31 == x << 5 - x
        final int prime = 31;
        return (prime + x) * prime + y;
    }
    
    /**
     * Generates the string representation in the format (x|y).
     * 
     * @return The String representation of this point.
     */
    @Override
    public String toString() {
        return "(" + getX() + "|" + getY() + ")";
    }

    /**
     * Calculates if the point {@code p} is Left of the vector from {@code this}
     * to {@code q}.
     * 
     * @param p The point which is either left, right or on the line.
     * @param q The point forming the line with this.
     * @return A value greater than zero if {@code p} is left of the line, zero 
     *         if it is on the line and a negative value if it is right of the 
     *         line.
     */
    public int leftOf(Point p, Point q) {
        return ( (q.getX() - getX()) * (p.getY() - getY()) 
               - (q.getY() - getY()) * (p.getX() - getX()));
    }
}
