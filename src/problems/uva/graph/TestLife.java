package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
/**
 * SPOJ 
 * @author gouthamvidyapradhan
 *
 */
public class TestLife {

	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));

//	static class MyNewScanner
//    {
//    	private static BufferedInputStream buff = new BufferedInputStream(System.in);
//    	
//    	public static String readLine() 
//    	{
//    	    StringBuffer response = new StringBuffer();
//    	    try 
//    	    {
//    	    	int in = 0;
//       	    	char inChar;
//       	    	while(((in = buff.read()) != -1) && (inChar = (char)in) != '\n' && (inChar != '\r'))
//       	    	{
//       	    		response.append(inChar);
//       	    	}
//       	    	return response.toString();
//    	    }
//    	    catch (IOException e) 
//    	    {
//    	      System.out.println("Exception: " + e.getMessage());
//    	      return null;
//    	    }
//    	}
//    	
//    	public static void close() throws IOException
//    	{
//    		buff.close();
//    	}
//    }

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
		 * Read string
		 * 
		 * @return
		 * @throws Exception
		 */
		public static String readString() throws Exception {
			if (st != null && st.hasMoreTokens()) {
				return st.nextToken();
			}
			String str = br.readLine();
			if (str != null) {
				if (str.trim().equals(""))
					return "";
				st = new StringTokenizer(str);
				return st.nextToken();
			}
			return null;
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

	public static void main(String[] args) throws java.lang.Exception 
	{
		String line;
		while((line = MyScanner.readString()) != null && !line.equals("42"))
			pw.println(line);
		pw.flush();
		pw.close();
		MyScanner.close();
	}
}
