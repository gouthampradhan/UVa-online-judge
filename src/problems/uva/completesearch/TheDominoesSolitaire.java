package problems.uva.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.050 O(N!) algorithm with backtracking. Actually the solution should not work for extreme cases where the algorithm runs for 14!, the input
 * data seems too weak or problem description is missing something.
 *
 */
public class TheDominoesSolitaire {

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
    private static int N, M;
    private static Domino[] L, D;
    private static Domino first, last;
    private static boolean found;
    private static boolean[] done;
    private static final String YES = "YES", NO = "NO";
    
    /**
     * Domino class
     * @author gouthamvidyapradhan
     *
     */
    private static class Domino
    {
    	int i, l, r; //face1, face2
    	Domino(int l, int r)
    	{
    		this.l = l;
    		this.r = r;
    	}
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
			if(N == 0) break;
			M = MyScanner.readInt();
			D = new Domino[M + 2];
			L = new Domino[M]; //list of dominos
			done = new boolean[M];
			found = false;
			first = new Domino(MyScanner.readInt(), MyScanner.readInt());
			last = new Domino(MyScanner.readInt(), MyScanner.readInt());
			for(int i = 0; i < M; i++)
			{
				Domino d = new Domino(MyScanner.readInt(), MyScanner.readInt());
				d.i = i;
				L[i] = d;
			}
			D[0] = first;
			D[M + 1] = last;
			backTrack(D, 1);
			pw.println(found ? YES : NO);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Backtrack to check if dominos fill spaces
	 * @param D
	 * @param pos
	 */
	private static void backTrack(Domino[] D, int pos)
	{
		if(pos == N + 1)
		{
			Domino l = D[pos - 1];
			if(l.r == last.l)
				found = true;
			return;
		}
		for(int i = 0; i < M; i++)
		{
			Domino d = L[i];
			if(!done[d.i])
			{
				Domino n = null;
				if(d.l == D[pos - 1].r)
					n = new Domino(d.l, d.r);
				else if(d.r == D[pos - 1].r)
					n = new Domino(d.r, d.l);
				if(n != null)
				{
					n.i = d.i;
					D[pos] = n;
					done[n.i] = true;
					backTrack(D, pos + 1);
					done[n.i] = false;
				}
			}
			if(found) break;
		}
	}
}
