package problems.dynamicprogramming;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 30/05/2016.
 * Accepted 0.280s. Simple LIS and LDS DP algorithm.
 */
public class MurciasSkyline
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
    private static int T, N, mil, miw, mdl, mdw;
    private static int[] L, W;
    private static Size[][] DP;

    /**
     *
     */
    private static class Size
    {
        int l, w;
        Size(int l, int w)
        {
            this.l = l;
            this.w = w;
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
        int count = 0;
        while(T-- > 0)
        {
            while((N = MyScanner.readInt()) == -1);
            L = new int[N]; W = new int[N];
            DP = new Size[N][2];
            mil = Integer.MIN_VALUE;
            miw = Integer.MIN_VALUE;

            mdl = Integer.MIN_VALUE;
            mdw = Integer.MIN_VALUE;
            for(int i = 0; i < N; i++)
                L[i] = MyScanner.readInt();
            for(int i = 0; i < N; i++)
                W[i] = MyScanner.readInt();

            LIS(0);
            LDS(0);
            if(miw >= mdw) pw.println("Case " + ++count + ". " + "Increasing (" + miw + "). " + "Decreasing (" + mdw + ").");
            else pw.println("Case " + ++count + ". " + "Decreasing (" + mdw + "). " + "Increasing (" + miw + ").");
        }
        pw.flush(); pw.close(); MyScanner.close();
    }

    private static Size LIS(int n)
    {
        if(DP[n][0] != null) return DP[n][0];
        for (int i = n; i < N; i++)
        {
            if(DP[i][0] != null) continue;
            for (int j = i + 1; j < N; j++)
            {
                if(L[j] > L[i])
                {
                    Size s = LIS(j);
                    Size newS = new Size(s.l + 1, s.w + W[i]);
                    if(DP[i][0] == null)
                        DP[i][0] = newS;
                    else
                    {
                        Size oldS = DP[i][0];
                        if(newS.w > oldS.w)
                            DP[i][0] = newS;
                        else if(newS.w == oldS.w)
                            if(newS.l > oldS.l)
                                DP[i][0] = newS;
                    }
                }
            }
            DP[i][0] = (DP[i][0] == null) ? new Size(1, W[i]) : DP[i][0];
            if(DP[i][0].w > miw)
            {
                miw = DP[i][0].w;
                mil = DP[i][0].l;
            }
            else if(DP[i][0].w == miw)
            {
                if(DP[i][0].l > mil)
                {
                    miw = DP[i][0].w;
                    mil = DP[i][0].l;
                }
            }
        }
        return DP[n][0];
    }

    private static Size LDS(int n)
    {
        if(DP[n][1] != null) return DP[n][1];
        for (int i = n; i < N; i++)
        {
            if(DP[i][1] != null) continue;
            for (int j = i + 1; j < N; j++)
            {
                if(L[j] < L[i])
                {
                    Size s = LDS(j);
                    Size newS = new Size(s.l + 1, s.w + W[i]);
                    if(DP[i][1] == null)
                        DP[i][1] = newS;
                    else
                    {
                        Size oldS = DP[i][1];
                        if(newS.w > oldS.w)
                            DP[i][1] = newS;
                        else if(newS.w == oldS.w)
                            if(newS.l > oldS.l)
                                DP[i][1] = newS;
                    }
                }
            }
            DP[i][1] = (DP[i][1] == null) ? new Size(1, W[i]) : DP[i][1];
            if(DP[i][1].w > mdw)
            {
                mdw = DP[i][1].w;
                mdl = DP[i][1].l;
            }
            else if(DP[i][1].w == mdw)
            {
                if(DP[i][1].l > mdl)
                {
                    mdw = DP[i][1].w;
                    mdl = DP[i][1].l;
                }
            }
        }
        return DP[n][1];
    }

}
