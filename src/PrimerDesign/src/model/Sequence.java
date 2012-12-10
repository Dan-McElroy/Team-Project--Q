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
   toString                 Provisional
   genStrand                X
   meltingTemp              PX
   isSelfAnnealing          PX
   complement               X
   isUnique                 PX
   Primer                   X
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
    
    public String parser(String input){
    
        String parsed = "";
        char x;
    
        for(int i = 0; i < input.length(); i++){
            x = input.charAt(i);
            if(x == 'a' || x == 't' || x == 'g' || x == 'c')
                parsed += x;
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
    
    public String toString() {
        // DO THIS WHEN YOU KNOW WHAT STUFF NEEDS
        return (oStrand + cStrand);
    }
}