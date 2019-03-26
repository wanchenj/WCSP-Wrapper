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
	public ArrayList<String> constraints;

	// store the filename, since we may open it multiple times
	String fileName;

	// consrtuctor
	public Reader() 
	{
		
		// instantiate the variables and constraints list
		variables = new HashMap<String, Variable>();
		constraints = new ArrayList<String>();
	}

	// constructor 2
	public Reader(String file, boolean DEBUG_)
	{
	
		// instantiate the variables and constraints list
		variables = new HashMap<String, Variable>();
		constraints = new ArrayList<String>();
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
			System.err.println("Error: " + e.getMessage());
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
		Pattern nameP = Pattern.compile(".:");
		Matcher nameMatcher = nameP.matcher(line);
		nameMatcher.find();
		String name = nameMatcher.group();
		name = name.replace(":", "");


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
		tempVar.domain = domain
			
			;	
		// put the variable in the variable dictionary
		variables.put(name, tempVar);

		
	}

	public void handleConstraint(String line)
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
			
			// remove the confidence and other shit from the string
			line = line.replace("[", "");
			line = line.replace("]", "");
			line = line.replace("c", "");
			line = line.replace(":", "");
			
			line = line.replace(Float.toString(confidence), "");	
			line = line.replace(Float.toString(confidence).replace("0", ""), "");
				
			if(DEBUG)
			{
				System.out.println("found confidence: " + confidence + " and constraint: " + line );
			}

		}		
		//System.out.println("Handling constraint: " + line);
	}

}
