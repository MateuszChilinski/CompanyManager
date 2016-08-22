package companyManager;

import java.util.ArrayList;

public class CompanyView {
	public void printLocations(ArrayList<Location> companyLocations)
	{
		for(Location currentLocation : companyLocations)
		{
			LocationController currentLocationController = new LocationController(currentLocation, new LocationView());
			currentLocationController.printLocationInfo();
		}
	}
}
