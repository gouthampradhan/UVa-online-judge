package problems;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShPath {

	int tC;
	
	Map<String, Integer> cityName = new HashMap<String, Integer>();
	
	Map<City, List<City>> neighbours = new HashMap<City, List<City>>();
	
	List<City> cities = new ArrayList<ShPath.City>();
	
	ShPath()
	{
		cities.add(0, null);
	}
	
	class City
	{
		String name;
		int index;
		int distance;
	}
	
	class CityComparator implements Comparator<City>
	{

		@Override
		public int compare(City o1, City o2) 
		{
			return (o1.distance < o2.distance)? o1.distance : o2.distance;
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		ShPath sh = new ShPath();
		sh.processInput();
	}
	
	void processInput() throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		if(line != null)
		if((tC = Integer.parseInt(line)) > 0)
		{
			for(int i=0; i<tC; i++)
			{
				line = br.readLine();
				for(int cC = 1; cC <= Integer.parseInt(line); cC++)
				{
					City c = new City();
					c.index = cC;
					cities.add(c);
				}
				for(int cC = 1; cC <= Integer.parseInt(line); cC++)
				{
					City c = cities.get(cC);
					//City name
					line = br.readLine();
					c.name = line;
					
					//count of neighbors
					int nC = Integer.parseInt(br.readLine());
					for(int cnt=0; cnt<nC; cnt++)
					{
						line = br.readLine();
						String[] str = line.split(" ");
						City nCity = cities.get(Integer.parseInt(str[0]));
						//neighbours.put(c, nCity);
					}
					
					cities.add(nC, c);
				}
				
				while(((line = br.readLine()) != null))
				{
				}
			}			
		}
	}
	
	void rest()
	{
		cities.clear();
		cities.add(0, null);
	}
}
