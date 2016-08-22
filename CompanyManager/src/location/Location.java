package location;

import java.util.ArrayList;

import item.Item;

public class Location 
{
	private String name;
	private ArrayList<Item> locationItems = new ArrayList<Item>();
	
	public Location(String aName)
	{
		this.name = aName;
	}
	public Location(String aName, ArrayList<Item> aLocationItems)
	{
		this.name = aName;
		this.locationItems = aLocationItems;
	}
	public void addItem(String aItemName, int aQuantity, int aMaximum, double aPrice) throws IllegalArgumentException
	{
		Item newItem;
		try
		{
			newItem = new Item(aItemName, aQuantity, aMaximum, aPrice);
		}
		catch(IllegalArgumentException e)
		{
			throw e;
		}
		if(this.locationItems.contains(newItem))
			throw new IllegalArgumentException("Item already in database!");
		this.locationItems.add(newItem);
	}
	public ArrayList<Item> getItems()
	{
		return this.locationItems;
	}
	public String getName()
	{
		return this.name;
	}
	public int getItemsCount()
	{
		return this.locationItems.size();
	}
}
