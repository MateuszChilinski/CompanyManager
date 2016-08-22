package item;

public class ItemView {
	public void printInfo(String itemName, int quantity, int maximum, double price)
	{
		System.out.printf("Item name: %s\nQuantity: %d\nMaximum quantity: %d\nPrice per unit: %f", itemName, quantity, maximum, price);
	}
}
