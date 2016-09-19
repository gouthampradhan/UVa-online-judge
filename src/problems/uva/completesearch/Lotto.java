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
 * Accepted : 0.056 s. Simple 6 loop O (N ^ 6) nested loop. Set the print buffer too high since the output could be very large.
 *
 */
public class Lotto {

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
    private static int K;
    private static int[] S = new int[13]; //array of elements
    private static String BLANK = " ";
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		int count = 0;
		while(true)
		{
			while((K = MyScanner.readInt()) == -1);
			if(K == 0) break;
			for(int i = 0; i < K; i++)
				S[i] = MyScanner.readInt();
			if(count++ > 0)
				pw.println();
			for(int a = 0; a < K; a++)
				for(int b = (a + 1); b < K; b++)
					for(int c = (b + 1); c < K; c++)
						for(int d = (c + 1); d < K; d++)
							for(int e = (d + 1); e < K; e++)
								for(int f = (e + 1); f < K; f++)
										pw.println(S[a] + BLANK + S[b] + BLANK + S[c] + BLANK + S[d] + BLANK + S[e] + BLANK + S[f]);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
