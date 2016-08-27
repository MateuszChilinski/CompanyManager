package fileSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.*;

import company.*;
import location.*;
import item.*;

public class FileSystem {
	JSONObject currentObject;
	public FileSystem()
	{
		
	}
	public String loadFile(String path) throws IOException
	{
		try(FileInputStream currentFile = new FileInputStream(path))
		{
			return new String(Files.readAllBytes(Paths.get(path)), "utf-8");
		}
	}
	public void saveFile(String path, String json) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) 
		{
			writer.write(json);
		}
	}
	public Company importCompany(String company)
	{
		currentObject = new JSONObject(company);
		Company importedCompany = new Company(currentObject.getString("companyName"));
		for(Object iterateLocation : (JSONArray) currentObject.get("companyLocations"))
		{
			importedCompany.addLocation(importLocation((JSONObject) iterateLocation));
		}
		return importedCompany;
	}
	public String exportCompany(Company company)
	{
		String newCompany = "{\"companyName\":\"" + company.getName() + "\", \"companyLocations\":[\n";
		for(Location location : company.getLocations())
		{
			newCompany += "{\"locationName\":\"" + location.getName() + "\", \"locationItems\":[\n";
			for(Item item : location.getItems())
			{
				newCompany += "{\"itemName\":\"" + item.getName() + "\", \"itemQuantity\":" + item.getQuantity() + ", \"itemMaximum\":"
							+ item.getMaxmimum() + ", \"itemPrice\":" + item.getPrice() + "},\n";
			}
			if(location.getItems().size() > 0)
				newCompany = newCompany.substring(0, newCompany.length()-2);
			else
				newCompany = newCompany.substring(0, newCompany.length()-1);
			newCompany += "]},\n";
		}
		newCompany = newCompany.substring(0, newCompany.length()-2);
		newCompany += "]}\"";
		return newCompany;
	}
	public Location importLocation(JSONObject location)
	{
		Location newLocation = new Location(location.getString("locationName"));
		for(Object iterateItem : (JSONArray) location.get("locationItems"))
		{
			newLocation.addItem(importItem((JSONObject) iterateItem));
		}
		return newLocation;
	}
	public Item importItem(JSONObject iterateItem)
	{
		return new Item(iterateItem.getString("itemName"), (int) iterateItem.get("itemQuantity"), (int) iterateItem.get("itemMaximum"), (double) iterateItem.get("itemPrice"));
	}
}
