package de.tim_greller.graham_scan.model;

import java.util.Comparator;

/**
 * Compares two points based on their position relative to the reference point.
 */
public class AngleDistComparator implements Comparator<Point> {

    private Point reference;

    /**
     * A Comparator for the given {@link Point}.
     * 
     * @param reference The point other points should get compared with.
     */
    public AngleDistComparator(Point reference) {
        this.reference = reference;
    }

    /**
     * Compares two points {@code p} and {@code q} according to the angle of
     * their vectors to the reference point. If the angle is equal, the vectors 
     * are compared by size.
     * 
     * @param p The first {@link Point}.
     * @param q The second {@link Point}.
     * @return A value greater than zero if the vector from the reference point 
     *         to p comes before the vector from the reference point to q if you
     *         sort them clockwise around the reference point or if all points 
     *         are on one line and the vector to q is shorter than the one to p.
     *         Zero if both points are equal. A negative value else.
     */
    public int compare(Point p, Point q) {
        int result = reference.leftOf(p, q);
        if (result == 0) {
            // All points on one line -> Compare distance.
            result = (int) (reference.distanceTo(p) - reference.distanceTo(q));
        }
        return result;
    }
}
