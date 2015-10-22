package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.379 s. Java toper for the moment !!
 * Using a BitSet XOR operation for the first time, i've learned something new !! Also, observed that StringBuilder is faster than StringBuffer as
 * the latter has synchronization overhead on it.
 *
 */
public class Dominator {

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
    
    private static int N, D; //the disabled vertex and the final destination
    private static int[][] graph = new int[101][101];
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String PIPE = "|";
    private static final String NO = "N";
    private static final String YES = "Y";
    private static final String CASE = "Case ";
    private static final String COLON = ":";
    private static int count = 0;
    private static BitSet reachable = new BitSet(101);
    private static BitSet result = new BitSet(101);
    private static StringBuilder line;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception 
    {
    	int T; while((T = MyScanner.readInt()) == -1);
    	while(T-- > 0)
    	{
    		while((N = MyScanner.readInt()) == -1);
	    	for(int i=0; i<101; i++) {Arrays.fill(graph[i], -1);}
	    	for(int i=0; i<N; i++)
	    	{
	    		int childCnt = 0;
	    		for(int j = 0; j<N; j++)
	    		{
	    			int v = MyScanner.readInt();
	    			if(v == 0) continue;
	    			graph[i][childCnt++] = j;
	    		}
	    	}
	    	pw.println(CASE + ++count + COLON);
	    	int sum = ( (2 * N) + 1 ) - 2; //calculation to show number of '-'
	    	line = new StringBuilder(sum);
	    	for(int s = 0; s < sum; s++) {line.append(MINUS);}
	    	D = -1; reachable = dfs(0, reachable); //perform the initial check of all the reachable vertices
	    	for(int i=0; i<N; i++)
	    	{
	    		D = i; //disable this vertex
	    		pw.print(PLUS);  pw.print(line); pw.println(PLUS); //+ ---- + start
	    		if(0 != D)
	    			result = dfs(0, result);
	    		result.xor(reachable);
    			for(int j = 0; j < N; j++)
    			{
    				pw.print(PIPE);
    				if(result.get(j)) pw.print(YES);
    				else pw.print(NO);
    			}
	    		pw.print(PIPE); pw.println();
	    		result.clear();
	    	}
    		pw.print(PLUS); pw.print(line); pw.println(PLUS); //+ ---- + end
    		reachable.clear();
    	}
    	pw.flush();
    	pw.close();
    	MyScanner.close();
	}
    
    /**
     * Dfs to check if the vertex is reachable.
     * @param i
     * @return
     */
    private static BitSet dfs(int i, BitSet visited)
    {
		visited.set(i);
		int[] children = graph[i];
		int child;
    	for(int c=0, l = children.length; c<l && ((child = children[c]) != -1); c++)
    	{
    		if(child != D && !visited.get(child)) //check if the child is not already visited AND not-disabled. 
    			visited = dfs(child, visited);
    	}
    	return visited;
    }
}
