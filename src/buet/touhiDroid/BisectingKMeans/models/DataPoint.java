package buet.touhiDroid.BisectingKMeans.models;

/**
 * Created by touhid on 12/21/15.
 * @author touhid
 */
public class DataPoint {

    private double dx, dy;
    private int clusterNo;

    public DataPoint(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public DataPoint(double dx, double dy, int clusterNo) {
        this.dx = dx;
        this.dy = dy;
        this.clusterNo = clusterNo;
    }

    public int getClusterNo() {
        return clusterNo;
    }

    public void setClusterNo(int clusterNo) {
        this.clusterNo = clusterNo;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "dx=" + dx +
                ", dy=" + dy +
                ", clusterNo=" + clusterNo +
                '}';
    }
}
