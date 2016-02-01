package buet.touhiDroid.BisectingKMeans.models;

import java.util.ArrayList;

/**
 * Created by touhid on 12/21/15.
 *
 * @author touhid
 */
public class Cluster {
    private static final double CENTROID_THRESHOLD = 0.005;

    private double cx, cy;
    private ArrayList<DataPoint> dataPoints;

    public Cluster(double cx, double cy) {
        this.cx = cx;
        this.cy = cy;
        dataPoints = new ArrayList<>();
    }

    public Cluster(DataPoint centroid) {
        this.cx = centroid.getDx();
        this.cy = centroid.getDy();
        dataPoints = new ArrayList<>();
    }

    public Cluster(double cx, double cy, ArrayList<DataPoint> dataPoints) {
        this.cx = cx;
        this.cy = cy;
        this.dataPoints = dataPoints;
    }

    public double getCx() {
        return cx;
    }

    public void setCx(double cx) {
        this.cx = cx;
    }

    public double getCy() {
        return cy;
    }

    public void setCy(double cy) {
        this.cy = cy;
    }

    public ArrayList<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(ArrayList<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public void addPoint(DataPoint p) {
        this.dataPoints.add(p);
    }


    public double getSSE() {
        double sse = 0.0;
        for (DataPoint p : dataPoints) {
            double dx = cx - p.getDx();
            double dy = cy - p.getDy();
            sse += (dx * dx + dy * dy);
        }
        return sse;
    }

    public double getDistSq(DataPoint p) {
        double dx = p.getDx() - cx;
        double dy = p.getDy() - cy;
        return dx * dx + dy * dy;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "cx=" + cx +
                ", cy=" + cy +
                ", dataPoints=" + dataPoints +
                '}';
    }

    public boolean updateCentroid() {
        double sumX = 0.0;
        double sumY = 0.0;
        for (DataPoint p : dataPoints) {
            sumX += p.getDx();
            sumY += p.getDy();
        }
        int size = dataPoints.size();
        if(size==0)
            size = 1;
        double tcx = cx;
        cx = sumX / size;
        double tcy = cy;
        cy = sumY / size;

        return !(
                Math.abs(tcx - cx) < CENTROID_THRESHOLD
                        &&
                        Math.abs(tcy - cy) < CENTROID_THRESHOLD
        );
    }
}
