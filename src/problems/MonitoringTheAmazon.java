package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted in first attempt 1.962 s. Definitely needs improvement in the algorithm applied
 *
 */
public class MonitoringTheAmazon {

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
				if (str != null && !str.trim().equals("")) {
					st = new StringTokenizer(str);
					return parseInt(st.nextToken());
				}
			} catch (IOException e) {
				close();
				return Integer.MAX_VALUE;
			}
			return Integer.MAX_VALUE;
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

	/**
	 * @author gouthamvidyapradhan
	 * Station class
	 *
	 */
	private static class Station
    {
        Station(int x, int y, int index)
        {
            this.x = x;
            this.y = y;
            this.index = index;
        }
        int x;
        int y;
        int index;
        double d;
    }
	
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Station comparator
	 *
	 */
	private static class StationComparator implements Comparator<Station>
	{

		@Override
		public int compare(Station s1, Station s2) 
		{
			if(s1.d == s2.d)
			{
				if(s1.x == s2.x)
					return (s1.y < s2.y)? -1 : ((s1.y > s2.y)? 1 : 0);
				else
					return (s1.x < s2.x)? -1 : 1;
			}
			return (s1.d < s2.d)? -1 : 1;
		}
	}
	
	private static final String REACHABLE = "All stations are reachable.";
	private static final String UNREACHABLE = "There are stations that are unreachable.";
	private static int[] X;
    private static int[] Y;
    private static int N;
    private static List<List<Station>> graph;
    private static BitSet done = new BitSet(500501);
    private static int[] q;
    private static int count;
    private static int head, tail;

	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == Integer.MAX_VALUE);
			if(N == 0) break;
			if(N == 1 || N == 2 || N == 3) 
			{
				pw.println(REACHABLE);
				MyScanner.readLine(); //ignore the next line
				continue;
			}
			X = new int[N]; Y = new int[N];
	        count = 3; //initial count is always 3 !
	        graph = new ArrayList<>(N);
	        for(int i=0; i<N; i++)
	        {
	            X[i] = MyScanner.readInt();
	            Y[i] = MyScanner.readInt();
	            graph.add(i, new ArrayList<Station>());
	        }
	        buildGraph(); //construct graph
	        List<Station> children = graph.get(0);
	        Station s1 = children.get(0);
	        Station s2 = children.get(1);
	        q = new int[N+1]; q[0] = s1.index; q[1] = s2.index;
	        done.set(0);done.set(s1.index);done.set(s2.index); //mark the vertices as done
	        head = 0; tail = 2;
	        bfs(); //check if reachable
	        if(count == N)
	            pw.println(REACHABLE);
	        else
	            pw.println(UNREACHABLE);
	        done.clear();
	        graph.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}

    /**
     * Create a bi-directional directed graph
     * @throws Exception
     */
    private static void buildGraph() throws Exception
    {
        for(int i=0; i<N; i++)
        {
            for(int j=(i+1); j<N; j++)
            {
                int x1 = X[i]; int y1 = Y[i];
                int x2 = X[j]; int y2 = Y[j];
                double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                addChild(distance, i, new Station(X[j], Y[j], j)); //forward link
                addChild(distance, j, new Station(X[i], Y[i], i)); //reverse link
            }
            Collections.sort(graph.get(i), new StationComparator());
        }
    }
    
    /**
     * Attach children
     * @param distance distance
     * @param pos index/vertex
     * @param child child node to be attached
     */
    private static void addChild(double distance, int pos, Station child)
    {
    	List<Station> children = graph.get(pos);
        child.d = distance;
        children.add(child);
    }
    
    /**
    *
    */
   private static void bfs()
   {
       while(head < tail)
       {
           int first = q[head++];
           List<Station> children = graph.get(first);
           if (!markFinished(children.get(0)))
               markFinished(children.get(1));
       }
   }

   /**
    * Mark each the station as reachable and add to the queue
    * @param station Station to be marked as finished
    * @param tail tail pointer
    * @return true if marked successfully. False if already marked as finished
    */
   private static boolean markFinished(Station station)
   {
       if(!done.get(station.index))
       {
           done.set(station.index);
           q[tail++] = station.index;
           count++;
           return true;
       }
       return false;
   }
}
