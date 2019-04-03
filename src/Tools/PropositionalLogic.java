package Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.Math.log;

public class PropositionalLogic
{
	private String infix;
	private String postfix;
	private int variableAmount;
	private int variableMax;
	private ArrayList <Boolean> truthTable;
	
	 public static void main(String[] args)
	    {
		 
		 double possibility = 0.8;
	     // usable tokens
		 System.out.println("Tokens:\n>: implication\n=: biconditional\n&: conjunction\nv: disjunction\n@: exclusive disjunction\n~: negation\n");

	     //get a logical expression from the user
	     System.out.println("Enter a logical expression:");
	        
		 Scanner scan = new Scanner(System.in);
		 String inputPropositionalLogic = scan.next();
		 PropositionalLogic logicSolver = new PropositionalLogic(inputPropositionalLogic, 3);
		 List<Boolean> truthTable = logicSolver.GenerateTruthTable();
		 List<Double> truthTableWeights = logicSolver.truthTableToWeight(truthTable, possibility);
	    }
	 
	public PropositionalLogic(String inputPropositionalLogic, int variableAmount){

        infix = inputPropositionalLogic;
        this.variableAmount = variableAmount;
        this.variableMax = variableAmount - 1;
        //infix = "(P&Q)>(PvQ)"; //tautology example
        //infix = "(~PvQ)=(P>Q)"; //tautology example
        //infix = "P>(P>(P>P))"; //tautology example

        //infix = "(P&~P)"; //contradiction example
        //infix = "(P>R)&(Q>R)"; //contingency example
       
    }
	
	public ArrayList<Boolean> GenerateTruthTable() {
		
			truthTable = new ArrayList <Boolean>();
			
		 	System.out.println("Infix expression: " + infix);
	        postfix = postfix(infix); //convert expression to postfix
	        System.out.println("Postfix expression: " + postfix); //display postfix expression

	      //print a truth table
	        for(int i = 0; i < variableAmount; i++) {
	        		System.out.print(i + "\t");
	        }
//	        System.out.println("0\t1\t2\t"+); 
	        System.out.println(infix);
	        System.out.println("-------------------------------------");
	        return evaluate(postfix);

	}

    public String postfix(String infix)
    {
        Stack<Character> stack = new Stack<>(); //operator stack
        stack.push('#'); //initialize stack with # token

        String postfix = "";
        char c; //current opperand/opperator being processed


        // parse Infix expression into stack
        for(int i=0; i<infix.length(); i++)
        {
            c = Character.toUpperCase(infix.charAt(i)); //process the infix expression, one opperand/opperator at a time
            if(c>='0' && c <= ('0' + variableMax)) //add propositions to the postfix
            {
                postfix += c;
            }
            else if(c=='>'||c=='='||c=='V'||c=='&'||c=='~'||c=='@') //add operators to the postfix
            {
                while(stack.peek()!='#' && priority(stack.peek())>= priority(c)) //check priority
                {
                    postfix += (char)stack.pop(); //add appropriate operator to the postfix
                }
                stack.push(c);
            }
            else if(c=='(') //push '(' on the stack
            {
                stack.push(c);
            }
            else if(c==')') //when ')' is processed, pop the rest of the stack to the postfix
            {
                while(stack.peek()!='#')
                {
                    c = (char)stack.pop();
                    if(c!='(')
                    {
                        postfix += c;
                    }
                }
            }
        }
        while(stack.peek()!='#') //pop everything left on the stack
            {
                c = (char)stack.pop();
                postfix += c;
            }
        return postfix;
    }

    public int priority(int token)
    {
        if(token=='=') //biconditional
            return 1;
        if(token=='>') //implication
            return 2;
        if(token=='@') //exclusive disjunction
            return 3;
        if(token=='V') //disjunction
            return 4;
        if(token=='&') //conjunction
            return 5;
        if(token=='~') //negation
            return 6;
        if(token=='(') //open bracket
            return 0;

        return 0;
    }

    public ArrayList <Boolean> evaluate(String postfix)
    {
        Stack<Character> stack = new Stack<>(); //value stack
        stack.push('#'); //initialize stack with # token

        char c; // current opperand/opperator being processed
        char t='T',f='F'; // true and false
        int p,q; //propositional variables
        int tCount=0, fCount=0; //counters for how many times True and False appear
        
//        System.out.println(variableAmount);
        
        for(int set=0; set< (Math.pow(2, variableAmount)); set++)
        {
//        		System.out.println("In loop for 2^3");
            for(int i=0; i<postfix.length(); i++)
            {
                c = postfix.charAt(i); //process the postfix expression
                if(c>='0' && c<=('0' + variableMax)){
//                    boolean[] a = new boolean[10];
                    char temp;
                    int s=set;
                    for(int j = variableMax; j >= 0; j--){
                        temp= s % 2==1 ? t:f;
                        s/=2;
                        if(c==j+'0') stack.push(temp);
                    }

                }
                else if(c=='>') //evaluate an implication
                {
                    q = stack.pop();
                    p = stack.pop();
                    if(p=='T' && q=='T')
                        stack.push(t); //T -> T = T
                    else if(p=='T' && q=='F')
                        stack.push(f); //T -> F = F
                    else if(p=='F' && q=='T')
                        stack.push(t); //F -> T = T
                    else if(p=='F' && q=='F')
                        stack.push(t); //F -> F = T
                }
                else if(c=='~') //evaluate a negation
                {
                    p = stack.pop();
                    if(p=='T')
                        stack.push(f); // ~T = F
                    else if(p=='F')
                        stack.push(t); // ~F = T
                }
                else if(c=='=') //evaluate a biconditional
                {
                    q = stack.pop();
                    p = stack.pop();
                    if(p=='T' && q=='T')
                        stack.push(t); //T <--> T = T
                    else if(p=='T' && q=='F')
                        stack.push(f); //T <--> F = F
                    else if(p=='F' && q=='T')
                        stack.push(f); //F <--> T = F
                    else if(p=='F' && q=='F')
                        stack.push(t); //F <--> F = T
                }
                else if(c=='&') //evaluate a conjunction
                {
                    q = stack.pop();
                    p = stack.pop();
                    if(p=='T' && q=='T')
                        stack.push(t); //T & T = T
                    else if(p=='T' && q=='F')
                        stack.push(f); //T & F = F
                    else if(p=='F' && q=='T')
                        stack.push(f); //F & T = F
                    else if(p=='F' && q=='F')
                        stack.push(f); //F & F = F
                }
                else if(c=='V') //evaluate a disjunction
                {
                    q = stack.pop();
                    p = stack.pop();
                    if(p=='T' && q=='T')
                        stack.push(t); //T v T = T
                    else if(p=='T' && q=='F')
                        stack.push(t); //T v F = T
                    else if(p=='F' && q=='T')
                        stack.push(t); //F v T = T
                    else if(p=='F' && q=='F')
                        stack.push(f); //F v F = F
                }
                else if(c=='@') //evaluate an exclusive disjunction
                {
                    q = stack.pop();
                    p = stack.pop();
                    if(p=='T' && q=='T')
                        stack.push(f); //T @ T = F
                    else if(p=='T' && q=='F')
                        stack.push(t); //T @ F = T
                    else if(p=='F' && q=='T')
                        stack.push(t); //F @ T = T
                    else if(p=='F' && q=='F')
                        stack.push(f); //F @ F = F
                }
            }
            c = (char)stack.pop(); //pop truth value of the current case

            int s = set;
            
            boolean[] lineOfTruthTable = new boolean[variableAmount];
            for(int j = variableMax; j >= 0; j--){
                if(s % 2 == 1){
                		lineOfTruthTable[j]=true;
                }
                else lineOfTruthTable[j]=false;
                s /= 2;
            }
            
            for(boolean b:lineOfTruthTable){
                System.out.print(b+"\t");
            }

            System.out.println(c); //print truth value for user entered expression
            if(c=='T') {
            		truthTable.add(true);
            		tCount++; //count truths
            }   
            else {
            		truthTable.add(false);
            		fCount++; //count falses
            }
        }
        String logicType = "";
        //Determine the type of the logical expression
        if(tCount == Math.pow(2, variableAmount))//all cases are true
        		logicType = "tautology.";
        else if(fCount == Math.pow(2, variableAmount)) //all cases are false
        		logicType = "contradiction.";
        else //mixed case
        		logicType = "contingency.";
        System.out.println(infix+" is a "+ logicType); // evaluate the postfix expression
        System.out.println();
        return truthTable;
    }

    public List<Double> truthTableToWeight(List<Boolean> truthTable, double possibility){
        int tNum=0;
        for (Boolean entry : truthTable) {
            if (entry) {
                tNum++;
            }
        }
        Double truthWeight =  -log( possibility / (double) tNum);
        Double falseWeight =  -log( (1 - possibility) / (double) (truthTable.size() - tNum));

        List<Double> weights = new ArrayList<>();
        for (Boolean entry : truthTable) {
            if (entry) {
                weights.add(truthWeight);
            }
            else{
                weights.add(falseWeight);
            }
        }
        for (double weight: weights) {
        		System.out.println(weight);
        }
        return weights;
    }


}