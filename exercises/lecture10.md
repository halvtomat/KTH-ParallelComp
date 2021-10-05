# Exercises lecture 10

## Exercise 1

### 1
Not allowed under SC, reading *Y* before writing *X* in thread *T0* is illegal.

Allowed under TSO, I don't know how *T1* can read *X* as 0 after *T0* wrote 1 to *X*.

### 2
Allowed under both SC and TSO.

### 3
Not allowed under neither SC not TSO, the write to *A* in *T0* before the write to *X* is illegal.

### 4
Not allowed under SC, reading *Y* before writing *X* in thread *T0* is illegal.

Allowed under TSO.

### 5
Allowed under both SC and TSO.

## Exercise 3
It was hard to find info about this topic but I think that a Java programmer will be alright if he/she only considers
the JMM (Java memory model) and not the architecture's memory model. I know that Java is meant to hold "write once, run anywhere"
and the JMM probably adapts the compiled machine code to work the same on any architecture.
I think that if a programmer writes Java code with a specific machine in mind, the programmer would need to know about both the 
JMM and the underlying architecture's memory model to achieve maximum performance.

The short answer: **TRUE** if considering program consistency, **FALSE** if considering specific system performance.