package problems.codeforces;

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
 * Accepted 296ms
 *
 */
public class KefaAndPark 
{

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

    private static int M, N, canVisit; //M rows, N columns
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static int[] A;
    private static boolean[] done;
    private static List<List<Integer>> graph;
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		N = MyScanner.readInt();
		M = MyScanner.readInt();
		graph = new ArrayList<>();
		done = new boolean[N + 1];
		A = new int[N + 1];
		canVisit = 0;
		for(int i = 0; i <= N; i++)
			graph.add(new ArrayList<Integer>());
		for(int i = 1; i <= N; i++)
			A[i] = MyScanner.readInt();
		for(int i = 0; i < N - 1; i++)
		{
			int from = MyScanner.readInt();
			int to = MyScanner.readInt();
			graph.get(from).add(to);
			graph.get(to).add(from); // make two way connection
		}
		dfs(0, 1);
		pw.println(canVisit);
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Count possible 
	 * @param catCnt
	 * @param v
	 */
	private static void dfs(int catCnt, int v)
	{
		done[v] = true;
		if(A[v] == 1)
			catCnt++;
		else
			catCnt = 0;

		if(catCnt > M) return;
		
		List<Integer> children = graph.get(v);
		if(children.size() == 1)
		{
			if(done[children.get(0)])
				canVisit++;
		}
		
		for(int c : children)
		{
			if(!done[c])
				dfs(catCnt, c);
		}
	}
}
