package cellularData;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  CSVReader class stores a data from a cellular.csv file.
 *  @author Foothill College, Bita M., Team 8
 */
public class CSVReader implements ICVReader {
    // member data
    private LinkedList<String> countryNames;
    private int[] yearLabels;
    private LinkedList<double[]> cellularDataTable;
    private String title;

    /**
     * parameter-taking constructor for creating CSVReader object.
     * @param fileName     takes a String value which contains the address of csv file
     */
    public CSVReader(String fileName) {
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
            countryNames = new LinkedList<>();
            line = myScanner.nextLine();
            tokens = line.split(",");
            yearLabels = new int[tokens.length-1];
            for(int i = 1; i < tokens.length; i++) {
                yearLabels[i-1] = Integer.parseInt(tokens[i]);
            }
            cellularDataTable = new LinkedList<>();

            while (myScanner.hasNextLine()) {
                line = myScanner.nextLine();
                tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                countryNames.add(tokens[0]);
                double[] data = new double[yearLabels.length];
                for(int i = 1; i < tokens.length; i++) {
                    data[i-1] = Double.parseDouble(tokens[i]);
                }
                cellularDataTable.add(data);

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