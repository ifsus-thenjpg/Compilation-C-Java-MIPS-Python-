//Author:sumorin

package cs1c;

import java.io.FileWriter;
import java.io.IOException;

/**
 * CSVFileWriter object writes the data from each array size to a CSV file in resources folder
 */
public class CSVFileWriter {

    //Delimiter used in CSV file
    private static final String SPACE_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    //CSV file header
    private static final String FILE_HEADER = "QS Recursion Limit, AVG Elapsed Time";

    /**
     * method that writes concatenated string to a CSV file for specific array size
     * @param str concatenated string holding recursion limit and elapsed time
     * @param fileName name for CSV file relating to array size
     */
    public static void writeCsvFile(String str, String fileName) {

        FileWriter fileWriter = null;

        try {
            //creates a CSV file of given filename
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a line to the CSV file{
            fileWriter.append(str);
            fileWriter.append(SPACE_DELIMITER);
            fileWriter.append(NEW_LINE_SEPARATOR);

        } catch (Exception e) {

            System.out.println("Error in CsvFileWriter !!!");

            e.printStackTrace();

        } finally {
            try {

                fileWriter.flush();

                fileWriter.close();

            } catch (IOException e) {

                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();

            }

        }

    }
}
