# Exercises lecture 4

## Exercise 4
It is **not** possible since the timestamp in the atmoic SRSW registers are required to determine if a value is newer or older than another value. 

Writer writes 2 out of 4 diagonals.

Reader 0 reads diagonal value and writes it to row 0.

Reader 3 reads differing values in diagonal and row 0 but can't determine which value is newer.

??? (*Undefined*)

This violates the property that a later read can't read a value that is older than the value read by an earlier read. (4.1.2)

## Exercise 5
A simple example is one with a single reader and a single writer but they always overlap. Quiescent consistency would only demand that all method calls appear. This would allow reads to take place in any order which isn't allowed by regularity.

## Exercise 7
Yes if using regular SRSW registers in the safe MRSW construction. A read that does not overlap a write will return the latest written value. If the read overlaps a write it will either return the new value or the old one. This would be considered a regular MRSW register.