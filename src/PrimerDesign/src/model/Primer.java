/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 1002852m
 */
public class Primer {
    
    private String code;
    
    public Primer(String c) {
        code = c;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String c) {
        code = c;
    }
    
    public TestResult meltingTemp() {
        int at = 0; int gc = 0;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == 'a' || c == 't')
                at++;
            else if (c == 'g' || c == 'c')
                gc++;
        }
        int meltTemp = (2*at) + (4*gc);
        if (meltTemp >= 50 && meltTemp <= 65)
            return new TestResult(true, null);
        else
            return new TestResult(false, (Integer.toString(meltTemp)));
    }
    
    public TestResult selfAnnealCheck() {
        /*split the string in half (missing out middle if uneven)
         *reverse back half
         * go through each char with both, compare using complement()
         * if a/t match, add 1 to score
         * if c/g match, add 2 to score
         * if < 20, pass
         * if >= 20, fail, with null string
         */
        int annealCount = 0;
        double split = ((double) code.length()/2);
        if (split % 1 == 0.5)
            split -= 0.5;
        String firstHalf = code.substring(0, (int) split-1);
        String secondHalf = code.substring((code.length() - (int) split), code.length() -1 );
        for (int i = 0; i < firstHalf.length(); i++) {
            if ((firstHalf.charAt(i) == 'a' || firstHalf.charAt(i) == 't')
                 && firstHalf.charAt(i) == Sequence.complement(secondHalf.charAt(i)))
                annealCount++;
            else if ((firstHalf.charAt(i) == 'g' || firstHalf.charAt(i) == 'c')
                 && firstHalf.charAt(i) == Sequence.complement(secondHalf.charAt(i)))
                annealCount += 2;
        }
        if (annealCount < 20)
            return new TestResult(true, null);
        else return new TestResult(true, null); //could be the anneal score if useful
    }
    
    
}
