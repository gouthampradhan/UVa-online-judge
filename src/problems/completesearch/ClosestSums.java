package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.185 s. Simple brute-force algorithm O(N^2)
 *
 */
public class ClosestSums {

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
    private static int N, M, count;
    private static int[] A = new int[1002];
    private static int[] S = new int[1500000];
    private static final String CLOSEST = "Closest sum to ", IS = " is ", STOP = ".", CASE = "Case ", COLON = ":";
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		count = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == 0) break;
			for(int i = 0; i < N; i++)
				A[i] = MyScanner.readInt();
			int k = 0;
			M = MyScanner.readInt();
			if(N == 1)
				S[k++] = A[0];
			else
			{
				for(int i = 0; i < (N - 1); i++)
					for(int j = i + 1; j < N; j++)
						S[k++] = A[i] + A[j];
			}
			int min, q, dif, closest = 0;
			pw.println(CASE + ++count + COLON);
			for(int i = 0; i < M; i++)
			{
				min = Integer.MAX_VALUE;
				q = MyScanner.readInt();
				for(int j = 0; j < k; j++)
				{
					dif = Math.abs(q - S[j]);
					if(dif <= min)
					{
						min = dif;
						closest = S[j];
						if(dif == 0) break; //can't be better than this.Prune
					}
				}
				pw.println(CLOSEST + q + IS + closest + STOP);
			}
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
