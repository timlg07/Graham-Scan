package de.tim_greller.graham_scan.model;

/**
 * Represents a point in a two-dimensional integer coordinate system.
 */
public class Point implements Comparable<Point> {

    /** 
     * The x coordinate of the point. 
     */
    private int x;
    
    /** 
     * The y coordinate of the point. 
     */
    private int y;

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

    /** 
     * <p>Compares two points using the x values first and if they are equal
     * comparing their y values.</p> 
     * {@inheritDoc} 
     */
    @Override
    public int compareTo(Point p) {
        int deltaX = getX() - p.getX();
        if (deltaX == 0) {
            return getY() - p.getY();
        } else {
            return deltaX;
        }
    }

    /**
     * <p>Two points are equal if both x and both y values are equal.</p>
     *  {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Point && compareTo((Point) o) == 0;
    }

    /** 
     * {@inheritDoc} 
     */
    @Override
    public int hashCode() {
        // Avoid losing information due to overflow while still using shifts for
        // better performance is possible with prime = 31: x * 31 == x << 5 - x
        final int prime = 31;
        return (prime + x) * prime + y;
    }

    /** 
     * Generates the string representation in the format {@code (x, y)}. 
     */
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    /**
     * Calculates if the point {@code p} is left of the vector from {@code this}
     * to {@code q}.
     * 
     * @param p The point which is either left, right or on the line.
     * @param q The point forming the line with this.
     * @return A value greater than zero if {@code p} is left of the line, zero 
     *         if it is on the line and a negative value if it is right of the 
     *         line.
     */
    public int leftOf(Point p, Point q) {
        return (q.getX() - getX()) * (p.getY() - getY()) 
             - (q.getY() - getY()) * (p.getX() - getX());
    }

    /**
     * Calculates the distance between this and another {@link Point}.
     * 
     * @param p The second {@link Point}.
     * @return The magnitude of the vector from {@code this} to {@code p}.
     */
    public double distanceTo(Point p) {
        int vectorX = p.getX() - getX();
        int vectorY = p.getY() - getY();
        return Math.sqrt(vectorX * vectorX + vectorY * vectorY);
    }
}
