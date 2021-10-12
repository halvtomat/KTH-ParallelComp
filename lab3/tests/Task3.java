package tests;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import skiplists.LockFreeConcurrentSkipListSet;

public class Task3 {

	private static ForkJoinPool POOL;
	private static int opDist[] = {80, 10, 10};
	private static LockFreeConcurrentSkipListSet<Integer> list = new LockFreeConcurrentSkipListSet<Integer>();
	private static boolean verbose = true;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
		int threadCount = Runtime.getRuntime().availableProcessors();
		if(args.length > 0)
			threadCount = Integer.parseInt(args[0]);
		if(args.length > 3){
			opDist[0] = Integer.parseInt(args[1]); // contains
			opDist[1] = Integer.parseInt(args[2]); // add
			opDist[2] = Integer.parseInt(args[3]); // remove
		}
		if(args.length > 4)
			verbose = false;

		populate();
		POOL = new ForkJoinPool(threadCount);

		int ops[] = new int[Constants.OP_COUNT];

		test(Constants.OP_COUNT, ops);
	}

	private static void populate() {
        int uniArray[] = new int[Constants.INITIAL_POPULATION];

        for(int i = 0; i < Constants.INITIAL_POPULATION; i++) {
            int uniformlyRandom = ThreadLocalRandom.current().nextInt(0, Constants.INITIAL_POPULATION);
            uniArray[i] = uniformlyRandom;
        }
		if(verbose)
        	System.out.println("numbers generated");
        long start = System.nanoTime();
        Arrays.stream(uniArray).parallel().forEach(random -> list.add(random));
        long end = System.nanoTime();
		if(verbose)
    	    System.out.println("time to populate = " + (end-start)/1000000 + "ms");
	}

	private static void test(int opCount, int ops[]) throws InterruptedException, ExecutionException {
		long totalTime = 0;
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < opCount; j++)
				ops[j] = ThreadLocalRandom.current().nextInt(0, 100);
			long start = System.nanoTime();
			POOL.submit(() ->
				Arrays.stream(ops).parallel().forEach(op -> performOp(op))
			).get();
			long end = System.nanoTime();
			if(i >= 2)
				totalTime += (end-start)/1000000;
		}
		if(verbose)
			System.out.println("average time to operate = " + totalTime/10 + "ms");
		else
			System.out.println(totalTime/10);
	}

	private static void performOp(int op) {
		int x = ThreadLocalRandom.current().nextInt(0, Constants.MAX_VALUE);
		if(op < opDist[0])
			list.contains(x);
		else if(op < opDist[1]+opDist[0])
			list.add(x);
		else if(op < opDist[2]+opDist[1]+opDist[0])
			list.remove(x);
	} 
}