#!/bin/bash

for i in {0..3}; do
	java lab2.Sequential 1000000 10 $((($i + 1)*4)) 0 >> result
	echo -n "," >> result
	java lab2.Executor 1000000 10 $((($i + 1)*4)) 0 >> result
	echo -n "," >> result
	java lab2.ForkJoin 1000000 10 $((($i + 1)*4)) 0 >> result
	echo -n "," >> result
	java lab2.Lambda 1000000 10 $((($i + 1)*4)) 0 >> result
	echo "," >> result
done