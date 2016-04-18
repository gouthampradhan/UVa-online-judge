package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.940 s. Simple backtracking with O(2 x 6^5) algorithm.
 *
 */
public class Password {

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
                if (str != null && !str.trim().equals("")) {
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
    private static int T, K;
    private static String[] S1, S2;
    private static final String NO = "NO";
    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			K = MyScanner.readInt();
			S1 = new String[6];
			S2 = new String[6];
			for(int i = 0; i < 6; i++)
				S1[i] = MyScanner.readLine();
			for(int i = 0; i < 6; i++)
				S2[i] = MyScanner.readLine();
			Set<String> passSet1 = new HashSet<>();
			Set<String> passSet2 = new HashSet<>();
			bT(S1, new char[5], 0, passSet1);
			bT(S2, new char[5], 0, passSet2);
			List<String> passwords = new ArrayList<>();
			Iterator<String> ite = passSet1.iterator();
			String str;
			while(ite.hasNext())
			{
				str = ite.next();
				if(passSet2.contains(str))
					passwords.add(str);
			}
			int total = passwords.size();
			if(total < K)
				pw.println(NO);
			else
			{
				Collections.sort(passwords);
				pw.println(passwords.get(K - 1));
			}
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Construct all possible passwords
	 * @param S
	 * @param p
	 * @param c
	 */
	private static void bT(String[] S, char[] p, int c, Set<String> passSet)
	{
		if(c == 5)
		{
			passSet.add(String.valueOf(p));
			return;
		}
		for(int i = 0; i < 6; i++)
		{
			String s = S[i];
			p[c] = s.charAt(c);
			bT(S, p, c + 1, passSet);
		}
	}
}
