package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.242. Interesting problem, bit complicated to understand the bit-masking. 
 * My Algorithm : Generate all combination of values recursively. For each element in the combination, iterate through the common service area
 * and using bit-masking calculate the value for each individual location (non-shared), sum up all the non-shared locations and add sum of intersection only once. 
 * Average WC Time complexity O(nCr * n * m)
 *
 */
public class Zones {

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
    private static int N, A, M, max;
    private static int[] P, L, B, LOC;
    private static int[][] T;
    private static Set<Integer> intersection = new HashSet<>();
    private static final String CASE = "Case Number  ", NUMBER = "Number of Customers: ", LOCATION = "Locations recommended:", BLANK = " ";
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		int count = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1); //number of available locations
			A = MyScanner.readInt(); //actual to be built
			if(N == 0) break;
			P = new int[N]; L = new int[N];
			B = new int[N + 1]; LOC = new int[A];
			max = Integer.MIN_VALUE;
			for(int i = 0; i < N; i++)
				P[i] = MyScanner.readInt();
			M = MyScanner.readInt();
			T = new int[M][2];
			for(int i = 0; i < M; i++)
			{
				int n = MyScanner.readInt();
				int sum = 0;
				for(int j = 0; j < n; j++)
					sum += (1 << MyScanner.readInt());
				T[i][0] = sum;
				T[i][1] = MyScanner.readInt();
			}
			for(int i = 0; i < N; i++)
				L[i] = i + 1; //save the locations starting from 1
			List<Integer> list = new ArrayList<>();
			for(int i = 0; i < N; i++)
				list.add(i, 0);
			combination(1, 0, list);
			pw.println(CASE + ++count);
			pw.println(NUMBER + max);
			pw.print(LOCATION);
			for(int i = 0; i < A; i++)
			{
				pw.print(BLANK);
				pw.print(LOC[i]);
			}
			pw.println();
			pw.println();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * 
	 * @param n
	 * @param next
	 * @param com
	 */
	private static void combination(int pos, int next, List<Integer> com)
	{
		for(int i = next; i < N; i++)
		{
			com.set(pos - 1, L[i]);
			if(pos < A)
				combination(pos + 1, i + 1, com);	
			else
				process(com);
		}
	}
	
	/**
	 * 
	 * @param combination
	 */
	private static void process(List<Integer> combination)
	{
		int sum = 0;
		intersection.clear();
		for(int i = 0; i < A; i++)
			sum += 1 << combination.get(i);
		for(int j = 0; j < A; j++)
		{
			int e = combination.get(j);
			int temp = (1 << e), shared = 0;
			for(int i = 0; i < M; i++)
			{
				int loc = T[i][0];
				if((sum & loc) > temp && ((loc & temp) != 0))
				{
					shared += T[i][1];
					intersection.add(i);
				}
			}
			B[e] = P[e - 1] - shared;
		}
		
		int comSum = 0;
		Object[] common = intersection.toArray();
		for(Object o : common)
			comSum += T[(int)o][1];
		
		sum = 0;
		for(int i = 0; i < A; i++)
			sum += B[combination.get(i)];
		sum += comSum;
		if(sum > max)
		{
			max = sum;
			for(int i = 0; i < A; i++)
				LOC[i] = combination.get(i);
		}
	}
}
