package Alignment;

import Help_classes.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fransilvion on 29.09.2016.
 * central class for pairwise alignment (Needleman-Wunsch) procedure
 */
public class PerformPairwiseAlignmentProbs {

    private PerformPairwiseAlignmentProbs(){

    }

    /////////////////////////////////////////////////////////////////////////
    //function for calculating of pairwise alignments of all sequence pairs
    public static ArrayList<ProbNeedlemanWunsch> performPairwiseAlignment(Profile profile, HashMap<String, double[][]> probs,
                                                                                          Gap gap, boolean isSum) throws Exception {

        String[] ids = profile.getIds();
        System.out.println("CALCULATING ALL-WITH-ALL PAIRWISE ALIGNMENT");
        //////////////////////////////////////////////////////////////////////////
        //#1 - write the calculated probs into the csv_files folder
        PutProbsToFile.WritetoFile(probs);

        //////////////////////////////////////////////////////////////////////////
        //#2 - build corresponding graphs for each sequences
        //(the python script plot_csv.py is used)
        ProbGraph.BuildGraphs();

        //////////////////////////////////////////////////////////////////////////
        //#3 - perform all pairwise alignments and build the result ArrayList
        int profile_size = profile.getProfileLength();
        //int num_of_alignments = profile_size * (profile_size - 1) / 2;
        ArrayList<ProbNeedlemanWunsch> alignments = new ArrayList<>();
        //ExecutorService exec = Executors.newFixedThreadPool(num_of_alignments);
        for (int i = 0; i < ids.length; i++){
            //getting a profile:
            Profile a = profile.getIdProfile(ids[i]);

            for (int j = i+1; j < ids.length; j++){

                //getting another profile
                Profile b = profile.getIdProfile(ids[j]);
                ProbNeedlemanWunsch align = new ProbNeedlemanWunsch(a, b, gap, probs, isSum);
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
        //#4 - write string representations into file
        StringBuilder for_file = new StringBuilder();
        for (ProbNeedlemanWunsch al : alignments){
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
