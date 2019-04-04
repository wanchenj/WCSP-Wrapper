# WCSPLift Wrapper
This is a wrapper for [WCSPLift](https://gitlab.com/xuphys/wcsp-solver/), a Weighted Constraint Satisfaction Problem (WCSP) Solver. The wrapper will provide a higher level format with which WCSPs can be input into the solver. The wrapper will translate files written in this higher level format into the format read by WCSPLift. It will then take the results from WCSPLift and translate them back into the format the user input in the first place. 

## Requirements
In order to use this wrapper, WCSPLift must already be installed, along with Java, and C++. 

## Installation
To get the wrapper working, there are two steps after downloading the repository. The wrapper needs to be given the correct permission, and then updated with the correct installation directory for WCSPLift. The main file that runs the wrapper is a bash script called ```run_wrapper``` located in the src/ directory. 

### update permissions
In order to give the correct permission run the following command:

```
chmod u+x run_wrapper
```

The chmod command has only been verified to work for Ubuntu so far. 

### Update WCSPLift Installation 
 
Open the run_wrapper file with any text editor. In this file, there is a variable ```$wcsp\_location``` that is used to run WCSPLift. Modify this line (line 11 in run\_wrappe) to reflect the location of WCSPLift on your machine.

## Usage
To run the wrapper using the terminal (only checked this in Ubunutu), type:

```
./run_wrapper
``` 

