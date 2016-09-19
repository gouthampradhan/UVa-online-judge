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
 * Accepted 0.069 s. Simple FloodFill problem, count connected components.
 *
 */
public class OilDeposits {
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
    
    private static char[][] grid = new char[101][101];
    private static final char OIL = '@', DONE = '.';
    private static final int R[] = {0, -1, -1, -1, 0, 1, 1, 1}; //{E, NE, N, NW, W, SW, S, SE}
    private static final int C[] = {1, 1, 0, -1, -1, -1, 0, 1};
    private static int M, N; //M rows, N columns
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
			int pos = 0, count = 0;
			while((M = MyScanner.readInt()) == -1); 
			N = MyScanner.readInt();
			if(M == 0) break;
			for(int i=0; i<M; i++)
				grid[pos++] = MyScanner.readLine().trim().toCharArray();
			for(int i=0; i<M; i++)
				for(int j=0; j<N; j++)
					if(grid[i][j] == OIL) {count++; dfs(i, j);}
			pw.println(count);
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
	private static void dfs(int r, int c)
	{
		grid[r][c] = DONE; //mark this as finished
		for(int i = 0, l = R.length; i < l; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= M || newC >= N) continue;
			if(grid[newR][newC] == OIL)
				dfs(newR, newC);
		}
	}
}
