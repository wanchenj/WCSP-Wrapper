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

	// create a list of variables
	static Map<String, Variable> variables;

	// consrtuctor
	public Reader() 
	{
		
		// instantiate the variables list
		variables = new HashMap<String, Variable>();
	}

	// constructor 2
	public Reader(String file)
	{
	
		// instantiate the variables list
		variables = new HashMap<String, Variable>();

		System.out.println("in Reader(string file) with file: " + file);		
		
		// read the file
		//
		try
		{
			// open the file
			FileInputStream fstream = new FileInputStream(file);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// read line by line
			while((strLine = br.readLine()) != null)
			{
				handleLine(strLine);
			}

			// close the file
			in.close();
		}catch(Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void handleLine(String line)
	{
		// trim the string
		line = line.trim();

		// ignore comments and blank lines
		if(!line.startsWith("#") && line.length() > 0)
		{
			//System.out.println(line);
			
			// check for variables

		}
		if(line.startsWith("v"))
		{
			handleVar(line);
		}
		if(line.startsWith("c"))
		{
			handleConstraint(line);
		}
	}

	public static void handleVar(String line)
	{
		//System.out.println("Creating variable: " + line);
		//create a variable object
		Variable tempVar = new Variable();
	
// TODO: get the name of the variable that is longer than 1 character

		// get the name
		Pattern nameP = Pattern.compile(".:");
		Matcher nameMatcher = nameP.matcher(line);
		nameMatcher.find();
		String name = nameMatcher.group();
		name = name.replace(":", "");

		System.out.println("name is: " + name);

		tempVar.name = name;

		// put the variable in the variable dictionary
		variables.put(name, tempVar);

		// add the variable to the list
		//variables.add(tempVar);
	}

	public static void handleConstraint(String line)
	{
		// get the confidence
		Pattern confPattern = Pattern.compile("\\[.\\d+\\]");

		Matcher matcher = confPattern.matcher(line);
		
		if(matcher.find())
		{
			String match = matcher.group();

			// a confidence was defined, extract the actual number
			Pattern numPattern = Pattern.compile("\\.\\d+");
			Matcher numMatcher = numPattern.matcher(match);
			numMatcher.find();
			float confidence = Float.parseFloat(numMatcher.group());

			System.out.println("found confidence: " + confidence);

			// TODO: call the correct function for the correct truth table
		}		
		//System.out.println("Handling constraint: " + line);
	}

}
