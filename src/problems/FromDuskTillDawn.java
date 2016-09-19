package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.182 s. Simple dijktra's but a bit tricky calculation for total hours and blood required. 
 *
 */
public class FromDuskTillDawn {

    /**
     * Scanner class
     *
     * @author gouthamvidyapradhan
     */
    static class MyScanner {
        /**
         * Buffered reader
         */
        private static BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        private static StringTokenizer st;

        /**
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static int readInt() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return parseInt(st.nextToken());
                }
                String str = br.readLine();
                if(str == null) return -2;
                if (!str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return parseInt(st.nextToken());
                }
            } catch (IOException e) {
                close();
                return -1;
            }
            return -1;
        }
        
        /**
         * Read line
         * @return
         * @throws Exception
         */
        public static String readLine() throws Exception
        {
        	return br.readLine();
        }
        /**
         * Parse to integer
         * @param in
         * @return integer value
         */
        public static int parseInt(String in)
        {
            // Check for a sign.
            int num  = 0, sign = -1, i = 0;
            final int len  = in.length( );
            final char ch  = in.charAt( 0 );
            if ( ch == '-' )
                sign = 1;
            else
                num = '0' - ch;

            // Build the number.
            i+=1;
            while ( i < len )
                num = num*10 + '0' - in.charAt( i++ );
            return sign * num;
        }

        /**
         * Close BufferedReader
         *
         * @throws Exception
         */
        public static void close() throws Exception {
            br.close();
        }
    }
    
    /**
     * City class
     * @author gouthamvidyapradhan
     *
     */
    private static class City
    {
    	String name;
    	int dep;
    	int duration;
    	int currentTime;
    	int totalBlood;
    	City(String name, int dep, int duration)
    	{
    		this.name = name;
    		this.dep = dep;
    		this.duration = duration;
    	}
    }
    
	/**
	 * Priority queue
	 */
	static java.util.Queue<City> pq = new java.util.PriorityQueue<City>(1000, new Comparator<City>()
	{
		@Override
		public int compare(City t1, City t2) 
		{
			return (t1.duration < t2.duration)? -1 : ((t1.duration > t2.duration)? 1 : 0);
		}
	});

    private static int T, M, testCase;
    private static Map<String, List<City>> graph = new HashMap<>();
    private static Set<String> done = new HashSet<>();
    private static Map<String, Integer> min = new HashMap<>();
    private static String[] sF = new String[2];
    private static final String VLADIMIR_NEEDS = "Vladimir needs ";
    private static final String LTS_OF_BLOOD = " litre(s) of blood.";
    private static final String NO_ROUTE = "There is no route Vladimir can take.";
    private static final String TEST_CASE_NUMBER = "Test Case ";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		while((T = MyScanner.readInt()) == -1);
		testCase = 0;
		while(T-- > 0)
		{
			while((M = MyScanner.readInt()) == -1);	
			while(M-- > 0)
			{
				StringTokenizer st = new StringTokenizer(MyScanner.readLine());
				String from = st.nextToken();
				List<City> children = graph.get(from);
				if(children == null)
					children = new ArrayList<City>();
				City newCity = new City(st.nextToken(), MyScanner.parseInt(st.nextToken()), MyScanner.parseInt(st.nextToken()));
				children.add(newCity);
				graph.put(from, children);
			}
			StringTokenizer st = new StringTokenizer(MyScanner.readLine());
			sF[0] = st.nextToken(); sF[1] = st.nextToken(); //start and finish city
			int ltsOfBlood = dijktras();
			pw.println(TEST_CASE_NUMBER + ++testCase + ".");
			if(ltsOfBlood == -1)
				pw.println(NO_ROUTE);
			else
				pw.println(VLADIMIR_NEEDS + ltsOfBlood + LTS_OF_BLOOD);
			graph.clear(); min.clear(); pq.clear(); done.clear();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Check if the train departure is valid
	 * @param dep departure time
	 * @param duration duration
	 * @return true if valid, false otherwise
	 */
	private static boolean isValid(int dep, int duration)
	{
		if(duration <= 12)
		{
			int arrivalTime = (dep + duration) % 24;
			if((dep >= 18 && (arrivalTime <= 6 || arrivalTime >= 18)) 
					|| (dep <= 6 && arrivalTime <= 6)) //check if the train departs on or after 18 hrs but arrives before 6 hrs and journey time is lt 12 hrs
				return true;
		}
		return false;
	} 
	
	/**
	 * 
	 * Dijktra's to find the shortest path
	 * @return int total blood required, -1 if not reachable
	 */
	private static int dijktras()
	{
		if(sF[0].equals(sF[1])) return 0;
		City sCity = new City(sF[0], 18, 0); //start city
		sCity.totalBlood = 0;
		sCity.currentTime = 18;
		pq.add(sCity);
		while(!pq.isEmpty())
		{
			City parentCity = pq.remove();
			if(done.contains(parentCity.name)) continue;
			if(parentCity.name.equals(sF[1])) {return parentCity.totalBlood;} //destination reached.
			List<City> connections = graph.get(parentCity.name);
			if(connections == null) continue;
			for(City con : connections)
			{
				if(done.contains(con.name)) continue;
				if(isValid(con.dep, con.duration))
				{
					int waitTime = con.dep - parentCity.currentTime;
					if(waitTime < 0) waitTime+=24;
					int connectionTime = con.duration + waitTime;//connection time to this destination includes wait_time + travel_duration
					int totalTime = connectionTime + parentCity.duration;  //total time to this destination includes wait_time + travel_duration + parentTime
					Integer mininum = min.get(con.name);
					if(mininum == null || totalTime < mininum)
					{
						min.put(con.name, totalTime);
						City newCity = new City(con.name, con.dep, totalTime);
						newCity.currentTime = (parentCity.currentTime + connectionTime) % 24; //future currentTime if this train is selected
						newCity.totalBlood = (waitTime >= 12) ? (parentCity.totalBlood + 1) : parentCity.totalBlood;
						pq.add(newCity);
					}
				}
			}
			done.add(parentCity.name); //mark this as finished
		}
		return -1;
	}
}
