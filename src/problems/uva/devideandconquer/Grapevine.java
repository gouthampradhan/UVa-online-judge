package problems.uva.devideandconquer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 1.980 s. 
 * Struggled initially to find a optimal solution. Algorithm will not work for extreme cases worst case time complexity is O (N log N) * N * 10 ^ 4
 *
 */
public class Grapevine {

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
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static long readLong() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return Long.parseLong(st.nextToken());
                }
                String str = br.readLine();
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return Long.parseLong(st.nextToken());
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static int[][] A;
    private static int N, M, Q, L, U;
    private static final String MINUS = "-";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args)  throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			if(N == 0) break;
			A = new int[N][M];
			int max, nr, nc, len, lb, up;
			for(int i = 0; i < N; i++)
				for(int j = 0; j < M; j++)
					A[i][j] = MyScanner.readInt();
			Q = MyScanner.readInt();
			while(Q-- > 0)
			{
				L = MyScanner.readInt();
				U = MyScanner.readInt();
				max = -1;
				for(int i = 0; (i < N) && (N - i) > max; i++)
				{
					int[] row = A[i];
					lb = bs(row, L, true);
					up = bs(row, U, false);
					if(lb != -1 && up != -1 && lb <= up)
					{
						len = (up - lb);
						for(int j = len; j > max; j--)
						{
							nr = i + j;
							nc = lb + j;
							if(nr >= N) continue;
							if(A[nr][nc] <= U)
							{
								max = (j > max) ? j : max;
								break;
							}
						}
					}
				}
				pw.println((max == -1) ? 0 : max + 1);
			}
			pw.println(MINUS);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Binary search the upper and lower bound
	 * @param A
	 * @param k
	 * @param isLowerbound
	 * @return
	 */
	private static int bs(int[] A, int k, boolean isLowerbound)
	{
		int l = 0, h = M - 1, m, index = -1, min = Integer.MAX_VALUE;
		while(l < (h - 1))
		{
			m = (l + h) / 2;
			if(isLowerbound)
			{
				if(A[m] >= k)
				{
					h = m;
					if((A[m] - k) <= min)
					{
						min = A[m] - k;
						index = m;
					}
				}
				else
					l = m;
			}
			else
			{
				if(A[m] <= k)
				{
					l = m;
					if((k - A[m]) <= min)
					{
						min = k - A[m];
						index = m;
					}
				}
				else
					h = m;
			}
		}
		if(isLowerbound)
		{
			if(A[l] >= k)
			{
				if((A[l] - k) <= min)
				{
					min = A[l] - k;
					index = l;
				}
			}
			else if(A[h] >= k)
			{
				if((A[h] - k) <= min)
				{
					min = A[h] - k;
					index = h;
				}
			}
		}
		else
		{
			if(A[h] <= k)
			{
				if((k - A[h]) <= min)
				{
					min = k - A[h];
					index = h;
				}
			}
			else if(A[l] <= k)
			{
				if((k - A[l]) <= min)
				{
					min = k - A[l];
					index = l;
				}
			}
		}
		return index;
	}
}
