package Alignment;

import Help_classes.Gap;
import Help_classes.Profile;

import java.util.HashMap;

/**
 * Created by Fransilvion on 03.02.2017.
 */
public abstract class ViterbiAlignment extends Alignment {

    //tables with probabilities
    //change after clarification with the multiple sequence case
    HashMap<String, char[]> pathViterbi = new HashMap<>();


    //////////////////////////////////////////////////////////////////////////////////////
    //constructor of the abstract class
    public ViterbiAlignment(Profile targetA, Profile queryB, Gap gapPenalty,
                         HashMap<String, char[]> pathViterbi){
        super(targetA, queryB, gapPenalty);

        this.pathViterbi = pathViterbi;
    }
}
