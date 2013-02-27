/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package model;

import controller.PrimerDesign;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import model.TestResult.PassState;
/**
 *
 * @author 1002852m
 * @author 1002858t
 */
public class Sequence {

    private String oStrand; //user strand
    private String cStrand; //generated strand
    private Primer fPrimer; //forward primer
    private Primer rPrimer; //reverse primer
    public int start, end;
    
    public Sequence(String o) {
        oStrand = parser(new Scanner(o));
        cStrand = genStrand();
        fPrimer = null;
        rPrimer = null;
    }
    public Sequence(String o, String c) {
        oStrand = o;
        cStrand = c;
    }
    public Sequence(String o, String c, Primer f, Primer r) {
    	oStrand = o;
    	cStrand = c;
    	fPrimer = f;
    	rPrimer = r;
    }
    public Sequence() {
        oStrand = null;
        cStrand = null;
        fPrimer = null;
        rPrimer = null;
    }
   
    public String getOStrand() {
        return oStrand;
    }
    public String getCStrand() {
        return cStrand;
    }
    public Primer getFPrimer() {
    	return fPrimer;
    }
    public Primer getRPrimer() {
    	return rPrimer;
    }
    public void setOStrand(String o) {
        oStrand = o;
    }
    public void setCStrand(String c) {
        cStrand = c;
    }
    public void setFPrimer(Primer f) {
    	fPrimer = f;
    }
    public void setRPrimer(Primer r) {
    	rPrimer = r;
    }
    
    public int length() {
        return oStrand.length();
    }
    
    public boolean containsN(int s, int e) {
            String oString = oStrand.substring(s-1, e-1);
            String cString = cStrand.substring(s-1, e-1);
            return (oString.contains("n") || cString.contains("n"));
    }
    
    public static String parser(Scanner input) {
    	/* 
    	 * Takes in a string (should be from NCBI) and turns it
    	 * into one long string of bases that can be better formatted
    	 * elsewhere
    	 */
        String parsed = "";
        char x;
        
        while (input.hasNext()) {
            String in = input.next();
            for (int i = 0; i < in.length(); i++) {
                x = in.charAt(i);
                if (x == 'a' || x == 't' || x == 'g' || x == 'c'|| x == 'n')
                    parsed += x;
            }
        }
        return parsed;
    }
    
    public static char complement(char c) {
    	/*
    	 * Simple function that returns a particular
    	 * base's complement.
    	 */
        char out;
        switch(c) {
            case 'a': out = 't';
                       break;
            case 't': out = 'a';
                       break;
            case 'g': out = 'c';
                       break;
            case 'c': out = 'g';
                       break;
            case 'n': out = 'n';
                       break;
            default:  out = ' ';
        }
        return out;
    }
    
    private String genStrand() {
    	/*
    	 * Takes in one strand, returns
    	 * complementary strand.
    	 */
        String r = "";
        for (int i = 0; i < oStrand.length(); i++) {
            r += complement(oStrand.charAt(i));
        }
        return r;
    }
    
        public TestResult isUnique(Primer p, char s) {
        /*
         * MOVE TO SEQUENCE
         */
        String strand;
        int instances = 0;
        ArrayList<Integer> startPoints = new ArrayList<Integer>();        
        char start = p.getCode().charAt(0);
        if (s == 'o' && cStrand.contains(p.getCode()))
            return new TestResult(PassState.FAIL, "Primer appears on"
                    + " the wrong strand.");
        if (s == 'c' && oStrand.contains(p.getCode()))
            return new TestResult(PassState.FAIL, "Primer appears on"
                    + " the wrong strand.");
        //Original strand search
        if (s == 'o') strand = oStrand;
        else strand = cStrand;
        for (int i = 0; i < strand.length(); i++) {
            if (strand.charAt(i) == p.getCode().charAt(0))
                if (p.matches(i, strand)) {
                    startPoints.add(i);
                    instances++;
                }
        }
        if (instances == 0 && s == 'c') {
            Primer r = new Primer(p.reverse(p.getCode()));
            for (int i = 0; i < strand.length(); i++) {
                if (strand.charAt(i) == r.getCode().charAt(0))
                    if (p.matches(i, strand)) {
                        startPoints.add(i);
                        instances++;
                    }
            }
        }
        if (instances == 1) {
            int point = startPoints.get(0);
            int diff = start - point;  
            if (diff >= 15 && diff <= 30)
                return new TestResult(PassState.PASS, "Primer is "
                        + "unique to the sequence, and is situated "
                        + "correctly.");
            else return new TestResult(PassState.FAIL, "Primer is "
                    + "unique to the sequence, and is located in the right "
                    + "strand, but is located in the wrong part of the sequence.");
        }
        else if (instances == 0) {
            return new TestResult(PassState.FAIL, "Primer does not appear in the "
                    + "correct strand.");
        }
        return new TestResult(PassState.FAIL, "Primer is not unique"
                + " to the sequence.");
    }
        
    public String toString(char x, int block, int line) {
        /*
         * Returns a specified strand, splitting the strand
         * into blocks of ten and lines of a specified amount.
         */ 
        String out = "";
        String strand = "";
        int iReset = 0;
        if (x == 'o')
            strand = oStrand;
        else if (x == 'c') strand = cStrand;
        else strand = oStrand;
        
        if (x == 'o' || x == 'c'){
            for (int i = 0; i < strand.length(); i++) {
                if (i % line == 0 && i != 0)
                    out += "\n";
                else if (i % block == 0 && i != 0)
                    out += " ";
                 out += strand.charAt(i);
            }
        } else {
            int i = 0;
            while (i < (strand.length() + 1)){
                
                if ((i % line == 0 && i != 0) || i == strand.length()){
                    out += "\n";
                    if (strand == oStrand){
                        strand = cStrand;
                        i = iReset;
                    } else {
                        strand = oStrand;
                        iReset = i;
                    }
                } else if (i % block == 0 && i != 0)
                    out += " ";
                
                try{
                    out += strand.charAt(i);
                }
                catch(Exception E){
                    break;
                }
            
                i++;
            }
        }
        return out;
    }
	    
    public boolean equals(Sequence s) {
    	/*
    	 * Checks if one primer equals another.
    	 */
        return (this.oStrand.equals(s.getOStrand()) &&
                this.cStrand.equals(s.getCStrand()) &&
                this.fPrimer.equals(s.getFPrimer()) &&
                this.rPrimer.equals(s.getRPrimer()));
    }
    
    public int difference(int i, int j) {
        int difference = i - j;
        if (difference < 0)
            difference -= 2*difference;
        return difference;
    }
    
    public TestResult tempDifference() {
        
        int fTemp = fPrimer.getMeltingTemp();
        int rTemp = rPrimer.getMeltingTemp();
        
        if (difference(fTemp, rTemp) > 5)
            return new TestResult(PassState.FAIL, "Primer melting temperatures should be"
                    + " within 3\u2103 of each other. Your temperatures are: "
                    + fTemp + " and " + rTemp + ".");
        else if (difference(fTemp, rTemp) > 3)
            return new TestResult(PassState.CLOSEFAIL, "Your primers"
                    + " are " + difference(fTemp, rTemp) + "\u2103 apart, just"
                    + " above the highest recommended difference of 3\u2103");
        else
            return new TestResult(PassState.PASS, "Your primers are"
                    + " within 3\u2103 of each other.");
    }
    
    public TestResult primerTest() {
        TestResult test = new TestResult(PassState.PASS, "");
        TestResult fTest = fPrimer.test(); fTest.add(isUnique(fPrimer, 'o'));
        TestResult rTest = rPrimer.test(); rTest.add(isUnique(rPrimer, 'c'));
        if (fTest.acceptable() && rTest.acceptable() && fPrimer.pairAnneal(rPrimer).passes()
                && tempDifference().passes()){ test.add("Congratulations, your"
                        + " primers meet all the design requirements!");
                return test;
        }
        test.add("Your primers haven't met the requirements in the "
                + "following areas:\n");
            test.add("Forward Primer:\n");
            test.addFull(fPrimer.test());
            test.add("\nReverse Primer:\n");
            test.addFull(rPrimer.test());
            test.add("\nGeneral:");
            test.add(tempDifference());
            test.add(fPrimer.pairAnneal(rPrimer));
        return test;
    }
}
