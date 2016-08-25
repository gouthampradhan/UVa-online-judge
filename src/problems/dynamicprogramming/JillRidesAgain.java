package problems.dynamicprogramming;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.320 s. Simple max 1D range sum DP problem.
 *
 */
public class JillRidesAgain {

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
    private static int T, B;
    private static int[] R;
    private static final String NICE_PARTS = "The nicest part of route ";
    private static final String IS = " is between stops ";
    private static final String NO_NICEPARTS = " has no nice parts";
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args)   throws Exception  
	{
		T = MyScanner.readInt();
		int count = 0;
		while(T-- > 0)
		{
			while((B = MyScanner.readInt()) == -1);
			R = new int[B];
			for(int i = 1; i < B; i++)
				R[i] = MyScanner.readInt();
			int max = 0, sum = 0, si = 0, ei = 0, S = 0, E = 0;
			for(int i = 1; i < B; i++)
			{
				sum += R[i];
				if(si == 0 && ei == 0) 
				{
					si = i;
					ei = si + 1;
				}
				else ei++;
				if(sum > max)
				{
					max = sum;
					S = si; E = ei;
				}
				else if(sum == max && sum != 0)
				{
					if((ei - si) > (E - S))
					{
						S = si; E = ei;
					}
				}
				else if(sum < 0) 
				{
					sum = 0;
					si = 0; 
					ei = 0;
				}
			}
			if(S > 0 && E > 0)
				pw.println(NICE_PARTS + ++count + IS + S + " and " + E);
			else pw.println("Route " + ++count + NO_NICEPARTS);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
