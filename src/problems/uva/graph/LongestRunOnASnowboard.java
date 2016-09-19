package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.212 s. Simple problem a straight forward dfs. Only difference is since its a DAG, keeping track of visited vertices is not necessary.
 *
 */
public class LongestRunOnASnowboard {

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
    
    private static int T;
    private static String line, burg; //hill
    private static int[][] grid;
    private static int max;
    private static final int[] R = {-1, 1, 0, 0};
    private static final int[] C = {0, 0, -1, 1};
    private static final String COLON = ": ";
    private static int ROW, COL;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
    	while((T = MyScanner.readInt()) == -1);
    	while(T-- > 0)
    	{
	    	line = MyScanner.readLine();
	    	StringTokenizer st = new StringTokenizer(line);
	    	burg = st.nextToken();
	    	ROW = MyScanner.parseInt(st.nextToken());
	    	COL = MyScanner.parseInt(st.nextToken());
	    	grid = new int[ROW][COL];
	    	max = 0;
	    	for(int i = 0; i<ROW; i++)
	    	{
	    		for(int j = 0; j<COL; j++)
	    			grid[i][j] = MyScanner.readInt();
	    	}
	    	for(int i = 0; i<ROW; i++)
	    	{
	    		for(int j = 0; j<COL; j++)
	    			dfs(i, j, 1);
	    	}
	    	pw.println(burg + COLON + max);
    	}
    	pw.flush(); pw.close(); MyScanner.close();
    }
    
    
    /**
     * Dfs to find the longest path
     * @param i
     */
    private static void dfs(int r, int c, int len)
    {
    	int newR, newC;
    	max = Math.max(len, max); //maintain maximum
    	for(int i = 0; i < 4; i++)
    	{
    		newR = r + R[i];
    		newC = c + C[i];
    		if(((newR < 0) || (newR >= ROW) || (newC < 0) || (newC >= COL))
    				|| (grid[newR][newC] >= grid[r][c])) continue;
    		dfs(newR, newC, len + 1);
    	}
    }
}
