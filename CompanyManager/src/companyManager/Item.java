package companyManager;

public class Item {
	private String itemName = "";
	private int quantity = 0;
	private int maximum = 0;
	private double price = 0;
	Item(String aItemName, int aQuantity, int aMaximum, double aPrice)
	{
		this.itemName = aItemName;
		this.quantity = aQuantity;
		this.maximum = aMaximum;
		this.price = aPrice;
	}
	public String getName()
	{
		return this.itemName;
	}
	public int getQuantity()
	{
		return this.quantity;
	}
	public double getPercentage() // add throwing if maximum == 0
	{
		/**if(maximum == 0)
			throw**/
		return quantity/maximum;
	}
	public void printInfo()
	{
		System.out.printf("Item name: %s\nQuantity: %d\nMaximum quantity: %d\nPrice per unit: %f", this.itemName, this.quantity, this.maximum, this.price);
	}
	public void removeItem(int removeQuantity)
	{
		this.quantity -= removeQuantity;
	}
	public void addItem(int addQuantity)
	{
		this.quantity += addQuantity;
	}
}
