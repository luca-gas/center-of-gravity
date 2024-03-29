package algorithms;

import datamodel.DataSeries;
import datamodel.Point;

import static java.lang.Math.*;

/**
 * Computes the center of Gravity for a data series.
 * The formula is the one described in
 * <a href="https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3723718/">Methods for serial analysis of long time series in the study of biological rhythms</a>
 */
public final class CenterOfGravity {
  private static final double CIRCLE = 2 * Math.PI;

  public static double compute(DataSeries dataSeries, int period) {
    return new CenterOfGravityComputer(dataSeries, period).centerOfGravity();
  }

  private static class CenterOfGravityComputer {
    private final DataSeries dataSeries;
    private final int period;

    private CenterOfGravityComputer(DataSeries dataSeries, int period) {
      this.dataSeries = dataSeries;
      this.period = period;
    }

    private static double normalizeAngle(double angle) {
      while (angle < 0) {
        angle += CIRCLE;
      }
      return angle % CIRCLE;
    }

    private double centerOfGravity() {
      double angleInRadians =
        normalizeAngle(
          atan2(sumCoordinateY(), sumCoordinateX()));
      return angleInRadians / CIRCLE * period;
    }

    private double sumCoordinateX() {
      return
        dataSeries.stream().map(this::weightedCoordinateX).reduce(Double::sum).orElse(0.0);
    }

    private double sumCoordinateY() {
      return
        dataSeries.stream().map(this::weightedCoordinateY).reduce(Double::sum).orElse(0.0);
    }

    private double weightedCoordinateX(Point point) {
      return point.getSignal() * cos(angle(point));
    }

    private double weightedCoordinateY(Point point) {
      return point.getSignal() * sin(angle(point));
    }

    private double angle(Point point) {
      return CIRCLE * point.getTime() / period;
    }
  }
}
