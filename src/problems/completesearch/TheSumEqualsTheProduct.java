package problems.completesearch;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.233 s. Brute-force O (N^2) algorithm similar to @see GroceryStore
 * Use this problem as an example on how to avoid precision point errors. (a.b * 10) / 10
 *
 */
public class TheSumEqualsTheProduct {

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
         * Ready
         * @return
         * @throws Exception
         */
        public static boolean ready() throws Exception
        {
            return br.ready();
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

            // Build the number
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

    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out, 500000)); //set the buffer too high.
    private static int l, h; //low and high
    private static List<Product> list;
    private static final String PLUS = " + ", PRODUCT = " * ", EQUAL = " = ";
    private static final int NUM = 10000;
    
    /**
     * Product class
     * @author gouthamvidyapradhan
     *
     */
    private static class Product
    {
    	double a, b, c, p;
    	Product(double a, double b, double c, double p)
    	{
    		this.a = a;
    		this.b = b;
    		this.c = c;
    		this.p = p;
    	}
    }
    /**
     * 
     * @param args
     */
	public static void main(String[] args) throws Exception 
	{
		while(true)
		{
			String line = MyScanner.readLine();
			if(line == null) break;
			StringTokenizer st = new StringTokenizer(line);
			double low = Double.parseDouble(st.nextToken());
			double high = Double.parseDouble(st.nextToken());
			l = (int)(low * 10000);
			h = (int)(high * 10000);
			list = new ArrayList<>();
			for(int i = 1; (i * i) <= h; i++)
			{
				for(int j = i; (i * j) <= h; j++)
				{
					//find d using formula 
					//(a * b * c) / 10000 = (a + b + c + d)
					int prod = i * j;
					int sum = i + j;
					int den = (prod - NUM); //denominator
					if(den <= 0) continue;
					int num = sum * NUM; //numerator
					if((num % den) != 0) continue;
					int q = num / den; //quotient
					if(q < j) continue;
					int v1 = sum + q;
					int v2 = (prod * q) / NUM;
					if(v1 > 25600 || v2 > 25600) continue;
					if(v1 == v2)
					{
						if((v1 >= l/100) && (v1 <= h/100))
							list.add(new Product((double)i/100, (double)j/100, (double)q/100, (double)v1/100));
					}
				}
			}
			//sort
			Collections.sort(list, new Comparator<Product>() 
			{
				@Override
				public int compare(Product o1, Product o2) 
				{
					return (o1.p < o2.p) ? -1 : (o1.p > o2.p)? 1 : 
						(o1.a < o2.a) ? -1 : (o1.a > o2.a) ? 1 : 
							(o1.b < o2.b) ? -1 : (o1.b > o2.b) ? 1 : (o1.c < o2.c) ? -1 : (o1.c > o2.c) ? 1 : 0;
				}
			});
			for(Product p : list)
				pw.println(String.format("%.2f", p.p) + EQUAL + String.format("%.2f", p.a) + PLUS + String.format("%.2f", p.b)
						+ PLUS + String.format("%.2f", p.c) + EQUAL  + String.format("%.2f", p.a) + PRODUCT + String.format("%.2f", p.b)
						+ PRODUCT + String.format("%.2f", p.c));

		}
		pw.flush(); pw.close();
	}
}
