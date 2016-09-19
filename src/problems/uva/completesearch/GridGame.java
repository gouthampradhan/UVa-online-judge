package problems.uva.completesearch;

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
 * Accepted 0.155 s. 
 * My Algorithm O(N!) solution to generate all the possible permutations of columns. Its important to notice that permutations of columns are necessary and
 * not rows !.
 *
 */
public class GridGame {

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
    
    /**
     * Permutation class
     * @author gouthamvidyapradhan
     *
     */
    private static class Permutation
    {
    	/**
    	 * Array of elements
    	 */
    	private int[] A;
    	
    	/**
    	 * Number of elements
    	 */
    	private int N, curIndex, permCount;
    	
    	/**
    	 * All permutations
    	 */
    	private List<List<Integer>> permutations;

    	/**
    	 * Constructor
    	 * @param A
    	 */
    	Permutation(int[] A)
    	{
    		this.A = A;
    		N = A.length;
    		curIndex = 0;
    		permutations = new ArrayList<>();
    		permute(0);
    		permCount = permutations.size();
    	}
    	
    	/**
    	 * Has more permutations
    	 * @return
    	 */
    	public boolean hasNext()
    	{
    		return (curIndex < permCount);
    	}
    	/**
    	 * Return next permutation
    	 * @return
    	 */
    	public List<Integer> nextPermutation()
    	{
    		return (curIndex < permCount) ? permutations.get(curIndex++) : null;
    	}
    	/**
    	 * Calculate all the possible permutation
    	 * @param pos
    	 */
    	private void permute(int pos)
    	{
    		if(pos == N - 1)
    		{
    			
    			List<Integer> temp = new ArrayList<>();
    			for(int i : A)
    				temp.add(i);
    			permutations.add(temp);
    			return;
    		}
    		for(int i = pos; i < N; i++)
    		{
    			swap(i, pos); //swap
    			permute(pos + 1);
    			swap(i, pos); //restore
    		}
    	}
    	
    	/**
    	 * Swap given positions 
    	 * @param f from
    	 * @param t to
    	 */
    	private void swap(int f, int t)
    	{
			int temp = A[f];
			A[f] = A[t];
			A[t] = temp;
    	}
    }
    
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000)); //set the buffer too high.
    private static int N, T, min;
    private static int[][] grid;

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		T = MyScanner.readInt();
		while(T-- > 0)
		{
			N = MyScanner.readInt();
			grid = new int[N][N];
			for(int i = 0; i < N; i++)
				for(int j = 0; j < N; j++)
					grid[i][j] = MyScanner.readInt();
			int[] A = new int[N];
			min = Integer.MAX_VALUE;
			for(int i = 0; i < N; i++)
				A[i] = i;
			Permutation perm = new Permutation(A);
			int sum, i;
			while(perm.hasNext())
			{
				List<Integer> p = perm.nextPermutation();
				sum = 0; i = 0;
				for(int c : p)
					sum += grid[i++][c];
				min = Math.min(sum, min);
			}
			pw.println(min);
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
}
