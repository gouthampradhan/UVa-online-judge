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
 * Accepted 2.452 !! I, Definitely need to work on DP algorithm skills. Really struggled to understand the algorithm. Trick is to perform a forward 
 * recursive addition until N is reached. 
 *
 */
public class WalkingOnAGrid {

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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
            return br.ready();
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

            // Build the number
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int[][] grid = new int[80][80]; //max is 75
    private static long[] max = new long[309623];
    private static boolean[][] done = new boolean[80][80]; //max is 75
    private static int N, K, count;
    private static long result;
    private static final int MIN_VALUE = Integer.MIN_VALUE;
    private static final String CASE = "Case ", BLANK = ": ", IMPOSSIBLE = "impossible";
    private static final int R[] = {1, 0, 0};
    private static final int C[] = {0, 1, -1};

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		count = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1); 
			K = MyScanner.readInt();
			if(N == 0 && K == 0) break;
			result = Integer.MIN_VALUE; //initialize result with min value
			for(int i = 0; i<N; i++)
			{
				for(int j = 0; j<N; j++)
				{
					grid[i][j] = MyScanner.readInt();
					done[i][j] = false;
					int encode = (((i << 7) + j) << 3);
					int temp;
					for(int k=0; k<=K; k++)
					{
						temp = encode + k;
						for(int d=0; d<3; d++)
							max[(temp << 2) + d] = MIN_VALUE;
					}
				}
			}
			if(grid[0][0] < 0) K--;
			dp(0, 0, K, 0, grid[0][0]); //invoke dp algorithm
			pw.print(CASE + ++count + BLANK); 
			if(result != Integer.MIN_VALUE)
				pw.println(result);
			else
				pw.println(IMPOSSIBLE);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * DP algorithm to find the max. Performs a forward addition until the destination is reached. Once the destination is reached 
	 * checks for new maximum and replaces the old maximum if necessary
	 * @param r Row
	 * @param c Column
	 * @param k K
	 * @param d Direction (from top, left and right)
	 * @param sum total sum
	 */
	private static void dp(int r, int c, int k, int d, int sum)
	{ 
		if(k < 0) return; //invalid state
		if(r == N-1 && c == N-1) {result = Math.max(result, sum); return;}
		done[r][c] = true;
		for(int i = 0; i< 3; i++)
		{
			int nr = r + R[i];
			int nc = c + C[i];
			if(nr < 0 || nc < 0 || nr >= N || nc >= N || done[nr][nc]) continue;
			int temp = grid[nr][nc] + sum;
			int encode = ((nr << 7) + nc) << 3;
			if(grid[nr][nc] < 0)
			{
				if(temp > max[((encode + k-1) << 2) + i]) // *** Very important. 
					//If this condition is not added then there will be repeated check for the same vertex
				{
					max[((encode + k-1) << 2) + i] = temp;
					dp(nr, nc, k - 1, i, temp);
				}
			}
			else if(temp > max[((encode + k) << 2) + i])
			{
				max[((encode + k) << 2) + i] = temp;
				dp(nr, nc, k, i, temp);
			}
		}
		done[r][c] = false;
	}
}
