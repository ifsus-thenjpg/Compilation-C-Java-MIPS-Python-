package view;

import cellularData.*;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import java.util.List;

/**
 * 	Class BarGraphView represents a data series for each Country object as a bar graph.
 * 	@author Foothill College, Bita M., Susanna Morin, Yuliia Trubchyk, Victoria Belvetlace
 */
public class BarGraphView extends BarChart<String, Number>{

    private DataModel model;    //Instance variable called model of type DataModel, which provide access to our CSV data.

    /**
     * The constructor sets up the instance variables.
     * Constructs the x and y axes using a NumberAxis, label the axes.
     * @param model data
     * @param title title of the chart
     * @param yAxisLabel label for y axis
     */
    public BarGraphView(DataModel model, String title, String yAxisLabel){
        super(new CategoryAxis(), new NumberAxis());
        setTitle(title);
        this.model = model;
        final CategoryAxis xAxis = (CategoryAxis)(getXAxis());
        xAxis.tickLabelFontProperty().set(Font.font(15));
        xAxis.setTickLabelRotation(90);
        final NumberAxis yAxis = (NumberAxis)getYAxis();
        yAxis.setForceZeroInRange(false);
        yAxis.setLabel(yAxisLabel);
    }

    /**
     * The method creates the series based on the SubscriptionYear information of the current country.
     * Sets the name of the series to the name of the country
     * @param year      year
     * @param countries linked list of country objects
     * @return series object
     */
    private Series<String, Number> seriesFromYear(int year, LinkedList<Country> countries){
        XYChart.Series series = new XYChart.Series();
        series.setName("" + year);
        for (Country c : countries ) {
            double data = 0;
            for (YearData y : c.getData()) {
                if (y.getYear() == year) {
                    data = y.getData();
                }
            }

            XYChart.Data d = new XYChart.Data( c.getName(), data );
            series.getData().add( d );
        }
        return series;
    }

    /**
     * Method shows the name and the data for the country
     * when the mouse hovers over the bar representation of the country.
     * @param d  data for the country
     * @param countryName name of the country
     */
    private void addTooltip(Data d, String countryName) {
        Tooltip.install(d.getNode(), new Tooltip(
                        "Country : " + countryName + " " + d.getYValue()));

        d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

        d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
    }

    /**
     * The method creates the series based on the data of the current country.
     * Sets the name of the series to the name of the country.
     * @param current Country object
     * @param year requested year
     * @return  series
     */
    private Series<String, Number> seriesFromCountry(Country current,int year){
            XYChart.Series series = new XYChart.Series();
            series.setName(current.getName());
            double data = 0;
            for (YearData y : current.getData()) {
                if (y.getYear() == year) {
                    data = y.getData();
                }
            }

            XYChart.Data d = new XYChart.Data( "", data );
            series.getData().add( d );

        return series;
    }

    /**
     * Creates a list of selected elements from the the model (list of Country objects)
     * Traverses each element and creates a series from each element.
     * Calls the method for hover over effect.
     * @param year requested year
     * @param countryNames list of the names of the requested countries
     */
    public void update(int year, List<String> countryNames) {
        CountrySelector countryS = new CountrySelector(model.getCellularData(), countryNames);
        LinkedList<Country> list = countryS.selectCountries();
        for (Country currentCountry : list) {
            Series<String, Number> someSeries = seriesFromCountry(currentCountry, year);
            this.getData().add(someSeries);
            for(XYChart.Data d : this.getData().get(this.getData().size()-1).getData())
                addTooltip(d, currentCountry.getName());
        }
    }
}
