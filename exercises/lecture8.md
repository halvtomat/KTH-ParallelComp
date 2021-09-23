# Exercises lecture 8

## Exercise 1
According to Wikipedia, uninitialized Java numerical variables are set to the default value of *0*. 
With this in mind I can only see 4 potential outputs (0,0), (0,1), (1,0), (1,1).
But another thing to consider is that the value of *y* is always set after *x*. This removes the possibility of (1,0) being outputted. 
Final answer is that (0,0), (0,1) and (1,1) are the possible outputs. 

## Exercise 3
The *volatile* keyword in this example is useless as the only thing it does is guarantee that *int[] a = arr;* always reads *arr* from memory. 
The elements in *arr* are not volatile, only the reference to *arr*. 
I believe it is completely possible to get the (r1,r2) = (1,0) outcome from this code as there is nothing preventing the hardware from writing *a[1]* before *a[0]*.
In fact all the four different outcomes are possible from this code.
## Exercise 4
- *why "unsurprisingly"?*
Because the ++ operator isn't thread-safe and will actually read, increment local copy and lastly write the value again. The problem occurs when one of the threads read while the other still hasn't written its new value. This leads to one incrementation being overwritten and lost.

- *Do we?*
Yes the counter will reach 10000000 this time. Or it won't terminate at all because it is stuck in an infinite loop.

- *Can you reproduce the above behavior on your machine? Do you observe a different surprising behavior? Or you observe the correct outcome?*
The same behavior is reproduced on my machine. (Ubuntu 20.04, Intel quadcore, openjdk 11.0.11)

- *What should this program print? Should it terminate? Why?*
It should print some large number twice or maybe even overflow the int, it probably won't. It should terminate but because of Java it won't.

- *What does this program print? Does it terminate?*
The program prints two different large numbers and never terminates because of the infinite loop the JVM created for us.

- *Declare the variable turn as volatile in the class Dekker, and observe the outcome of the program. Is it the expected one?*
No this is still not the expected outcome. On my machine it terminates but the final value of counter is close but not 10000000 

- *Modify Dekker.java accordingly and observe the result.*
I finally observe the expected result.

- *BONUS*
Based on the hint I expected to see some results where **y = 1, x = 0** but I didn't, I got the expected **y = 1, x = 1** every time. 
But I am guessing the global code motion could schedule the write to x after the for loop which could result in **y = 1, x = 0**.
