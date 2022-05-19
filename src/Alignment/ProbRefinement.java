package Alignment;

import Help_classes.*;
import UPGMA.UPGMA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fransilvion on 07.11.2016.
 * Class for refinement procedure
 * contains bunch of static methods
 * implemented as in MUSCLE (doi:10.1186/1471-2105-5-113)
 */
public class ProbRefinement extends Refinement{

    //////////////////////////////////////////////////////////////////////////
    //private constructor so no one can create a class
    private ProbRefinement(){

    }

    //////////////////////////////////////////////////////////////////////////
    //main method for performing the refinement procedure
    //for now made for only one iteration (implement with the number of iteration as the parametr)
    public static ProbMultipleSequenceAlignment performRefinement(ProbMultipleSequenceAlignment alignment,
                                                                  Gap gap, HashMap<String, double[][]> probs, boolean isSum) throws Exception {
        ProbMultipleSequenceAlignment newAlignment = alignment;

        //step #0 - calculate sum of pairs
        char[][] profileSeqs = alignment.getProfile().getSequences();
        String[] profileSeqIds = alignment.getProfile().getIds();
        double sumOfPairs = SPscore.calculateProbsMultipleSPscore(alignment, probs);

        //step #1 - recalculate distance matrix
        double[][] matrix = buildDistMatrixRefinement(profileSeqs);

        //step #2 - recalculate the tree
        UPGMA tree = new UPGMA(matrix, profileSeqIds, profileSeqs);

        int i;
        //for testing
        //int start_point = tree.getNumberOfClusters() - ((tree.getNumberOfClusters() - profileSeqIds.length) / 2);
        int start_point = tree.getNumberOfClusters() - profileSeqIds.length;
        for (i = start_point; i < tree.getNumberOfClusters() - 1; i++) {

            //step #3 - choice of bipartition
            ArrayList<String> idsForDividing = treeBipartition(tree, i);

            //step #4 - profile extraction
            Pair<Profile, Profile> pairOfProfiles = profileExtraction(idsForDividing, alignment.getProfile());


            //step #5 - delete only gap columns
            Profile profile1 = deleteOnlyGapsColumns(pairOfProfiles.getLeft());
            Profile profile2 = deleteOnlyGapsColumns(pairOfProfiles.getRight());
            pairOfProfiles = null;

            //step #6 - re-alignment
            newAlignment = new ProbMultipleSequenceAlignment(profile1, profile2, gap, probs, isSum);

            //step #7 - calculate SP score of newAligment
            double newSumOfPairs = SPscore.calculateProbsMultipleSPscore(newAlignment, probs);

            //step #8 - accept or reject (SP scores?)
            if (newAlignment.getScore() > alignment.getScore()){
                alignment = newAlignment;
                sumOfPairs = newSumOfPairs;
                System.out.println("Accepted new variant");
            }


            int k = i+1;
            System.out.println("CALCULATED " + k + " REFINEMENT PARTITION");
        }
        return alignment;
    }

}
