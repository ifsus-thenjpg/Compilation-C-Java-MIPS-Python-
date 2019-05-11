package cellularData;

/**
 * ICVReader interface provides methods for CSV classes
 * @author Foothill College, Bita M., Team 8
 */
public interface ICVReader {

        /**
         * Array of CSVReader object which has a LinkedList of country names.
         * @return              array of country names
         */
        LinkedList<String> getCountryNames();

        /**
         * Gets array of years of CSVReader object.
         * @return              array of years
         */
        int[] getYearLabels();

        /**
         * Gets LinkedList of CSVReader object which has country names and number of subscriptions.
         * @return              country names and number of subscriptions
         */
        LinkedList<double[]> getParsedTable();

        /**
         * Gets title of CSVReader object.
         * @return      title of the parsed file
         */
        String getTitle();

}
