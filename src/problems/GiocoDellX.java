package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.109 s. Simple flood fill problem, interesting because it has only 6 neighbor nodes to trace! Something different from other grids which are normally
 * either 8 or 4 neighbor nodes to trace.
 *
 */
public class GiocoDellX 
{
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
         * Close BufferedReader
         *
         * @throws Exception
         */
        public static void close() throws Exception {
            br.close();
        }
    }
    
    private static char[][] grid = new char[202][];
    private static final String BLANK = " ", BLACK = "B", WHITE = "W";
    private static final int R[] = {0, -1, 0, 1, 1, -1}; //{E, N, W, S, NE, SW}
    private static final int C[] = {1, 0, -1, 0, 1, -1};
    private static int N, count = 0; //number of nodes and case counter
    private static final char DONE = '.', black = 'b', white = 'w';
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1) break;
			if(N == 0) break; 
			count++; //increment case counter
			for(int i=0; i<N; i++)
				grid[i] = MyScanner.readLine().toCharArray();
			for(int i=0; i<N; i++)
			{
				if(grid[i][0] == white)
					if(dfs(i, 0)) {pw.println(count + BLANK + WHITE); break;}
				if(grid[0][i] == black)
					if(dfs(0, i)) {pw.println(count + BLANK + BLACK); break;}
			}
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Flood fill to find the winner
	 * @param r
	 * @param c
	 * @return true if the winner is found, false otherwise
	 */
	private static boolean dfs(int r, int c)
	{
		char p = grid[r][c];
		grid[r][c]  = DONE; //mark finished
		for(int i=0; i<6; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= N || newC >= N || grid[newR][newC] == DONE) continue;
			if(grid[newR][newC] == p)
			{
				if(((p == white) && newC == (N-1)) || ((p == black) && newR == (N-1))) return true; //winner is found
				if(dfs(newR, newC)) return true;
			}
		}
		return false;
	}
}
