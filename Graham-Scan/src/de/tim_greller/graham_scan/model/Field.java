package de.tim_greller.graham_scan.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Field {
    
    /** The current set of points */
    private List<Point> points = new ArrayList<Point>();
    
    /** The last calculated convex hull */
    private List<Point> convex = new ArrayList<Point>();
    
    /** Indicates if {@code points} has been modified since it was sorted. */
    private boolean isSorted = true;
    
    /** 
     * Indicates if {@code points} has been modified since the last convex 
     * hull was calculated.
     */
    private boolean isConvexUpToDate = true;
    
    
    /**
     * Appends a {@link Point} to the field if the point is not already in it.
     * 
     * @param p The new {@link Point}.
     * @return {@code true} if {@code p} was added successful.
     */
    public boolean add(Point p) {
        isSorted = false;
        isConvexUpToDate = false;
        return !points.contains(p) && points.add(p);
    }
    
    /**
     * Removes a {@link Point} from the field if present.
     * @param p The {@link Point} to remove.
     * @return {@code true} if {@code p} was removed successful.
     */
    public boolean remove(Point p) {
        isConvexUpToDate = false;
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
    
    /**
     * Calculates the convex hull of all {@link Point}s stored in this 
     * {@link Field} using the graham scan algorithm. <br> If the set of points 
     * stored in the field was not modified since the last execution of this
     * method, a new list containing the same points will get returned without 
     * running the algorithm again.
     * 
     * @return The {@link Point}s forming the convex hull, starting with the
     *         {@link Point} furthest to the left and bottom and continuing 
     *         counter clockwise.
     */
    public List<Point> convexHull() {
        if (!isConvexUpToDate) {
            sortPoints();
            // Trivial cases: The convex hull of 2 or less points are always the
            // points themselves.
            if (points.size() <= 2) {
                convex = new ArrayList<Point>(points);
            } else {
                convex = grahamScan();
            }
            isConvexUpToDate = true;
        }
        // Return a new list so the stored one cannot get modified.
        // Using the Constructor is sufficient because each Point is immutable.
        return new ArrayList<Point>(convex);
    }
    
    /**
     * Implementation of the graham scan algorithm. <br>
     * Preconditions: <ul>
     *     <li>{@code points} contains at least 3 elements.</li>
     *     <li>{@code isSorted} is {@code true}.</li>
     * </ul>
     * 
     * @return The {@link Point}s forming the convex hull, starting with the
     *         {@link Point} furthest to the left and bottom and continuing 
     *         counter clockwise.
     */
    private List<Point> grahamScan() {
        // Points ordered by Angle and if necessary by distance relative to p0.
        List<Point> orderedPoints = new ArrayList<Point>(points);
        Point p0 = orderedPoints.remove(0);
        Collections.sort(orderedPoints, new AngleDistComparator(p0));
        
        // List that does not contain two points collinear with p0.
        List<Point> res = new LinkedList<Point>();
        int lastIndex = orderedPoints.size() - 1;
        for (int i = 0; i < lastIndex; i++) {
            if (p0.leftOf(orderedPoints.get(i), orderedPoints.get(i + 1)) 
                    != 0) {
                res.add(orderedPoints.get(i));
            }
        }
        // Last point is always included.
        res.add(orderedPoints.get(lastIndex));
        
        // End result:
        Deque<Point> stack = new LinkedList<Point>();
        stack.push(p0);
        
        for (Point point : res) {
            while (stack.size() > 1 
                    && point.leftOf(stack.peek(), peekNextToTop(stack)) < 0) {
                stack.pop();
            }
            stack.push(point);
        }
        
        Collections.reverse((LinkedList<Point>) stack);
        return (LinkedList<Point>) stack;
    }
    
    /**
     * Returns the second element from the top of a {@link Deque} stack without 
     * modifying it.
     * 
     * @param <T> The type of the elements stored in {@code stack}.
     * @param stack The {@link Deque} whose second element should be returned.
     * @return The second element of {@code stack}.
     */
    private <T> T peekNextToTop(Deque<T> stack) {
        T head = stack.pop();
        T next = stack.peek();
        stack.push(head);
        return next;
    }
}
