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
 * Accepted 2.452 !! I, Definitely need to work on DP algorithm skills. Really struggled to understand the algorithm. Trick is to perform a forward 
 * recursive addition until N is reached. 
 * 
 * Accepted 0.118 s !!! Fastest in Java. What a great feeling of having solved the problem finally with my approach. The key was to use a boolean flag 
 * called verified, if this is not used then the same vertex is reexamined which results in TLE.
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
    private static boolean[] verified;
    private static int N, K, count;
    private static long result;
    private static final int MIN_VALUE = Integer.MIN_VALUE;
    private static final String CASE = "Case ", BLANK = ": ", IMPOSSIBLE = "impossible";
    private static final int R[] = {-1, 0, 0};
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
			verified = new boolean[309623];
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
			for(int i = 0; i<=K; i++)
				for(int j = 0; j<3; j++)
					{max[(i << 2) + j] = grid[0][0]; verified[(i << 2) + j] = true;}
			if(grid[N-1][N-1] < 0) K--;
			result = dp(N-1, N-1, K, 0); //invoke dp algorithm
			pw.print(CASE + ++count + BLANK); 
			if(result != Integer.MIN_VALUE)
				pw.println(result);
			else
				pw.println(IMPOSSIBLE);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * DP algorithm to find the max. Perform a recursive check until a valid value is found and marks each visited vertex as verified. (so that once again 
	 * the same vertex is not evaluated).
	 * @param r Row
	 * @param c Column
	 * @param k K
	 * @param d Direction (from top, left and right)
	 * @param sum total sum
	 */
	private static long dp(int r, int c, int k, int d)
	{
		if(k < 0) return MIN_VALUE;
		int encode = (((((r << 7) + c) << 3) + k) << 2) + d;
		if(verified[encode]) return max[encode];
		done[r][c] = true; verified[encode] = true; //***This is very important. If verified is not set then the same vertex is revisited and analyzed again.
		for(int i = 0; i< 3; i++)
		{
			int nr = r + R[i];
			int nc = c + C[i];
			if(nr < 0 || nc < 0 || nr >= N || nc >= N || done[nr][nc]) continue;
			long temp;
			if(grid[nr][nc] < 0)
				temp = dp(nr, nc, k-1, i);
			else 
				temp = dp(nr, nc, k, i);
			if(temp != MIN_VALUE)
				temp += grid[r][c];
			if(temp > max[encode])
				max[encode] = temp;
		}	
		done[r][c] = false;
		return max[encode];
	}
}
