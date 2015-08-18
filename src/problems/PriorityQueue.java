package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author gouthamvidyapradhan
 * Implementation of MIN HEAP priority queue
 *
 */
public class PriorityQueue {

	/**
	 * Initial queue size
	 */
	private static int[] a = new int[100];
	
	/**
	 * Queue rear
	 */
	private static int rear = 0;
	
	/**
	 * Main 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter number of inputs");
		String str = br.readLine();
		
		int curr = 0;
		int parent = 1;
		for(int i=1; i<=Integer.parseInt(str); i++)
		{
			a[++rear] = Integer.parseInt(br.readLine());
			curr = rear;
			parent = curr >> 1;
			while((parent > 0) && (a[curr] < a[parent]))
			{
				//exchange
				int temp = a[parent];
				a[parent] = a[curr];
				a[curr] = temp;
				curr = parent;
				parent = curr >> 1;
			}
		}
		
		System.out.println("Contents in order");
		for(int i=1; (i<a.length && a[i] !=0 ); i++)
		{
			System.out.println(a[i]);
		}
		
		System.out.println("Remove front: " + remove());
		
		for(int i=1; (i<a.length && a[i] !=0 ); i++)
		{
			System.out.println(a[i]);
		}
	}
	
	/**
	 * Remove front element from the queue
	 * @return
	 */
	private static int remove()
	{
		int top = a[1];
		a[1] = a[rear];
		a[rear] = 0;
		rear--;
		heapify(1);
		return top;
	}
	
	/**
	 * Heapify
	 * @param i
	 */
	private static void heapify(int i)
	{
		int left = i << 1;
		int right = (i << 1) + 1;
		int smallest;
		while(i<=rear)
		{
			if((left <= rear) && (a[left] < a[i]))
				smallest = left;
			else if((right <= rear) && (a[right] < a[i]))
				smallest = right;
			else break;
			
			int temp = a[i];
			a[i] = a[smallest];
			a[smallest] = temp;
			heapify(smallest);
		}
	}
}
