# Exercises lecture 3

## Exercise 2
Fist history: (fig 3.11 in book)

It **is** quiescently consistent because the method call after the quiescence preiod follows principle 3.5.1

It **is** sequentially consistent because it is possible to arrange the method calls in a way that makes the history follow principles 3.3.1 and 3.3.2.

It **is not** linearizable because the method calls does not appear to take instantaneus effect as they should according to 3.4.1.

Second history: (fig 3.12 in book)

It **is** quiescently consistent because the method call after the quiescence preiod follows principle 3.5.1

It **is** sequentially consistent because it is possible to arrange the method calls in a way that makes the history follow principles 3.3.1 and 3.3.2.

It **is not** linearizable because the method calls does not appear to take instantaneus effect as they should according to 3.4.1.

## Exercise 3
Considering two threads *A* and *B* with the following history:

*A* : runs enq(*x*) up until line *10*

*B* : runs enq(*y*) all the way through

*B* : runs deq() but, because *A* didn't set items[1] = x yet, returns EmptyException

*A* : finishes enq(*x*)

This is **not** linearizable because enq can't be followed by EmptyException. 

## Exercise 4
- Not linearizable at line 15:

*A* : runs enq(*x*) up until line *16*

*B* : runs enq(*y*) all the way through

*B* : runs deq() all the way through

*A* : finishes enq(*x*)

Deq should return *x* if it was linearizable but will return *y*.

- Not linearizable at line 16

*A* : runs enq(*x*) up until line *16*

*B* : runs enq(*y*) all the way through

*A* : finishes enq(*x*)

*B* : runs deq() all the way through

Deq should return *y* if it was linearizable but will return *x*.

- Enq is not linearizable.