import java.util.Arrays;

public class BruteCollinearPoints {
    private int segmentsCounter;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        validateInput(points);

        segmentsCounter = 0;
        lineSegments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = i + 2; k < points.length; k++) {
                    Point p = points[i];
                    Point q = points[j];
                    Point r = points[k];

                    if (areCollinear(p, q, r)) {
                        for (int t = i + 3; t < points.length; t++) {
                            Point s = points[t];
                            if (areCollinear(p, q, s)) addSegment(p, q, r, s);
                        }
                    }
                }
    }

    public int numberOfSegments() {
        return segmentsCounter;
    }

    public LineSegment[] segments() {
        return Arrays.copyOfRange(lineSegments, 0, numberOfSegments());
    }

    private void addSegment(Point p, Point q, Point r, Point s) {
        Point[] collinearPoints = {p, q, r, s};

        Arrays.sort(collinearPoints);
        Point minPoint = collinearPoints[0];
        Point maxPoint = collinearPoints[3];

        if (segmentsCounter < lineSegments.length) lineSegments[segmentsCounter++] = new LineSegment(minPoint, maxPoint);
        else throw new IllegalStateException("Exceeded maximum number of line segments");
    }


    private void validateInput(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points cannot be null");

        for (Point point : points)
            if (point == null) throw new IllegalArgumentException("Point cannot be null");

        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Points cannot be repeated");
    }

    private boolean areCollinear(Point p, Point q, Point r) {
        return (Double.compare(p.slopeTo(q), p.slopeTo(r)) == 0);
    }
}
