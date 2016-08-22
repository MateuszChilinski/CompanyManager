package item;

public class Item {
	private String itemName = "";
	private int quantity = 0;
	private int maximum = 0;
	private double price = 0;
	public Item(String aItemName, int aQuantity, int aMaximum, double aPrice) throws IllegalArgumentException
	{
		if(aMaximum <= 0 || aQuantity < 0 || aPrice < 0)
			throw new IllegalArgumentException("Invaild arguments! Maximum value must be more than 0, quantity and price must be equal or more than 0!");
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
	public double getPercentage() throws ArithmeticException
	{
		if(maximum < 0)
			throw new ArithmeticException("Maximum value is zero!");
		return quantity/maximum;
	}
	public int getMaxmimum() {
		return this.maximum;
	}

	public double getPrice() {
		return this.price;
	}
	
	public void removeItem(int removeQuantity)
	{
		this.quantity -= removeQuantity;
	}
	public void addItem(int addQuantity)
	{
		this.quantity += addQuantity;
	}
	
	public boolean equals(Object o)
	{
		if(this.getClass() != o.getClass())
			return false;
		if(this.itemName.equals(((Item) o).itemName))
			return true;
		else
			return false;
	}
}
