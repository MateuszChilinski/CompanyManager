package companyManager;

public class ItemController {
	private Item model;
	private ItemView view;
	
	ItemController(Item model, ItemView view)
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
}
