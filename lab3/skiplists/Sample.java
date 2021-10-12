package skiplists;

public class Sample<T> implements Comparable<Sample<T>> {

    private long currentTime;
    private long threadId;
    private int method;
    private T argument;
    private boolean status;

    public Sample(int method, T argument, boolean status){
        this.threadId = Thread.currentThread().getId();
        this.currentTime = System.nanoTime();
        this.method = method;
        this.argument = argument;
        this.status = status;
    }
    public Sample(long currentTime, int method, T argument, boolean status){
        this.threadId = Thread.currentThread().getId();
        this.currentTime = currentTime;
        this.method = method;
        this.argument = argument;
        this.status = status;
    }

    public int compareTo(Sample<T> s) {
        if (currentTime == s.currentTime)
            return 0;
        if (currentTime > s.currentTime)
            return 1;
        return -1;
    }

    public void printSample(){
        System.out.println("Thread-" + threadId + ", time: " + currentTime + "ns, method: " + getMethodName() + "(" +argument+")" + ", res: " + status);
    }

    private String getMethodName(){
        switch(this.method) {
            case 0:
                return "contains";
            case 1:
                return "add";
            default:
                return "remove";
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public T getArgument() {
        return argument;
    }

    public void setArgument(T argument) {
        this.argument = argument;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}