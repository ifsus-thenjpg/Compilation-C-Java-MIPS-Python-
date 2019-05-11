package view;

import cellularData.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import  javafx.collections.ListChangeListener;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Class GraphPane represents the BorderPane with top and left menus and center to graph data.
 * @author Foothill College, Bita M., Susanna Morin, Yuliia Trubchyk, Victoria Belvetlace.
 */
public class GraphPane extends BorderPane {

    private String label;
    private ICVReader reader;
    private StackPane centerPane;
    private CheckComboBox<String> checkComboBox;
    private ComboBox<Integer> yearComboBox;
    private boolean displayBarGraph = false;
    private Button chooseFeatureBtn;
    private Label sceneLabel;
    private Text selectYearLabel;

    /**
     * Constructor sets top, left, and center part of the GraphPane(which is BorderPane).
     */
    GraphPane(){
        setTop(addHBox());
        setLeft(addVBox()); // left column with user menus
        setCenter(addCenterPane()); // for displaying the second feature data
        updateData();
    }


    /**
     * Method for the displaying the second feature data
     * @param year selected year
     * @param countryNames linked list of the names of the countries
     * @return lineGraphView
     */
    public Region updateCenterPane(int year, List<String> countryNames) {

        DataModel model = new DataModel(reader);
        String title = model.getTitle();
        if (displayBarGraph) {
            BarGraphView barGraphView = new BarGraphView(model, title, label);
            barGraphView.update(year, countryNames);
            return barGraphView;
        } else {
            LineGraphView lineGraphView = new LineGraphView(model, title, label);
            lineGraphView.update(countryNames);
            return lineGraphView;
        }
    }

    /**
     * Displaying the years.
     * @return yearComboBox
     */
    public ComboBox createYearDropDown(){
        yearComboBox = new ComboBox();//need to take in year labels
        yearComboBox.setVisibleRowCount(5);
        yearComboBox.setMaxWidth(150);

        //after choosing a year it will auto graph
        yearComboBox.setOnAction(e -> updateData());
        return yearComboBox;
    }

    /**
     * Updates pane according to displayBarGraph variable.
     */
    private void updateData() {
        chooseFeatureBtn.setText(displayBarGraph? "Switch to Line Graph": "Switch to BarGraph");
        sceneLabel.setText(!displayBarGraph? "LineGraph": "BarGraph");
        yearComboBox.setVisible(displayBarGraph);
        selectYearLabel.setVisible(displayBarGraph);
        if (reader == null) return;
        int year = 0;
        if(yearComboBox.getValue() != null) {
            year = yearComboBox.getValue();
        }
        List<String> countries = checkComboBox.getCheckModel().getCheckedItems();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(updateCenterPane(year, countries));
    }


    /**
     * Sets the year labels for the dropdown menu.
     */
    public void setYears(){
        int minYear = 9999;
        int maxYear = 0;

        for(int i = 0; i< reader.getYearLabels().length; i++){
            int newYear = reader.getYearLabels()[i];
            if(newYear < minYear){
                minYear = newYear;
            }
            if(newYear > maxYear){
                maxYear = newYear;
            }
        }
        List<Integer> yearList = new ArrayList<>();
        for (int i = minYear; i<= maxYear; i++)
            yearList.add(i);

        yearComboBox.getItems().setAll(yearList);
    }

    /**
     * Method for setting up the left menu with dropdown menus for
     * selecting data file, countries and years.
     * @return leftColumn
     */
    private VBox addVBox(){
        VBox leftColumn = new VBox();
        leftColumn.setPadding(new Insets(30, 15, 20, 15));
        leftColumn.setSpacing(30);
        leftColumn.setStyle("-fx-background-color: #C0C0C0;");

        checkComboBox = new CheckComboBox<>();
        checkComboBox.setMaxWidth(150);

        checkComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) r->{
                updateData();
        });

        Text dataChoice = new Text("1. Select Data File:");
        dataChoice.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        leftColumn.getChildren().add(dataChoice);
        leftColumn.getChildren().add(fileChoice());

        Text choice3 = new Text("2. Select Countries:");
        choice3.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        leftColumn.getChildren().add(choice3);
        leftColumn.getChildren().add(checkComboBox);

        selectYearLabel = new Text("3. Select Year:");
        selectYearLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        leftColumn.getChildren().add(selectYearLabel);
        leftColumn.getChildren().add(createYearDropDown());

        return leftColumn;
    }

    /**
     * Method for setting up the header with label for the scene
     * and buttons to switch between the bar graph and line graph.
     * @return featureChoice HBox the header
     */
    public HBox addHBox() {
        HBox featureChoice = new HBox();
        featureChoice.setPadding(new Insets(20, 20, 20, 20));
        featureChoice.setSpacing(30);
        featureChoice.setStyle("-fx-background-color: lightgrey;");
        featureChoice.setAlignment(Pos.CENTER_LEFT);
        chooseFeatureBtn = new Button("");

        chooseFeatureBtn.setOnAction(event -> switchGraph());

        sceneLabel = new Label("");
        sceneLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        Button clearSelectionBtn = new Button("Clear");
        clearSelectionBtn.setOnAction(e -> checkComboBox.getCheckModel().clearChecks());

        featureChoice.getChildren().addAll(sceneLabel, chooseFeatureBtn,clearSelectionBtn);

        return featureChoice;
    }

    /**
     * Stands for Graph switching, calls updateData() method;
     */
    private void switchGraph() {
        displayBarGraph = ! displayBarGraph;
        updateData();
    }

    /**
     * Sets default center for the first feature.
     * @return StackPane object centerPane
     */
    public StackPane addCenterPane() {
        centerPane = new StackPane();
        centerPane.setAlignment(Pos.TOP_CENTER);
        Text manual = new Text("In order to use the application:\n\n1. Select Data File." +
                "\n\n2. Select countries you would like to add to the chart line." +
                "\n\n3. To clear selected data, click on the \"Clear\" button on top of the screen.\n\n\n" +
                "To explore bar graph feature, click on the button \"Switch to BarGraph\"\non the top of the screen.");
        manual.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        centerPane.setMargin(manual, new Insets(30, 30, 30, 30));
        centerPane.getChildren().add(manual);
        return centerPane;
    }

    /**
     * Gives user to choose a data file.
     * Defines the parser to use.
     * Defines the label for the Y axis of the chart.
     * @return reader
     */
    public ChoiceBox<String> fileChoice(){
        ChoiceBox<String> fileChoice = new ChoiceBox<>(FXCollections.observableArrayList(
                "Cellular Data", "Human Development")
        );
        fileChoice.setMaxWidth(150);
        fileChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0){
                    reader = new CSVReader( "resources/cellular.csv");
                    label = "Subscriptions";
                }
                else {
                    reader = new NewCSVReader("resources/humandevelopment.csv");
                    label = "HDI";
                }

                for(int i = 0; i < checkComboBox.getItems().size(); i++){
                    //String current = checkComboBox.getItems().get(i);
                    checkComboBox.getCheckModel().clearCheck(i);
                }
                checkComboBox.getItems().clear();
                yearComboBox.getItems().clear();

                String countryName;
                for(int i = 0; i < reader.getCountryNames().size(); i++) {
                    countryName = reader.getCountryNames().getIndex(i);
                    checkComboBox.getItems().add(countryName);
                }

                setYears();
            }
        }
        );
        return fileChoice;
    }
}
