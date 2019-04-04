# WCSPLift Wrapper
This is a wrapper for [WCSPLift](https://gitlab.com/xuphys/wcsp-solver/), a Weighted Constraint Satisfaction Problem (WCSP) Solver. The wrapper will provide a higher level format with which WCSPs can be input into the solver. The wrapper will translate files written in this higher level format into the format read by WCSPLift. It will then take the results from WCSPLift and translate them back into the format the user input in the first place. 

## Requirements
In order to use this wrapper, WCSPLift must already be installed, along with Java, and C++. 

## Usage
In order to use the wrapper, the location of WCSPLift must be known. The wrapper is run through the run\_wrapper bash script to do work. In this file, there is a variable $wcsp\_location that is used to run WCSPLift. Modify this line (line 11 in run\_wrappe) to reflect the location of WCSPLift on your machine.

To run the wrapper using the terminal (only checked this in Ubunutu), type:

```
chmod u+x run_wrapper
./run_wrapper
``` 

The chmod line only needs to be run once (gives executable access to the wrapper file). The wrapper will now prompt you for any additional information it needs, such as the name of the swc file. 
