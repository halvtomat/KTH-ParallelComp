package tests;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import skiplists.LockFreeConcurrentSkipListSet;

public class Task2 {
    public static void main(String[] args) {
        LockFreeConcurrentSkipListSet<Integer> uniformlyDistributed = new LockFreeConcurrentSkipListSet<Integer>();
        LockFreeConcurrentSkipListSet<Integer> normallyDistributed = new LockFreeConcurrentSkipListSet<Integer>();
        int uniArray[] = new int[Constants.INITIAL_POPULATION];
        int normArray[] = new int[Constants.INITIAL_POPULATION];
        for(int i = 0; i < Constants.INITIAL_POPULATION; i++) {
            int uniformlyRandom = ThreadLocalRandom.current().nextInt(0, Constants.MAX_VALUE);
            int normallyRandom = (int) ((ThreadLocalRandom.current().nextGaussian() * Constants.INITIAL_POPULATION/6) + Constants.INITIAL_POPULATION/2);
            uniArray[i] = uniformlyRandom;
            normArray[i] = normallyRandom;
        }
        System.out.println("numbers generated");
        long start = System.nanoTime();
        Arrays.stream(uniArray).parallel().forEach(random -> uniformlyDistributed.add(random));
        Arrays.stream(normArray).parallel().forEach(random -> normallyDistributed.add(random));
        long end = System.nanoTime();
        System.out.println("time to populate = " + (end-start)/10000 + "ms");
        System.out.println("lists populated");
        Integer value = uniformlyDistributed.getNextValue();
        long sum = 0;
        int count = 0;
        while(value != null){
            sum += value;
            count++;
            value = uniformlyDistributed.getNextValue();
        }
        System.out.println("Mean = " + (sum/count));

        value = normallyDistributed.getNextValue();
        sum = 0;
        count = 0;
        while(value != null){
            sum += value;
            count++;
            value = normallyDistributed.getNextValue();
        }
        System.out.println("Mean = " + (sum/count));
    }
}
