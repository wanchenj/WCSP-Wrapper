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

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
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

		// get a PropositionalLogic object
		PropositionalLogic logicBuddy = new PropositionalLogic();

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
		formatter.FormatterHeaderLine(numVariables, 10, numConstraints, 10);
		
		// iterate through the constraints
		for(int i = 0; i < reader.constraints.size(); i++){
			//TODO: call the logicBuddy for this constraint, and store it:
			//	tempArray = logicBuddy.func1(reader.constraints.get(i), reader.weight_to_confidence.get(i));
			System.out.println("add constraint: " + reader.constraints.get(i) + " with confidence: " + reader.weight_to_confidence.get(i));
	
			// get these lists from the logicBuddy	
			ArrayList<Integer> arrayOfConstraint = new ArrayList<Integer>();
			ArrayList<Double> arrayOfWeights = new ArrayList<Double>();

			// for now use dummy variables
			ArrayList<Integer> a = new ArrayList<Integer>(Arrays.asList(0,2));
			ArrayList<Double> b = new ArrayList<Double>(Arrays.asList(0.5,0.5,0.5,0.5));



			// close the formatter
			// now pass this tempArray to the formatter
			formatter.FormatterConstraintInput(a, b);
			formatter.FormatterClose();
		}

	}

}
