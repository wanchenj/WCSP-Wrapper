//package Tools;

import Tools.Variable;
import Tools.Reader;
import Tools.Formatter;

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

	public static void main(String[] args)
	{
		// get the reader for reading
		Reader reader = new Reader("Tools/format.txt");		
		
		// get a Formatter object to write the UCS file
		Formatter formatter = new Formatter();
		formatter.FormatterInit();		
	}

}
