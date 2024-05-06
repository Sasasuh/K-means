import java.util.ArrayList;
import java.util.List;

class Cluster {
    private Point centroid;
    private List<Point> points;

    public Cluster(Point centroid) {
        this.centroid = centroid;
        this.points = new ArrayList<>();
    }

    public Point getCentroid() {
        return centroid;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void clearPoints() {
        points.clear();
    }

    public void updateCentroid() {
        if (points.isEmpty()) {
            return;
        }
        double[] newCentroid = new double[centroid.getAttributes().length];
        for (Point point : points) {
            double[] attributes = point.getAttributes();
            for (int i = 0; i < attributes.length; i++) {
                newCentroid[i] += attributes[i];
            }
        }
        for (int i = 0; i < newCentroid.length; i++) {
            newCentroid[i] /= points.size();
        }
        centroid = new Point(newCentroid);
    }

    public double calculateSumSquaredDistances() {
        double sum = 0;
        for (Point point : points) {
            sum += Math.pow(point.distanceTo(centroid), 2);
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Centroid: ").append(centroid).append(", Points: ");
        for (Point point : points) {
            sb.append(point).append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}