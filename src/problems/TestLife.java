package problems;

import java.util.Scanner;
/**
 * SPOJ 
 * @author gouthamvidyapradhan
 *
 */
public class TestLife {

	public static void main(String[] args) throws java.lang.Exception 
	{
		Scanner scan = new Scanner(System.in);
		String line;
		while ((line = scan.nextLine()) != null && !line.equals("42")) 
		{
			System.out.println(line);
		}
		scan.close();
	}
}
