package companyManager;

public class CompanyManager 
{
	public static void main(String[] args) 
	{
		Company myCompany = new Company();
		System.out.printf("Welcome, your company name is %s\n", myCompany.getName());
		myCompany.printLocations();
		//System.exit(0);
	}

}
