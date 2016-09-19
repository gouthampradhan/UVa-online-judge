package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.532 s. Intelligent use of dijktra's once from source to destination and again from destination to source and finally iterate through each
 * edge to find the answer.
 *
 */
public class HighestPaidToll {

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
    private static class TollStation
    {
    	int s; //station index
    	int c; //cost
    	TollStation(int s, int c)
    	{
    		this.s = s;
    		this.c = c;
    	}
    }
    
	/**
	 * Priority queue
	 */
	static java.util.Queue<TollStation> pq = new java.util.PriorityQueue<TollStation>(1000, new Comparator<TollStation>()
	{
		@Override
		public int compare(TollStation t1, TollStation t2) 
		{
			return (t1.c < t2.c)? -1 : ((t1.c > t2.c)? 1 : 0);
		}
	});

	private static List<List<TollStation>> graphS = new ArrayList<>(100002);
	private static List<List<TollStation>> graphT = new ArrayList<>(100002);
    private static int N, M, S, D, T, P, U, V, C, maxToll; //vertices, edges, start, destination, testcase, P taka, (u, v) vertices of an edge, cost, maxtoll
    private static boolean doneS[] = new boolean[100002]; //source 
    private static boolean doneT[] = new boolean[100002]; //destination
    private static int[] minS = new int[100002]; 
    private static int[] minT = new int[100002]; 
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			S = MyScanner.readInt(); D = MyScanner.readInt();
			P = MyScanner.readInt();
			maxToll = -1;
			for(int i=0; i<=N; i++) //Vertices begin from 1 and end at N
			{
				graphS.add(i, new ArrayList<TollStation>());
				graphT.add(i, new ArrayList<TollStation>());
				minS[i] = Integer.MAX_VALUE; minT[i] = Integer.MAX_VALUE;
				doneS[i] = false; doneT[i] = false;
			}
			while(M-- > 0)
			{
				U = MyScanner.readInt(); V = MyScanner.readInt(); C = MyScanner.readInt();
				addChild(U, V, C); 
			}
			dijktras(S, D, minS, doneS, graphS); //shortest path for forward iteration.
			dijktras(D, S, minT, doneT, graphT); //shortest path for reverse iteration.
			for(int i = 1, l = graphS.size(); i < l; i++)
			{
				for(TollStation ts : graphS.get(i))
				{
					if(minS[i] == Integer.MAX_VALUE || minT[ts.s] == Integer.MAX_VALUE) continue;
					if((minS[i] + minT[ts.s] + ts.c) > P) continue;
					maxToll = Math.max(ts.c, maxToll); //maintain a new maximum
				}
			}
			pw.println(maxToll);
			graphS.clear(); graphT.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Add child node. Build two separate graphs one for forward iteration and the other for reverse iteration.
	 * @param from from vertex
	 * @param to to vertex
	 * @param cost cost 
	 */
	private static void addChild(int from, int to, int cost)
	{
		TollStation forward = new TollStation(to, cost);
		TollStation reverse = new TollStation(from, cost);
		graphS.get(from).add(forward); //forward graph
		graphT.get(to).add(reverse); //reverse graph
	}
	
	/**
	 * Dijktra's to find the shortest path
	 * @return
	 */
	private static int dijktras(int source, int destination, 
			int[] min, boolean[] done, List<List<TollStation>> graph)
	{
		if(source == destination) return 0;
		if(graph.get(source).isEmpty()) return -1; //no connection available from source
		TollStation s = new TollStation(source, 0);
		pq.add(s);
		min[source] = 0; //initialize the source min to 0
		while(!pq.isEmpty())
		{
			TollStation parent = pq.remove();
			if(done[parent.s] || (parent.s == destination)) continue;
			List<TollStation> children = graph.get(parent.s);
			for(TollStation c : children)
			{
				if(!done[c.s])
				{
					int newMin = parent.c + c.c;
					if(newMin > P) continue; //important check if the sum of new path formed is > p taka
					min[c.s] = Math.min(min[c.s], newMin);
					pq.add(new TollStation(c.s, min[c.s]));
				}
			}
			done[parent.s] = true;
		}
		return -1;
	}
}
