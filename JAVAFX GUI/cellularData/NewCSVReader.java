package cellularData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * NewCSVReader class stores a data from a humandevelopment.csv file.
 * @author Foothill College, Bita M., Team 8
 */
public class NewCSVReader implements ICVReader {
    // member data
    private LinkedList<String> countryNames;
    private int[] yearLabels;
    private LinkedList<double[]> cellularDataTable;
    private String title;

    public NewCSVReader(String fileName) {
        Scanner myScanner;
        String line;
        String[] tokens;

        File infile = new File (fileName);
        try {
            myScanner = new Scanner(infile);
            title = myScanner.nextLine();
            //System.out.println(title);
            line = myScanner.nextLine();
            tokens = line.split(",");
            yearLabels = new int[tokens.length-2];
            for(int i = 2; i < tokens.length; i++) {
                yearLabels[i-2] = Integer.parseInt(tokens[i]);
            }
            countryNames = new LinkedList<>();
            cellularDataTable = new LinkedList<>(); /////// line to be changed!
            while (myScanner.hasNextLine()) {
                line = myScanner.nextLine();
                tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                countryNames.add(tokens[1]);
                double[] dIndexes = new double[yearLabels.length];
                for(int i = 2; i < tokens.length; i++) {
                    dIndexes[i-2] = Double.parseDouble(tokens[i]);
                }
                cellularDataTable.add(dIndexes);
            }
            myScanner.close();
        } catch(FileNotFoundException a) {
            System.out.println("File " + fileName + " not found!");
        }
    }

    /**
     * Gets and returns the linked list with country names
     * @return countryNames, the linked list with country names
     */
    @Override
    public LinkedList<String> getCountryNames() {
        return countryNames;
    }

    /**
     * Gets and returns an array of integers with year labels
     * @return  yearLabels
     */
    @Override
    public int[] getYearLabels() {
        return yearLabels;
    }

    /**
     * Gets and returns the data in the linked list of arrays of doubles.
     * @return cellularDataTable
     */
    @Override
    public LinkedList<double[]> getParsedTable() {
        return cellularDataTable;
    }

    /**
     * Gets and returns the title for the chart.
     * @return title
     */
    @Override
    public String getTitle(){
        return title;
    }

}
