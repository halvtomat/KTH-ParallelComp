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

