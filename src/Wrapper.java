//package Tools;

import Tools.Variable;
import Tools.Reader;
import Tools.Formatter;
import Tools.PropositionalLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.*;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wrapper
{

	int maxDomain;
	int maxCost;

	public Wrapper(int maxDomain, int maxCost){
		this.maxDomain = maxDomain;	
		this.maxCost = maxCost;
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		// create a Wrapper object
		Wrapper wrapper = new Wrapper(2, 10);

		// print the args
		try{ 
			if(args.length == 0){
				System.out.println("please write the filename directly after \"java Wrapper\" ");
				throw new IOException();
			}
	
		}
		catch(IOException e){
			System.out.println("please write the filename directly after \"java Wrapper\" ");
		}

		String fileName = "";
		if(args.length != 0){
			fileName = args[0];	
		}


		// get the reader for reading
		Reader reader = new Reader(fileName, true);		
		
		// get a Formatter object to write the UCS file
		Formatter formatter = new Formatter();
		formatter.FormatterInit();	


		// get all the variables from the reader
		reader.readAllVars();
		Map<String, Variable> vars = reader.variables;

		// iterate through the variables, and display them to the console for now
		Set keys = vars.keySet();
		Iterator<String> keyIt = keys.iterator();
		while(keyIt.hasNext())
		{
			String key = keyIt.next();
			Variable tempVar = vars.get(key);
			System.out.println("var:" + key + "  with domain: " + tempVar.domain);
		}


		// now read all the constraints
		reader.readAllConstraints();	
		
		// get the number of constraints and variables
		int numConstraints = reader.constraints.size();
		int numVariables = keys.size(); 
		System.out.println("num numConstraints: " + numConstraints + " with " + numVariables + " variables");

		
		//TODO: pass the variables and the number of constraints to the Formatter
		formatter.FormatterHeaderLine(numVariables, 2, numConstraints, 10);
		
		// iterate through the constraints
		for(int i = 0; i < reader.constraints.size(); i++){
			//TODO: call the logicBuddy for this constraint, and store it:
			//	tempArray = logicBuddy.func1(reader.constraints.get(i), reader.weight_to_confidence.get(i));
			System.out.println("const: " + reader.constraints.get(i) + " conf: " + reader.weight_to_confidence.get(i));
			Float tempWeight = reader.weight_to_confidence.get(i);
			String tempConstraint = reader.constraints.get(i);
			
			// get all the ids from the constraints
			Pattern numPatt = Pattern.compile("[0-9]");
			Matcher numMatcher = numPatt.matcher(tempConstraint);	
			ArrayList<Integer> indexArray = new ArrayList<Integer>();
			while(numMatcher.find()){
				// convert the number from string to int before adding to the list			
				indexArray.add(Integer.valueOf(numMatcher.group()));
				System.out.println("indexArray: " + indexArray);
			}
		


			 PropositionalLogic logicSolver = new PropositionalLogic(tempConstraint, 3);
			 List<Boolean> truthTable = logicSolver.GenerateTruthTable();
			 ArrayList<Double> truthTableWeights = logicSolver.truthTableToWeight(truthTable, tempWeight);
			

			// for now use dummy variables
			ArrayList<Integer> a = new ArrayList<Integer>(Arrays.asList(0,2));
			ArrayList<Double> b = new ArrayList<Double>(Arrays.asList(0.5,0.5,0.5,0.5));



			// close the formatter
			// now pass this tempArray to the formatter
			formatter.FormatterConstraintInput(indexArray, truthTableWeights);
			formatter.FormatterClose();
		}

	}

}
