import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class swc_reader
{
	public static void main(String[] args)
	{
		// read the file
		//
		try
		{
			// open the file
			FileInputStream fstream = new FileInputStream("format.txt");

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
		}		
		//System.out.println("Handling constraint: " + line);
	}
}
