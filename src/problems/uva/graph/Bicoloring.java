package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.052 s. Simple bipartite graph check. 
 * This is a undirected graph so have to be a bit more cautious when creating a graph (it has to be bi-directional).
 *
 */
public class Bicoloring {

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
                if ((str != null) && !str.trim().equals("")) {
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
    
    private static List<List<Integer>> graph;
    private static int N, L; //test case number and number of vertices
    private static int[] color; 
    private static int[] q = new int[1000];
    private static final String NOT_BICOLORABLE = "NOT BICOLORABLE.";
    private static final String BICOLORABLE = "BICOLORABLE.";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
	{
    	while(true)
    	{
	    	while((N = MyScanner.readInt()) == -1);
	    	if(N == 0) break;
	    	L = MyScanner.readInt();
	    	color = new int[L+1]; graph = new ArrayList<>(L+1);
	    	for(int i=0; i<L+1; i++)
	    		graph.add(null);
	    	Arrays.fill(color, Integer.MAX_VALUE);
	    	while(L-- > 0)
	    	{
	    		int from = MyScanner.readInt();
	    		int to = MyScanner.readInt();
	    		addChildren(from, to); //construct a two-way mapping
	    		addChildren(to, from);
	    	}
	    	if(bfs()) pw.println(BICOLORABLE);
	    	else pw.println(NOT_BICOLORABLE);
	    	graph.clear();
    	}
    	pw.flush();
    	pw.close();
    	MyScanner.close();
	}
    
    
    /**
     * Create a bi-directional graph
     * @param from
     * @param to
     */
    private static void addChildren(int from, int to)
    {
		List<Integer> children = graph.get(from);
		if(children == null)
			children = new ArrayList<>();
		children.add(to);
		graph.set(from, children);
    }
    
    /**
     * Bfs to bicolor the graph
     * @return
     */
    private static boolean bfs()
    {
    	int head = 0, tail = 0;
    	q[tail++] = 0; color[0] = 0; //enqueue the start and color it 0.
    	while(head < tail)
    	{
    		int parent = q[head++];
    		List<Integer> children = graph.get(parent);
    		if(children == null) continue;
    		for(int c : children)
    		{
    			if(color[c] == Integer.MAX_VALUE)
    			{
    				color[c] = 1 - color[parent];
    				q[tail++] = c;
    			}
    			else if(color[c] == color[parent])
    				return false;
    			//ignore already visited vertices
    		}
    	}
    	return true;
    }

}
