package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
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
 * Accepted 0.062 s. Solved using Dijktra's shortest path algorithm - A small tweak in the algorithm as its a bi-directional graph.
 * The problem is categorized as graph trace problem in Felix Halim's book but looks like its a shortest path problem on a weighted graph. 
 *
 */
public class DominoEffect {

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
     * 
     * @author gouthamvidyapradhan
     * Class to store domino index
     *
     */
    private static class Domino
    {
    	Domino(int v, int d)
    	{
    		this.v = v;
    		this.d = d;
    	}
    	int v; //vertex
    	int p; //parent
    	int d; //distance
    }
    
	/**
	 * Priority queue
	 */
	static java.util.Queue<Domino> pq = new java.util.PriorityQueue<Domino>(1000, new Comparator<Domino>()
	{
		@Override
		public int compare(Domino d1, Domino d2) 
		{
			return (d1.d < d2.d)? -1 : ((d1.d > d2.d)? 1 : 0);
		}
	});

    private static int N, M;
    private static List<List<Domino>> graph = new ArrayList<List<Domino>>(501);
    private static BitSet done = new BitSet(501);
    private static final String SYSTEM = "System #";
    private static final String SENTENCE1 = "The last domino falls after ";
    private static final String SENTENCE2 = "at key domino ";
    private static final String SENTENCE3 = "between key dominoes ";
    private static final String AND = " and ";
    private static final String SECONDS = " seconds, ";
    private static double total;
    private static int V1;
    private static int V2;
    private static int[] min;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		int count = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			if(N == 0 && M == 0) break; //termination condition
			for(int i=0; i<=N; i++)
				graph.add(i, new ArrayList<Domino>());
			min = new int[N + 1]; Arrays.fill(min, Integer.MAX_VALUE);
			total = 0; V1 = 0; V2 = 0;
			while(M-- > 0)
			{
				int f = MyScanner.readInt();
				Domino d1 = new Domino(MyScanner.readInt(), MyScanner.readInt());
				d1.p = f; // add parent
				graph.get(f).add(d1);
				//make two way connection
				Domino d2 = new Domino(f, d1.d);
				d2.p = d1.v;
				graph.get(d1.v).add(d2);
			}
			dijktras();
			pw.println(SYSTEM + ++count);
			pw.print(SENTENCE1 + String.format("%.1f", total) + SECONDS);
			if(V1 == V2)
				pw.println(SENTENCE2 + V1 + ".");
			else
				pw.println(SENTENCE3 + V1 + AND + V2 + ".");
			pw.println();
			graph.clear();
			done.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Method to run dijktras to find shortest path
	 */
	private static void dijktras()
	{
		min[1] = 0;
		Domino d = new Domino(1, 0);
		d.p = 0;
		pq.add(d);
		while(!pq.isEmpty())
		{
			Domino parent = pq.remove();
			if(done.get(parent.v)) continue;
			List<Domino> children = graph.get(parent.v);
			for(Domino child : children)
			{
				if(child.v == parent.p) continue;
				if(done.get(child.v))
				{
					double sum = parent.d + (double)(child.d - Math.abs(min[parent.v] - min[child.v]))/2; //check if it falls in between
					if(sum > total)
						setLastDomino(child.v, parent.v, sum);
				}
				else
				{
					int newMin = Math.min(min[child.v], min[parent.v] + child.d);
					if(newMin < min[child.v]) //if the new min is lt current min only then add this to the pq.
					{
						min[child.v] = newMin;
						child.d = newMin;
						pq.add(child);
					}
				}
			}
			if(parent.d >= total) // >= is ideally not necessary but to show the last domino as a single domino instead if in-between answer seems to be 
				//correct for some unknown reason
				setLastDomino(parent.v, parent.v, parent.d);
			done.set(parent.v);
		}
	}
	
	/**
	 * Set new last domino
	 * @param v1
	 * @param v2
	 * @param t
	 */
	private static void setLastDomino(int v1, int v2, double t)
	{
		total = t;
		if(v1 < v2)
		{
			V1 = v1;
			V2 = v2;
		}
		else
		{
			V1 = v2;
			V2 = v1;
		}
	}
}
