package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 
 * @author gouthamvidyapradhan
 *
 */
public class ShPath {

	/**
	 * Test case index
	 */
	int tC = 0;
	
	/**
	 * Mapping of city name and index
	 */
	Map<String, Integer> cityName = new HashMap<String, Integer>();
	
	/**
	 * Neighboring cities AdjList
	 */
	Map<Integer, List<City>> neighbours = new HashMap<Integer, List<City>>();
	
	/**
	 * Array of shortest distances
	 */
	int[] shortestDist;
	
	/**
	 * Priority Queue
	 */
	PriorityQueue<TempCity> pQ = new PriorityQueue<TempCity>(20, new CityComparator());
	
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Class to hold info about the city
	 *
	 */
	class City
	{
		int index;
		int distance;
	}
	
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Class to hold info about the city
	 *
	 */
	class TempCity
	{
		int index;
		int distance;
	}

	/**
	 * @author gouthamvidyapradhan
	 * Comparator for the pQ
	 *
	 */
	class CityComparator implements Comparator<TempCity>
	{

		@Override
		public int compare(TempCity o1, TempCity o2) 
		{
			return (o1.distance < o2.distance)? -1 : (o1.distance == o2.distance)? 0 : 1;
		}
	}
	
	/**
	 * Main
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		ShPath sh = new ShPath();
		sh.processInput();
		//System.out.println();
	}
	
	/**
	 * Process input lines
	 * @throws Exception
	 */
	void processInput() throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		if(line != null)
			tC = Integer.parseInt(line.trim());
			for(int i=0; i<tC; i++)
			{
				if(i != 0)
					line = br.readLine();
				line = br.readLine();
				int cityCnt = Integer.parseInt(line.trim());
				shortestDist = new int[cityCnt+1];
				for(int cC = 1; cC <= cityCnt; cC++)
				{
					//City name
					line = br.readLine();
					String cName = line.trim();
					cityName.put(cName, cC);
					
					//count of neighbors
					line = br.readLine();
					int nC = 0;
					if(line != null)
					nC = Integer.parseInt(line.trim());
					//int nC = Integer.parseInt(br.readLine());
					for(int cnt=0; cnt<nC; cnt++)
					{
						line = br.readLine();
						String[] str = line.trim().split(" ");
						City nCity = new City();
						//TODO can we store String??
						nCity.index = Integer.parseInt(str[0]);
						nCity.distance = Integer.parseInt(str[1]);
						
						//Check if the current city list is available?
						if(neighbours.get(cC) != null)
						{
							neighbours.get(cC).add(nCity);
						}
						else
						{
							//Create a new list
							List<City> list = new ArrayList<City>();
							list.add(nCity);
							neighbours.put(cC, list);
						}
					}
				}
				if(cityCnt != 0)
				{
					int paths = Integer.parseInt(br.readLine());	
					for(int iPath=0; iPath<paths; iPath++)
					{
						String[] str = br.readLine().split(" ");
						int from = cityName.get(str[0]);
						int to = cityName.get(str[1]);
						//TODO check for null from and to
						System.out.println(findShortestPath(from, to));
					}
					reset();
//					if(i+1 != tC)
//						System.out.println();
				}
			}			
	}
	
	/**
	 * Run dijktra's to find shortest path
	 * @param from Source
	 * @param to Destination
	 * @return shortest path distance.
	 */
	int findShortestPath(int from, int to)
	{
		for(int e =0; e<shortestDist.length; e++)
		{
			shortestDist[e] = 0; 
		}
//		//Shortest distance form source to itself is 0
//		shortestDist[from] = 0;
		List<City> cities = neighbours.get(from);
		if(cities != null)
		for(City city : cities)
		{
			TempCity tCity = new TempCity();
			tCity.distance = city.distance;
			tCity.index = city.index;
			shortestDist[city.index] = city.distance;
			pQ.add(tCity);
		}
		//Start relaxing vertices from the pQ
		while(!pQ.isEmpty())
		{
			TempCity tC = pQ.remove();
			int curIndex = tC.index;
			List<City> nCities = neighbours.get(curIndex);
			if(nCities != null)
			for(City child : nCities)
			{
				if(child.index != curIndex)
				{
					if(shortestDist[child.index] == 0)
					{
						relax(child, tC);
					}
					else 
					{
						if((child.distance + tC.distance)
								< shortestDist[child.index])
						{
							relax(child, tC);
						}
					}
				}
			}
		}
		pQ.clear();
		return shortestDist[to];
	}
	
	/**
	 * Relax vertices
	 * @param child
	 * @param parent
	 */
	void relax(City child, TempCity parent)
	{
		int sum = child.distance + parent.distance;
		shortestDist[child.index] = sum;
		TempCity tcC = new TempCity();
		tcC.distance = sum;
		tcC.index = child.index;
		pQ.add(tcC);
	}
	
	/**
	 * Reset all the initial class member variables
	 */
	void reset()
	{
		//pQ.clear();
		neighbours.clear();
		cityName.clear();
	}
}
