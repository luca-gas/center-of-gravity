import algorithms.CenterOfGravity;
import datamodel.DataSeries;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.showMessageDialog;

public class Main {
  private static final String OUTPUT_FILE_NAME = "output.txt";
  private static final double OUTPUT_NORMALIZATION = 24; // Hours per day.
  private static final String DELIMITER = "\t";

  public static void main(String[] args) {
    createGUI();
  }

  private static void createGUI() {
    JFrame frame = new JFrame("Center of Gravity");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(200, 160);

    JLabel periodLabel = new JLabel();
    periodLabel.setText("Period");
    periodLabel.setBounds(10, 10, 80, 30);
    frame.add(periodLabel);

    JTextField periodTextField = new JTextField();
    periodTextField.setText("24");
    periodTextField.setBounds(100, 10, 95, 30);
    frame.add(periodTextField);

    JFileChooser jFileChooser = new JFileChooser();
    frame.add(jFileChooser);
    JButton openFileButton = new JButton();
    openFileButton.setText("Select File");
    openFileButton.setBounds(0, 50, 200, 30);
    frame.add(openFileButton);
    JTextField inputFile = new JTextField();

    JButton runButton = new JButton("Run");
    runButton.setVisible(false);
    runButton.setBounds(0, 90, 200, 30);
    frame.add(runButton);
    frame.setLayout(null);

    openFileButton.addActionListener(unusedAction -> {
      if (jFileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
        inputFile.setText(jFileChooser.getSelectedFile().getAbsolutePath());
        runButton.setVisible(true);
      }
    });

    runButton.addActionListener(unusedAction -> {
      try {
        if (inputFile.getText().isEmpty()) {
          throw new RuntimeException("You must select a file first");
        }
        Path inputFilePath = Path.of(inputFile.getText());
        Path outputFile = Path.of(inputFilePath.getParent().toString(),
          OUTPUT_FILE_NAME);
        compute(inputFilePath, outputFile,
          Integer.parseInt(periodTextField.getText()));
        showMessageDialog(null, "Done.");
      } catch (Exception ex) {
        ex.printStackTrace();
        showMessageDialog(null, "Error: \n " + ex);
      }
    });
    frame.setVisible(true);
  }

  private static void compute(Path inputFile, Path outputFile, int period)
    throws IOException {

    Map<String, List<DataSeries>> input =
      InputOutput.parse(inputFile.toString(), period);
    printToFile(outputFile.toString(), toCenterOfGravityString(input, period));
  }

  private static String toCenterOfGravityString(
    Map<String, List<DataSeries>> input, int period) {

    StringBuilder stringBuilder = new StringBuilder();
    for (Map.Entry<String, List<DataSeries>> entry : input.entrySet()) {
      stringBuilder.append(toCenterOfGravityString(entry.getKey(),
        entry.getValue(), period));
      stringBuilder.append("\n");
    }

    return stringBuilder.toString();
  }

  private static String toCenterOfGravityString(
    String name, List<DataSeries> dataSeriesList, int period) {

    List<String> resultsAsStrings = dataSeriesList.stream()
      .map(dataSeries -> CenterOfGravity.compute(dataSeries, period))
      .map(val -> normalizeForOutput(val, period))
      .map(val -> String.format("%.2f", val))
      .collect(Collectors.toList());

    return name + DELIMITER + String.join(DELIMITER, resultsAsStrings);
  }

  private static void printToFile(String fileName, String content)
    throws IOException {

    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    writer.write(content);
    writer.close();
  }

  private static double normalizeForOutput(double val, double period) {
    return val / period * OUTPUT_NORMALIZATION;
  }
}

