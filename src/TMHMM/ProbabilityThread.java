package TMHMM;

import java.util.HashMap;

/**
 * Created by Fransilvion on 08.11.2016.
 * small test class for calculating the probability
 */
public class ProbabilityThread implements Runnable{
    String id, sequence;
    double[][] table;

    public ProbabilityThread(String id, String sequence){
        this.sequence = sequence;
        this.id = id;
    }

    public String getId() { return this.id; }
    public String getSequence() { return  this.sequence; }
    public double[][] getTable() { return this.table; }
    @Override
    public void run() {
        //get table - first column is for soluble probs, second - for membrane.
        table = Hmm.parse(sequence);
        //additional info for debuging (for sequence id)
        System.out.println("Calculated probs for " + id);
    }
}
