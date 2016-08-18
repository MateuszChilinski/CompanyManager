package companyManager;

import java.util.ArrayList;
import java.util.Random;
 
public class Company 
{
	private ArrayList<Location> companyLocations = new ArrayList<Location>();
	private String name = "Testing Company";
	Company() // debugging purpose, 3 locations with 10 items in each
	{
		Random rand = new Random();
		for(int i=1;i<=3;i++)
		{
			Location currentLocation = addLocation("Location #"+i);
			for(int z=1; z<=10; z++) 
			{
				int randomNumber = rand.nextInt(50);
				currentLocation.addItem("Item no #" + z, randomNumber, randomNumber, 1.11);
			}
		}
	}
	Company(ArrayList<Location> aCompanyLocations)
	{
		this.companyLocations = aCompanyLocations;
	}
	public String getName()
	{
		return this.name;
	}
	public Location addLocation(String aName)
	{
		Location newLocation = new Location(aName);
		this.companyLocations.add(newLocation);
		return newLocation;
	}
	public void printLocations()
	{
		for(Location currentLocation : this.companyLocations)
		{
			currentLocation.printLocationInfo();
		}
	}
}
