package Tools;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Formatter {
	boolean fileExist = false;
	PrintWriter writer;
	int[] binaryExpression;

	public Formatter()
	{
		
	}

	public void FormatterInit() throws FileNotFoundException, UnsupportedEncodingException {
		try{	
			if (fileExist == false) {
				writer = new PrintWriter("UCS-input.txt", "UTF-8");
				fileExist = true;
			}
			else {
				System.out.println("file already exist, please close it before init");
				return;
			}
		}
		catch(FileNotFoundException f)
		{
			System.out.println("File not found");
			return;
		}
		catch(UnsupportedEncodingException u)
		{
			System.out.println("unsuported encoding");
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
	
	public void FormatterConstraintInput(ArrayList<Integer> arrayofConstraint, ArrayList<Double> arrayofWeights) {
		//default first variable is 0. default weight is 0
		if (arrayofConstraint == null || arrayofConstraint.size() == 0) {
			System.out.println("error occured when trying to read the constraint array");
			return;
		}
		if (arrayofWeights == null || arrayofWeights.size() == 0) {
			System.out.println("error occured when trying to read the weight array");
		}
		
		int constraintNum = arrayofConstraint.size();
		int combinationNum = (int) Math.pow(2, constraintNum);
		
		binaryExpression = new int[constraintNum];
		String header = "";
		for (int i=0; i<binaryExpression.length; i++) {
			binaryExpression[i] = 0;
			header = header + Integer.toString(arrayofConstraint.get(i)) + " ";
		}
		
		writer.println(constraintNum+" "+header+combinationNum);
		for (int i=0; i<combinationNum; i++) {
			String temp = "";
			for (int j=0; j<binaryExpression.length; j++) {
				temp = temp + binaryExpression[j] + " ";
			}
			
			writer.println(temp + arrayofWeights.get(i));
			
			binaryUpdate();
		}
		
		
		
	}
	
	public void FormatterClose() {
		writer.close();
		fileExist = false;
	}
	
	private boolean binaryUpdate() {
		boolean overflow = true;
		for (int i=binaryExpression.length-1; i>=0; i--) {
			if(binaryExpression[i] == 0) {
				overflow = false;
			}
		}
		if (overflow == true) {
			return false;
		}
		
		int carry = 1;
		for (int i=binaryExpression.length-1; i>=0; i--) {
			if(carry != 0) {
				if (binaryExpression[i] == 0) {
					binaryExpression[i] = 1;
				}
				else {
					binaryExpression[i] = 0;
					carry++;
				}
				
				carry--;
			}
		}
		
		return true;
	}
}
