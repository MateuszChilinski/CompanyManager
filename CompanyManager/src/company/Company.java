package company;

/*
 * Copyright (c) 2016 Mateusz Chiliñski https://chilinski.eu. All rights reserved.
 * 
 */

import java.util.ArrayList;
import java.util.Random;

import item.Item;
import location.Location;
 
public class Company 
{
	private ArrayList<Location> companyLocations = new ArrayList<Location>();
	private String name = "New Company";
	
	public Company(String aName) {
		name = aName;
	}
	public Company() {
		
	}
	public String getName()
	{
		return name;
	}
	public ArrayList<Location> getLocations()
	{
		return companyLocations;
	}
	public Location getLocation(int i)
	{
		return companyLocations.get(i);
	}
	public void setName(String name) {
		name = name;
	}
	public Location addLocation(String aName)
	{
		Location newLocation = new Location(aName);
		companyLocations.add(newLocation);
		return newLocation;
	}
	public Location addLocation(String aName, ArrayList<Item> items)
	{
		Location newLocation = new Location(aName, items);
		companyLocations.add(newLocation);
		return newLocation;
	}
	public Location addLocation(Location location)
	{
		companyLocations.add(location);
		return location;
	}
}
