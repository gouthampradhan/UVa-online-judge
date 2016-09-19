package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted 0.168 s. But, runtime is much slower compared to @see Ordering.  
 *
 */
public class FollowingOrders {

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
        private static int parseInt(String in)
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
    
    private static Map<String, StringBuffer> graph = new HashMap<String, StringBuffer>();
    private static List<String> vertices = new ArrayList<String>();
    private static final String BLANK = " ", EMPTY = "";
    private static int L, caseCount; //test case number and number of vertices
    private static List<String> zeroOrder = new ArrayList<String>();
    private static Set<String> done = new HashSet<String>();
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		caseCount = 0; //case counter
		while(true)
		{
			String line1, line2;
			line1 = MyScanner.readLine();
			if(line1 == null) break;
			StringTokenizer input = new StringTokenizer(line1, BLANK);
			while(input.hasMoreTokens())
				vertices.add(input.nextToken());
			Collections.sort(vertices); //sort the vertices in ascending order
			L = vertices.size(); //number of vertices
			zeroOrder.addAll(vertices);
			line2 = MyScanner.readLine();
			StringTokenizer edges = new StringTokenizer(line2.trim(), BLANK);
			while(edges.hasMoreTokens())
			{
				String from = edges.nextToken(); String to = edges.nextToken();
				StringBuffer childern = graph.get(from);
				if(childern == null) graph.put(from, new StringBuffer(100).append(to));
				else {childern.append(to);}
				if(zeroOrder.contains(to)) {zeroOrder.remove(zeroOrder.indexOf(to));} //maintain a zero order vertices
			}
			if(caseCount++ != 0)
				pw.println();
			for(int i=0, l=zeroOrder.size(); i < l; i++) //iterate only for the zero order vertices
			{dfs(zeroOrder.get(i), EMPTY);}
			done.clear(); zeroOrder.clear(); graph.clear(); vertices.clear();//reset
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * 
	 * @param node
	 * @param result
	 */
	private static void dfs(String node, String path)
	{
		done.add(node); //mark this as done.
		path += BLANK; path += node;
		StringTokenizer nodeLst;
		if((nodeLst = new StringTokenizer(path.toString().trim(), BLANK)).countTokens() == L)
		{
			while(nodeLst.hasMoreTokens())
				pw.print(nodeLst.nextToken());
			pw.println();
		}
		for(int i=0; i<L ;i++)
		{
			String s = String.valueOf(vertices.get(i));
			if(done.contains(s)) continue;
			StringBuffer children = graph.get(s);
			boolean valid = true;
			if(children != null)
			{
				for(int j=0, l = children.length(); j<l; j++)
					if(path.indexOf((String.valueOf(children.charAt(j)))) >= 0) { valid = false; break;}
			}
			if(valid){dfs(s, path);}
		}
		done.remove(node);
	}
}
