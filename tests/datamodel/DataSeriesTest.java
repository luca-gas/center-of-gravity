package datamodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public final class DataSeriesTest {


  @Test
  public void splitByPeriod_oneAdditional() {
    DataSeries dataSeries = DataSeries.create("dataSeries");
    for (int i = 0; i < 3; i++) {
      dataSeries.add(Point.of(i, 2 * i));
    }

    List<DataSeries> dataSeriesList = dataSeries.splitByPeriod(2);
    assertEquals(2, dataSeriesList.size());

    DataSeries dataSeries1 = DataSeries.create("dataSeries_Day_1");
    dataSeries1.add(Point.of(0, 0));
    dataSeries1.add(Point.of(1, 2));
    assertEquals(dataSeries1, dataSeriesList.get(0));

    DataSeries dataSeries2 = DataSeries.create("dataSeries_Day_2");
    dataSeries2.add(Point.of(0, 4));
    assertEquals(dataSeries2, dataSeriesList.get(1));
  }

  @Test
  public void splitByPeriod() {
    DataSeries dataSeries = DataSeries.create("dataSeries");
    for (int i = 0; i < 4; i++) {
      dataSeries.add(Point.of(i, 2 * i));
    }

    List<DataSeries> dataSeriesList = dataSeries.splitByPeriod(2);
    assertEquals(2, dataSeriesList.size());

    DataSeries dataSeries1 = DataSeries.create("dataSerie_Day_1");
    dataSeries1.add(Point.of(0, 0));
    dataSeries1.add(Point.of(1, 2));
    assertEquals(dataSeries1, dataSeriesList.get(0));

    DataSeries dataSeries2 = DataSeries.create("dataSerie_Day_2");
    dataSeries2.add(Point.of(0, 4));
    dataSeries2.add(Point.of(1, 6));
    assertEquals(dataSeries2, dataSeriesList.get(1));
  }

}
