package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.076. Simple formula to calculate fractions and decide the range of x and y values.
 * Formula used X = - (K * Y) / (K - Y)
 * Try all possible values for Y starting from (K + 1) and exit as soon as value of X < Y
 *
 */
public class FractionsAgain {

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
    private static int K;
    private static List<Fraction> fractions;
    private static final String FRACTION = "1/", PLUS = "+", EQUAL = "=", BLANK = " ";
    
    /**
     * Fraction class
     * @author gouthamvidyapradhan
     *
     */
    private static class Fraction
    {
    	int x, y;
    	Fraction(int x, int y)
    	{
    		this.x = x;
    		this.y = y;
    	}
    }
	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		while(true)
		{
			while((K = MyScanner.readInt()) == -1);
			if(K == -2) break;
			int x, count = 0, numerator, denominator;
			fractions = new ArrayList<Fraction>();
			for(int i = (K + 1); ; i++)
			{
				numerator = (K * i);
				denominator = Math.abs(K - i);
				x = numerator / denominator;
				if(x < i) break;//pruning condition
				if((numerator % denominator) > 0) continue; //ignore the fractions
				count++;
				fractions.add(new Fraction(x, i));
			}
			pw.println(count);
			for(Fraction f : fractions)
				pw.println(FRACTION + K + BLANK + EQUAL + BLANK + FRACTION + f.x + BLANK + PLUS + BLANK + FRACTION + f.y);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
