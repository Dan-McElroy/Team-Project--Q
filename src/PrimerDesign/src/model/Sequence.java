/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 1002852m
 */
public class Sequence {

/* DAN TODO:
   toString
   genStrand                X
   meltingTemp              PX
   isSelfAnnealing          PX
   complement               X
   isUnique                 P
   Primer
*/
    private String oStrand; //user strand
    private String cStrand; //generated strand
    
    public Sequence(String o) {
        oStrand = parser(o);
        cStrand = genStrand();
    }
    public Sequence(String o, String c) {
        oStrand = o;
        cStrand = c;
    }
    
   
    public String getOStrand() {
        return oStrand;
    }
    public String getCStrand() {
        return cStrand;
    }
    public void setOStrand(String o) {
        oStrand = o;
    }
    public void setCStrand(String c) {
        cStrand = c;
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
    
    
}
