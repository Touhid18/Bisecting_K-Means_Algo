package buet.touhiDroid.BisectingKMeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import buet.touhiDroid.BisectingKMeans.models.Cluster;
import buet.touhiDroid.BisectingKMeans.models.DataPoint;
import buet.touhiDroid.BisectingKMeans.utils.Lg;

/**
 * Created by touhid on 12/21/15.
 *
 * @author touhid
 */
public class BisectingKMeans {

    private static final int NUM_ITERATIONS_BISECTION = 5;

    private static final int K_BISECTING = 6;

    private ArrayList<Cluster> clusterList;

    private BisectingKMeans() {
        ArrayList<DataPoint> dataList = readDataList();
        clusterList = new ArrayList<>();

        Cluster cluster = calcCluster(dataList);
        cluster.setDataPoints(dataList);
        clusterList.add(cluster);
        runBisectingKMeans(cluster);

        int i=1;
        for(Cluster c : clusterList)
            Lg.pl("Cluster "+ i++ + " :" + c.getCx() + ", " + c.getCy());
    }

    private void runBisectingKMeans(Cluster worstCluster) {
        clusterList.remove(worstCluster); // TODO Recheck whether it's being removed or not

        Cluster cluster1 = null, cluster2 = null;
        double minSSE = Double.MAX_VALUE;

        for (int i = 0; i < NUM_ITERATIONS_BISECTION; i++) {
            ArrayList<Cluster> ac = kMeansClustering(2, worstCluster.getDataPoints());

            double sumSSE = ac.get(0).getSSE() + ac.get(1).getSSE();
            if(sumSSE < minSSE){
                cluster1 = ac.get(0);
                cluster2 = ac.get(1);
            }
        }
        clusterList.add(cluster1);
        clusterList.add(cluster2);

        if(clusterList.size() < K_BISECTING){
            runBisectingKMeans(getWorstCluster());
        }
    }

    private ArrayList<Cluster> kMeansClustering(int k, ArrayList<DataPoint> dataPoints) {
        Random rand = new Random();

        ArrayList<Cluster> tempClusterList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            Cluster cluster = new Cluster(dataPoints.get(rand.nextInt(dataPoints.size())));
            tempClusterList.add(cluster);
        }

        boolean isUpdated = true;
        do {
            // Assign Data-points to each of the clusters
            tempClusterList.get(0).setDataPoints(new ArrayList<>());
            tempClusterList.get(1).setDataPoints(new ArrayList<>());
            for (DataPoint p : dataPoints) {
                double minDist = Double.MAX_VALUE;
                int idx = 0;

                for (int i = 0; i < tempClusterList.size(); i++) {
                    double d = tempClusterList.get(i).getDistSq(p);
                    if (d < minDist) {
                        minDist = d;
                        idx = i;
                    }
                }
                tempClusterList.get(idx).addPoint(p);
            }
            isUpdated = false;
            for (int i = 0; i < k; i++) {
                boolean isClUpdated = tempClusterList.get(i).updateCentroid();
                if (isClUpdated)
                    isUpdated = true;
            }
        }while(isUpdated);

        return tempClusterList;
    }

    private Cluster getWorstCluster() {
        double maxSSE = -1;
        Cluster worstCluster = null;
        for (Cluster c : clusterList) {
            double sse = c.getSSE();
            if (sse > maxSSE) {
                worstCluster = c;
                maxSSE = sse;
            }
        }
        return worstCluster;
    }

    private Cluster calcCluster(ArrayList<DataPoint> dataList) {
        double scx = 0, scy = 0;
        for (DataPoint p : dataList) {
            scx += p.getDx();
            scy += p.getDy();
        }
        int size = dataList.size();
        return new Cluster(scx / size, scy / size);
    }

    private ArrayList<DataPoint> readDataList() {
        ArrayList<DataPoint> dataList = new ArrayList<>();
        // int i =0;
        try {
            Scanner inScanner = new Scanner(new File("in/test_set.txt"));
            while (inScanner.hasNextLine()) {
                StringTokenizer tokens = new StringTokenizer(inScanner.nextLine());
                String sx = tokens.nextToken();
                double cx = Double.parseDouble(sx);
                String sy = tokens.nextToken();
                double cy = Double.parseDouble(sy);
                dataList.add(new DataPoint(cx, cy));
                // Lg.pl("New Data point " + i++ +": " + cx + ", " + cy);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static void main(String args[]) {
        new BisectingKMeans();
    }
}
