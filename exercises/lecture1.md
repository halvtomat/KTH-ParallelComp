# Exercises lecture 1

## Exercise 3 (H&S 2)
**For each of the following, state whether it is a safety or liveness property. Identify the bad or good thing of interest.**

1. Patrons are served in the order they arrive.

Safety, bad thing: *out of order*

2. What goes up must come down.

Liveness, good thing: *return of what goes up*

3. If two or more processes are waiting to enter their critical sections, at least one
succeeds.

Liveness, good thing: *program continues running*

4. If an interrupt occurs, then a message is printed within one second.

Safety, bad thing: *hard to find reason for interrupt*

5. If an interrupt occurs, then a message is printed.

Liveness, good thing: *finding cause of interrupt*

6. The cost of living never decreases.

Safety, bad thing: *powerbalance in world is changed*

7. Two things are certain: death and taxes.

Liveness, good thing: *circle of life continues*

8. You can always tell a Harvard man.

Safety, bad thing: *interacting with a Harvard man without knowing it*


## Exercise 4 (H&S 6)

**Answer the following questions**

- Suppose the sequential part of a program accounts for 40% of the program’s 
execution time on a single processor. Find a limit for the overall speedup that
can be achieved by running the program on a multiprocessor machine.

*Max speedup = 2.5*

- Now suppose the sequential part accounts for 30% of the program’s computation time. Let sn be the program’s speedup on n processes, assuming the rest
of the program is perfectly parallelizable. Your boss tells you to double this
speedup: the revised program should have speedup s
n > 2sn. You advertise
for a programmer to replace the sequential part with an improved version that
runs k times faster. What value of k should you require?

*k = 2*

-  Suppose the sequential part can be sped up three-fold, and when we do so,
the modified program takes half the time of the original on n processors.
What fraction of the overall execution time did the sequential part account
for? Express your answer as a function of n.

*f(n) = 1 - n / (n + 3)*

## Exercise 5 (H&S 8)

**Answer the following question**

You have a choice between buying one uniprocessor that executes
five zillion instructions per second, or a ten-processor multiprocessor where
each processor executes one zillion instructions per second. Using Amdahl’s Law,
explain how you would decide which to buy for a particular application.

The speedup for this particular case is calculated with:

s(*p*) = 1 / (5 - 5*p* + *p*/2)

where *p* is the parallel part of the application.

I would choose the multiprocessor when s(*p*) > 1