package problems;

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
 * 
 * Accepted 0.52. I found it a bit complex to backtrack and print all the possible combinations but, I finally succeeded after a bit of struggle.
 * The algorithm involves recursive backtracking to print all the possible combinations.
 *
 */
public class Ordering {

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
    private static final String BLANK = " ", EMPTY = "", NO = "NO", LT = "<";
    private static int T, L, caseCount; //test case number and number of vertices
    private static List<String> zeroOrder = new ArrayList<String>();
    private static Set<String> done = new HashSet<String>();
    private static boolean possible;
    private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    
    /**
     * Main method
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception 
	{
		while((T = MyScanner.readInt()) == -1);
		caseCount = 0;
		while(T-- > 0)
		{
			String line1, line2;
			while((line1 = MyScanner.readLine()).trim().equals(EMPTY));	
			StringTokenizer input = new StringTokenizer(line1, BLANK);
			while(input.hasMoreTokens())
				vertices.add(input.nextToken());
			Collections.sort(vertices); //sort the vertices in ascending order
			L = vertices.size(); //number of vertices
			zeroOrder.addAll(vertices);
			while((line2 = MyScanner.readLine()).trim().equals(EMPTY));
			StringTokenizer edges = new StringTokenizer(line2.trim(), BLANK);
			while(edges.hasMoreTokens())
			{
				StringTokenizer nodes = new StringTokenizer(edges.nextToken(), LT);
				String from = nodes.nextToken().trim(); String to = nodes.nextToken();
				StringBuffer childern = graph.get(from);
				if(childern == null) graph.put(from, new StringBuffer(100).append(to));
				else {childern.append(to);}
				if(zeroOrder.contains(to)) {zeroOrder.remove(zeroOrder.indexOf(to));} //maintain a zero order vertices
			}
			if(caseCount++ != 0)
				pw.println();
			for(int i=0, l=zeroOrder.size(); i < l; i++) //iterate only for the zero order vertices
			{dfs(zeroOrder.get(i), EMPTY);}
			if(!possible)
			pw.println(NO);
			done.clear(); possible = false; zeroOrder.clear(); graph.clear(); vertices.clear();//reset
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
		if(new StringTokenizer(path.toString().trim(), BLANK).countTokens() == L)
		{
			pw.println(path.toString().trim());
			possible = true;
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
