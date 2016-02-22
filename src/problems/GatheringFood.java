package problems;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.165s. Interesting problem but, a bit complex to understand how to formulate the dp algorithm. After quite a bit of struggle was able to get through.
 * But, there are better solutions in java which was accepted in 0.06 seconds. So, definitely there is a better algorithm.
 * My algorithm uses a BFS to find the shortest path and then uses this shortest path value as K and runs Convert Graph to DAG algorithm using DP
 *
 */
public class GatheringFood {

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
    private static char[][] grid; //max is 10
    private static int[] min, count, q;
    private static BitSet done = new BitSet();
    private static boolean[] visited;
    private static char[] collect;
    private static int N, K, S, caseCount;
    private static char D;
    private static Set<Character> collectFood = new HashSet<Character>();
    private static final String CASE = "Case ", COLON = ": ", BLANK = " ", IMPOSSIBLE = "Impossible";
    private static final char EMPTY = '.';
    private static final int R[] = {1, -1, 0, 0};
    private static final int C[] = {0, 0, 1, -1};
    private static final int MOD = 20437;
    private static final char[] FOOD = {'A','B','C','D','E','F','G','H','I','J',
    	'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) throws Exception
	{
		caseCount = 0;
		while(true)
		{
			while((N = MyScanner.readInt()) == -1);
			if(N == 0) break;
			grid = new char[N][N];
			int dest = -1, temp;
			count = new int[5602586]; visited = new boolean[5602586]; //699546
			min = new int[200]; q = new int[200]; //180
			collect = new char[6000]; //5470
			for(int i = 0; i<N; i++)
			{
				String line = MyScanner.readLine();
				if(line.contains("A"))
					S = ((i << 4) + line.indexOf("A")); //initial start
				grid[i] = line.toCharArray();
				for(char e : grid[i])
				{
					temp = Arrays.binarySearch(FOOD, e);
					dest = Math.max(temp, dest); //check for the final destination alphabet
				}
			}
			D = FOOD[dest];
			K = bfs(); //find K
			pw.print(CASE + ++caseCount + COLON);
			if(K == -1)
				pw.println(IMPOSSIBLE);
			else if(K == 0)
				pw.println(K + BLANK + 1);
			else
				pw.println(K + BLANK + dp(((S >> 4) & 15), (S & 15), K, 0, 0));
			collectFood.clear(); done.clear();
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Perform bfs to find the shortest distance
	 * @return
	 */
	private static int bfs()
	{
		int col = S & 15;
		int row = (S >> 4) & 15;
		if(grid[row][col] == D) return 0;//source == destination
		int head = 0; int tail = 0;
		q[tail++] = S;
		done.set(S); //mark start as finished
		collectFood.add('A');
		int nextDest = 1; //initial destination is B (index of ALPHABET)
		start:
		while(head < tail)
		{
			int first = q[head++];
			int r = (first >> 4) & 15;
			int c = first & 15;
			for(int i = 0; i < 4; i++)
			{
				int nr = r + R[i];
				int nc = c + C[i];
				if(nr >= N || nc >= N || nr < 0 || 
						nc < 0 || (grid[nr][nc] != EMPTY 
						&& grid[nr][nc] != FOOD[nextDest]
						&& !collectFood.contains(grid[nr][nc]))) continue;
				int child = (nr << 4) + nc;
				if(!done.get(child))
				{
					min[child] = min[first] + 1;
					if(grid[nr][nc] == D) //has reached final destination
						return min[child];
					if(grid[nr][nc] == FOOD[nextDest])
					{
						collectFood.add(grid[nr][nc]);
						nextDest++; //pick next destination
						head = 0; tail = 0; //reset head and tail
						done.clear(); //clear visited vertices
						q[tail++] = child; //push to queue
						continue start;
					}
					q[tail++] = child; //push to queue
					done.set(child); //mark this as finished.
				}
			}
		}
		return -1; 
	}
	
	/**
	 * Dp algorithm to find the total number of paths
	 * @param r Row
	 * @param c Column
	 * @param k Shortest path
	 * @param s Source index
	 * @param d Destination index
	 * @return Total number of shortest paths
	 */
	private static int dp(int r, int c, int k, int s, int d)
	{
		if(k < 0) return 0;
		if(grid[r][c] == D)
			return 1; //final destination found 
		if(grid[r][c] == FOOD[d])
		{
			collect[(((r << 4) + c) << 5) + s] = FOOD[d]; //collect food
			grid[r][c] = EMPTY; //mark this as empty
			s = d; d++; //mark the destination as the new source and pick the next destination
		}
		int encode = (((((r << 4) + c) << 10) + k) << 5) + s;
		if(visited[encode]) return count[encode];
		visited[encode] = true; 
		for(int i = 0; i < 4; i++)
		{
			int nr = r + R[i];
			int nc = c + C[i];
			if(nr >= N || nc >= N || nr < 0 || 
					nc < 0 || (grid[nr][nc] != EMPTY && 
					grid[nr][nc] != FOOD[d])) continue;
			int temp = dp(nr, nc, k - 1, s, d);
			count[encode] = (count[encode] + temp) % MOD;
			if(collect[(((nr << 4) + nc) << 5) + s] == FOOD[d])
				{grid[nr][nc] = FOOD[d]; collect[(((nr << 4) + nc) << 5) + s] = ' ';} //replace the collected food
		}
		return count[encode];
	}
}
