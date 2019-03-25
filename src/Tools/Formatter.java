package Tools;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Formatter {
	boolean fileExist = false;
	PrintWriter writer;

	public Formatter()
	{
		// do nothing
	}

	public void FormatterInit() throws FileNotFoundException, UnsupportedEncodingException {
		if (fileExist == false) {
			writer = new PrintWriter("UCS-input.txt", "UTF-8");
			fileExist = true;
		}
		else {
			System.out.println("file already exist, please close it before init");
			return;
		}
	}
	
	public void FormatterHeaderLine(int variableNum, int variableMaxDomain, int constraintNum, int globalUpperBound) {
		writer.println("Sample"+" "+variableNum+" "+variableMaxDomain+" "+constraintNum+" "+globalUpperBound);
		String temp = "";
		for (int i=0; i<variableNum; i++) {
			temp = temp + Integer.toString(variableMaxDomain) + " ";
		}
		writer.println(temp);
	}
	
	public void FormatterConstraintArityOne(int numDiff, int firstWeight, int secondWeight) {
		//default first variable is 0. default weight is 0
		writer.println("1"+" "+"0"+" "+"0"+numDiff);
		if (firstWeight == 0) {
			writer.println("1"+secondWeight);
		}
		else if (secondWeight == 0) {
			writer.println("0"+firstWeight);
		}
		else {
			writer.println("0"+firstWeight);
			writer.println("1"+secondWeight);
		}
	}
	
	public void FormatterConstraintArityTwo(int numDiff, int firstWeight, int secondWeight, int thirdWeight, int fourthWeight) {
		//default fist variable is 0, second is 1
		writer.println("2"+" "+"0"+" "+"1"+" "+"0"+numDiff);
		if (firstWeight == 0) {
			
		}
		else if (secondWeight == 0) {
			
		}
		else if (thirdWeight == 0) {
			
		}
		else if (fourthWeight == 0) {
			
		}
		else {
			
		}
	}
	
	public void FormatterConstraintArityThree() {
		//default first variable is 0, second is 1, third is 2.
	}
	
	
	public void FormatterClose() {
		writer.close();
		fileExist = false;
	}
}
