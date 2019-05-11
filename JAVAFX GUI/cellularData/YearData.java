package cellularData;

/**
 * YearData class stores a year and the data for that year of specific country.
 * @author Foothill College, Bita M., Team 8
 */
public class YearData {
    private int year;
    private double data;


    /**
     * Parameter-taking constructor for creating YearData object.
     * @param year  takes an int value which contains the year
     * @param data  takes a double value which contains the data for the year
     */
    public YearData(int year, double data){
        this.year = year;
        this.data = data;
    }


    /**
     * Constructor for creating a deep copy of YearData object.
     * @param src              yearData object to copy
     */
    public YearData(YearData src){
        this.year = src.year;
        this.data = src.data;
    }


    /**
     * The method returns the year of the YearData Object.
     * @return     year
     */
    public int getYear() { return year; }


    /**
     * The method is used to get data of the YearData Object
     * @return                 subscription
     */
    public double getData() { return data; }


    /**
     * The method is used to set year for the YearData Object
     * @param year             year, that we want to set
     */
    public void setYear(int year) { this.year = year; }


    /**
     * The method is used to set the data for the YearData Object
     * @param data    data, that we want to set
     */
    public void setData(double data) {
        this.data = data;
    }


    /**
     * String representation of the value of the YearData Object
     * @return                 number of data.
     */
    public String toString() {
        return "yearData{" +
                "data=" + data;
    }
}