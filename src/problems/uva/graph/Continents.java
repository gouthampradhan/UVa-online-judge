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
 * Accepted first attempt 0.049 s. Simple flood fill involves scrolling from Right->Left and Left->Right
 *
 */
public class Continents {
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
    
    private static char[][] grid = new char[21][21];
    private static final char DONE = '.';
    private static char LAND;
    private static final int R[] = {0, -1, 0, 1}; //{E, N, W, S}
    private static final int C[] = {1, 0, -1, 0};
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
			int pos = 0, max = 0;
			while((M = MyScanner.readInt()) == -1); 
			if(M == -2) break;
			N = MyScanner.readInt();
			for(int i=0; i<M; i++)
				grid[pos++] = MyScanner.readLine().trim().toCharArray();
			int r = MyScanner.readInt(); int c = MyScanner.readInt();
			LAND = grid[r][c];
			dfs(r, c); //ignore the count here.
			for(int i=0; i<M; i++)
				for(int j=0; j<N; j++)
					if(grid[i][j] == LAND) { max = Math.max(dfs(i, j), max); }
			pw.println(max);
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
		grid[r][c] = DONE; //mark this as finished
		int count = 0; count++;
		for(int i = 0, l = R.length; i < l; i++)
		{
			int newR = r+R[i]; int newC = (c+C[i]+N) % N;
			if(newR < 0 || newR >= M) continue;
			if(grid[newR][newC] == LAND)
				count += dfs(newR, newC);
		}
		return count;
	}
}
