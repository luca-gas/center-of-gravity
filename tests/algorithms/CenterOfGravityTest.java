package algorithms;

import datamodel.DataSeries;
import datamodel.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CenterOfGravityTest {

  @Test
  public void symmetric() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(3, 1))
      .add(Point.of(6, 1))
      .add(Point.of(9, 1))
      .add(Point.of(12, 1))
      .add(Point.of(15, 1))
      .add(Point.of(18, 1))
      .add(Point.of(21, 1));

    double result = CenterOfGravity.compute(input, 24);
    assertEquals(12, result, 0.1);
  }

  @Test
  public void symmetricPointsPlusOne() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(3, 1))
      .add(Point.of(4, 1))
      .add(Point.of(6, 1))
      .add(Point.of(9, 1))
      .add(Point.of(12, 1))
      .add(Point.of(15, 1))
      .add(Point.of(18, 1))
      .add(Point.of(21, 1));

    double result = CenterOfGravity.compute(input, 24);
    // 8 is between 4 and 12.
    assertEquals(8, result, 0.1);
  }

  @Test
  public void onlyOnePoint() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(3, 1));

    double result = CenterOfGravity.compute(input, 24);
    assertEquals(3, result, 0.1);
  }

  @Test
  public void twoPointsSameSignal() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(3, 1))
      .add(Point.of(6, 1));

    double result = CenterOfGravity.compute(input, 24);
    assertEquals(4.5, result, 0.1);
  }

  @Test
  public void twoSymmetricPoints() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(6, 1))
      .add(Point.of(18, 1));

    double result = CenterOfGravity.compute(input, 24);
    assertEquals(12, result, 0.1);
  }

  @Test
  public void twoSymmetricPointsSecondSignalStronger() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(6, 1))
      .add(Point.of(18, 2));

    double result = CenterOfGravity.compute(input, 24);
    assertEquals(18, result, 0.1);
  }

  @Test
  public void twoPointSecondSignalStronger() {
    DataSeries input = DataSeries.create("test")
      .add(Point.of(3, 1))
      .add(Point.of(12, 50));

    double result = CenterOfGravity.compute(input, 24);
    // Result is almost 12 (with very low delta).
    assertEquals(12, result, 0.1);
  }

  @Test
  public void sinusoid() {
    DataSeries input = DataSeries.create("test");

    for (int i = 0; i < 24; i++) {
      input.add(Point.of(i, Math.sin(2 * Math.PI * i / 24)));
    }

    double result = CenterOfGravity.compute(input, 24);
    assertEquals(6, result, 0.1);
  }
}
