package problems.codeforces;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.982 s. Simple datastructures problem using TreeSet and Hashing.
 *
 */
public class HostnameAliases 
{
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
    private static Map<String, TreeSet<String>> HOST = new HashMap<String, TreeSet<String>>();
    private static Map<TreeSet<String>, List<String>> WEBSITE = new HashMap<TreeSet<String>, List<String>>();
    private static int N;
    private static final String HTTP = "http://";
    private static final String NULL = "", SLASH = "/", BLANK = " ";
    
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		while((N = MyScanner.readInt()) == -1);
		for(int i = 0; i < N; i++)
		{
			String line = MyScanner.readLine().trim();
			String address = line.substring(HTTP.length());
			if(address.contains(SLASH))
			{
				StringTokenizer st = new StringTokenizer(address, SLASH);
				String key = st.nextToken(); //server
				int index = address.indexOf(SLASH);
				String path = address.substring(index);
				TreeSet<String> paths = HOST.get(HTTP + key);
				if(paths == null)
					paths = new TreeSet<>();
				paths.add(path);
				HOST.put(HTTP + key, paths);
			}
			else
			{
				TreeSet<String> paths = HOST.get(line);
				if(paths == null)
					paths = new TreeSet<String>();
				paths.add(NULL);
				HOST.put(line, paths);
			}
		}
		groupHosts();
		Set<TreeSet<String>> keys = WEBSITE.keySet();
		int count = 0;
		for(TreeSet<String> website : keys)
		{
			List<String> list = WEBSITE.get(website);
			if(list.size() > 1)
				count++;
		}
		pw.println(count);
		for(TreeSet<String> website : keys)
		{
			List<String> list = WEBSITE.get(website);
			if(list.size() > 1)
			{
				for(int j = 0, l = list.size(); j < l; j++)
				{
					if(j > 0)
						pw.print(BLANK);
					pw.print(list.get(j));
				}
				pw.flush();
				pw.println();
			}
		}
		pw.flush(); pw.close(); MyScanner.close();
	}
	
	/**
	 * Group hosts
	 */
	private static void groupHosts()
	{
		for(String s : HOST.keySet())
		{
			TreeSet<String> paths = HOST.get(s);
			List<String> hosts = WEBSITE.get(paths);
			if(hosts == null)
				hosts = new ArrayList<String>();
			hosts.add(s);
			WEBSITE.put(paths, hosts);
		}
	}
}
