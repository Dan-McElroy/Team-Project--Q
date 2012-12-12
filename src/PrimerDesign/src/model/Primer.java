/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
        else return new TestResult(false, String.valueOf(code.length()));
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
                if (strand.subSequence(i, (i + code.length())) == code) {
                    count++;
                    startPoints+= i + " ";
                }
        }
        if (count > 1) return new TestResult(false, startPoints);
        else return new TestResult(true, null);
    }

    public TestResult gcContent(){

        /* count g and c and store in 'gcCount'
         * if gcCount % is between 0.4 and 0.6 (inclusive):  passes test
         * else: fails test and returns actual % gc content
         */

        int gcCount = 0;
   
        for(int i = 0; i < code.length(); i++){
            if(code.charAt(i) == 'g' || code.charAt(i) == 'c')
                gcCount++;
        }

        if (gcCount/code.length() >= 0.4 && gcCount/code.length() <= 0.6){
            return new TestResult(true, null);
        }else{
            String retVal = "";
            retVal += gcCount/code.length();
            return new TestResult(false, retVal);
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
                    String retVal = String.valueOf(current);
                    return new TestResult(false, retVal);
                }
            }else{
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

        String retVal = "";
        retVal += last;
        return new TestResult(p,retVal);   
    }
    
    public String toString() {
        return code;
    }
    
    public boolean equals(Primer p) {
        return (this.code.equals(p.getCode()));
    }
    
    public TestResult test() {
        TestResult t = new TestResult(false, "");
        t.setOut(t.getOut() + "Melting Temp: ");
        t.add(meltingTemp());
        t.setOut(t.getOut() + "Self-Anneal Check: ");
        t.add(selfAnnealCheck());
        // t.add(isUnique());
        t.setOut(t.getOut() + "GC Content: ");
        t.add(gcContent());
        t.setOut(t.getOut() + "Repetition: ");
        t.add(repetition());
        
        return t;
    }
}
