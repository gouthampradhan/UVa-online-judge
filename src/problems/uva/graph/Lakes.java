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
 * Accepted 0.106 s. Java toper ! Simple FloodFill problem similar to @see WetlandsOfFlorida
 *
 */
public class Lakes {
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
    
    private static char[][] grid = new char[101][101];
    private static final char WATER = '0', DONE = '.';
    private static final String EMPTY = "";
    private static final int R[] = {0, -1, 0, 1}; //{E, N, W, S}
    private static final int C[] = {1, 0, -1, 0};
    private static int M, N;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		int T = MyScanner.readInt(), count = 0;
		while(T-- > 0)
		{
			String line; int pos = 0, r, c;
			while((r = MyScanner.readInt()) == -1); 
			c = MyScanner.readInt();
			
			while((line = MyScanner.readLine()) == null || line.trim().equals(EMPTY));
			M = line.trim().length(); //columns
			grid[pos++] = line.trim().toCharArray();
			while((line = MyScanner.readLine()) != null && !line.trim().equals(EMPTY))
				grid[pos++] = line.trim().toCharArray();
			N = pos; //rows
			if(count++ != 0)
				pw.println();
			if(grid[r-1][c-1] == WATER)
				pw.println(dfs(r-1, c-1));
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Flood fill algorithm
	 * @param r
	 * @param c
	 * @return
	 */
	private static int dfs(int r, int c)
	{
		int count = 0;
		count++;//Increment count 
		grid[r][c] = DONE; //mark this as done
		for(int i = 0, l = R.length; i < l; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= N || newC >= M) continue;
			if(grid[newR][newC] == WATER)
				count += dfs(newR, newC);
		}
		return count;
	}
}
