/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    
}
