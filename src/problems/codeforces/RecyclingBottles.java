package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class RecyclingBottles {


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
    private static int N;
    private static List<Point> bottles;
    private static double totalDist = 0D;
    
    private static class Point
    {
    	int x, y;
    	long distance;
    	Point(int x, int y)
    	{
    		this.x = x;
    		this.y = y;
    	}
    }
    /**
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		Point adi = new Point(MyScanner.readInt(), MyScanner.readInt());
		Point behra = new Point(MyScanner.readInt(), MyScanner.readInt());
		Point bin = new Point(MyScanner.readInt(), MyScanner.readInt());
		N = MyScanner.readInt();
		bottles = new ArrayList<Point>(N + 1);
		for(int i = 0; i < N; i++)
		{
			Point bottle = new Point(MyScanner.readInt(), MyScanner.readInt());
			bottle.distance = ((long)(bottle.x - bin.x) * (bottle.x - bin.x)) + ((long)(bottle.y - bin.y) * (bottle.y - bin.y));
			bottles.add(bottle);
		}
		
		for(int i = 0; i < N; i++)
		{
			Point b = bottles.get(i);
			long adiDist = ((long)(adi.x - b.x) * (adi.x - b.x)) + ((long)(adi.y - b.y) * (adi.y - b.y));
			long behraDist = ((long)(behra.x - b.x) * (behra.x - b.x)) + ((long)(behra.y - b.y) * (behra.y - b.y));
			if(adiDist == behraDist) continue;
			else if(adiDist < behraDist)
			{
				totalDist += Math.sqrt(adiDist) + Math.sqrt(b.distance);
				adi.x = bin.x;
				adi.y = bin.y;
				bottles.remove(i);
				break;
			}
			else
			{
				totalDist += Math.sqrt(behraDist) + Math.sqrt(b.distance);
				behra.x = bin.x;
				behra.y = bin.y;
				bottles.remove(i);
				break;
			}
		}
		
		for(Point b : bottles)
		{
			long adiDist = ((long)(adi.x - b.x) * (adi.x - b.x)) + ((long)(adi.y - b.y) * (adi.y - b.y));
			long behraDist = ((long)(behra.x - b.x) * (behra.x - b.x)) + ((long)(behra.y - b.y) * (behra.y - b.y));
			long binDist = b.distance;	
			if(adiDist <= behraDist)
			{
				totalDist += (Math.sqrt(adiDist) + Math.sqrt(binDist));
				adi.x = bin.x;
				adi.y = bin.y;
			}
			else
			{
				totalDist += (Math.sqrt(behraDist) + Math.sqrt(binDist));
				behra.x = bin.x;
				behra.y = bin.y;
			}
		}
		pw.println(totalDist);
		pw.flush();
		pw.close();
		MyScanner.close();
	}
}
