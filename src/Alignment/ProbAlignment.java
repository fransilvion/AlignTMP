package Alignment;

import Help_classes.Gap;
import Help_classes.Profile;

import java.util.HashMap;

/**
 * Created by Fransilvion on 15.09.2016.
 * Abstract class for alignment of sequences.
 * Both pairwise and multiple alignments go through same procedure of
 * dynamic programming (sequences itself in pairwise and profile in multiple cases)
 */
public abstract class ProbAlignment extends Alignment {

    //tables with probabilities
    //change after clarification with the multiple sequence case
    HashMap<String, double[][]> profProbs = new HashMap<>();

    //boolean parametr for switching between
    //different cost functions
    //if true - use sum function
    //if false - use Pfam stat function
    boolean isSum;


    //////////////////////////////////////////////////////////////////////////////////////
    //constructor of the abstract class
    public ProbAlignment(Profile targetA, Profile queryB, Gap gapPenalty,
                         HashMap<String, double[][]> profProbs, boolean isSum){
        super(targetA, queryB, gapPenalty);

        this.profProbs = profProbs;
        this.isSum = isSum;
    }

}
