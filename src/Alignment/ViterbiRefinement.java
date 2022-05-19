package Alignment;

import Help_classes.Gap;
import Help_classes.Pair;
import Help_classes.Profile;
import UPGMA.UPGMA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fransilvion on 05.02.2017.
 */
public class ViterbiRefinement extends Refinement {

    //////////////////////////////////////////////////////////////////////////
    //private constructor so no one can create a class
    private ViterbiRefinement(){

    }

    //////////////////////////////////////////////////////////////////////////
    //main method for performing the refinement procedure
    //for now made for only one iteration (implement with the number of iteration as the parametr)
    public static ViterbiMultipleSequenceAlignment performRefinement(ViterbiMultipleSequenceAlignment alignment,
                                                                  Gap gap, HashMap<String, char[]> paths) throws Exception {
        ViterbiMultipleSequenceAlignment newAlignment = alignment;
        char[][] profileSeqs = alignment.getProfile().getSequences();
        String[] profileSeqIds = alignment.getProfile().getIds();

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
            newAlignment = new ViterbiMultipleSequenceAlignment(profile1, profile2, gap, paths);

            //step #7 - accept or reject (SP scores?)
            if (newAlignment.getScore() > alignment.getScore()){
                alignment = newAlignment;
                System.out.println("Accepted new variant");
            }
            int k = i+1;
            System.out.println("CALCULATED " + k + " REFINEMENT PARTITION");
        }
        return newAlignment;
    }

}
