# Exercises lecture 2

## Exercise 1

1. What are the relevant events?

	- entering method *increment()*

	- reading value of variable *x*

	- adding 1 to local version of *x*

	- writing updated value to non-local variable *x*

	- returning from method *increment()*

	- creating, reading, comparing and writing to local variable *i*

	- enter and return from methods *actor1()* and *actor2()*

2. Sketch the state machine graph (only parts, it is quite large). Include all events you have identified in 1.

	- A - Enter *actorX()*

	- B - Initialize *i*

	- C - Compare *i* with 10
	
	- D - Return from *actorX()*

	- E - Enter *increment()*

	- F - Read *x*

	- G - Add 1 to local *x*

	- H - Write *x*

	- I - Return from *increment()*

	- J - Increment *i*

![The state machine sketch](./images/state_machine.png)

3. Sketch a few traces of the state machine.

s0 -> A -> B -> C -> E -> F -> G -> H -> I -> J -> C -> D -> s0

This trace is based on the for loop running just one loop.

4. List the relevant intervals for the program.



5. Which are the possible final values for x?  Explain your reasoning very carefully.


