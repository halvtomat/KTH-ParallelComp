# Exercises lecture 13

## Exercise 1
### a)
1. Nobody waiting
1. Cohorts wait for TM to send *ready* message
1. TM waits for for cohorts to send *yes* or *no* message
1. Cohorts wait for TM to send *abort* or *prepare* message
1. TM waits for cohorts to send *ack* message
1. Cohorts wait for TM to send *commit* message
1. TM waits for cohorts to send *ackCommit* message

### b)
After a TM failure is detected, a new TM is always elected.
If just the TM fails, the new one restarts the protocol.
If the TM and a cohort fails, the others have either all received a *prepare* or not.
If a *prepare* was received, new TM should decide to *commit*, if no one received *commit* new TM can safely *abort* all nodes. 

## Exercise 2
1. T_0.0 	--> *Q* sends *prepare(22,1)* to *A* and *B*
1. T_0.5	--> *R* sends *prepare(33,2)* to *B* and *C*, *A* and *B* sends *acc({},0)* to *Q*
1. T_1.0	--> *Q* sends *propose(22,1)* to *A* and *B*, *B* and *C* sends *acc({},0)* to *R*
1. T_2.0 	--> *Q* sends *prepare(22,3)* to *A* and *B*
1. T_2.5 	--> *R* sends *propose(33,2)* to *B* and *C*, *A* and *B* sends *acc({},0)* to *Q*
1. T_3.0 	--> *Q* sends *propose(22,3)* to *A* and *B*
1. T_3.5 	--> *A* and *B* sends *acc(22,3)* to *Q*
1. T_4.5 	--> *R* sends *prepare(33,4)* to *B* and *C*
1. T_5.0	--> *B* sends *acc(22,3)* and *C* sends *acc({},0)* to *R*
1. T_6.5 	--> *R* sends *propose(22,4)* to *B* and *C*
1. T_7.0	--> *B* and *C* sends *acc(22,4)* to *R*

All nodes have chosen *22*

## Exercise 3
### a)
This can pose a problem to the Paxos algorithm. 
In a worst case scenario this could lead to a acceptor node choosing several different values and making the proposers think their value was chosen.
This would lead to catastrophic failure where no consensus is reached.

### b)
The *prepare* stage is for proposers to learn about earlier proposals which might conflict with their planned proposal.
If a proposer receives another value during the *prepare* stage, it will change its own value to the received value,
after this point, at least two proposers must have the same value. 
It is also here the proposer might learn that its request number is low and raise it.