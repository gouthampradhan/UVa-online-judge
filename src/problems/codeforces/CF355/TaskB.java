package problems.codeforces.CF355;

import problems.codeforces.CF353.MyScanner;
import java.io.PrintWriter;

public class TaskB
{
    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        int n = in.nextInt();
        int h = in.nextInt();
        int k = in.nextInt();
        long s = 0;
        long sum = 0;
        int[] p = new int[n];
        for(int i = 0; i < n; i++)
        {
            p[i] = in.nextInt();
        }
        for(int i = 0; i < n; i++)
        {
            sum += p[i];
            if(i + 1 < n)
            {
                if(sum + p[i + 1] > h)
                {
                    s += (sum / k);
                    if(((sum % k) + p[i + 1]) > h)
                    {
                        s += 1;
                        sum = 0;
                    }
                    else
                    {
                        sum = (sum % k);
                    }
                }
            }
        }
        if(sum > 0)
        {
            s += (sum / k);
            if((sum % k) > 0)
                s += 1;
        }
        out.println(s);
    }
}
