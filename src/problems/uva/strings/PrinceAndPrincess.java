package problems.uva.strings;

import java.io.*;
import java.util.*;

/**
 * Created by gouthamvidyapradhan on 24/10/2016.
 * Accepted 0.230 s O(N log N) LIS DP algorithm
 */
public class PrinceAndPrincess
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 100000));
    private static int max, T, N, p, q;
    private static int[] Q;
    private static Map<Integer, Integer> map = new HashMap<>();

    private static TreeSet<Lis> treeSet = new TreeSet<>(new Comparator<Lis>() {
        @Override
        public int compare(Lis o1, Lis o2) {
            return Integer.compare(o1.index, o2.index);
        }
    });

    /**
     *
     */
    static class Lis
    {
        int index, dp;
        Lis(int index, int dp)
        {
            this.index = index;
            this.dp = dp;
        }
    }
    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        T = MyScanner.readInt();
        int C = 1;
        while(T-- > 0)
        {
            N = MyScanner.readInt();
            p = MyScanner.readInt();
            q = MyScanner.readInt();
            Q = new int[q + 1];
            map.clear();
            treeSet.clear();
            for (int i = 0; i < p + 1; i ++)
                map.put(MyScanner.readInt(), i);
            for (int i = 0; i < q + 1; i ++)
            {
                Integer value = map.get(MyScanner.readInt());
                Q[i] = (value == null) ? 0 : value;
            }
            max = 0;
            int first = Q[0];
            treeSet.add(new Lis(first, 1));
            for (int i = 1; i < q + 1; i ++)
            {
                if(Q[i] == 0) continue;
                Lis lis = new Lis(Q[i], 0);
                Lis ceil = treeSet.ceiling(lis);
                if(ceil != null)
                {
                    lis.dp = ceil.dp;
                    treeSet.remove(ceil);
                    treeSet.add(lis);
                }
                else
                {
                    Lis floor = treeSet.floor(lis);
                    if(floor != null)
                        lis.dp = floor.dp + 1;
                    else lis.dp = 1;
                    treeSet.add(lis);
                }
                max = Math.max(max, lis.dp);
            }
            pw.println("Case " + C++  + ": " + max);
        }
        pw.flush(); pw.close(); MyScanner.close();
    }
}
