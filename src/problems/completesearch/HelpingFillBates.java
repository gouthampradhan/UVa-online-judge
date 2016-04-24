package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.240 s. Difficult to understand the problem statement, few ambiguities in the problem statement. But once figured out what to do then its
 * simple application of binary search.
 * Algorithm runs at O(Q x Q-length x log(1000000)) + 1M = 8M (approximately)  
 *
 */
public class HelpingFillBates {

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
    private static Map<String, List<Integer>> CANDIDATE;
    private static int Q;
    private static final String NOT_MATCHED = "Not matched", MATCHED = "Matched", BLANK = " ";
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		String line = MyScanner.readLine();
		CANDIDATE = new HashMap<>();
		for(int i = 0, l = line.length(); i < l; i++)
		{
			char c = line.charAt(i);
			List<Integer> candidates = CANDIDATE.get(String.valueOf(c));
			if(candidates == null)
				candidates = new ArrayList<>();
			candidates.add(i);
			CANDIDATE.put(String.valueOf(c), candidates);
		}
		Q = MyScanner.readInt();
		while(Q-- > 0)
		{
			String in = MyScanner.readLine();
			int pos = -1, s = 0, e = 0;
			boolean found = true;
			for(int i = 0, l = in.length(); i < l; i++)
			{
				List<Integer> candidates = CANDIDATE.get(String.valueOf(in.charAt(i)));
				if(candidates == null)
				{
					found = false; 
					break;
				}
				else
				{
					pos = bs(candidates, pos + 1);
					if(pos == -1)
					{
						found = false;
						break;
					}
					if(i == 0)
						s = pos;
					if(i == l - 1)
						e = pos;
				}
			}
			pw.println(found ? (MATCHED + BLANK + s + BLANK + e) : NOT_MATCHED);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * 
	 * @param candidates
	 * @param pos
	 * @return
	 */
	private static int bs(List<Integer> candidates, int pos)
	{
		int l = 0, h = candidates.size() - 1, m, mIndex;
		while(l < h - 1)
		{
			mIndex = (l + h) / 2;
			m = candidates.get(mIndex);
			if(m == pos)
				return m;
			else if(m < pos)
				l = mIndex;
			else if(m > pos)
				h = mIndex;
		}
		if(candidates.get(l) >= pos)
			return candidates.get(l);
		else if(candidates.get(h) >= pos)
			return candidates.get(h);
		return -1;
	}
}
