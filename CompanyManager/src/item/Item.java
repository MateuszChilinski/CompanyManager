package item;

public class Item {
	private String name = "";
	private int quantity = 0;
	private int maximum = 0;
	private double price = 0;
	
	public Item(String aItemName, int aQuantity, int aMaximum, double aPrice) {
		name = aItemName;
		quantity = aQuantity;
		maximum = aMaximum;
		price = aPrice;
	}
	public boolean equals(Object o)
	{
		if(this.getClass() != o.getClass())
			return false;
		if(this.name.equals(((Item) o).name))
			return true;
		else
			return false;
	}
	public String getName() {
		return name;
	}
	public int getQuantity() {
		return this.quantity;
	}
	public double getPercentage() throws ArithmeticException {
		if(maximum == 0)
			return 1.0;
		return quantity/maximum;
	}
	public int getMaxmimum() {
		return maximum;
	}
	public double getPrice() {
		return price;
	}
	public void addItem(int addQuantity) {
		quantity += addQuantity;
	}
	public void removeItem(int removeQuantity) {
		quantity -= removeQuantity;
	}
	public void removeItem() {
		name = null; 
		quantity = 0; 
		maximum = 0; 
		price = 0;
	}
	public void setName(String aName) {
		name = aName;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
