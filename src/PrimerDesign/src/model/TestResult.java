/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Scanner;

/**
 *
 * @author 1002852m
 */
public class TestResult {
 
    private boolean pass;
    private String out;
    
    public TestResult(boolean p, String o) {
        pass = p;
        out = o;
    }
    
    public boolean getPass() {
        return pass;
    }
    public String getOut() {
        return out;
    }
    public void setPass(boolean p) {
        pass = p;
    }
    public void setOut(String o) {
        out = o;
    }
    
    public void add(String s) {
        out += s + "\n";
    }
    
    public void add(TestResult t) {
    	/*
    	 * Adds one test result to another, to be used for the 
    	 * final cumulative test result.
    	 */
        if (out == null)
            out = "";
        pass = (this.pass && t.getPass());
        if (t.getPass())
            out += "PASS:\t" + t.getOut() + "\n";
        else if (!t.getPass())
            out += "FAIL:\t" + t.getOut() + "\n";
    }
    public void addQuiet(TestResult t) {
        if (out == null)
            out = "";
        pass = (this.pass && t.getPass());
        out += t.getOut() + "\n";
    }
    
    public String toString() {          //REFINE THIS.		
    	/* 
    	 * Returns a readable explanation of the test result,
    	 * also to be used for the final test result.
    	 */
        
        return out;
        
        /*
        OLD, KEEPING FOR A RAINY DAY
        if (pass) return ("Primer is good.");
    	else {
    	    String print = "";
            
            Scanner printy = new Scanner(out).useDelimiter("#");
            int j = 1; 
            while (printy.hasNext() ) {        
                String next = printy.next();
                if (next.contains("\t")) {
                    print += next + "\n";
                    j = 1;
                    continue;
                }
                print += j + ". " + next + "\n";
                j++;
            }
            return print;
    	}
        */
    }
    
    public boolean equals(TestResult t) {
        return (this.pass == t.getPass() && this.out.equals(t.getOut()));
    }
}
