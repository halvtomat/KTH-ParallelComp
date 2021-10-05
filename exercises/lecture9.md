# Exercises lecture 9

## Exercise 1
```java
public class ArraySum {

private static ForkJoinPool POOL = new ForkJoinPool();

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
```
According to my own tests, this parallel version is 30 000 times slower than a sequential version and uses vast amounts more memory (on my computer it runs out of memory when summing arrays larger than 10 000).

## Exercise 2

The following could happen:

	q0.size < q1.size --> qMin = q0
	Thread goes on vacation for a while...
	q0.size >= q1.size --> qMax = q0

Nothing will break because of this but the queues will not be balanced either.

To solve this, the locks would have to be acquired before setting qMin and qMax.

## Exercise 3

### 1
(I assume the question is wrong and it is supposed to say line *17*, not 15).
If bottom were not declared as volatile a possibility exists where two threads would read the same bottom value and therefore return the same task twice.

### 2
We want to reset the bottom field to zero as early as possible to avoid costly *compareAndSwap* calls.
The earliest the reset can be done safely should be as soon as we have read the bottom and top values.
There is nothing stopping the queue from trying to fill beyond the capacity of the queue. That would overflow the queue.
