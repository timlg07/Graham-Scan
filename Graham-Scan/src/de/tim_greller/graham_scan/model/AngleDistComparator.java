package de.tim_greller.graham_scan.model;

import java.util.Comparator;

/**
 * Compares two points based on their position relative to the point reference.
 */
public class AngleDistComparator implements Comparator<Point> {
    
    private Point reference;
    
    /**
     * A Comparator for the given {@link Point}.
     * @param reference The point other points should get compared with.
     */
    public AngleDistComparator(Point reference) {
        this.reference = reference;
    }
    
    /**
     * Compares two points {@code p} and {@code q} according to the angle of
     * their vectors to p0. If the angle is equal, the vectors get compared by
     * size.
     * 
     * @param p The first {@link Point}.
     * @param q The second {@link Point}.
     * @return A value greater than zero if the vector from p0 to p comes before
     *         the vector from p0 to q if you sort them clockwise around p0 or 
     *         if all points are on one line and the vector to q is shorter than
     *         the one to p. Zero if both points are equal. A negative value 
     *         else.
     */
    public int compare(Point p, Point q) {
        int result = reference.leftOf(p, q);
        // All points on one line => Same angle -> Compare distance.
        if (result == 0) {
            result = (int) (calculateDistance(reference, p) 
                            - calculateDistance(reference, q));
        }
        return result;
    }
    
    /**
     * Calculates the distance between two {@link Point}s.
     * 
     * @param a The first {@link Point}.
     * @param b The second {@link Point}.
     * @return The magnitude of the vector from {@code a} to {@code b}.
     */
    public double calculateDistance(Point a, Point b) {
        int vectorX = b.getX() - a.getX();
        int vectorY = b.getY() - a.getY();
        return Math.sqrt(vectorX * vectorX + vectorY * vectorY);
    }
}
