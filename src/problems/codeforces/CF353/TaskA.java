package problems.codeforces.CF353;

import problems.codeforces.CF353.MyScanner;
import java.io.PrintWriter;

public class TaskA
{
    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        if(c == 0)
        {
            if(a == b) out.println("YES");
            else out.println("NO");
        }
        else
        {
            long d = (long)(c * -1);
            long diff = ((long)b - d - a);
            if(diff == 0) out.println("NO");
            else
            {
                long q = diff / c;
                if(q < 0) out.println("NO");
                else
                {
                    long mod = diff % c;
                    if(mod == 0) out.println("YES");
                    else out.println("NO");
                }
            }
        }
    }
}
