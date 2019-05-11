package view;

        import cellularData.*;

        import javafx.scene.chart.LineChart;
        import javafx.scene.chart.NumberAxis;
        import javafx.scene.chart.XYChart;
        import javafx.util.StringConverter;

        import java.util.List;

/**
 * 	Class LineGraphView represents a data series for each Country object as a line graph.
 * 	@author Foothill College, Bita M., Susanna Morin, Yuliia Trubchyk, Victoria Belvetlace
 */
public class LineGraphView extends LineChart<Number,Number> {
    private DataModel model;    //Instance variable called model of type DataModel, which provide access to our CSV data.

    /**
     * The constructor sets up the instance variables.
     * Constructs the x and y axes using a NumberAxis, label the axes.
     * @param model data
     * @param title title of the chart
     * @param yAxisLabel label for y axis
     */
    public LineGraphView(DataModel model, String title, String yAxisLabel){
        super(new NumberAxis(), new NumberAxis());
        setTitle(title);
        this.model = model;
        final NumberAxis xAxis = (NumberAxis)getXAxis();
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.intValue() == object.doubleValue()?object.intValue() + "":"";
            }

            @Override
            public Number fromString(String string) {
                return Double.parseDouble(string);
            }
        });
        final NumberAxis yAxis = (NumberAxis)getYAxis();
        xAxis.setForceZeroInRange(false);
        yAxis.setForceZeroInRange(false);
        xAxis.setLabel("Years");
        yAxis.setLabel(yAxisLabel);
    }

    /**
     * The method creates the series based on the SubscriptionYear information of the current country.
     * Sets the name of the series to the name of the country.
     * @param newCountry Country object
     * @return series object
     */
    private Series<Number, Number> seriesFromCountry(Country newCountry){
        XYChart.Series series = new XYChart.Series();
        series.setName(newCountry.getName());      //the name of the series is the name of the country.
        for (YearData year:newCountry.getData()){
            series.getData().add( new XYChart.Data( year.getYear(), year.getData() ) );
        }
        return series;
    }

    /**
     * Gets all the data from the model (list of Country objects)
     * Creates a list of selected elements from the data.
     * Traverses each element and creates a series from each element.
     * @param countryNames linked list of country names
     */
    public void update(List<String> countryNames) {
        CountrySelector countryS = new CountrySelector(model.getCellularData(), countryNames);
        LinkedList<Country> list = countryS.selectCountries();
        //System.out.println(list); //for test purposes
        for (Country currentCountry : list) {
            Series<Number, Number> someSeries = seriesFromCountry(currentCountry);
            this.getData().add(someSeries);
        }
    }

}
