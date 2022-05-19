package TMHMM;

/**
 * Created by Fransilvion on 02.02.2017.
 */
public class ViterbiThread implements Runnable {
    String id, sequence;
    char[] path;

    public ViterbiThread(String id, String sequence){
        this.sequence = sequence;
        this.id = id;
    }

    public String getId() { return this.id; }
    public String getSequence() { return  this.sequence; }
    public char[] getPath() { return this.path; }

    @Override
    public void run() {
        //get the most probable path for the sequence of particular states
        path = ViterbiHmm.runViterbi(sequence);
        //additional info for debuging (for sequence id)
        System.out.println("Got Viterbi path for " + id);
    }
}
