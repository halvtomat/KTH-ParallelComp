# Exercises lecture 12

## Exercise 1
As stated in the lecture slides, both the king and queen algorithms works with any input and not just binary.
Whichever values the processes supports initially is forgotten when a majority of the processes have
some other value. If there is no majority value, choose the value given by the king/queen. 
Because of this principal, all processes will converge towards a few values and finally a single consensus value.

## Exercise 2
I don't see why the Dolev-Strong algorithm as it is wouldn't work for arbitrary input.
The only value that matters is that of the leader *P* which could be any value. 
If *P* sends out *x*, all correct nodes receiving *x* should choose it.
If *P* doesn't send a value, choose 0.
If *P* is Byzantine and sends different values to different nodes, check the trustworthy values you received and
compare them, if they are the same, choose that value, if not choose 0.
This should work for any value.

## Exercise 3
The randomness in the Ben-Or randomized consensus algorithm is needed to avoid the scenario where half of the
processes has decided on one value and the other half on another value. The randomness makes sure that each round
is different and at least one of these rounds have to lead to consensus between the threads.