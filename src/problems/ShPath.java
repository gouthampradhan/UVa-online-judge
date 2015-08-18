package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

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
	 * Mapping of city name and index
	 */
	Map<String, Integer> cache = new HashMap<String, Integer>();
	
	/**
	 * Output writer
	 */
	PrintWriter printWriter = new PrintWriter(System.out);
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
	public static void main (String[] args) throws java.lang.Exception
	{
		ShPath sh = new ShPath();
		sh.processInput();
//		int[][][][] exp = new int[10][][][];
//		exp[0] = new int[3][1][2];
//		exp[1] = new int[4][1][2];
//		
//		int[][] city = exp[0][1];
//		
//		int[][] arr = new int[1][];
//		arr[0] = new int[2];
//		arr[1][0] = 1;
//		arr[1][0] = 3;
//		Object[][] arr = new City[5][];
//		arr[1] = new City[5];
//		City c = null;
//		arr[1][1] = c;
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
			tC = Integer.parseInt(line);
			for(int i=0; i<tC; i++)
			{
				if(i != 0)
					line = br.readLine();
				line = br.readLine();
				int cityCnt = Integer.parseInt(line);
				for(int cC = 1; cC <= cityCnt; cC++)
				{
					//City name
					line = br.readLine();
					String cName = line;
					cityName.put(cName, cC);
					
					//count of neighbors
					line = br.readLine();
					int nC = 0;
					if(line != null)
					nC = Integer.parseInt(line);
					for(int cnt=0; cnt<nC; cnt++)
					{
						line = br.readLine();
						StringTokenizer st = new StringTokenizer(line, " ");
						City nCity = new City();
						nCity.index = Integer.parseInt(st.nextToken());
						nCity.distance = Integer.parseInt(st.nextToken());
						
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
						line = br.readLine();
						StringTokenizer st = new StringTokenizer(line, " ");
						String fromCity = st.nextToken();
						String toCity = st.nextToken();
						String fromToCity = new StringBuilder().append(fromCity).append(toCity).toString();
						if(cache.get(fromToCity) == null)
						{
							int from = cityName.get(fromCity);
							int to = cityName.get(toCity);
							int dist = findShortestPath(from, to, cityCnt);
							printWriter.println(dist);
							cache.put(fromToCity, dist);
						}
						else
						{
							printWriter.println(cache.get(fromToCity));
						}
					}
					reset();
				}
			}
			printWriter.flush();
			printWriter.close();
			br.close();
	}
	
	/**
	 * Run dijktra's to find shortest path
	 * @param from Source
	 * @param to Destination
	 * @return shortest path distance.
	 */
	int findShortestPath(int from, int to, int cityCnt)
	{
		shortestDist = new int[cityCnt+1];
		for(int i=0; i<shortestDist.length; i++)
		{
			shortestDist[i] = 200001;
		}
		TempCity tCity = new TempCity();
		tCity.distance = 0;
		tCity.index = from;
		shortestDist[from] = 0;
		pQ.add(tCity);
		//Start relaxing vertices from the pQ
		while(!pQ.isEmpty())
		{
			TempCity tC = pQ.remove();
			if(tC.distance != shortestDist[tC.index])
			continue;
			int curIndex = tC.index;
			List<City> nCities = neighbours.get(curIndex);
			if(nCities != null)
			for(City child : nCities)
			{
				if((child.distance + tC.distance)
						< shortestDist[child.index])
				{
					relax(child, tC);
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
		cache.clear();
	}
}
