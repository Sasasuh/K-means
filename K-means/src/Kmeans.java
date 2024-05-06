import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class KMeans {
    private List<Point> dataPoints;
    private List<Cluster> clusters;
    private int k;
    private int maxIterations;

    public KMeans(List<Point> dataPoints, int k) {
        this.dataPoints = dataPoints;
        this.k = k;
        this.clusters = new ArrayList<>();
        this.maxIterations = 1000;
    }

    public void run() {
        initializeClusters();
        int iteration = 0;
        double previousSum = Double.MAX_VALUE;

        while (iteration < maxIterations) {
            assignPointsToClusters();
            double sumSquaredDistances = calculateSumSquaredDistances();
            System.out.println("Iteration " + (iteration + 1) + ", Sum: " + sumSquaredDistances);
            for (Cluster cluster : clusters) {
                System.out.println(cluster);
            }

            if (Math.abs(previousSum - sumSquaredDistances) < 0.00001) {
                System.out.println("\nStopped after " + (iteration + 1) + " iterations.");
                break;
            }

            updateCentroids();
            previousSum = sumSquaredDistances;
            iteration++;
        }

        System.out.println("\nFinal Clusters:");
        for (Cluster cluster : clusters) {
            System.out.println(cluster);
        }
    }

    private void initializeClusters() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            Point centroid = dataPoints.get(random.nextInt(dataPoints.size()));
            Cluster cluster = new Cluster(centroid);
            clusters.add(cluster);
        }
    }

    private void assignPointsToClusters() {
        for (Cluster cluster : clusters) {
            cluster.clearPoints();
        }

        for (Point point : dataPoints) {
            Cluster closestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                double distance = point.distanceTo(cluster.getCentroid());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCluster = cluster;
                }
            }
            closestCluster.addPoint(point);
        }
    }

    private void updateCentroids() {
        for (Cluster cluster : clusters) {
            cluster.updateCentroid();
        }
    }

    private double calculateSumSquaredDistances() {
        double sum = 0;
        for (Cluster cluster : clusters) {
            sum += cluster.calculateSumSquaredDistances();
        }
        return sum;
    }

    public static List<Point> readDataPointsFromCSV(String filename) throws IOException {
        List<Point> dataPoints = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            double[] attributes = new double[values.length];
            for (int i = 0; i < values.length; i++) {
                attributes[i] = Double.parseDouble(values[i]);
            }
            dataPoints.add(new Point(attributes));
        }
        br.close();
        return dataPoints;
    }

    public static void main(String[] args) {


        String filename = "Data/test.csv";
        int k = 3;

        try {
            List<Point> dataPoints = readDataPointsFromCSV(filename);
            KMeans kMeans = new KMeans(dataPoints, k);
            kMeans.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}