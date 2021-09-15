# Exercises lecture 6

## Exercise 1
Consider a situation with only a single thread.

1. lock(): Initial tail.locked == false -> lock is acquired.

1. unlock(): No problems here.

1. lock(): Current tail is *me*, *me*.locked == true -> deadlock for all eternity.

This could happen with this bad implementation with any amount of threads, deadlock **will** occur if a thread tries to lock() twice in a row.

The MCS lock avoids this by always leaving a unused QNode with locked = false behind.

## Exercise 2
The first implementation has the disadvantage that if all threads finish at similar times there will be a lot of locking and releasing, blocking all other threads in the process. This wont be a big problem during low loads with few threads or if the threads arrive at the barrier at different times. 
This implementation would also invalidate all the threads different cached counters every time a new thread increments.

The second implementation instead will have a disadvantage if the threads arrive in reversed order (the first thread 0 arrives late), then the whole array would have to be set in a stair like fashion and create a lot of congestion at the bus. The congestion would be even worse because of false sharing, this could easily be solved with some buffer elements in the array or by making just the last element be separated from the rest. 

I think that the second solution (with my improvements) would perform better under high load, mostly because it is lock-less. Under low load I'm not as certain.
