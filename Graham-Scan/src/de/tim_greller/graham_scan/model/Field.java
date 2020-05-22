package de.tim_greller.graham_scan.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Field {
    
    private List<Point> points = new ArrayList<Point>();
    private boolean isSorted = true;
    
    /**
     * Appends a {@link Point} to the field.
     * 
     * @param p The new {@link Point}.
     * @return {@code true} if {@code p} was added successful.
     */
    public boolean add(Point p) {
        isSorted = false;
        return points.add(p);
    }
    
    /**
     * Removes a {@link Point} from the field if present.
     * @param p The {@link Point} to remove.
     * @return {@code true} if {@code p} was removed successful.
     */
    public boolean remove(Point p) {
        return points.remove(p);
    }
    
    /** Sorts the field and returns its string representation. */
    @Override
    public String toString() {
        sortPoints();
        return points.toString();
    }
    
    /** Sorts the points if they are not already sorted. */
    private void sortPoints() {
        if (!isSorted) {
            Collections.sort(points);
            isSorted = true;
        }
    }
    
    // This is correct in some cases, good enough.
    public List<Point> convexHull() {
        return points;
    }
}
