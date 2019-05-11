package cellularData;

/**
 * Provides access to CSV data.
 * @author Foothill College, Bita M., Team 8
 */
public class DataModel
{
    private Country[] cellularModel;
    private String title;

	/**
	 * Parameter-taking constructor for creating DataModel object.
	 * @param reader ICVReader
	 */
    public DataModel(ICVReader reader)
    {
        parseCSVFile(reader);
    }


	/**
	 * Stores parsed data to the  array of Country objects.
	 * @param parser ICVReader
	 * @return cellularModel, an array of Country objects
	 */
	private Country[] parseCSVFile(final ICVReader parser)
    {
        // Parses the CSV data file
        // In class CSVReader the accessor methods only return values of instance variables.
        LinkedList<String> countryNames = parser.getCountryNames();
        int [] yearLabels = parser.getYearLabels();
        LinkedList<double[]> parsedTable = parser.getParsedTable();
        title = parser.getTitle();

        // Initializes the to the number of entries read by CSVReader.
        cellularModel = new Country[countryNames.size()];

        // Reference to a Country object
        Country current;

        // Go through each country name parsed from the CSV file.
        for (int countryIndex = 0; countryIndex < cellularModel.length; countryIndex++)
        {
            int numberOfYears = yearLabels.length;

            current = new Country(countryNames.getIndex(countryIndex));

            // Go through each year of cellular data read from the CSV file.
            for (int yearIndex = 0; yearIndex < numberOfYears; yearIndex++)
            {
                double [] allData = parsedTable.getIndex(countryIndex);
                double countryData = allData[yearIndex];
                current.addYearData(yearLabels[yearIndex], countryData);
            }

            // Add the newly created country to the 1D array.
            cellularModel[countryIndex] = current;
        }

        return cellularModel;
    }

	/**
	 * Returns an array of Country objects.
	 * @return cellularModel
	 */
    public Country[] getCellularData()
    {
        return this.cellularModel;
    }

    /**
     * Gets and returns a string with title of the chart.
     * @return title
     */
    public String getTitle(){
        return title;
    }
}
