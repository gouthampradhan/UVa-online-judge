package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.053s. Simple problem of counting paths. Toposort and run the DP algorithm to count paths.
 *
 */
public class ManyPathsOneDestination {

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
         * Ready
         * @return
         * @throws Exception
         */
		public static boolean ready() throws Exception
        {
        	return br.ready();
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

            // Build the number
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
    
    private static List<List<Integer>> graph;
    private static int[] noOfPaths, toposort;
    private static int total, N, top;
    private static boolean[] done;
    private static List<Integer> death;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		int count = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == -2) break; // eof
			graph = new ArrayList<>(N); death = new ArrayList<>(N);
			total = 0; top = -1;
			done = new boolean[N]; noOfPaths = new int[N]; toposort = new int[N];
			noOfPaths[0] = 1; //no of paths to the starting vertex 0 is always 1
			for(int i = 0; i<N; i++)
			{
				graph.add(new ArrayList<Integer>());
				done[i] = false; 
			}
			for(int i = 0; i<N; i++)
			{
				int e = MyScanner.readInt(); //events
				if(e == 0) death.add(i); //add a new death node
				List<Integer> events = graph.get(i);
				for(int j = 0; j<e; j++)
					events.add(MyScanner.readInt());
			}
			//toposort
			for(int i = 0; i < N; i++)
			{
				if(!done[i])
					dfs(i);
			}
			//count path
			for(int i = top; i>=0; i--)
			{
				List<Integer> children = graph.get(toposort[i]);
				for(int c : children)
					noOfPaths[c] = noOfPaths[c] + noOfPaths[toposort[i]]; //count paths 
			}
			//sum up total and print
			for(int i = 0, l = death.size(); i<l; i++)
				total += noOfPaths[death.get(i)]; //sum up the number of paths to the death vertex
			if(count++ != 0) pw.println();
			pw.println(total);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Dfs to toposort the vertices
	 * @param e
	 */
	private static void dfs(int e)
	{
		done[e] = true; //mark this as finished
		List<Integer> children = graph.get(e);
		for(int c : children)
		{
			if(!done[c])
				dfs(c);
		}
		toposort[++top] = e; //push to stack
	}
}
