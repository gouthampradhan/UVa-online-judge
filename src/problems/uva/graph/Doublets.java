package problems.uva.graph;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author gouthamvidyapradhan
 * Accepted in first attempt 1.358 s. Their might be a better solution for this as 1.358 is probably a bit on the higher side. I read in discussion forum 
 * that one of the java solution was accepted in under 1 s !
 *
 */
public class Doublets {
	
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

	private static PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
	private static Map<Integer, Set<String>> dictonary = new HashMap<>();
	private static Set<String> done = new HashSet<>();
	private static Word[] q;
	private static String[] sF; //Start and finish words, the size should always be two.
	
	/**
	 * 
	 * @author gouthamvidyapradhan
	 * Word class
	 *
	 */
	private static class Word
	{
		String word;
		Word parent;
	}
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		String word, start;
		int T = 0;
		while(!(word = MyScanner.readString()).equals(""))
		{
			int key = word.length();
			Set<String> words;
			if((words = dictonary.get(key)) == null)
			{
			 	words = new HashSet<String>();
			 	dictonary.put(key, words);
			}
			words.add(word);
		}
		while((start = MyScanner.readString()) != null)
		{
			if(start.equals(""))
				while((start = MyScanner.readString()).equals(""));
			sF = new String[2];
			sF[0] = start; // Start
			sF[1] = MyScanner.readString(); //Finish
			Word destination = bfs();
			if(T++ > 0)
				pw.println(); //Print a blank line in between TC
			if(destination == null)
				pw.println("No solution.");
			else
				print(destination);
			done.clear(); //Clear
		}
		pw.flush();
		pw.close();
		MyScanner.close();
	}
	
	/**
	 * Print path recursively
	 * @param word
	 */
	private static void print(Word word)
	{
		if(word.parent != null)
			print(word.parent);
		pw.println(word.word);
	}
	
	/**
	 * Get the doublets from the dictionary (Ignore already visited vertices)
	 * @param parent
	 * @return
	 */
	private static List<String> getDoublets(String parent)
	{
		Set<String> words = dictonary.get(parent.length());
		List<String> children = new ArrayList<>();
		char[] parentChar = parent.toCharArray();
		if(words != null)
		{
			Iterator<String> ite = words.iterator();
			while(ite.hasNext())
			{
				String child = ite.next();
				if(!done.contains(child))
				{
					int count = 0;
					char[] childChar = child.toCharArray();
					for(int i=0; i<childChar.length; i++)
					{
						if(parentChar[i] != childChar[i])
						{
							if(++count > 1)
								break;
						}
					}
					if(count == 1) //if count is > 1 OR 0 then its not a child
						children.add(child);
				}
			}
		}
		return children;
	}
	/**
	 * Perform bfs
	 * @return
	 */
	private static Word bfs()
	{
		int head = 0, tail = 0;
		Word w = new Word();
		w.word = sF[0];
		w.parent = null;
		int l1 = sF[0].length();
		int l2 = sF[1].length();
		Set<String> startSet = dictonary.get(l1);
		Set<String> finishSet = dictonary.get(l2);
		if(startSet == null || finishSet == null)
			return null;
		else if(!startSet.contains(sF[0]) && !finishSet.contains(sF[1]))
		{
			w.word = "";
			return w;
		}
		else if(!finishSet.contains(sF[1]) || !startSet.contains(sF[0])) 
			return null;
		else if(sF[0].length() != sF[1].length())
			return null; //dictionary contains both but their lengths are not equal 
		else if(sF[0].equals(sF[1]))
			return w; //dictionary contains and both are equal
		
		q = new Word[26000];
		done.add(sF[0]);
		q[tail++] = w;
		while(head < tail)
		{
			Word firstWord = q[head++];
			List<String> children = getDoublets(firstWord.word);
			for(int i=0; i<children.size(); i++)
			{
				String child = String.valueOf(children.get(i));
				Word childWord = new Word();
				childWord.word = child;
				childWord.parent = firstWord;
				if(sF[1].equals(child)) //Destination reached
					return childWord;
				q[tail++] = childWord;
				done.add(child); //Mark as done
			}
 		}
		return null; //No Solution
	}
}
