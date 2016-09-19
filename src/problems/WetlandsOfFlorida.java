package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted first attempt 0.225 s ! Simple FloodFill problem.
 *
 */
public class WetlandsOfFlorida 
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
    
    private static char[][] grid = new char[101][101];
    private static final char WATER = 'W';
    private static final int R[] = {0, -1, -1, -1, 0, 1, 1, 1}; //{E, NE, N, NW, W, SW, S, SE}
    private static final int C[] = {1, 1, 0, -1, -1, -1, 0, 1};
    private static int M, N;
    private static BitSet done = new BitSet(12900);
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
			String line; int pos = 0;
			while((line = MyScanner.readLine()) == null || line.trim().equals(""));
			M = line.trim().length(); //columns
			while(true)
			{
				if(isString(line))
					grid[pos++] = line.trim().toCharArray();
				else break;
				line = MyScanner.readLine();
			}
			N = pos; //rows
			StringTokenizer st = new StringTokenizer(line.trim());
			int r = MyScanner.parseInt(st.nextToken()); int c = MyScanner.parseInt(st.nextToken());
			if(count++ != 0)
				pw.println();
			if(grid[r-1][c-1] == WATER)
				pw.println(dfs(r-1, c-1));
			done.clear();
			while(true)
			{
				if((r = MyScanner.readInt()) == -1) break;
				c = MyScanner.readInt();
				if(grid[r-1][c-1] == WATER)
					pw.println(dfs(r-1, c-1));
				done.clear();
			}
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
		done.set((r << 7) + c); //mark this as finished.
		for(int i = 0, l = R.length; i < l; i++)
		{
			int newR = r+R[i]; int newC = c+C[i];
			if(newR < 0 || newC < 0 || newR >= N || newC >= M || done.get((newR << 7) + newC)) continue;
			if(grid[newR][newC] == WATER)
				count += dfs(newR, newC);
		}
		return count;
	}
	
	/**
	 * Method to check if the given String is an integer or just a String
	 * @param line
	 * @return
	 */
	private static boolean isString(String line)
	{
		StringTokenizer st = new StringTokenizer(line.trim());
		String e = st.nextToken();
		try
		{
			Integer.parseInt(e);
		}
		catch(Exception ex)
		{
			return true;
		}
		return false;
	}
}
