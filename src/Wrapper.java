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

import static java.lang.Math.log;


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
		Set keys = vars.keySet();

		// now read all the constraints
		reader.readAllConstraints();	
		
		// get the number of constraints and variables
		int numConstraints = reader.constraints.size();
		int numVariables = keys.size(); 
		//System.out.println("num numConstraints: " + numConstraints + " with " + numVariables + " variables");

		
		//pass the variables and the number of constraints to the Formatter for the header
		formatter.FormatterHeaderLine(numVariables, 2, numConstraints + numVariables, 100000000);
	
			
		// iterate through the variables, and add them as unary constraints
		Iterator<String> keyIt = keys.iterator();
		while(keyIt.hasNext())
		{
			// get the key and variable with t hat key
			String key = keyIt.next();
			Variable tempVar = vars.get(key);
			
			// create an arraylist that only has this element in it
			ArrayList<Integer> varIdxArray = new ArrayList<Integer>();
			varIdxArray.add(Integer.parseInt(key));

			// create an arrayList with the truth and false values
			ArrayList<Double> varWeightArr = new ArrayList<Double>();
			varWeightArr.add(-log(1 - tempVar.confidence));
			varWeightArr.add(-log(tempVar.confidence));

			// try to add this to the formatter as a constraint with one argument
			formatter.FormatterConstraintInput(varIdxArray, varWeightArr);
			
			//System.out.println("var:" + key + "  with domain: " + tempVar.domain + " and confidence: " + tempVar.confidence);
		}



		// iterate through the constraints and write them to the UCS file
		for(int i = 0; i < reader.constraints.size(); i++){
			//TODO: call the logicBuddy for this constraint, and store it:
			//	tempArray = logicBuddy.func1(reader.constraints.get(i), reader.weight_to_confidence.get(i));
			//System.out.println("const: " + reader.constraints.get(i) + " conf: " + reader.weight_to_confidence.get(i));
			Float tempWeight = reader.weight_to_confidence.get(i);
			String tempConstraint = reader.constraints.get(i);
			
			// get all the ids from the constraints
			Pattern numPatt = Pattern.compile("[0-9]");
			Matcher numMatcher = numPatt.matcher(tempConstraint);	
			ArrayList<Integer> indexArray = new ArrayList<Integer>();
			int idx = 0;
			while(numMatcher.find()){
				
				// convert the number from string to int before adding to the list			
				String tempStringId = numMatcher.group();
				int tempID = Integer.valueOf(tempStringId);

				// if the ID isn't in there, then add it
				if(!indexArray.contains(tempID)){
					indexArray.add(tempID);

					// replace the string ID with the index
					tempConstraint =  tempConstraint.replace(tempStringId, Integer.toString(idx));

					// increment the idx
					idx += 1;
					
				}
				else{
					// this means that the ID is in there, so get the index it's at
					int idIdx = indexArray.indexOf(tempID);

					// replace the ID with the idx
					tempConstraint = tempConstraint.replace(tempStringId, Integer.toString(idIdx));
				}

				System.out.println("indexArray: " + indexArray);
				System.out.println("tempConstraint: " + tempConstraint);


			}
		
			// iterate  through the tempConstraint, replace each variable ID with it's index from the indexArray
			

	 		// use the logicSolver to get the array of weights 
			PropositionalLogic logicSolver = new PropositionalLogic(tempConstraint, indexArray.size());
			List<Boolean> truthTable = logicSolver.GenerateTruthTable();
			ArrayList<Double> truthTableWeights = logicSolver.truthTableToWeight(truthTable, tempWeight);
			

			// close the formatter
			// now pass this tempArray to the formatter
			formatter.FormatterConstraintInput(indexArray, truthTableWeights);
		}

		// now that all constraints have been written, close the formatter	
		formatter.FormatterClose();

	}

	public void writeVars(Map<String, Variable> vars){
		// get the keys
		
	}
}
