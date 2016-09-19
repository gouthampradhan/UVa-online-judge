package problems.uva.graph;

import java.io.BufferedOutputStream; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 *
 */
class ShPath {

	/**
	 * Test case index
	 */
	static byte tC = 0;
	
	/**
	 * Mapping of city name and index
	 */
	static Map<String, Short> cityName = new HashMap<String, Short>();
	
	/**
	 * Array of shortest distances
	 */
	static int[] shortestDist;
	
	/**
	 * Mapping of city name and index
	 */
	//Map<String, Integer> cache = new HashMap<String, Integer>();
	
	/**
	 * Output writer
	 */
	static PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(System.out));
	
	/**
	 * AdjList to store the graph
	 */
	static short[][][][] cityLst;
	
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Customized, MIN Priority queue
	 *
	 */
	static class Pqueue
	{
		static short[][][] pq = new short[100000][1][2];
		
		static short rear = 0;
		
		static short curr = 0;
		
		static short parent = 1;

		static void push(short[][] e)
	 	{
			pq[++rear] = e;
			curr = rear;
			parent = (short)(curr >> 1);
			while((parent > 0) && (pq[curr][0][1] < pq[parent][0][1]))
			{
				//exchange
				short temp[][] = pq[parent];
				pq[parent] = pq[curr];
				pq[curr] = temp;
				curr = parent;
				parent = (short)(curr >> 1);
			}
	 	}
	 	
		/**
		 * Remove front element from the queue
		 * @return
		 */
		static short[][] remove()
		{
			short top[][] = new short[1][2];
			top[0][0] = pq[1][0][0];
			top[0][1] = pq[1][0][1];
			
			pq[1][0][0] = pq[rear][0][0];
			pq[1][0][1] = pq[rear][0][1];
			
			pq[rear][0][0] = 0;
			pq[rear][0][1] = 0;
			rear--;
			heapify((short)1);
			//Return the index and the distance
			return top;
		}
		
		/**
		 * Heapify
		 * @param i
		 */
		static void heapify(short i)
		{
			short left = (short)(i << 1);
			short right = (short)((i << 1) + 1);
			short smallest;
			while(i<=rear)
			{
				if((left <= rear) && (pq[left][0][1]!= 0 && (pq[left][0][1] < pq[i][0][1])))
					smallest = left;
				else if((right <= rear) && (pq[right][0][1]!= 0 && (pq[right][0][1] < pq[i][0][1])))
					smallest = right;
				else break;
				
				short[][] temp = new short[1][2];
				temp[0][0] = pq[i][0][0];
				temp[0][1] = pq[i][0][1];
				
				pq[i] = pq[smallest];
				pq[smallest] = temp;
				heapify(smallest);
			}
		}
		
		/**
		 * Is pq empty
		 * @return
		 */
		static boolean isEmpty()
		{
			if((pq[1][0][0] == 0) && (pq[1][0][1] == 0))
				return true;
			return false;
		}
	}

	/**
	 * Main
	 * @param args
	 * @throws Exception
	 */
	public static void main (String[] args) throws java.lang.Exception
	{
//		ShPath sh = new ShPath();
//		sh.processInput();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		if(line != null)
			tC = Byte.parseByte(line);
			for(int i=0; i<tC; i++)
			{
				if(i != 0)
					line = br.readLine();
				line = br.readLine();
				//int cityCnt = Integer.parseInt(line);
				short cityCnt = Short.parseShort(line);
				cityLst = new short[cityCnt+1][][][];
				for(short cC = 1; cC <= cityCnt; cC++)
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
					if(nC == 0)
						cityLst[cC] = new short[0][0][0];
					else
						cityLst[cC] = new short[nC][1][2];
					for(int cnt=0; cnt<nC; cnt++)
					{
						line = br.readLine();
						StringTokenizer st = new StringTokenizer(line, " ");
						cityLst[cC][cnt][0][0] = Short.parseShort(st.nextToken());
						cityLst[cC][cnt][0][1] = Short.parseShort(st.nextToken());
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
//						String fromToCity = new StringBuilder().append(fromCity).append(toCity).toString();
//						if(cache.get(fromToCity) == null)
//						{
							short from = cityName.get(fromCity);
							short to = cityName.get(toCity);
							int dist = findShortestPath(from, to, cityCnt);
							printWriter.println(dist);
							printWriter.flush();
							//cache.put(fromToCity, dist);
//						}
//						else
//						{
//							printWriter.println(cache.get(fromToCity));
//						}
					}
					reset();
				}
			}
			//printWriter.flush();
			printWriter.close();
			br.close();
	}
	
//	/**
//	 * Process input lines
//	 * @throws Exception
//	 */
//	void processInput() throws Exception
//	{
//	}
	
	/**
	 * Run dijktra's to find shortest path
	 * @param from Source
	 * @param to Destination
	 * @return shortest path distance.
	 */
	static int findShortestPath(short from, short to, int cityCnt)
	{
		shortestDist = new int[cityCnt+1];
		for(int i=0; i<shortestDist.length; i++)
		{
			shortestDist[i] = 200001;
		}
		short[][] tCity = new short[1][2];
		tCity[0][0] = from;
		tCity[0][1] = 0;
		shortestDist[from] = 0;
		Pqueue.push(tCity);
		while(!Pqueue.isEmpty())
		{
			short[][] top = Pqueue.remove();
			if(top[0][1] != shortestDist[top[0][0]])
				continue;
			short[][][] nC = cityLst[top[0][0]];
			if(nC.length > 0)
				for(short c[][] : nC)
				{
					if((c[0][1] + top[0][1]) < shortestDist[c[0][0]])
					{
						//relax
						short sum = (short)(c[0][1] + top[0][1]);
						shortestDist[c[0][0]] = sum;
						short[][] nE = new short[1][2];
						nE[0][0] = c[0][0]; 
						nE[0][1] = sum;
						Pqueue.push(nE);
					}
				}
			
		}
		return (shortestDist[to] == 200001)? 0 :  shortestDist[to];
	}
	
	/**
	 * Reset all the initial class member variables
	 */
	static void reset()
	{
		cityName.clear();
		//cache.clear();
	}
}