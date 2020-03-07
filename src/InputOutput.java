import datamodel.DataSeries;
import datamodel.Point;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public final class InputOutput {

  static Map<String, List<DataSeries>> parse(String fileName, int period) throws IOException {
    return parse(fileScanner(fileName), period);
  }

  static Map<String, List<DataSeries>> parse(Scanner inputScanner, int period) {

    List<String> names = firstLine(inputScanner.nextLine());
    List<DataSeries> dataSeries =
      names.stream()
        .map(DataSeries::create)
        .collect(Collectors.toCollection(ArrayList::new));

    // The first line was the one with the names.
    int line = 2;
    while (inputScanner.hasNextLine()) {

      try {
        addDataFromLine(dataSeries, inputScanner.nextLine());
      } catch (RuntimeException e) {
        throw new RuntimeException("Error while reading line " + line + "\n" + e, e);
      }
      line++;
    }

    return dataSeries.stream().collect(Collectors.toMap(DataSeries::name,
      data -> data.splitByPeriod(period), InputOutput::concat,
      LinkedHashMap::new));
  }

  private static <X> List<X> concat(List<X>... lists) {
    return Arrays.stream(lists).flatMap(List::stream).collect(Collectors.toList());
  }


  /**
   * Returns the list of names for each column.
   * The first column should be "time".
   */
  private static List<String> firstLine(String line) {
    Scanner lineScanner = new Scanner(line);
    String time = lineScanner.next(); // Drop the first one. it should be 'Time'
    if (!time.equals("Time")) {
      throw new RuntimeException("The first word should be Time");
    }

    ArrayList<String> names = new ArrayList<>();
    while (lineScanner.hasNext()) {
      names.add(lineScanner.next());
    }
    return names;
  }

  /**
   * Parses a line and adds a single data point to each data series.
   */
  private static List<DataSeries> addDataFromLine(List<DataSeries> dataSeriesList, String line) {

    Scanner lineScanner = new Scanner(line);
    double time = lineScanner.nextDouble();

    ArrayList<Double> values = values(lineScanner);
    if (values.size() != dataSeriesList.size()) {
      throw new RuntimeException("The line has the wrong number of elements");
    }

    for (int i = 0; i < values.size(); i++) {
      double signal = values.get(i);
      DataSeries dataSeries = dataSeriesList.get(i);
      dataSeries.add(Point.of(time, signal));
    }

    return dataSeriesList;
  }

  /**
   * Extract the values from a single line.
   */
  private static ArrayList<Double> values(Scanner scanner) {
    ArrayList<Double> values = new ArrayList<>();
    while (scanner.hasNextDouble()) {
      values.add(scanner.nextDouble());
    }
    return values;
  }

  private static Scanner fileScanner(String fileName) throws IOException {
    return new Scanner(new BufferedInputStream(Files.newInputStream(new File(fileName).toPath())));
  }
}
