package Tools;

import Tools.Variable;


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

public class Reader
{

	boolean DEBUG;

	// create a list of variables
	public Map<String, Variable> variables;
	public List<String> constraints;		// must be  indexed same as 
	public List<Float> weight_to_confidence;		// 		this


	// store the filename, since we may open it multiple times
	String fileName;

	// consrtuctor
	public Reader() 
	{
		
		// instantiate the variables and constraints list
		variables = new HashMap<String, Variable>();
		constraints = new ArrayList<String>();
		weight_to_confidence = new ArrayList<Float>();
	}

	// constructor 2
	public Reader(String file, boolean DEBUG_)
	{
	
		// instantiate the variables and constraints list
		variables = new HashMap<String, Variable>();
		constraints = new ArrayList<String>();
		weight_to_confidence = new ArrayList<Float>();
		fileName = file;

		DEBUG = DEBUG_;
		
		if(DEBUG)
		{
			System.out.println("in Reader(string file) with file: " + file);		
		}	
	}

	public void readAllVars()
	{

		//debug message
		if(DEBUG)
		{
			System.out.println("\n\nin readAllVars");
		}

		// read the file and get all the variables
		//
		try
		{
			// open the file
			FileInputStream fstream = new FileInputStream(fileName);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// read line by line
			while((strLine = br.readLine()) != null)
			{
				// trim the line
				strLine = strLine.trim();

				if(strLine.startsWith("v"))
				{

					handleVar(strLine);

				}
			}

			// close the file
			in.close();
		}catch(Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void readAllConstraints()
	{

		//debug message
		if(DEBUG)
		{
			System.out.println("\n\nin readAllConstraints");
		}

		// read the file and get all the variables
		//
		try
		{
			// open the file
			FileInputStream fstream = new FileInputStream(fileName);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// read line by line
			while((strLine = br.readLine()) != null)
			{
				// trim the line
				strLine = strLine.trim();

				if(strLine.startsWith("c"))
				{

				handleConstraint(strLine);

				}
			}

			// close the file
			in.close();
		}catch(Exception e)
		{
			System.err.println("Error with DataInputStream or something: " + e.getMessage());
		}
	}

	public void handleLine(String line)
	{
		// trim the string
		line = line.trim();

		// ignore comments and blank lines
		if(!line.startsWith("#") && line.length() > 0)
		{
			//System.out.println(line);
			
			// check for variables

		}
		if(line.startsWith("c"))
		{
			handleConstraint(line);
		}
	}

	public void handleVar(String line)
	{
		//System.out.println("Creating variable: " + line);
		//create a variable object
		Variable tempVar = new Variable();
	
// TODO: get the name of the variable that is longer than 1 character

		// get the name
		Pattern nameP = Pattern.compile("\\w+:");
		Matcher nameMatcher = nameP.matcher(line);
		nameMatcher.find();
		String name = nameMatcher.group();
		name = name.replace(":", "");
		name = name.replace("v", "");

		tempVar.name = name;

		// set the domain of the variable
		Pattern domainToken = Pattern.compile(",");
		Matcher domainMatcher = domainToken.matcher(line);
	
		// get the number of matches
		int domain = 0;
		while(domainMatcher.find())
		{
			domain++;
		}
		
		// set the domain of the Variable
		tempVar.domain = domain;

		// get the probability of the vars
		Pattern probPattern = Pattern.compile("\\d\\.\\d+");
		Matcher probMatcher = probPattern.matcher(line);	
	
		// for now, only work with only binary variables,
		// 	the first prob will be assumed to be for FALSE
		probMatcher.find();

		// get this as a number
		String probFalseString = probMatcher.group();
		Double probFalse = (double)Float.parseFloat(probFalseString);
//		System.out.println("prob False: " + probFalse);	

		tempVar.confidence = 1 - probFalse;
		// put the variable in the variable dictionary
		variables.put(name, tempVar);

		
	}

	public void handleConstraint(String line)
	{
		// get the confidence
		Pattern confPattern = Pattern.compile("\\[.\\d+\\]");

		Matcher matcher = confPattern.matcher(line);
		float confidence = 1;
		if(matcher.find())
		{
			String match = matcher.group();

			// a confidence was defined, extract the actual number
			Pattern numPattern = Pattern.compile("\\.\\d+");
			Matcher numMatcher = numPattern.matcher(match);
			numMatcher.find();
			confidence = Float.parseFloat(numMatcher.group());
			
			// remove the confidence and other shit from the string
			line = line.replace("[", "");
			line = line.replace("]", "");	

		}

			line = line.replace("c", "");
			line = line.replace(":", "");
			while(line.contains("v")){
				line = line.replace("v", "");
			}
			
			line = line.replace(Float.toString(confidence), "");	
			line = line.replace(Float.toString(confidence).replace("0", ""), "");
				
//				System.out.println("found confidence: " + confidence + " and constraint: " + line );

			String tempConstraint = line;
			// add the constraint to the list
			constraints.add(tempConstraint);
			weight_to_confidence.add(confidence);	
		//System.out.println("Handling constraint: " + line);
	}

}
