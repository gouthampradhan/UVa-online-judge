package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 124ms. Simple DP with strings
 *
 */
public class ReberlandLinguistics {

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
    private static Set<String> subStr = new TreeSet<>();
    private static boolean[][] dp;
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		String s = MyScanner.readLine();
		int length = s.length();
		dp = new boolean[length][2]; //two, three
		if(length <= 6)
			pw.println(0);
		else
		{
			dp[length - 1][0] = false;
			dp[length - 1][1] = false;
			dp[length - 2][0] = true;
			dp[length - 2][1] = false;
			dp[length - 3][0] = false;
			dp[length - 3][1] = true;
			if(length >= 7)
				subStr.add(s.substring(length - 2, length));
			if (length >= 8)
				subStr.add(s.substring(length - 3, length));
			for(int i = length - 4; i >= 5; i--)
			{
				if(dp[i + 2][1] || dp[i + 2][0] && !s.substring(i + 2, i + 4).equals(s.substring(i, i + 2)))
				{
					subStr.add(s.substring(i, i + 2));
					dp[i][0] = true;
				}
				if(dp[i + 3][0] || (dp[i + 3][1] && !s.substring(i + 3, i + 6).equals(s.substring(i, i + 3))))
				{
					subStr.add(s.substring(i, i + 3));
					dp[i][1] = true;
				}
			}
			pw.println(subStr.size());
			for(String a : subStr)
				pw.println(a);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
