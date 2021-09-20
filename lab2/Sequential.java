package lab2;

import java.util.concurrent.ThreadLocalRandom;

public class Sequential {

	public void quicksort(int []arr, int low, int high) {
		if(low < high) {
			int mid = Common.partition(arr, low, high);
			quicksort(arr, low, mid - 1);
			quicksort(arr, mid + 1, high);
		}
	}
	public static long test(int arrLength) {
		int[] arr = new int[arrLength];
        for(int i = 0; i < arrLength; i++)
            arr[i] = ThreadLocalRandom.current().nextInt(1001);
        long startTime = System.nanoTime();
        new Sequential().quicksort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        return endTime - startTime;
	}	
	public static void main(String[] args) {
		int runs = 10;
		int arrLength = 10000;
		long duration = 0;
		if(args.length > 0)
			arrLength = Integer.parseInt(args[0]);
		if(args.length > 1)
			runs = Integer.parseInt(args[1]);
		for(int i = 0; i < runs; i++) 
			duration += test(arrLength);
		duration /= runs;
		System.out.println("average execution time: " + duration/1000000 + " ms");
    }
}
