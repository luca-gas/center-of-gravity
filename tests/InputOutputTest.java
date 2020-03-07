import datamodel.DataSeries;
import datamodel.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(JUnit4.class)
public class InputOutputTest {

  private static Scanner scannerFromText(String str) {
    return new Scanner(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
  }

  @Test
  public void validInput() {
    String input =
      "Time\tSeries1\tSeries2\n" +
        "1\t3\t5\n" +
        "13\t5\t7\n" +
        "35\t22\t11";

    Map<String, List<DataSeries>> output =
      InputOutput.parse(scannerFromText(input), /*period=*/24);

    Map<String, List<DataSeries>> expectedOutput = Map.of(
      "Series1",
      List.of(
        DataSeries.create("Series1_Day_1")
          .add(Point.of(1, 3))
          .add(Point.of(13, 5)),
        DataSeries.create("Series1_Day_2")
          .add(Point.of(11, 22))),
      "Series2",
      List.of(
        DataSeries.create("Series2_Day_1")
          .add(Point.of(1, 5))
          .add(Point.of(13, 7)),
        DataSeries.create("Series2_Day_2")
          .add(Point.of(11, 11))));

    assertEquals(expectedOutput, output);
  }

  @Test
  public void invalidStart() {
    String input =
      "Timezzz\tSeries1\tSeries2\n" +
        "1\t3\t5\n" +
        "13\t5\t7\n" +
        "35\t22\t11";

    assertThrows(RuntimeException.class,
      () -> InputOutput.parse(scannerFromText(input), /*period=*/24));
  }

  @Test
  public void invalidNumberOfValues() {
    String input =
      "Time\tSeries1\tSeries2\n" +
        "1\t3\t5\n" +
        // Too many values here
        "13\t5\t7\t9\n" +
        "35\t22\t11";

    assertThrows(RuntimeException.class,
      () -> InputOutput.parse(scannerFromText(input), /*period=*/24));
  }

  @Test
  public void notANumber() {
    String input =
      "Time\tSeries1\tSeries2\n" +
        // Third value is not a number
        "1\t3\tabc\n" +
        "13\t5\t7\n" +
        "35\t22\t11";

    assertThrows(RuntimeException.class,
      () -> InputOutput.parse(scannerFromText(input), /*period=*/24));
  }
}
