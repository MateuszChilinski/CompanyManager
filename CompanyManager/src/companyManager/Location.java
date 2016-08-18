package companyManager;

import java.util.ArrayList;

public class Location 
{
	private String name;
	private ArrayList<Item> locationItems = new ArrayList<Item>();
	Location(String aName)
	{
		this.name = aName;
	}
	Location(String aName, ArrayList<Item> aLocationItems)
	{
		this.name = aName;
		this.locationItems = aLocationItems;
	}
	public void addItem(String aItemName, int aQuantity, int aMaximum, double aPrice)
	{
		locationItems.add(new Item(aItemName, aQuantity, aMaximum, aPrice));
	}
	public void printLocationInfo()
	{
		System.out.printf("Location name: %s\nItems in stock: %d\n", name, locationItems.size());
	}
	public void printLocationItemsInfo()
	{
		System.out.printf("Location name: %s\n", name);
		for(Item currentItem : locationItems)
		{
			currentItem.printInfo();
		}
	}
}
