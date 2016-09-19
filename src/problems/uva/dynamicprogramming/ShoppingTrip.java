package problems.uva.dynamicprogramming;

import java.io.*;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 06/07/2016.
 * Accepted 0.370 s. Interesting combination of FloydWarshall's and DP algorithm on a DAG using TSP.
 */
public class ShoppingTrip
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
         * Read integer
         *
         * @return
         * @throws Exception
         */
        public static double readDouble() throws Exception {
            try {
                if (st != null && st.hasMoreTokens()) {
                    return Double.parseDouble(st.nextToken());
                }
                String str = br.readLine();
                if (str != null && !str.trim().equals("")) {
                    st = new StringTokenizer(str);
                    return Double.parseDouble(st.nextToken());
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 1000000));
    private static int[][] G1, G2, DP;
    private static int N, M, P, T;
    private static int[] S;
    private static Map<Integer, Integer> shop = new HashMap<>();
    private static final String DONT = "Don't leave the house", SAVE = "Daniel can save $";

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        T = MyScanner.readInt();
        while(T -- > 0)
        {
            while((N = MyScanner.readInt()) == -1);
            M = MyScanner.readInt();
            G1 = new int[N + 1][N + 1];
            for (int i = 0; i < N + 1; i++)
                Arrays.fill(G1[i], Integer.MAX_VALUE);
            for (int i = 0; i < M; i++)
            {
                String line = MyScanner.readLine();
                StringTokenizer st = new StringTokenizer(line);
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                StringTokenizer st1 = new StringTokenizer(st.nextToken(), ".");
                G1[from][to] = Math.min(G1[from][to],
                        Integer.parseInt(st1.nextToken()) * 100 + Integer.parseInt(st1.nextToken()));
                G1[to][from] = G1[from][to];
            }
            P = MyScanner.readInt();
            S = new int[P + 1];
            G2 = new int[P + 1][P + 1];
            DP = new int[P + 1][(int)Math.pow(2, P + 1)];
            shop.clear();
            shop.put(0, 0);
            S[0] = 0;
            for (int i = 1; i <= P; i++)
            {
                String line = MyScanner.readLine().trim();
                StringTokenizer st = new StringTokenizer(line);
                int shopInd = Integer.parseInt(st.nextToken());
                StringTokenizer st1 = new StringTokenizer(st.nextToken(), ".");
                int distance = Integer.parseInt(st1.nextToken()) * 100 + Integer.parseInt(st1.nextToken());
                shop.put(shopInd, distance);
                S[i] = shopInd;
            }
            //run floyd-warshall's
            for (int k = 0; k < N + 1; k++)
                for (int i = 0; i < N + 1; i++)
                    for (int j = 0; j < N + 1; j++)
                        if (G1[i][k] != Integer.MAX_VALUE && G1[k][j] != Integer.MAX_VALUE)
                            G1[i][j] = Math.min(G1[i][j], G1[i][k] + G1[k][j]);

            //build a new graph, with starring index 0
            for(int i = 0; i < P + 1; i++)
            {
                for(int j = i + 1; j < P + 1; j++)
                {
                    int u = S[i];
                    int v = S[j];
                    G2[i][j] = G1[u][v];
                    G2[j][i] = G1[u][v];
                }
            }
            for(int i = 0; i < P + 1; i++)
                Arrays.fill(DP[i], Integer.MIN_VALUE);
            for(int i = 0; i < P + 1; i++)
                DP[i][(int)Math.pow(2, P + 1) - 1] = (shop.get(S[i])) - G2[i][0];
            int result = dp(0, 1);
            if(result <= 0)
                pw.println(DONT);
            else pw.println(SAVE + String.format("%.2f", (double)(result) / 100));
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    /**
     * TSP DP algorithm
     * @param pos
     * @param state
     * @return
     */
    private static int dp(int pos, int state)
    {
        if(DP[pos][state] != Integer.MIN_VALUE)
            return DP[pos][state];
        else
        {
            for(int i = 0; i < P + 1; i++)
            {
                if(((1 << i) & state) == 0)
                {
                    int newState = state | (1 << i);
                    int value = dp(i, newState);
                    DP[pos][state] = Math.max(DP[pos][state], value - G2[pos][i] + shop.get(S[pos]));
                    DP[pos][state] = Math.max(DP[pos][state], shop.get(S[pos]) - G2[pos][0]); //can go home without proceeding to the next shop
                }
            }
        }
        return DP[pos][state];
    }
}
