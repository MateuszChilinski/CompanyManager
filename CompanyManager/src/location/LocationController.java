package location;

public class LocationController {
	private Location model;
	private LocationView view;
	public LocationController(Location location, LocationView view) {
		this.model = location;
		this.view = view;
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
}
