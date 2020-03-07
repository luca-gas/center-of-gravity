package datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DataSeries {
  private final String name;
  private final ArrayList<Point> points;

  private DataSeries(String name) {
    this.name = name;
    this.points = new ArrayList<>();
  }

  public static DataSeries create(String name) {
    return new DataSeries(name);
  }

  public String name() {
    return name;
  }

  private static Point normalizedPoint(Point point, int sequenceIndex,
                                       int period) {
    double normalizedTime = point.getTime() - (sequenceIndex - 1) * period;
    return Point.of(normalizedTime, point.getSignal());
  }

  public Point get(int index) {
    return points.get(index);
  }

  public Stream<Point> stream() {
    return points.stream();
  }

  public int size() {
    return points.size();
  }

  public DataSeries add(Point point) {
    points.add(point);
    return this;
  }

  // period is the number of 'units' per day. 
  // We start from a series of data points lasting for potentially multiple,
  // and we return a list of data series (one per day).
  public List<DataSeries> splitByPeriod(int period) {
    ArrayList<DataSeries> result = new ArrayList<>();
    int sequenceIndex = 1;
    result.add(new DataSeries(nameForSequence(name, sequenceIndex)));
    for (Point point : points) {
      if (point.getTime() >= sequenceIndex * period) {
        sequenceIndex++;
        result.add(new DataSeries(nameForSequence(name, sequenceIndex)));
      }
      getLast(result).add(normalizedPoint(point, sequenceIndex, period));
    }
    return result;
  }

  private static <X> X getLast(ArrayList<X> list) {
    return list.get(list.size() - 1);
  }

  private static String nameForSequence(String name, int sequenceIndex) {
    return name + "_Day_" + sequenceIndex;
  }

  @Override
  public String toString() {
    return name +
      ": [" +
      String.join(", ",
        points.stream().map(Point::toString).collect(Collectors.toList())) +
      "]";
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DataSeries)) {
      return false;
    }

    DataSeries that = (DataSeries) o;
    if (!that.name.equals(this.name)) {
      return false;
    }

    if (that.size() != this.size()) {
      return false;
    }

    for (int i = 0; i < this.size(); i++) {
      if (!this.get(i).equals(that.get(i))) {
        return false;
      }
    }
    return true;
  }

}
