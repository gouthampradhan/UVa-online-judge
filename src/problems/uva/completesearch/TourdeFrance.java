package problems.uva.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.059s. Simple brute-force O(N^2) solution
 *
 */
public class TourdeFrance {

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
    private static int NF, NR;
    private static double spread;
    private static double[] velocity;
    private static double[] F = new double[10];
    private static double[] R = new double[10];
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((NF = MyScanner.readInt()) == -1);
			if(NF == 0) break;
			NR = MyScanner.readInt();
			spread = -1.0;
			for(int i = 0; i < NF; i++)
				F[i] = MyScanner.readInt();
			for(int i = 0; i < NR; i++)
				R[i] = MyScanner.readInt();
			int k = 0;
			velocity = new double[NF * NR];
			for(int i = 0; i < NF; i++)
				for(int j = 0; j < NR; j++)
					velocity[k++] = (R[j] / F[i]);
			Arrays.sort(velocity);
			int j; double temp;
			if(k > 1)
			{
				for(int i = 0; i < k - 1; i++)
				{
					j = i + 1;
					temp = velocity[j] / velocity[i];
					spread = Math.max(spread, temp);
				}
			}
			else
				spread = velocity[0];
			pw.println(String.format("%.2f", spread));
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
