package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.592 s. Very tricky dijktra's algorithm - multiple sources shortest path
 * Accepted 0.562 s. 
 *
 */
public class FireStation {

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
     * Class to store TollStation 
     *
     */
    private static class Intersection
    {
    	int s; //station index
    	int c; //cost
    	Intersection(int s, int c)
    	{
    		this.s = s;
    		this.c = c;
    	}
    }
    
	/**
	 * Priority queue
	 */
	static java.util.Queue<Intersection> pq = new java.util.PriorityQueue<Intersection>(1000, new Comparator<Intersection>()
	{
		@Override
		public int compare(Intersection t1, Intersection t2) 
		{
			return (t1.c < t2.c)? -1 : ((t1.c > t2.c)? 1 : 0);
		}
	});

	private static List<List<Intersection>> graph = new ArrayList<>(501);
    private static int T, F, I, testCase, newFStation, maxDist; //Tests, Fire station, Intersection
    private static BitSet done  = new BitSet(501); //visited vertices
    private static BitSet isFireStation  = new BitSet(501); //firestations (sources)
    private static int[] min = new int[501];
    private static List<Intersection> fireSLst = new ArrayList<Intersection>(501);
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
			while((F = MyScanner.readInt()) == -1);
			I = MyScanner.readInt();
			newFStation = 1; maxDist = Integer.MAX_VALUE;
			for(int i=0; i<=I; i++) //Vertices begin from 1 and end at N
			{
				graph.add(i, new ArrayList<Intersection>());
				min[i] = Integer.MAX_VALUE;
			}
			while(F-- > 0)
			{
				int f = MyScanner.readInt();
				isFireStation.set(f);
				min[f] = 0;
				fireSLst.add(new Intersection(f, 0));
			}
			while(true)
			{
				int f = MyScanner.readInt();
				if(f == -1 || f == -2) break; //break in case of blank line or eof
				int t = MyScanner.readInt(); int c = MyScanner.readInt();
				graph.get(f).add(new Intersection(t, c)); //make a bidirectional graph
				graph.get(t).add(new Intersection(f, c));
			}
			for(int i=1; i<=I ; i++)//Vertices begin from 1 and end at N
			{
				if(!isFireStation.get(i)) //if this is not a firestation then, set this as a new firestation and run dijktra's.
				{
					min[i] = 0;
					pq.add(new Intersection(i, 0)); //add a new source
					pq.addAll(fireSLst);
					int max = dijktras();
					setNewFStation(i, max);
					min[i] = Integer.MAX_VALUE; //reset
					pq.clear(); done.clear();
				}
			}
			if(testCase++ > 0)
				pw.println();
			pw.println(newFStation);
			graph.clear(); fireSLst.clear(); isFireStation.clear(); //clear
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Set new fire station
	 * @param i
	 * @param max
	 */
	private static void setNewFStation(int i, int max)
	{
		if(max < maxDist)
		{
			maxDist = max;
			newFStation = i;
		}
		else if(max == maxDist)
		{
			if(i < newFStation)
				newFStation = i;
		}
	}
	/**
	 * Dijktra's to find the shortest path
	 * @return
	 */
	private static int dijktras()
	{
		int max = -1; 
		while(!pq.isEmpty())
		{
			Intersection parent = pq.remove();
			if(done.get(parent.s)) continue;
			List<Intersection> children = graph.get(parent.s);
			for(Intersection c : children)
			{
				if(!done.get(c.s))
				{
					if(!isFireStation.get(c.s))
					{
						min[c.s] = Math.min(min[c.s], parent.c + c.c);
						if(min[c.s] != 0)
							pq.add(new Intersection(c.s, min[c.s]));
					}
				}
			}
			done.set(parent.s);
			if(min[parent.s] > max){ max = min[parent.s];} //maintain a max value
			if(min[parent.s] != 0) min[parent.s] = Integer.MAX_VALUE; //reset. We anyway will not revisit this vertex
		}
		return max;
	}
}
