package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BussesBetweenCities {

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
                if (str != null && !str.trim().equals("")) {
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
    private static List<Time> timeDep = new ArrayList<>();
    private static List<Time> timeArr = new ArrayList<>();
    private static int count = 0;
    
    private static class Time
    {
    	int h, m;
    	Time(int h, int m)
    	{
    		this.h = h;
    		this.m = m;
    	}
    }
    
	public static void main(String[] args) throws Exception 
	{
		int a = MyScanner.readInt();
		int ta = MyScanner.readInt();
		
		int b = MyScanner.readInt();
		int tb = MyScanner.readInt();
		
		String line = MyScanner.readLine();
		StringTokenizer st = new StringTokenizer(line, ":");
		Time aToBDep = new Time(MyScanner.parseInt(st.nextToken().trim()), MyScanner.parseInt(st.nextToken().trim()));
		Time aToBArr = new Time(aToBDep.h + ((aToBDep.m + ta) / 60), ((aToBDep.m + ta) % 60));
		
		Time tDep = new Time(5, 0);
		timeDep.add(tDep);
		Time tArr = new Time(5 + (0 + tb / 60), (0 + tb % 60));
		timeArr.add(tArr);
		
		for(int i = 1; ; i++)
		{
			Time time =  timeDep.get(i - 1);
			int newMin = (time.m + b) % 60;
			int newHr = time.h + (time.m + b) / 60;
			if(newHr > 23) break;

			Time newTime = new Time(newHr, newMin);
			timeDep.add(newTime);
			
			Time nextTime = timeDep.get(i);
			int newArrMin = (nextTime.m + tb) % 60;
			int newArrHr =  nextTime.h + (nextTime.m + tb) / 60;
			Time newArrTime = new Time(newArrHr, newArrMin);
			timeArr.add(newArrTime);
		}
		
		for(int i = 0, l = timeDep.size(); i < l; i++)
		{
			Time bToADep = timeDep.get(i);
			Time bToAArr = timeArr.get(i);
			if(((bToAArr.h > aToBDep.h) || (bToAArr.h == aToBDep.h && bToAArr.m > aToBDep.m))
					&& (bToADep.h < aToBArr.h || (bToADep.h == aToBArr.h && bToADep.m < aToBArr.m)))
					{
						count++;
					}
		}
		pw.println(count);
		pw.flush();
		pw.close();
		MyScanner.close();
	}
}
