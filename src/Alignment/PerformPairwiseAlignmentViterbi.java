package Alignment;

import Help_classes.Gap;
import Help_classes.ProbGraph;
import Help_classes.Profile;
import Help_classes.PutProbsToFile;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fransilvion on 03.02.2017.
 */
public class PerformPairwiseAlignmentViterbi {
    private PerformPairwiseAlignmentViterbi(){

    }

    /////////////////////////////////////////////////////////////////////////
    //function for calculating of pairwise alignments of all sequence pairs
    public static ArrayList<ViterbiNeedlemanWunsch> performPairwiseAlignment(Profile profile, HashMap<String, char[]> paths,
                                                                          Gap gap) throws Exception {

        String[] ids = profile.getIds();
        System.out.println("CALCULATING ALL-WITH-ALL PAIRWISE ALIGNMENT");

        //////////////////////////////////////////////////////////////////////////
        //perform all pairwise alignments and build the result ArrayList
        int profile_size = profile.getProfileLength();
        //int num_of_alignments = profile_size * (profile_size - 1) / 2;
        ArrayList<ViterbiNeedlemanWunsch> alignments = new ArrayList<>();
        //ExecutorService exec = Executors.newFixedThreadPool(num_of_alignments);
        for (int i = 0; i < ids.length; i++){
            //getting a profile:
            Profile a = profile.getIdProfile(ids[i]);

            for (int j = i+1; j < ids.length; j++){

                //getting another profile
                Profile b = profile.getIdProfile(ids[j]);
                ViterbiNeedlemanWunsch align = new ViterbiNeedlemanWunsch(a, b, gap, paths);

                alignments.add(align);
                //exec.execute(align);
            }
        }
        //exec.shutdown();
        //try {
            //exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        //} catch (InterruptedException e) {
            //e.printStackTrace();
        //}

        //////////////////////////////////////////////////////////////////////////
        //write string representations into file
        StringBuilder for_file = new StringBuilder();
        for (ViterbiNeedlemanWunsch al : alignments){
            for_file.append(al.getStringRepresentation());
        }
        String result_for_file = for_file.toString();
        PrintWriter out = new PrintWriter("pairwise_sequence_alignments.txt");
        out.println(result_for_file);
        out.close();

        //////////////////////////////////////////////////////////////////////////
        //#5 - return list with alignments
        return alignments;
    }
}
