package location;

import java.util.ArrayList;

import item.Item;

public class Location 
{
	private String name;
	private ArrayList<Item> locationItems = new ArrayList<Item>();
	
	public Location(String aName) {
		name = aName;
	}
	public Location(String aName, ArrayList<Item> aLocationItems) {
		name = aName;
		locationItems = aLocationItems;
	}
	public ArrayList<Item> getItems() {
		return locationItems;
	}
	public String getName() {
		return name;
	}
	public int getItemsCount() {
		return locationItems.size();
	}
	public Item getItem(int itemID) {
		return locationItems.get(itemID);
	}
	public void setName(String aName) {
		name = aName;
	}
	public void addItem(Item item)
	{
		locationItems.add(item);
	}
	public Item addItem(String aItemName, int aQuantity, int aMaximum, double aPrice) {
		Item newItem = new Item(aItemName, aQuantity, aMaximum, aPrice);
		locationItems.add(newItem);
		return newItem;
	}
	public void removeLocation() {
		name = null;
		locationItems = null;
	}
}
