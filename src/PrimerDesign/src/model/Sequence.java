/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package model;

import java.util.Scanner;
import java.io.*;
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
    
    public static String parser(Scanner input) {
        String parsed = "";
        char x;
        
        while (input.hasNext()) {
            String in = input.next();
            for (int i = 0; i < in.length(); i++) {
                x = in.charAt(i);
                if (x == 'a' || x == 't' || x == 'g' || x == 'c')
                    parsed += x;
            }
        }
        return parsed;
    }
    
    public static char complement(char c) {
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
            default:  out = ' ';
        }
        return out;
    }
    
    private String genStrand() {
        String r = "";
        for (int i = 0; i < oStrand.length(); i++) {
            r += complement(oStrand.charAt(i));
        }
        return r;
    }
        
    public String toString(char x, int line) {
        String out = "1\t";
        String strand;
        if (x == 'o')
            strand = oStrand;
        else strand = cStrand;
        for (int i = 0; i < strand.length(); i++) {
            if (i % line == 0 && i != 0)
                out += "\n" + String.valueOf(i + 1) + "\t";
            else if (i % 10 == 0 && i != 0)
                out += " ";
             out += strand.charAt(i);
        }
        return out;
    }
	    
    public boolean equals(Sequence s) {
        return (this.oStrand.equals(s.getOStrand()) &&
                this.cStrand.equals(s.getCStrand()) &&
                this.fPrimer.equals(s.getFPrimer()) &&
                this.rPrimer.equals(s.getRPrimer()));
    }
    
    /*public static void main(String[] args) {
        Sequence s = new Sequence(args[0]);
        System.out.println("Sequence:\n" + s);
        Primer p = new Primer(s.getCStrand());
        System.out.println("Primer:\n" + p);
        TestResult t = p.test();
        System.out.println("Test Result:\n" + t);
        
    }
    */
}
