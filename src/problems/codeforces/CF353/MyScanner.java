package problems.codeforces.CF353;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.DoubleSummaryStatistics;
import java.util.StringTokenizer;

/**
 * Created by gouthamvidyapradhan on 16/05/2016.
 */
public class MyScanner
{
    public BufferedReader reader;

    public StringTokenizer tokenizer;

    public MyScanner(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }
    /**
     * Parse to integer
     * @param in
     * @return integer value
     */
    public int parseInt(String in)
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

}
