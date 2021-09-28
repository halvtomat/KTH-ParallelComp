package lecture9;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Arrays;

public class ArraySum {

	private static ForkJoinPool POOL = new ForkJoinPool();

	public static int sumSeq(int[] a) {
		int sum = 0;
		for(int x: a)
			sum += x;
		return sum;
	}

	public static int sum(int[] a) {
		return POOL.invoke(new sumUtil(a, 0, a.length - 1));
	}

	private static class sumUtil extends RecursiveTask<Integer> {
		private final int[] a;
		private final int low;
		private final int high;
		sumUtil(int[] a, int low, int high) {
			this.a = a;
			this.low = low;
			this.high = high;
		}

		protected Integer compute() {
			int diff = high - low;
			if(diff == 1)
				return a[high] + a[low];
			if(diff == 0)
				return a[high];
			int mid = high - diff/2;
			ForkJoinPool pool = new ForkJoinPool();
			return pool.invoke(new sumUtil(a, low, mid - 1)) + pool.invoke(new sumUtil(a, mid, high));	
		}
	}
	
	public static void main(String[] args) {
		int[] arr = new int[10000];
		Arrays.fill(arr, 10);

		long startTime = System.nanoTime();
		int res = sum(arr);
		long endtime = System.nanoTime();
		System.out.println("Parallel time:\t\t" + (endtime - startTime) + "\tns\tres: " + res);
		startTime = System.nanoTime();
		res = sumSeq(arr);
		endtime = System.nanoTime();
		System.out.println("Sequential time:\t" + (endtime - startTime) + "\t\tns\tres: " + res);
	}
}