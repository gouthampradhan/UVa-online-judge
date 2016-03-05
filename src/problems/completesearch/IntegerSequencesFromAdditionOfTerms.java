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
 * Accepted 0.039 s. Fastest in java. Simple problem involving AP and calculation using sum of up to N terms formula.
 *
 */
public class IntegerSequencesFromAdditionOfTerms {

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
    private static int T, x, D, K, Di, limit;
    private static int[] co;
    private static long result;
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			co = new int[30];
			x = 0; result = 0;
			Di = MyScanner.readInt();
			for(int i = 0; i<=Di; i++)
				co[i] = MyScanner.readInt(); //read coefficients
			D = MyScanner.readInt();
			K = MyScanner.readInt();
			limit = 2 * K;
			int temp;
			for(int i = 1; i < 1000000; i++)
			{
				temp = (2 * i * D) + (i * i * D) - (i * D);
				if(temp >= limit)
				{
					x = i;
					break;
				}
			}
			for(int i = 0, l = co.length; i < l; i++)
				result += (co[i] * Math.pow(x, i));
			pw.println(result);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
