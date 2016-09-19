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
 * Accepted 0.288 s. Simple bipartite graph algorithm, but interesting because, it involves counting the two different set of vertices.
 *
 */
public class PlaceTheGuards {

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
    private static int T, V, E, set[] = new int[2], guards, temp; //test case, vertices, edges, set count of vertices colored zero or one
    private static int[] color; 
    private static final int IMPOSSIBLE = -1;
    private static int[] q = new int[1000];
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
	    	while((V = MyScanner.readInt()) == -1);
	    	E = MyScanner.readInt();
	    	color = new int[V+1]; graph = new ArrayList<>(V+1);
	    	temp = 0; guards = 0;
	    	for(int i=0, l = V+1; i<l; i++)
	    		graph.add(null);
	    	Arrays.fill(color, Integer.MAX_VALUE);
	    	while(E-- > 0)
	    	{
	    		int from = MyScanner.readInt();
	    		int to = MyScanner.readInt();
	    		addChildren(from, to); //construct a two-way mapping
	    		addChildren(to, from);
	    	}
	    	for(int i = 0; i<V; i++)
	    	{
	    		if(color[i] != Integer.MAX_VALUE) continue;
	    		set[0] = 0; set[1] = 0;
	    		temp = bfs(i);
	    		if(temp == IMPOSSIBLE)
	    			break;
	    		guards+=temp;
	    	}
	    	pw.println((temp == IMPOSSIBLE)? IMPOSSIBLE : guards);
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
    private static int bfs(int i)
    {
    	int head = 0, tail = 0;
    	q[tail++] = i; color[i] = 0; set[0]++; //enqueue the start and color it 0.
    	while(head < tail)
    	{
    		int parent = q[head++];
    		List<Integer> children = graph.get(parent);
    		if(children == null) continue;
    		for(int c : children)
    		{
    			if(color[c] == Integer.MAX_VALUE)
    			{
    				int newColor = 1 - color[parent];
    				set[newColor]++;
    				color[c] = newColor;
    				q[tail++] = c;
    			}
    			else if(color[c] == color[parent])
    				return IMPOSSIBLE;
    			//ignore already visited vertices
    		}
    	}
    	int min = Math.min(set[0], set[1]);
    	return (min == 0)? 1 : min; //This is important to handle cases with vertices with no edges
    }
}
