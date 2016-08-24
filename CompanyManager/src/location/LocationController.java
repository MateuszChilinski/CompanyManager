package location;

import javax.swing.JFrame;

public class LocationController {
	private Location model;
	private LocationView view;
	public LocationController(Location location, LocationView view) {
		this.model = location;
		view.setController(this);
		this.view = view;
	}
	public void removeLocation()
	{
		model.setName(null);
	}
	public void addItem(String aItemName, int aQuantity, int aMaximum, double aPrice) throws IllegalArgumentException
	{
		model.addItem(aItemName, aQuantity, aMaximum, aPrice);
	}
	public void printLocationInfo()
	{
		view.printLocationInfo(model.getName(), model.getItemsCount());
	}
	public String getName()
	{
		return model.getName();
	}
	public int getItemsCount()
	{
		return model.getItemsCount();
	}
	public void printLocationItemsInfo()
	{
		view.printLocationItemsInfo(model.getItems(), model.getName());
	}
	public void locationEditor(JFrame owner)
	{
		view.displayDialbox(owner);
	}
	public void editLocation(String name)
	{
		this.setName(name);
	}
	public void setName(String name)
	{
		model.setName(name);
	}
}
