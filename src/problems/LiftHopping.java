package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.098 s. Tricky graph modeling especially to model lift hopping scenario. Once the graph is modeled correctly its just a simple 
 * dijktra's implementation. 
 *
 */
public class LiftHopping {

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
         * Check if the next int is available
         * 
         * @return
         */
        public static boolean hasNext()
        {
        	if(st != null && st.hasMoreTokens())
        		return true;
        	return false;
        }
        
        /**
         * Read next int
         * @return
         */
        public static int readNext()
        {
        	return parseInt(st.nextToken());
        }
        
        /**
         * Read line
         * @return
         * @throws Exception
         */
        public static int readLine() throws Exception
        {
            String str = br.readLine();
            if(str == null) return -2; //eof
            else if(str.trim().equals("")) return -1; //blank line
            st = new StringTokenizer(str);
            return 0; //success
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
    /**
     *
     * @author gouthamvidyapradhan
     * Class to store Floor 
     *
     */
    private static class Floor
    {
    	int s; //seconds
    	int f; //combination of floor and lift
    	Floor(int f, int s)
    	{
    		this.s = s;
    		this.f = f;
    	}
    }
    
	/**
	 * Priority queue
	 */
	static java.util.Queue<Floor> pq = new java.util.PriorityQueue<Floor>(1000, new Comparator<Floor>()
	{
		@Override
		public int compare(Floor f1, Floor f2) 
		{
			return (f1.s < f2.s)? -1 : ((f1.s > f2.s)? 1 : 0);
		}
	});

	private static List<List<Floor>> graph = new ArrayList<>(1006);
    private static int N, K;
    private static final int D = 60, constant = 10;
    private static BitSet done = new BitSet(1006);
    private static int[] min = new int[1006]; //max possible is 995
    private static int[] T = new int[5]; //travel time from one floor to another for each lift
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		while(true)
		{
			int in, prev, current;
			while((in = MyScanner.readLine()) == -1);
			if(in == -2) break;
			for(int i=0; i<1006; i++)
			{
				graph.add(i, new ArrayList<Floor>());
				Arrays.fill(min, Integer.MAX_VALUE);
			}
			Arrays.fill(T, -1);
			min[0] = 0; // set the minimum value of start vertex to 0
			N = MyScanner.readNext(); K = MyScanner.readNext();
			while(MyScanner.readLine() == -1); //ignore blank lines
			for(int i = 0; i<N; i++)
				T[i] = MyScanner.readNext();
			for(int i = 0, l = T.length; ((i < l) && T[i] != -1); i++) // i indicates lift index [0 - 4]
			{
				while(MyScanner.readLine() == -1); //ignore blank lines
				int t = T[i]; prev = -1;
				while(MyScanner.hasNext())
				{
					current = MyScanner.readNext();
					if(prev != -1)
					{
						current = (current * constant) + i;
						Floor f = new Floor(current, getTravelTime(prev, current, t));
						graph.get(prev).add(f);
						graph.get(current).add(new Floor(prev, getTravelTime(current, prev, t)));
						liftInterLink(current, i);
					}
					else if(current != 0)
					{
						current = (current * constant) + i;
						liftInterLink(current, i);
					}
					prev = current; //mark current as previous
				}
			}
			int dist = dijktras();
			if(dist == -1) pw.println(IMPOSSIBLE);
			else pw.println(dist);
			pq.clear(); graph.clear(); done.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Calculate travel time and return abs value
	 * @param f from
	 * @param t to
	 * @param s seconds required to move from one floor to next
	 * @return
	 */
	private static int getTravelTime(int f, int t, int s)
	{
		f/=constant; t/=constant;
		return Math.abs(((t - f) * s));
	}
	
	/**
	 * Interlink lifts in the same floor. 
	 * Method checks if any other lifts stop in the same floor and makes a connection with time delay of 60 sec 
	 * @param id
	 * @param l
	 */
	private static void liftInterLink(int id, int l)
	{
		int current = id;
		id/=constant;
		for(int i=0; i<l; i++)
		{
			int temp = (id * constant) + i;
			if(!graph.get(temp).isEmpty())
			{
				graph.get(temp).add(new Floor(current, D));
				graph.get(current).add(new Floor(temp, D));
			}
		}
	}
	
	/**
	 * Dijktra's to find the shortest path
	 * @return
	 */
	private static int dijktras()
	{
		if(K == 0) return 0;
		if(graph.get(0).isEmpty()) return -1; //no lift available at floor 0
		Floor s = new Floor(0, 0);
		pq.add(s);
		while(!pq.isEmpty())
		{
			Floor parent = pq.remove();
			if(done.get(parent.f)) continue;
			if((parent.f / constant) == K) return parent.s;
			List<Floor> children = graph.get(parent.f);
			for(Floor c : children)
			{
				if(!done.get(c.f))
				{
					min[c.f] = Math.min(min[c.f], parent.s + c.s);
					pq.add(new Floor(c.f, min[c.f]));
				}
			}
			done.set(parent.f);
		}
		return -1;
	}
}
