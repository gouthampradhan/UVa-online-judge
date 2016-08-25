package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DifferentIsGood {


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
    private static final String STR = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		int n = MyScanner.readInt();
		String line = MyScanner.readLine();
		int count = 0, next = 0;
		boolean found = true;
		StringBuilder lineB = new StringBuilder(line);
		if(lineB.length() > 26)
			pw.println(-1);
		else
		{
			for(int i = 0; i < n; i++)
			{
				char a = lineB.charAt(i);
				String temp1 = lineB.substring(0, i);
				String temp2 = "";
				if(i <= n - 1)
				{
					temp2 = lineB.substring(i + 1, n);
				}
				String tempStr = temp1 + temp2;
				if(tempStr.contains(String.valueOf(a)))
				{
					for(; next <= 26; next++)
					{
						if(next == 26)
						{
							found = false;
							break;
						}
						char c = STR.charAt(next);
						if(!tempStr.contains(String.valueOf(c)))
						{
							lineB.setCharAt(i, c);
							//line = temp1 + String.valueOf(c) + temp2;
							count++;
							break;
						}
					}
				}
			}
			if(found)
				pw.println(count);
			else
				pw.println(-1);
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}

}
