# Graham-Scan
A tool to create the convex hull of a set of points using the Graham scan algorithm.

## The Convex Hull
A set of points like `[(-2, -1), (-2, 0), (0, 0), (1, 2), (2, -1), (2, 3)]` would look like this in a coordinate system:
![points](img/points.png)
And the convex hull of these points is a shape formed by the points `[(-2, -1), (2, -1), (2, 3), (-2, 0)]` and looks like this:
![convex hull](img/convexhull.png)

## Shell usage
A dialogue with the shell to calculate the above convex hull using the Graham Scan algorithm looks like this:
```
gs> add 0 0
gs> add -2 0
gs> add 2 -1
gs> add 1 2
gs> add 2 3
gs> add -2 -1
gs> print
[(-2, -1), (-2, 0), (0, 0), (1, 2), (2, -1), (2, 3)]
gs> convex
[(-2, -1), (2, -1), (2, 3), (-2, 0)]
```
