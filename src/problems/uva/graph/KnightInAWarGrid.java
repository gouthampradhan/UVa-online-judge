package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.345 s. Got WA first time but later resolved the error. The input is very tricky, problem statement says (x,y) coordinates where X is Row and
 * Y is column. General perception is to assume X as Column and Y as row, but in this case its different - The grid is similar to two dimensional array
 *
 */
public class KnightInAWarGrid {

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
        private static int parseInt(String in)
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

    private static final String CASE = "Case";
    private static final String COLON = ":";
    private static final String BLANK = " ";
    private static int N, M, R, C, W;
	private static int even;
	private static int odd;
	private static final int constant = 127;
	private static int[] I;
	private static int[] II;
	private static BitSet done = new BitSet(13000);
	private static BitSet waterGrid = new BitSet(13000);
	private static BitSet visited = new BitSet(13000);
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception
	{
		int T, count = 1;
		while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((R = MyScanner.readInt()) == -1);
			C = MyScanner.readInt();
			M = MyScanner.readInt();
			N = MyScanner.readInt();
			odd = 0; even = 0; //reset even & odd
			I =  new int[] { 1, 1, -1, -1, 1, 1, -1, -1}; II = new int[] { 1, -1, 1, -1, 1, -1, 1, -1};
			int i = 0;
			for(; i<4 ; i++)
			{
				I[i] = M * I[i];
				II[i] = N * II[i];
			}
			for(; i<8 ; i++)
			{
				I[i] = N * I[i];
				II[i] = M * II[i];
			}
			while((W = MyScanner.readInt()) == -1);
			while(W-- > 0)
				waterGrid.set((MyScanner.readInt() << 7) + MyScanner.readInt()); //unique value for water grids
			bfs();
			pw.println(CASE + BLANK + count++ + COLON + BLANK + even + BLANK + odd);
			waterGrid.clear();
			done.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Bfs search to verify all the reachable grids
	 */
	private static void bfs()
	{
		done.set(0); //start vertex
		int head = 0, tail = 0;
		int q[] = new int[10300];
		q[tail++] = 0; //add the initial vertex to the queue
		while(head < tail)
		{
			int first = q[head++];
			int y = first & constant;
			int x = first >> 7;
			int count = 0;
			for(int i=0, l = I.length; i<l; i++)
			{
				int newX = I[i] + x; int newY = II[i] + y;
				int grid = (newX << 7) + newY; 
				if(newX >= 0 && newY >= 0 && newX < R && newY < C && !waterGrid.get(grid) && !visited.get(grid)) //this is very tricky, 
					//generally we do newX < C but in this case it is newX < R
				{
					count++; visited.set(grid); //mark this as already counted
					if(!done.get(grid)) //very important, this avoids duplicates in the queue
					{
						done.set(grid);
						q[tail++] = grid;
					}
				}
			}
			visited.clear();
			if((count % 2) == 0) even++; else odd++; //check if the total reachable grids is odd or even
		}
	}
}
