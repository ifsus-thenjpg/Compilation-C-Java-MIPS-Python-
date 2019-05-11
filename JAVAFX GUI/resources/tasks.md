Tasks for Project 9 and Team [NUMBER]
=====================================

Part A
---------

- task 1: [DESCRIPTION, including classes and methods involved] creating bar chart, adding it to scene, handling input events,
     organizing stage into HBox, VBox,StackPanes, adding checkbox for country selection and adding to scene and stage, 
     creating drop down for years available to each data files, handling input events, add icon to stage
	- [OWNER] Susanna 
	- [PROJECTED COMPLETION DATE]  6/21
	- [ACTUAL COMPLETION DATE TO github.com repo] TBD


- task 2: [DESCRIPTION, including classes and methods involved] adding line chart to stage, organizing stage into HBox, VBox,
        StackPanes, creating tabs to toggle from Scene1(line) and Scene 2(bar), handling input events for scene 1
	- [OWNER] Victoria 
	- [PROJECTED COMPLETION DATE] 6/21
	- [ACTUAL COMPLETION DATE TO github.com repo] TBD


- task 3: [DESCRIPTION, including classes and methods involved] implementing checkbox for scene 1 for line chart 
	- [OWNER] Julia 
	- [PROJECTED COMPLETION DATE] 6/21
	- [ACTUAL COMPLETION DATE TO github.com repo] TBD
etc.



<br><br>

Part B - Tasks completed by each member 
Susanna: 
- created BarGraphView class - BarGraphView constructor, seriesFromYear() (*later used for seriesFromCountry*), update()
- separated the GraphPane into two separate files to handle implementations of two scenes (merged back to GraphPane later*)
- made ChartGraph only handle simple tasks instead of having GraphPane tasks nested inside
- added the checkbox feature for countries to the BartChart
- added icon to the application window 
- updated fileChoice() for dropdown year menu to handle clears for countries and year menus
- setYears()creates the series based on the SubscriptionYear information of the current country.
- YearComboBox to allow user to select year that will be graphed along with selected countries, only allows min,max
  year dependent on the file chosen
- handled keyEvents to show on the console when the user selected from file, country, and year menu (removed later*)

Victoria: 
- Created the layout using BorderPane, addHBox(), addVBox(), add CentralPane(), updateCentralPane()
  with initial switching between two scenes
- Created fileChoice() to choose a data file to parse
- Added short user manual to the default scene
- updated fileChoice() to be shorter in accordance with program guidelines by moving code that belongs to the setYears()
- removed unused variables, converted some variables to local.
- Added/corrected comments, generated javadocs, wrote README file, created snapshots of the run.

Julia:
- added library for CheckComboBox
- implemented CheckComboBox for the LineChart
- addToolTip method for the hover effect label on the BarChart
- style.css stylesheet for formatting bar graph
- code refactoring into concise and efficient style with less classes and methods
- moved country names from the axis labels to the legend for more spacious fit
- returned user manual that was lost somewhere in the process
- added clear button

Branches used: master
Since each team member had different schedules, we divided the work by what needed to be done first.
Our work flow followed schedules and each person sequentially added and/or updated previous work and then integrated
their work on top.

---------

<br><br>

Extra Credit (if applicable)
-----------------------
Will be demoing project during class - sent email to Bita Friday 6/23.


<br><br>

Extra Credit Discussion (if applicable)
-----------------------
A discussion that was useful when programming side by side was one that involved whether we wanted to use a BarChart in the 
second scene or if we wanted to instead use text output and make use of the getNumSubscriptionsForPeriod() in CountryClass. 
We opted to use the BarChart feature because it looks more visually appealing and modern.

Also, we discussed whether it would be a better design to add a range of years (start year and end year) that can
chosen by user to be graphed in the BarChart with different series plotted against each other. We ultimately, decided on
having the user select one year so we can concentrate on the hover effect to satisfy InputEvent.


<br><br>

