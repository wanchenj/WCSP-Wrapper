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
		// get the reader for reading
		Reader reader = new Reader("Tools/format.txt", true);		
		
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
	}

}
