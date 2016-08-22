package company;

import java.util.ArrayList;
import java.util.Random;

import location.Location;
 
public class Company 
{
	private ArrayList<Location> companyLocations = new ArrayList<Location>();
	private String name = "Testing Company";
	public Company() // debugging purpose, 3 locations with 10 items in each
	{
		Random rand = new Random();
		for(int i=1;i<=30;i++)
		{
			Location currentLocation = addLocation("Location #"+i);
			for(int z=1; z<=10; z++) 
			{
				int randomNumber = rand.nextInt(50);
				try
				{
					currentLocation.addItem("Item no #" + z, randomNumber, randomNumber+1, 1.11);
				}
				catch(IllegalArgumentException e)
				{
					System.err.printf("Warrning!\nError occured: %s\n\n", e.getMessage());
				}
			}
		}
	}
	public Company(ArrayList<Location> aCompanyLocations)
	{
		this.companyLocations = aCompanyLocations;
	}
	public String getName()
	{
		return this.name;
	}
	public ArrayList<Location> getLocations()
	{
		return this.companyLocations;
	}
	public Location addLocation(String aName)
	{
		Location newLocation = new Location(aName);
		this.companyLocations.add(newLocation);
		return newLocation;
	}
}
