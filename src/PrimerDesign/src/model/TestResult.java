/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author 1002852m
 */
public class TestResult {
 
    public enum PassState {PASS, FAIL, CLOSEFAIL};
    private ArrayList<PassState> passes;
    private String out;
    
    public TestResult(PassState p, String o) {
        passes = new ArrayList<PassState>();
        passes.add(p);
        out = o;
    }
    
    public PassState getPass(int x) {
        return passes.get(x);
    }
    public String getOut() {
        return out;
    }
    public void setPass(PassState p) {
        passes.set(0, p);
    }
    public void setOut(String o) {
        out = o;
    }
    
    public boolean acceptable() {
        int total = passes.size();
        int cfails = 0;
        if (passes.contains(PassState.FAIL))
            return false;
        for (PassState p : passes) {
            if (p == PassState.CLOSEFAIL)
                cfails++;
        }
        if ((double) cfails / (double) total > 0.6)
            return false;
        return true;
    }
    
    public boolean passes() {
        if (passes.get(0) == PassState.PASS)
            return true;
        else return false;
    }
    
    public void add(String s) {
        out += s + "\n";
    }
    
    public void add(TestResult t) {
        System.out.println(out);
    	/*
    	 * Adds one test result to another, to be used for the 
    	 * final cumulative test result.
    	 */
        if (out == null)
            out = "";
        passes.add(t.getPass(0));
        if (t.getPass(0) == PassState.PASS)
            out += "Pass:\t" + t.getOut() + "\n";
        else if (t.getPass(0) == PassState.FAIL)
            out += "Fail:\t" + t.getOut() + "\n";
        else if (t.getPass(0) == PassState.CLOSEFAIL)
            out += "Close Fail:\t" + t.getOut() + "\n";
    }
    public void addQuiet(TestResult t) {
        if (out == null)
            out = "";
        passes.add(t.getPass(0));
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
        return (this.acceptable() == t.acceptable() && this.out.equals(t.getOut()));
    }
}
