# Exercises lecture 11

## Exercise 1
It is theoretically justified to increase each *n* to the nearest multiple of 3 in the lower bound of the Byzantine
failures proof because the processes could be divided and grouped into three bigger processes where one is Byzantine.

Since the supposed algorithm *A* is *f*-resilient for *n* processes, where *f* => *n*/3 there should be no problem,
but since one larger process is byzantine (all processes in that process is byzantine) the three larger processes cant reach
consensus.

## Exercise 2
The proof of the Pease-Shostak-Lamport algorithm does not work if we replace the condition.
I think that the algorithm would still work most of the times but in situations where many processes fail simultaneously
the test might give some false negatives and classify trustworthy nodes as faulty ones.

## Exercise 3
### 1
Yes leader election is possible. If a process with *id = 0* exists, that process sends *not leader* messages to all other processes and
is elected leader. If there isn't a process with *id = 0*, go to the next phase where instead the process with *id = 1* becomes leader
and make the rest *not leader*. This algorithm will make sure the process with lowest *id* becomes leader.
### 2
1. Send *id* number to the next and previous process.
1. If *id == received_number1 or received_number2* set *not leader*
1. If *id != received number1 or received_number2* set *leader*
1. ????
1. profit

### 3
I don't think it is possible in this setting. Neither of my two previous algorithms would work in this scenario.
The leader needs to be a unique process, if there exist another identical process (in this case same *id*) it is impossible to determine
which one of them should be leader. Suppose an algorithm exists to determine one of them as leader, this algorithm must then also determine
the other identical process as leader because they are identical.
The only case where an algorithm of this kind would work is if there is something I haven't thought of that could make the processes unique.
