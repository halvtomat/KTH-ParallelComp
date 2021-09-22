# Exercises lecture 7

## Exercise 1
The fine-grained list's *add*(*a*) method is linearizable because both successful and unsuccessful calls have a linearization point.
The successful calls are linearized when the *next* field of the predecessor node is changed. 
The unsuccessful calls are linearized when a lock is acquired to a node whose key is greater than or equal to the element which is to be added *a*.
## Exercise 2

## Exercise 3