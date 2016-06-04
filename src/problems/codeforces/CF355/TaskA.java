package problems.codeforces.CF355;

import problems.codeforces.CF353.MyScanner;
import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        int n = in.nextInt();
        int h = in.nextInt();
        int sum = 0;
        for(int i = 0; i < n; i++)
        {
            if(in.nextInt() > h)
                sum += 2;
            else sum += 1;
        }
        out.println(sum);
    }
}
