package location;

import java.util.ArrayList;

import item.Item;
import item.ItemController;
import item.ItemView;

public class LocationView {
	
	public void printLocationInfo(String name, int itemsCount)
	{
		System.out.printf("Location name: %s\nItems in stock: %d\n", name, itemsCount);
	}

	public void printLocationItemsInfo(ArrayList<Item> items, String name)
	{
		System.out.printf("Location name: %s\n", name);
		for(Item currentItem : items)
		{
			ItemController currentItemController = new ItemController(currentItem, new ItemView());
			currentItemController.printInfo();
		}
	}
}
