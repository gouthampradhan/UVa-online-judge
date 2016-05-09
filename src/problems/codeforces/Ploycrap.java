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
 * Accepted 577ms. Received runtime exception for StackOverflow Error. The reason for the error is the multiple initialization of boolean array 
 * done[N][M] to keep track of visited vertices. Array or variable initialization always eats up the stack memory space which code result in stackoverflow.
 */
public class Ploycrap {
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

    private static int[][] grid, index;
    private static boolean[][] verified;
    private static boolean found;
    private static final int R[] = {0, -1, 0, 1}; //{E, NE, N, NW, W, SW, S, SE}
    private static final int C[] = {1, 0, -1, 0};
    private static int N, M, newR, newC, e, id; //M rows, N columns
    private static long sum, K, hay;
    private static List<Node> list = new ArrayList<>();
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 500000));

    /**
     *
     */
    private static class Node
    {
        int r, c;
        Node(int r, int c)
        {
            this.r = r;
            this.c = c;
        }
    }
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        N = MyScanner.readInt();
        M = MyScanner.readInt();
        K = MyScanner.readLong();
        grid = new int[N][M];
        index = new int[N][M];
        verified = new boolean[N][M];
        sum = 0L;
        id = 0;
        found = false;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
            {
                e = MyScanner.readInt();
                if(e != 0) 
                {
                    if ((K % e) == 0) //possible
                    {
                        if ((K / e) <= M * N)
                            list.add(new Node(i, j));
                    }
                }
                grid[i][j] = e;
            }
        for(Node n : list)
        {
            if(!verified[n.r][n.c])
            {
                hay = grid[n.r][n.c];
                sum = hay;
                id++;
                if(dfs(n.r, n.c))
                {
                	found = true;
                	break;
                }
            }
        }
        if(found)
        {
            pw.println("YES");
            for (int i = 0; i < N; i++) 
            {
                for (int j = 0; j < M; j++) 
                {
                	pw.print((index[i][j] == id) ? hay : "0"); 
                    if (j != M - 1)
                        pw.print(" ");
                }
                pw.println();
            }
        }
        else
            pw.println("NO");
        pw.flush();
        pw.close();
        MyScanner.close();
    }

    /**
     * Flood fill algorithm
     * @param r
     * @param c
     * @return
     */
    private static boolean dfs(final int r, final int c)
    {
        index[r][c] = id; //mark this as finished
        if(sum == K)
        	return true;
        for(int i = 0; i < 4; i++)
        {
            newR = r+R[i]; newC = c+C[i];
            if(newR < 0 || newC < 0 || newR >= N || newC >= M || index[newR][newC] == id) continue;
            if(grid[newR][newC] >= hay)
            {
                if(grid[newR][newC] == hay)
                    verified[newR][newC] = true;
                sum += hay;
                if(dfs(newR, newC)) return true;
            }
        }
        return false;
    }
}