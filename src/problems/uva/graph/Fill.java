package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Acceped 0.239 s. A bit difficult to undersntad how to transform the fill senario to a graph but once the algorithm was clear then
 * implementation was pretty simple except the code for 'fill' which was a bit tedious
 *
 */
public class Fill {

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
    /**
     * 
     * @author gouthamvidyapradhan
     * Class to store State 
     *
     */
    private static class State
    {
    	State(int s, int d)
    	{
    		this.s = s;
    		this.d = d;
    	}
    	int s; //state
    	int d; //distance
    }
    
	/**
	 * Priority queue
	 */
	static java.util.Queue<State> pq = new java.util.PriorityQueue<State>(1000, new Comparator<State>()
	{
		@Override
		public int compare(State d1, State d2) 
		{
			return (d1.d < d2.d)? -1 : ((d1.d > d2.d)? 1 : 0);
		}
	});

    private static BitSet done = new BitSet(13107200);
    private static int A, B, C, T, total, target, d;
    private static Map<Integer, Integer> min = new HashMap<>();
    private static final int constant = 255;
    private static final String BLANK = " ";
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		while((T = MyScanner.readInt()) == -1);
		while(T-- > 0)
		{
			while((A = MyScanner.readInt()) == -1);
			B = MyScanner.readInt();
			C = MyScanner.readInt();
			target = MyScanner.readInt();
			total = 0; d = 0;
			dijktras();
			pw.println(total + BLANK + d);
			done.clear(); min.clear(); pq.clear();
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Method to run dijktras to find shortest path
	 */
	private static void dijktras()
	{
		State s = new State(C, 0);
		if(s.d == target){d = target; total = 0; return;}
		min.put(s.s, 0);
		pq.add(s);
		int[] result;
		while(!pq.isEmpty())
		{
			State parent = pq.remove();
			if(done.get(parent.s)) continue;
			int state = parent.s;
			int c = state & constant;
			int b = (state >> 8) & constant;
			int a = (state >> 16) & constant;
			if(a == target || b == target || c == target)
				{d = target; total = parent.d; break;}
			if(newMin(a, d)) {total = parent.d; d = a;} //maintain a new minimum
			if(newMin(b, d)) {total = parent.d; d = b;}
			if(newMin(c, d)) {total = parent.d; d = c;}
			
			result = fill(a, b, B); //fill a to b
			setMin(result[0], result[1], c, parent.d + result[2]);

			result = fill(a, c, C); //fill a to c
			setMin(result[0], b, result[1], parent.d + result[2]);

			result = fill(b, a, A); //fill b to a
			setMin(result[1], result[0], c, parent.d + result[2]);

			result = fill(b, c, C); //fill b to c
			setMin(a, result[0], result[1], parent.d + result[2]);

			result = fill(c, a, A); //fill c to a
			setMin(result[1], b, result[0], parent.d + result[2]);

			result = fill(c, b, B); //fill c to b
			setMin(a, result[1], result[0], parent.d + result[2]);

			done.set(parent.s);
		}
	}
	
	/**
	 * Check for new minimum
	 * @param element
	 * @param d existing minimum
	 * @return true if a new minimum exists
	 */
	private static boolean newMin(int ele, int d)
	{
		if(((target - ele) > 0) && ((target - ele) < (target - d))) 
			return true;
		return false;
	}
	
	/**
	 * Fill up
	 * @param f from
	 * @param t to
	 * @param C total available capacity of 'to'
	 * @return {f capacity after fill, t capacity after fill, amount of water moved}
	 */
	private static int[] fill(int f, int t, int C)
	{
		int[] temp = new int[3];
		int available = C - t;
		if(f <= available) { temp[0] = 0; temp[1] = (t + f); temp[2] = f; }
		else { temp[0] = (f - available); temp[1] = C; temp[2] = available; }
		return temp;
	}
	/**
	 * Set new minValue
	 * @param a
	 * @param b
	 * @param c
	 * @param fill
	 */
	private static void setMin(int a, int b, int c, int fill)
	{
		int newC = (((a << 8) + b) << 8) + c;
		if(!done.get(newC))
		{
			Integer value = min.get(newC);
			if(value == null || fill < value)
			{
				min.put(newC, fill);
				pq.add(new State(newC, fill));
			}
		}
	}
}
