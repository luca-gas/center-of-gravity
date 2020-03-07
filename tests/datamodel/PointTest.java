package datamodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public final class PointTest {
  private static final double DELTA = 0.2;

  @Test
  public void getX() {
    assertEquals(1, Point.of(1, 2).getTime(), DELTA);
  }

  @Test
  public void getY() {
    assertEquals(2, Point.of(1, 2).getSignal(), DELTA);
  }

}
