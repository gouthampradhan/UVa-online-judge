package problems.uva.graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 
 * @author gouthamvidyapradhan
 * Customized input scanner class
 *
 */
public class MyScanner 
{
	/**
	 * Buffered reader
	 */
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	StringTokenizer st;
	
	/**
	 * Read integer
	 * @return
	 * @throws Exception
	 */
	public int readInt() throws Exception
	{
		if(st != null && st.hasMoreTokens())
		{
			return Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}
	
	/**
	 * Read byte
	 * @return
	 * @throws Exception
	 */
	public byte readByte() throws Exception
	{
		if(st != null && st.hasMoreTokens())
		{
			return Byte.parseByte(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		return Byte.parseByte(st.nextToken());
	}
	
	/**
	 * Read string
	 * @return
	 * @throws Exception
	 */
	public String readString() throws Exception
	{
		if(st != null && st.hasMoreTokens())
		{
			return st.nextToken();
		}
		return new StringTokenizer(br.readLine()).nextToken();
	}
}
