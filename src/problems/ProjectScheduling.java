package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.495 s. Shitty input format, failed many times due to RE. Passed when br.ready() was used.
 * NOTE: Found the root cause of RE. Its not the input which was shitty but the use of return br.readLine().trim(); was the reason. This fails 
 * when eof has reached due to NullPointerException.
 * Also, noticed that use of br.ready() is highly efficient than the manual null check or empty string check.
 *
 * Accepted 0.462 s ! Fastest in java
 */
public class ProjectScheduling {

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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
        	return br.ready();
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
    
    private static int T, currentMax, testCase, top;
    private static int[] max = new int[27];
    private static int[] weight = new int[27];
    private static List<List<Integer>> graph = new ArrayList<>();
    private static BitSet done = new BitSet(27);
    private static int[] toposort = new int[27];
    private static final char A = 'A';
    private static final int MAX = 27;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
    	while((T = MyScanner.readInt()) == -1);
    	MyScanner.readLine(); //Ignore blank line
    	String line;
    	testCase = 0;
    	while(T-- > 0)
    	{
    		currentMax = -1; top = -1;
    		for(int i = 0; i < MAX; i++) //initialize
    		{
    			graph.add(new ArrayList<Integer>());
    			max[i] = 0; toposort[i] = 0; weight[i] = -1;
    		}
    		while(MyScanner.ready()) //this is important for efficiency and speed
    		{
    			line = MyScanner.readLine();
    			if(line.trim().equals("")) break;
    			StringTokenizer st = new StringTokenizer(line);
    			int toIndex = st.nextToken().charAt(0) - A;
    			int days = MyScanner.parseInt(st.nextToken());
    			weight[toIndex] = days; max[toIndex] = days; //initialize vertices and max to the same value
    			currentMax = (currentMax > days) ? currentMax : days;
    			if(st.hasMoreTokens())
    			{
    				char[] from = st.nextToken().toCharArray();
    				for(char f : from)
    					graph.get(f - A).add(toIndex);
    			}
    		}
    		
    		if(testCase++ != 0)
    			pw.println();
    		for(int v = 0; v < MAX; v++)
    		{
    			if(weight[v] != -1 && !done.get(v))
    				dfs(v);
    		}
    		while(top >= 0)
    			relax(toposort[top--]); //relax each of the toposorted vertices
    		pw.println(currentMax);
    		graph.clear(); done.clear();
    	}
    	pw.flush(); pw.close(); MyScanner.close();
    }
    
    /**
     * Dfs to toposort the vertices
     * @param i
     */
    private static void dfs(int i)
    {
    	done.set(i); // mark this as visited
    	List<Integer> children = graph.get(i);
    	for(int p : children)
    	{
    		if(!done.get(p))
    			dfs(p);
    	}
    	toposort[++top] = i; //push to stack
    }
    
    /**
     * Relax vertices and update currentMax
     * @param i
     */
    private static void relax(int i)
    {
    	List<Integer> children = graph.get(i);
    	for(int c : children)
    	{
    		int newMax = max[i] + weight[c];
    		if(newMax > max[c])
    		{
    			max[c] = newMax;
    			currentMax = (newMax > currentMax) ? newMax : currentMax; //update new currentMax
    		}
    	}
    }
}
