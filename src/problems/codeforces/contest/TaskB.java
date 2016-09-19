package problems.codeforces.contest;

import problems.codeforces.DifferentIsGood;
import problems.codeforces.contest.MyScanner;
import java.io.PrintWriter;

public class TaskB
{

    public void solve(int testNumber, MyScanner in, PrintWriter out)
    {
        int n = in.nextInt();
        String line = in.next();
        int count = 0, next = 0;
        boolean found = true;
        if(line.length() > 26)
            out.println(-1);
        else
        {
            for(int i = 0; i < n - 1; i++)
            {
                char c = line.charAt(i);
                if(line.substring(i + 1, n).contains(String.valueOf(c)))
                    count++;
            }
            out.println(count);
        }
    }
}
