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
    
    public void add(TestResult t) {
        if (out == null)
            out = "";
        pass = (this.pass && t.getPass());
        if (!t.getPass())
            out += t.getOut() + " ";
        else out += "PASS ";
    }
    
   /*public String toString() {
    	if (pass) return ("Primer is good.");
    	else {
    	    String print = "Primer fails, for the following reasons:\n";
    	    Scanner printy = new Scanner(out).useDelimiter("#");
    	    int i = 1; 
    	    while (printy.hasNext()) {
    	    	print += i + ". " + printy.next() + "\n";
    	    	i++;
    	    }
    	}
    }
    */
    public boolean equals(TestResult t) {
        return (this.pass == t.getPass() && this.out.equals(t.getOut()));
    }
}
