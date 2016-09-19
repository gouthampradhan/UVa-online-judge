package problems.uva.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.399 s. Faced problem with double precision pointer, but the key was to completely avoid calculation using double.
 * Avoid double precision using the method -> 0.10 = > (0.10 * 100) / 100 => 10 / 100  
 *
 */
public class GroceryStore {

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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 500000)); //set the buffer too high. 
    private static final String BLANK = " ";
    private static final long NUM = 1000000;
    
    /**
     * 
     * @param args
     */
	public static void main(String[] args) 
	{
		for(int i = 8; i<= 125; i++)
		{
			for(int j = i; (i * j) <= 20000; j++)
			{
				for(int k = j, prod; ((prod = (i * j * k)) <= 3500000); k++)
				{
					//find d using formula 
					//(a * b * c * d) / 1000000 = (a + b + c + d) 
					int sum = i + j + k;
					long den = (prod - NUM); //denominator
					if(den <= 0) continue;
					long num = sum * NUM; //numerator
					if((num % den) != 0) continue;
					long q = num / den; //quotient
					if(q < k) continue;
					long v1 = sum + q;
					long v2 = (prod * q) / NUM;
					if(v1 > 2000 || v2 > 2000) continue;
					if(v1 == v2)
						pw.println(String.format("%.2f", (double)i/100) + BLANK + String.format("%.2f", (double) j / 100) + 
								BLANK + String.format("%.2f", (double)k / 100) + BLANK + String.format("%.2f", (double)q/100));
				}
			}
		}
		pw.flush(); pw.close();
	}
}
