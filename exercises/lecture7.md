# Exercises lecture 7

## Exercise 1
The fine-grained list's *add*(*a*) method is linearizable because both successful and unsuccessful calls have a linearization point.
The successful calls are linearized when the *next* field of the predecessor node is changed. 
The unsuccessful calls are linearized when a lock is acquired to a node whose key is greater than or equal to the element which is to be added *a*.
## Exercise 2
For a deadlock to occur there must be at least two threads and two locks.
One of the threads acquires lock *a* and is now trying to acquire lock *b*.
Lock *b* is currently held by the other thread which is trying to acquire lock *a*.
The above situation is a deadlock because neither of the threads can continue running and both are waiting for each other. 

In both the lazy and optimistic algorithms a thread must first acquire lock *a* before they can acquire lock *b*, this eliminates any risk of deadlock.
If the lists were circular instead a deadlock could occur in the very unlikely scenario where all nodes are locked at the same time by different threads.


## Exercise 3

1. Thread *a* start remove(*n*)

1. Thread *b* finishes add(*n-1*)

1. Thread *a* acquires locks, validates and have to start over because the validation failed.

1. Thread *a* starts remove(*n*)

1. Thread *b* finishes remove(*n-1*)

1. Thread *a* acquires locks, validates and have to start over because the validation failed.

1. ...

This turn of events could keep going til the end of time.