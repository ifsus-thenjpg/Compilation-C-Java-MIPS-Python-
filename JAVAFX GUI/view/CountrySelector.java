package view;

import cellularData.Country;
import cellularData.LinkedList;

import java.util.List;

/**
 * Class CountrySelector represents a list of selected countries selected by user.
 * @author Foothill College, Bita M., Susanna Morin, Yuliia Trubchyk, Victoria Belvetlace
 */
public class CountrySelector 
{
	private LinkedList<Country> selectedCountries;

	/**
	 * Builds a linked list of country objects selected by user.
	 * @param allCountries      An array of all Country objects
	 * @param countyNames	The list of requested country names.
	 */
	public CountrySelector(Country[] allCountries, List<String> countyNames)
	{

		selectedCountries = new LinkedList<>();
		for (int i = 0; i < allCountries.length; i++)
		{
			Country country = allCountries[i];
			if(countyNames.contains(country.getName()))
				selectedCountries.add(country);
		}
	}

	/**
	 * Accessor method for selected list of countries.
	 * @return LinkedList of Country objects.
	 */
	public LinkedList<Country> selectCountries()
	{
		return this.selectedCountries;
	}
}
