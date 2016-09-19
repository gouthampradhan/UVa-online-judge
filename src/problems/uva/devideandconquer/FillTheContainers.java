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
 * Accepted 0.040 s.  Binary search the answer
 * MyAlgorithm O(n log n) - Worst case = > 1000 x log 199000000
 *
 */
public class FillTheContainers {

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
    private static int min, max, fill, N, M, conCnt;
    private static int[] A;

    /**
     * Status of each check
     * @author gouthamvidyapradhan
     *
     */
    private static enum Status
    {
    	LOW,
    	HIGH,
    	CORRECT;
    }
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
			if(N == -2) break;
			M = MyScanner.readInt();
			A = new int[N];
			int temp, sum = 0, high = Integer.MIN_VALUE;
			for(int i = 0; i < N; i++)
			{
				temp = MyScanner.readInt();
				high = Math.max(high, temp);
				sum += temp;
				A[i] = temp;
			}
			min = Integer.MAX_VALUE; 
			if(M < N)
			{
				int l = high, h = sum, m;
				while(l < h - 1)
				{
					m = (l + h) / 2;
					Status status = check(m);
					switch(status)
					{
						case LOW:
								l = m;
								break;
								
						case HIGH:
								h = m;
								break;
								
						case CORRECT:
								min = Math.min(min, m);
								h = m; //explore next lower value
								break;
					}
				}
				if(l == h - 1)
				{
					Status status;
					status = check(l);
					if(status == Status.CORRECT)
						min = Math.min(min, l);
					status = check(h);
					if(status == Status.CORRECT)
						min = Math.min(min, h);
				}
				pw.println(min);
			}
			else
				pw.println(high);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Check if the answer fits
	 * @param ans
	 * @return
	 */
	private static Status check(int ans)
	{
		conCnt = M; fill = 0;
		max = Integer.MIN_VALUE;
		for(int i = 0; i < N ; i++)
		{
			if(conCnt == 0)
				return Status.LOW;
			int a = A[i];
			if((fill + a) > ans)
			{
				if(--conCnt == 0)
					return Status.LOW;
				max = Math.max(max, fill);
				fill = a;
			}
			else
				fill += a;
		}
		conCnt--; // do the filling for the last container. There can be containers left over but don't care about it.
		max = Math.max(max, fill);
		if(max == ans) return Status.CORRECT;
		else return Status.HIGH;
	}
}
