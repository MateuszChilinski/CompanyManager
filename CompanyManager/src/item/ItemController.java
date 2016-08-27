package item;

import javax.swing.*;

import location.LocationView;

public class ItemController {
	private Item model;
	private ItemView view;
	
	public ItemController(Item model, ItemView view)
	{
		this.model = model;
		this.view = view;
	}
	public String getName()
	{
		return model.getName();
	}
	public int getQuantity()
	{
		return model.getQuantity();
	}
	public double getPercentage() throws ArithmeticException
	{
		return model.getPercentage();
	}
	public void removeItem()
	{
		model.removeItem();
	}
	public void removeItem(int removeQuantity)
	{
		model.removeItem(removeQuantity);
	}
	public void addItem(int addQuantity)
	{
		model.addItem(addQuantity);
	}
	public void printInfo()
	{
		view.printInfo(model.getName(), model.getQuantity(), model.getMaxmimum(), model.getPrice());
	}
	public void itemEditor(JDialog locationDialbox, boolean isNew) {
		view.displayDialbox(locationDialbox, isNew);
	}
	public int getMaximum() {
		return model.getMaxmimum(); 
	}
	public double getPrice() {
		return model.getPrice();
	}
	public void setName(String name) {
		model.setName(name);
	}
	public void setQuantity(int quantity){
		model.setQuantity(quantity);
	}
	public void setMaximum(int maximum){
		model.setMaximum(maximum);
	}
	public void setPrice(double price){
		model.setPrice(price);
	}
}
