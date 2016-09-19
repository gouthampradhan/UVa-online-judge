package problems.uva.graph;

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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.142. Simple problem of topological sort, but have to use Khan's algorithm to solve this problem - Not possible with regular DFS toposort.
 *
 */
public class Beverages {

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
        private static int parseInt(String in)
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
    
    private static Map<String, List<String>> graph = new HashMap<>();
    private static Map<String, Integer> order = new HashMap<>();
    private static Map<String, Integer> index = new HashMap<>();
    private static final String BLANK = " ", COLON = ": ", DOT = ".";
    private static int N, M, caseCount; //test case number and number of vertices
    private static Set<String> done = new HashSet<String>();
    private static final String DILBERT = "Dilbert should drink beverages in this order:", CASE = "Case #";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Class drink
     * @author gouthamvidyapradhan
     *
     */
    private static class Drink
    {
    	String name; int id;
    	Drink(String name, int id)
    	{
    		this.name = name;
    		this.id = id;
    	}
    }
    
    /**
     * Priority queue to hold Drink objects
     */
    static Queue<Drink> pq = new PriorityQueue<Drink>(300, new Comparator<Drink>() 
	{
    	@Override
		public int compare(Drink d1, Drink d2) 
    	{
    		return (d1.id < d2.id) ? -1 : (d1.id > d2.id) ? 1 : 0;
		}
    	
	});
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		caseCount = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == -2) break; //eof
			for(int i=0; i<N; i++)
			{
				String name = MyScanner.readLine().trim();
				index.put(name, i); //map the drink with index
				order.put(name, 0); //initialize with zero
			}
			while((M = MyScanner.readInt()) == -1);
			for(int i=0; i<M; i++)
			{
				String line = MyScanner.readLine().trim();
				StringTokenizer tokens = new StringTokenizer(line); 
				String from = tokens.nextToken();
				String to = tokens.nextToken();
				List<String> children = graph.get(from);
				if(children == null)
					children = new ArrayList<String>();
				children.add(to); graph.put(from, children);
				int o = order.get(to);
				o+=1; //increment the order of this vertex by 1
				order.put(to, o);
			}
			Set<String> drinkName = order.keySet();
			for(String name : drinkName)
			{
				if(order.get(name) != 0) continue;
				Drink drink = new Drink(name, index.get(name));
				pq.add(drink);
			}
			pw.print(CASE + ++caseCount + COLON + DILBERT);
			toposort(); //invoke toposort
			pw.println(DOT); pw.println();
			graph.clear(); order.clear(); index.clear(); done.clear(); //clear 
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Toposort using Khan's algorithm
	 */
	private static void toposort()
	{
		while(!pq.isEmpty())
		{
			Drink drink = pq.remove();
			String name = drink.name;
			done.add(name); //mark this as finished
			pw.print(BLANK + name);
			List<String> children = graph.get(name);
			if(children == null) continue;
			for(int i=0, l=children.size(); i<l; i++)
			{
				String cName = children.get(i);
				if(done.contains(cName)) continue;
				int o = order.get(cName);
				o-=1; order.put(cName, o);
				if(o == 0) {pq.add(new Drink(cName, index.get(cName)));}
			}
		}
	}

}
