# Exercises lecture 5

## Exercise 1
If a consensus protocol don't have a bivalent initial state, that means the initial state is univalent, which in term means that the outcome of the protocol was predetermined and that doesn't meet the *valid* condition.

## Exercise 2
Another name for a stack is a *LIFO queue* and the only difference from a FIFO queue is in which order the elements are dequeued which is irrelevant in this case.

Like in a FIFO queue, a win value and a lose value is initially enqueued, the 2 competing threads runs deq() and gets a value. The winner thread decides on its own value and the loser thread decides on the winners value.

If we assume there are 3 threads instead we would have to have 2 lose values in the queue and 1 win value, the winner would decide on its own value like before but the losers wouldn't know which thread won so could decide on either of the other twos values.

## Exercise 3
The queue is initially empty.

All threads enqueue their own value.

All threads now *peek()* the queue to get the first value.

The value returned by peek() is the decided value.

This solution has an infinite consensus number because no matter how many threads enqueue their values, the first value will always be the same and all threads can read and return this value.