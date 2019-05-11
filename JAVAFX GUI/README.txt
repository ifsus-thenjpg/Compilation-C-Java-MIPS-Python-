project folder:
team08-project09/


Brief description of submitted files:

src/view/ChartGraph
    - Main application Instantiates an JavaFX application which creates a GraphPane(that extends BorderPane).
    - Sets up the scene with basic modification to the stage.
    - Includes main() for running the application.

src/view/GraphPane
    -A class that represents the BorderPane with top and left menus and center to graph data.

src/view/CountrySelector
    - A class that generates a linked list of country objects from the list of requested country names.

src/view/BarGraphView
    - A class that creates a bar graph.

src/view/LineGraphView
    - A class that creates a line graph.

src/view/Icon.png
    - icon for the stage

lib/controlsfx-8.40.12.jar
    - library used for combo check boxes

lib/jfxrt.jar
    - javafx library

src/DataModel
    - A class that provides access to CSV data and returns an array of objects that contain all data.

src/cellularData/ICVReader.java
    - interface that provides methods for CSVReader classes.

src/cellularData/CVReader.java
    - Reads the cellular data CSV file and parses it
    - stores year labels in the array of integers
    - stores country names in the linked list of strings
    - stores cellular data in the linked list of arrays of doubles

src/cellularData/NewCVReader.java
    - Reads the human development CSV file and parses it
    - stores year labels in the array of integers
    - stores country names in the linked list of strings
    - stores cellular data in the linked list of arrays of doubles

src/cellularData/LinkedList.java
    - Generic Linked List
    - Keeps track of generic Node objects

src/cellularData/Node.java
    - Holds data for one generic node in a data structure.

src/cellularData/Country.java
    - One object of class  represents one country.
    - Stores country name and a Linked List with the YearData objects.
    - Calculates the number of subscriptions data for given range of years

src/cellularData/YearData.java
    -  One object of class represents one year of data
    -  Stores the year of the data and the data for that year.

resources/cellular.csv
    - A CSV (Comma Separated Value) file.
    - First row contains the title.
    - Second row contains number of countries.
    - Third row contains years for with subscription information is available in the format:
      Country Name,year,year,year
    - Lines 4 to EOF (end of file) contain name of the country and number of subscriptions in the format:
      country name,number of subscriptions,number of subscriptions,number of subscriptions

resources/humandevelopment.csv
    - A CSV (Comma Separated Value) file.
    - First row contains the title.
    - Second row contains years for with data is available in the format:
      HDI Rank (2015),Country,year,year,year
    - Lines 3 to EOF (end of file) contain country rank, name of the country and data in the format:
      rank,country name,data,data,data

resources/project_proposal.md
    - description of the project features

resources/storyboard1.png
resources/storyboard2.png
    - pictures of the proposed GUI features

resources/style.css
    - styles for the graphs

resources/task.md
    - description of the work to be done and who did it

resources/RUN.png
    - screen of the output

README.txt
    - description of submitted files