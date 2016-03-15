package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 1.752 s. Brute-force with pruning. One of the google interview questions.
 *
 */
public class TriangleCounting {

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
    private static int T, N, M;
	private static int[] V;
    private static Set<Integer> vertices = new HashSet<Integer>();
    private static boolean[][] graph;
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			while((N = MyScanner.readInt()) == -1);
			M = MyScanner.readInt();
			graph = new boolean[3001][3001];
			int v1, v2, triangle = 0, pos = 0;
			
			V = new int[N];
			for(int i = 0; i < M; i++)
			{
				v1 = MyScanner.readInt();
				v2 = MyScanner.readInt();
				vertices.add(v1);
				vertices.add(v2);
				graph[v1][v2] = true;
				graph[v2][v1] = true;
			}
			for(Integer i : vertices)
				V[pos++] = i;
			int commonEdge = 0;
			for(int i = 0; i < N; i++)
				for(int j = i + 1; j < N; j++, commonEdge = 0)
				{
					if(graph[V[i]][V[j]]) //has an edge
					{
						for(int k = j + 1; k < N; k++)
						{
							if(graph[V[j]][V[k]] && graph[V[i]][V[k]]) //check for triangle
							{
								commonEdge++;
								triangle++;
								if(commonEdge == 2) break; //can't be more than 2
							}
						}
					}
				}
			pw.println(triangle);
			vertices.clear();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
