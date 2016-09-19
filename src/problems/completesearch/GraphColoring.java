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
 * Accepted 0.100. O(N!) algorithm does not work for extreme cases with 100 nodes but, looks like the input is very small.
 *
 */
public class GraphColoring {

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
    
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000)); //set the buffer too high.
    private static int T, N, K;
    private static List<List<Integer>> graph;
    private static int[] blackNode;
    private static boolean[] color;
    private static int max;
    private static final String BLANK = " ";
    
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
			K = MyScanner.readInt();
			graph = new ArrayList<>();
			color = new boolean[N + 1];
			blackNode = new int[N + 1];
			for(int i = 0; i < N + 1; i++)
				graph.add(new ArrayList<Integer>());
			max = Integer.MIN_VALUE;
			for(int i = 0; i < K; i++)
			{
				int from = MyScanner.readInt();
				int to = MyScanner.readInt();
				List<Integer> children1 = graph.get(from);
				children1.add(to);
				
				List<Integer> children2 = graph.get(to);
				children2.add(from);
			}
			backTrack(1, 0);
			pw.println(max);
			for(int i = 0; i < max; i++)
			{
				if(i != 0)
					pw.print(BLANK);
				pw.print(blackNode[i]);
			}
			pw.println();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Backtrack to color and check max
	 * @param pos
	 * @param next
	 * @param colorCnt
	 */
	private static void backTrack(int pos, int colorCnt)
	{
		if(pos == N + 1)
		{
			if(colorCnt > max)
			{
				max = colorCnt;
				int count = 0;
				for(int i = 0; i < N + 1; i++)
				{
					if(color[i])
						blackNode[count++] = i; 
				}
			}
			return;
		}
		
		List<Integer> children = graph.get(pos);
		boolean colorNode = true;
		for(int c : children)
		{
			if(color[c])
			{
				colorNode = false;
				break;
			}
		}
		if(colorNode)
		{
			color[pos] = true;
			backTrack(pos + 1, colorCnt + 1);
		}
		color[pos] = false;
		backTrack(pos + 1, colorCnt);
	}
}
