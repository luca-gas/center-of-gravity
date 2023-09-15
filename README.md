# center-of-gravity

COG is a phase marker that meaningfully represents the central tendency of circadian activity in a time-series.
The Center of Gravity was calculated as described
in [Díez-Noguera, 2013](https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3723718/).

The Java package for the application can be downloaded
as [centerOfGravity.jar](https://github.com/luca-gas/center-of-gravity/blob/master/out/artifacts/centerOfGravity_jar/centerOfGravity.jar)
.

The time-series should be saved in a .txt file where:

- The first column, labelled "Time", should contain the time information relative to sampling in hours,
- The first row should contain the sample names, each element separated by a Tab character

If the length of the Time column and columns containing sample values are mismatched, or if there are mistakes with
labelling, an
error will appear.

In this repository you can find an example
of [input](https://github.com/luca-gas/center-of-gravity/blob/master/input.txt)
and [output](https://github.com/luca-gas/center-of-gravity/blob/master/output.txt) files

To use the tool:

- Create a folder containing the application and the input file.
- Run the application with "java run -jar centerOfGravity.jar"
- Insert for Period the number of units per day (e.g. 24 if time is in hours, 1440 if it is minutes).
- Click "Select File" and select the input file.
- Click Run, an "output.txt" file with the results will automatically appear in the same folder as the input file.
