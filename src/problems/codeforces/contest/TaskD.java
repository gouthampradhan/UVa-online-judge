package problems.codeforces.contest;

import java.io.PrintWriter;

public class TaskD
{
    private static int[] T;

    private static int N, K;

    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        N = in.nextInt();
        K = in.nextInt();
        T = new int[N];
        for(int i = 0; i < N; i++)
            T[i] = in.nextInt();
        int low = 0, high = 0;
        int l = 0, h = (int)1e9 + 1, m;
        while(l <= h)
        {
            m = (l + h) / 2;
            if(checkLow(m))
            {
                low = m;
                l = m + 1;
            }
            else h = m - 1;
        }
        l = 0; h = (int)1e9 + 1;
        while(l <= h)
        {
            m = (l + h) / 2;
            if(checkHigh(m))
            {
                high = m;
                h = m - 1;
            }
            else l = m + 1;
        }
        if(high > low)
            out.println(high - low);
        else
        {
            //given K is very high. So check if we can share the coins completely to maximum share possible
            long temp = 0;
            for(int i = 0; i < N; i++)
                temp += T[i];
            out.println(((temp % N) == 0) ? 0 : 1);
        }
    }

    /**
     * Check low possible
     * @param p
     * @return
     */
    private static boolean checkLow(int p)
    {
        long sum = 0;
        for(int i = 0; i < N; i++)
        {
            if(p > T[i])
                sum += p - T[i];
        }
        return (sum <= K);
    }

    /**
     * Check high possible
     * @param p
     * @return
     */
    private static boolean checkHigh(int p)
    {
        long sum = 0;
        for(int i = 0; i < N; i++)
        {
            if(T[i] > p)
                sum += T[i] - p;
        }
        return (sum <= K);
    }
}
