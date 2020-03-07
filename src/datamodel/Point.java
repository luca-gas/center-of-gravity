package datamodel;

import java.util.Objects;

public final class Point {
  // The time when the signal was observed.
  private final double time;
  // The observed signal.
  private final double signal;

  private Point(double time, double signal) {
    this.time = time;
    this.signal = signal;
  }

  public static Point of(double time, double signal) {
    return new Point(time, signal);
  }

  public double getTime() {
    return time;
  }

  public double getSignal() {
    return signal;
  }

  @Override
  public String toString() {
    return String.format("Point(%f,%f)", getTime(), getSignal());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Point)) {
      return false;
    }

    Point that = (Point) o;
    return Objects.equals(this.time, that.time) &&
      Objects.equals(this.signal, that.signal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.time, this.signal);
  }

}
