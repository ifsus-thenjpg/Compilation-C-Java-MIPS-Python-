package cellularData;

import java.util.Iterator;

/**
 * Country class stores a name of the particular country and LinkedList of data.
 * @author Foothill College, Bita M., Team 8
 */
public class Country {
    private String name;                  // stores the name of the country
    private LinkedList<YearData> data;    // holds all the data for that country
    private int minYear = 9999;
    private int maxYear = 0;


    /**
     * Parameter-taking constructor for creating Country object.
     * @param name               name of the country
     */
    public Country(String name){
        this.name = name;
        data = new LinkedList<>();
    }


    /**
     * Adds the year and the data for that year to the LinkedList
     * @param newYear              particular year
     * @param newData      data for particular year
     */
    public void addYearData(int newYear, double newData) {
        data.add(new YearData(newYear, newData));
        if(newYear < minYear){
            minYear = newYear;
        }
        if(newYear > maxYear){
            maxYear = newYear;
        }
    }


    /**
     * Calculates the total number of data for particular period.
     * @param startYear                 starting year of the requested period
     * @param endYear                   ending year of the requested period
     * @return totalNumOfSubscriptions   total number of data
     * @throws IllegalArgumentException is thrown when years are out of range
     */
    public double getNumSubscriptionsForPeriod(int startYear, int endYear) throws IllegalArgumentException {
        double totalNumOfSubscriptions = 0;
        int period = endYear - startYear;
        if(period < 0 || startYear < minYear ||  endYear > maxYear) {
            if (data.size() != 0) {
                totalNumOfSubscriptions = getNumSubscriptionsForPeriod(minYear, maxYear);
            } else {
                totalNumOfSubscriptions = 0;
            }
            String totalRes = String.format( "Total data between %d - %d = %.2f" , minYear, maxYear, totalNumOfSubscriptions);
            throw new IllegalArgumentException("Illegal Argument Request of year range " + startYear + "-" + endYear + "." +
                    " Valid period for " + name + " is " + minYear + " to " + maxYear + ".\n" + totalRes + "\n");
        }

        Iterator<YearData> iterator = data.iterator();

        while (iterator.hasNext()) {
            YearData currentYear = iterator.next();
            if (currentYear.getYear() >= startYear && currentYear.getYear() <= endYear) {
                totalNumOfSubscriptions += currentYear.getData();
            }
        }
        return totalNumOfSubscriptions;
    }

    /**
     * The method is used to get a String object representing the value of the Country Object
     * @return           name of country and its data
     */
    public String toString(){
        String result = "";
        for(YearData currentYear: data) {
            result += String.format("\t%.2f", currentYear.getData());
        }
        return this.name + ": " + result;
    }


    /**
     * Returns the name of the country .
     * @return           name of country
     */
    public String getName() {
        return name;
    }


    /**
     * Returns data of County object
     * @return data     LinkedList of yearData
     */
    public LinkedList<YearData> getData() {
        return data;
    }

    /**
     * Compares two Country objects.
     * @param o       object, that we want to compare with
     * @return        boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return name != null ? name.equals(country.name) : country.name == null;
    }
}