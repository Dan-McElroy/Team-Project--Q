/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.lang.Math;

/**
 *
 * @author 1002852m & 1002858t
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
    
    public TestResult goodLength() {
        if (code.length() >= 20 && code.length() <= 30)
            return new TestResult(true, null);
        else return new TestResult(false, ("Primer length should be between 20 and 30 bases, current length: " + String.valueOf(code.length())));
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
        /*
         * NEEDS FIXED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * 
         * split the string in half (missing out middle if uneven)
         * reverse back half
         * go through each char with both, compare using complement()
         * if a/t match, add 1 to score
         * if c/g match, add 2 to score
         * if < 20, pass
         * if >= 20, fail, with null string
         */
        int annealCount = 0;
        int size = code.length();
        double split = ((double) size/2);
        if (split % 1 == 0.5)
            split -= 0.5;
        int s = (int) split;
        String firstHalf = code.substring(0, s-1);
        String secondHalf = code.substring((size - s), size -1 );
        for (int i = 0; i < firstHalf.length(); i++) {
            if ((firstHalf.charAt(i) == 'a' || firstHalf.charAt(i) == 't')
                 && firstHalf.charAt(i) == Sequence.complement(secondHalf.charAt(i)))
                annealCount++;
            else if ((firstHalf.charAt(i) == 'g' || firstHalf.charAt(i) == 'c')
                 && firstHalf.charAt(i) == Sequence.complement(secondHalf.charAt(i)))
                annealCount += 2;
        }
        if (annealCount < 20) return new TestResult(true, null);
        else return new TestResult(false, String.valueOf(annealCount)); //could be the anneal score if useful
    }
    
    public TestResult isUnique(String strand) {
        /*run through the string until char matches first char of code
         * if subsection(i, i+code.length()) == code {add 1 to count}
         * if count > 1 return false, null
         * else return true, null
         */
        int count = 0;
        String startPoints = "";
        char start = code.charAt(0);
        for (int i = 0; i < strand.length(); i++) {
            if (strand.charAt(i) == start)
                if (strand.subSequence(i, Math.min(strand.length(), 
                        (i + code.length()))).equals(code)) {
                    count++;
                    if (startPoints.length() != 0)
                    	startPoints += ", ";
                    startPoints += (i+1);
                }
        }
        if (count > 1) return new TestResult(false, ("Primer is not unique to strand, seen at points " + startPoints + "."));
        else return new TestResult(true, null);
    }

    public TestResult gcContent(){

        /* count g and c and store in 'gcCount'
         * if gcCount ratio is between 0.4 and 0.6 (inclusive):  passes test
         * else: fails test and returns actual % gc content
         */

        double gcCount = 0;
   
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == 'g' || code.charAt(i) == 'c')
                gcCount++;
        }
        
        double ratio = gcCount/(double) code.length();
        if (ratio >= 0.4 && ratio <= 0.6) {
            return new TestResult(true, null);
        }
        else {
            return new TestResult(false, ("Percentage of g's and c's in primer should be between 40% and 60%." 
            					+ "Current percentage: " + String.valueOf(ratio) + "%."));
        }

    }

    public TestResult repetition(){

        /* for each base in 'code'
         * if it is the same as previous base: increment reps
         * if reps passes 3: fails test and returns repeated base
         * if for is completed without a fail: passed test
         */

        char current = code.charAt(0);
        int reps = 1;

        for(int i = 1; i < code.length(); i++){
            if(current == code.charAt(i)){
                reps++;
                if(reps > 3){
                    return new TestResult(false, ("Base " + String.valueOf(current) + " repeats too many times"
                    				   + " in a row."));
                }
            }
            else {
                current = code.charAt(i);
                reps = 1;
            }
        }   
    
        return new TestResult(true, null);

    }

    public TestResult lastLetter(){
        
        /* if last letter is not a g or c: fails test
         * always returns last letter, pass or fail
         */

        char last = code.charAt(code.length() - 1);
        boolean p = false;

        if (last == 'g' || last == 'c')
            p = true;

        return new TestResult(p,("Primer must end in a g or c, instead ends in " + String.valueOf(last) + "." ));   
    }

    public TestResult pairAnneal(Primer p){
        
        /* returns true if highest number of consecutive complementary base
         * pairs is less than 4, false otherwise
         */
        
        int maxMatches = 0; // highest number of consecutive matches found so far
        
        String max, min;    // max is the longest primer, min is the shortest
        
        if (code.length() >= p.getCode().length()){
            max = code;
            min = p.getCode();
        }else{
            max = p.getCode();
            min = code;
        }
            
        // This segment compares the primers when the start of the longer primer
        // joins somewhere in the middle of the shorter primer with the end of
        // the longer primer and the start of the shorter primer unjoined.
        //
        // i.e.     agtcatcg
        //       acatca
        int minStart = min.length() - 4;
        String minCheck, maxCheck;
        int matches;
        while (minStart != 0){
            minCheck = min.substring(minStart);
            maxCheck = max.substring(0, minCheck.length());
            if ((matches = checkMatches(minCheck, maxCheck)) > maxMatches)
                maxMatches = matches;
            minStart--;
        }
        
        // This segment compares the primers when the shorter primer joins the
        // longer primer somewhere in the middle so that both ends of the
        // shorter primer are joined to the longer primer.
        //
        // i.e.      agatcgattgcagt
        //              agctaac
        int maxEnd = min.length() - 1;
        int maxStart = 0;
        while (maxEnd < max.length()){
            maxCheck = max.substring(maxStart, maxEnd + 1);
            if ((matches = checkMatches(min, maxCheck)) > maxMatches)
                maxMatches = matches;
            maxStart++;
            maxEnd++;
        }
        
        // This segment compares the primers when the start of the shorter
        // primer joins somewhere in the middle of the longer primer with the
        // start of the longer primer and the end of the shorter primer 
        // unjoined.
        //
        // i.e.      agtacgtaggtc
        //                  tccagtac
        int minEnd = min.length() - 2;
        while (minEnd >= 4){
            minCheck = min.substring(0, minEnd + 1);
            maxCheck = max.substring(maxStart);
            if ((matches = checkMatches(min, maxCheck)) > maxMatches)
                maxMatches = matches;
            maxStart++;
            minEnd--;
        }
        
        return (new TestResult(maxMatches >= 4, null)); // change to return useful info about matches
    }
    
    public TestResult selfAnneal(){
    
        int maxMatches = 0;
        String front, back;
    
        int split = 4;
        int matches;
    
        while (split < code.length() - 4){
            front = code.substring(0,split);
            back = code.substring(split);
            if ((matches = checkMatches(front, back)) > maxMatches)
                    maxMatches = matches;
            split++;
        }

        return (new TestResult(maxMatches >= 4, null)); // change to return useful info about matches
}
    
    public int checkMatches(String min, String max){
        
        /* Method to return the highest number of consecutive complementary
         * bases in two primer subsequences
         */
        
        int matches = 0;
        
        for(int i = 0; i < max.length(); i++){
            if (max.charAt(i) == Sequence.complement(min.charAt(i)))
                matches++;
            else
                matches = 0;    // chain of matches broken
        }
        return matches;
    }
    
    public String toString() {
        return code;
    }
    
    public boolean equals(Primer p) {
        return (this.code.equals(p.getCode()));
    }
    
    public TestResult test() {
        TestResult t = new TestResult(true, "");
        t.add(meltingTemp());
        t.setOut(t.getOut() + "#");
        t.add(selfAnnealCheck());
        t.setOut(t.getOut() + "#");
        t.add(gcContent());
        t.setOut(t.getOut() + "#");
        t.add(repetition());
        t.setOut(t.getOut() + "#");
        t.add(isUnique(code));
        t.setOut(t.getOut() + "#");
        t.add(goodLength());
        t.setOut(t.getOut() + "#");
        t.add(selfAnneal());
        t.setOut(t.getOut() + "#");
        return t;
    }
}