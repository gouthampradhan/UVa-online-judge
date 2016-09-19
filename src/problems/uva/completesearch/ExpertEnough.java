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
 * Accepted 0.229 s. Simple brute-force O(N^2) solution.
 *
 */
public class ExpertEnough {

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

    /**
     * Class to store Automobile manufacturer and the price range
     * @author gouthamvidyapradhan
     *
     */
    private static class Auto
    {
    	String carMaker;
    	int low, high;
    	Auto(String carMaker, int low, int high)
    	{
    		this.carMaker = carMaker;
    		this.low = low;
    		this.high = high;
    	}
    }
    
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int T, D, N;
    private static Auto[] automobiles;
    private static String UNDETERMINED = "UNDETERMINED";
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		int count = 0;
		while(T-- > 0)
		{
			D = MyScanner.readInt();
			String line;
			automobiles = new Auto[D];
			for(int i = 0; i < D; i++)
			{
				line = MyScanner.readLine();
				StringTokenizer st = new StringTokenizer(line);
				while(st.hasMoreTokens())
					automobiles[i] = new Auto(st.nextToken().trim(), MyScanner.parseInt(st.nextToken().trim()), MyScanner.parseInt(st.nextToken().trim()));
			}
			N = MyScanner.readInt(); //number of queries
			int q;
			if(N > 0 && count++ > 0) 
				pw.println();
			for(int i = 0; i < N; i++)
			{
				q = MyScanner.readInt(); //query price
				int found = 0; String carMaker = "";
				for(Auto a : automobiles)
				{
					if(q >= a.low && q <= a.high)
					{
						found++;
						carMaker = a.carMaker;
						if(found > 1) break;
					}
				}
				if(found == 1)
					pw.println(carMaker);
				else
					pw.println(UNDETERMINED);
			}
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
