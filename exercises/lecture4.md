# Exercises lecture 4

## Exercise 4
It is **not** possible since the timestamp in the atmoic SRSW registers are required to determine if a value is newer or older than another value. 

Writer writes 2 out of 4 diagonals.

Reader 0 reads diagonal value and writes it to row 0.

Reader 3 reads differing values in diagonal and row 0 but can't determine which value is newer.

??? (*Undefined*)

This violates the property that a later read can't read a value that is older than the value read by an earlier read.

## Exercise 5
A simple example is one with a single reader and a single writer but they always overlap. This would be quiscent consistent but would allow reads to take place in any order which isn't allowed by regularity.

## Exercise 7
Yes if using regular SRSW registers in the safe MRSW construction that would mean a read would always return either the new or old value, making the MRSW regular.