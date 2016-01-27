package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class WinterimBackpackingTrip {

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
    private static int[][] distance, min;
    private static int[] dist;
    private static int N, K, MAX_VALUE = Integer.MAX_VALUE;
    
    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == -2) break;
			K = MyScanner.readInt();
			dist = new int[N+2]; distance = new int[N+2][N+2]; min = new int[N+2][K+2];
			//accept input
			for(int i=0; i<(N+1); i++)
				dist[i] = MyScanner.readInt();
			//construct the distance graph
			for(int i = 0; i<(N+1); i++)
				distance[i][i+1] = dist[i];
			for(int i = 0; i<(N+1); i++)
				for(int j = (i+1); j<(N+2); j++)
					distance[i][j] = (distance[i][j-1] + distance[j-1][j]);
			for(int i = 0; i<N+2; i++)
				for(int j = 0; j<(K+2); j++)
					min[i][j] = MAX_VALUE; //initialize with max value
			pw.println(dfs(0, K+1));
		}
		pw.flush(); pw.close(); MyScanner.close(); 
	}
	
	/**
	 * Perform a dfs to fetch the min of maximum
	 * @param cur current node being examined
	 * @param n_left number of nights left
	 * @return
	 */
	private static int dfs(int cur, int n_left)
	{
		if(n_left < 0) return MAX_VALUE; //invalid node, prune node
		if(cur == N+1) return 0; //end of trail
		if(min[cur][n_left] != MAX_VALUE) return min[cur][n_left]; //return if already evaluated the minimum
		for(int i=(cur+1); i<=(N+1); i++)
		{
			int value = dfs(i, (n_left - 1));
			int temp = (value > distance[cur][i]) ? value : distance[cur][i];
			if(temp < min[cur][n_left])
				min[cur][n_left] = temp;
		}
		return min[cur][n_left];
	}
}
