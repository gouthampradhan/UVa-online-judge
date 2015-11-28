package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.273 s. Simple problem of finding SCC, sorting is a bit tricky.
 *
 */
public class Test {

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
     * 
     * @author gouthamvidyapradhan
     *
     */
    static class Vertex 
    {
    	ArrayList<Integer> adj;
		public Vertex() {
			adj = new ArrayList<>();
		}
	}

    
    private static Vertex[] graph, printArr;
    private static int N, next, top, caseCount;
    private static int[] dfs_low, dfs_num, stack;
    private static boolean[] done, visited;
    private static final String CHOICE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", BLANK = " ";
    private static final int NO_VERTEX = 26; 
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) throws Exception
	{
    	caseCount = 0; 
    	while(true)
    	{
	    	while((N = MyScanner.readInt()) == -1);
	    	if(N == 0) break;
    		graph = new Vertex[NO_VERTEX]; printArr = new Vertex[NO_VERTEX];
	    	next = 0; top = 0; 
	    	dfs_low = new int[NO_VERTEX]; stack = new int[NO_VERTEX]; done = new boolean[NO_VERTEX]; 
	    	dfs_num = new int[NO_VERTEX]; visited = new boolean[NO_VERTEX];
	    	for(int i=0; i<NO_VERTEX; i++)
	    		{dfs_low[i] = -1; stack[i] = -1; dfs_num[i] = -1; done[i] = false; visited[i] = false; graph[i] = new Vertex(); printArr[i] = new Vertex();}
	    	while(N-- > 0)
	    	{
		    	String line = MyScanner.readLine();
		    	StringTokenizer tokens = new StringTokenizer(line);
		    	int[] nV = new int[6];
		    	for(int i=0; i<6; i++)
		    		nV[i] = CHOICE.indexOf(tokens.nextToken());
		    	int root = nV[5];
		    	Vertex v = graph[root];
		    	for(int i=0; i<5; i++)
		    		if(nV[i] != root)
		    			v.adj.add(nV[i]);
		    	graph[root] = v;
	    	}
	    	for(int i=0; i<NO_VERTEX; i++)
	    		if(!done[i] && !graph[i].adj.isEmpty()) dfs(i); //check for SCC
	    	if(caseCount++ > 0)
	    		pw.println();
	    	for(int i=0; i<NO_VERTEX; i++) //print
	    	{
	    		List<Integer> scc = printArr[i].adj;
	    		if(!scc.isEmpty())
	    		{
	    			pw.print(CHOICE.charAt(scc.get(0)));
	    			for(int j=1, l = scc.size(); j<l; j++)
	    			{
	    				pw.print(BLANK);
	    				pw.print(CHOICE.charAt(scc.get(j)));
	    			}
	    			pw.println();
	    		}
	    	}
    	}
    	pw.flush();
    	pw.close();
    	MyScanner.close();
	}
    
    
    /**
     * Dfs to find SCC
     */
    private static void dfs(int i)
    {
    	int curVal = next++;
    	dfs_low[i] = curVal; dfs_num[i] = curVal; 
		done[i] = true; //mark finished
		visited[i] = true; //mark visited (vertex currently being explored)
		stack[top++] = i; //push to stack
    	List<Integer> children = graph[i].adj;
    	for(int child : children)
    	{
    		if(!done[child]) //unvisited
    			dfs(child);
    		if(visited[child])
    			dfs_low[i] = Math.min(dfs_low[child], dfs_low[i]);
    	}
    	if(dfs_low[i] == dfs_num[i]) //root
    	{
    		List<Integer> temp = new ArrayList<Integer>();
    		while(true)
    		{
        		int t = stack[--top];
        		visited[t] = false;
        		temp.add(t);
        		if(t == i) break; //root
    		}
    		Collections.sort(temp); //very important, sort first and then add to printArray
    		printArr[temp.get(0)].adj.addAll(temp);
    	}
    }
}
