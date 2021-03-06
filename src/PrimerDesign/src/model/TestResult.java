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
    private ArrayList<String> out;
    
    public TestResult(PassState p, String o) {
        passes = new ArrayList<PassState>();
        passes.add(p);
        out = new ArrayList<String>();
        out.add(o);
    }
    
    public TestResult() {
        passes = new ArrayList<PassState>();
        out = new ArrayList<String>();
    }
    
    public ArrayList<PassState> getPasses() {
        return passes;
    }
    
    public PassState getPass(int x) {
        return passes.get(x);
    }
    public String getOut(int x) {
        return out.get(x);
    }
    public int size() {
        return out.size();
    }
    public void setPass(PassState p) {
        passes.set(0, p);
    }
    public void setOut(String o) {
        out.set(0, o);
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
    
    public boolean perfect() {
        if (size() > 0)
            for (PassState p : passes)
                if (p != PassState.PASS && p != null)
                    return false;
        return true;
    }
    
    public boolean passes() {
        if (passes.get(0) == PassState.PASS)
            return true;
        else return false;
    }
    
    public void add(String s) {
        passes.add(null);
        out.add(s);
    }
    
    public void add(TestResult t) {
        passes.add(t.getPass(0));
        String addy = "\u2022 ";
        if (t.getPass(0) == PassState.PASS)
            out.add(addy + "Pass: " + t.getOut(0));
        if (t.getPass(0) == PassState.CLOSEFAIL)
            out.add(addy + "Close Fail: " + t.getOut(0));
        if (t.getPass(0) == PassState.FAIL)
            out.add(addy + "Fail: " + t.getOut(0));
    }
    
    public void addFull(TestResult t) {
        int i = 0;
        for (PassState p : t.getPasses()) {
            passes.add(p);
            if (i == 0) {
                if (p == PassState.PASS)
                    out.add("\u2022 Pass: " + t.getOut(i));
                else if (p == PassState.CLOSEFAIL)
                    out.add("\u2022 Close Fail: " + t.getOut(i));
                else if (p == PassState.FAIL) 
                    out.add("\u2022 Fail: " + t.getOut(i));
            }
            else
                out.add(t.getOut(i));
            i++;
        }
    }
    
    public TestResult get(int i) {
    	if (i < size() && i > 0)
            return new TestResult(passes.get(i), out.get(i));
        return null;
    }
    
    public String toString() {
        String hate = "";
        for (String i : out) {
            hate += i + "\n";
        }
        return hate;
    }
    
    public boolean equals(TestResult t) {
        return (this.acceptable() == t.acceptable() && this.out.equals(t.getOut(0)));
    }
}