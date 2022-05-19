package RunProgram;

import Alignment.*;
import Help_classes.FastaReader;
import Help_classes.Gap;
import Help_classes.Profile;
import Help_classes.SPscore;
import TMHMM.PerformViterbi;
import TMHMM.TransProbs;
import UPGMA.UPGMA;
import UPGMA.PerformUPGMAforProbs;
import UPGMA.PerformUPGMAforViterbi;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fransilvion on 03.05.2017.
 * Main class for running the program
 */
public class AlignTMP {
    public static void main(String[] args) throws Exception {

        //set ot variables
        Gap gap = new Gap();
        boolean isProbs = false;
        boolean isSum = false;
        boolean isRef = false;
        Profile profile;
        String[] ids;
        char[][] sequences;

        //Introduction for user
        System.out.println("############################");
        System.out.println("THE TEST VERSION OF AlignTMP");
        System.out.println("############################");
        System.out.println();

        //provide the input file
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter full path to file with sequences: ");

        try {
            String line = reader.readLine();
            String input = line;
            File filename = new File(line);
            profile = FastaReader.readSequences(filename);
            ids = profile.getIds();
            sequences = profile.getSequences();


            //provide the output file
            System.out.print("Enter full path to outputfile: ");
            String fileout = reader.readLine();

            //choose the cost function
            while (true) {
                System.out.print("Use probabilities (P) or Viterbi (V) cost function? (P/V): ");
                String linePV = reader.readLine();
                if (linePV.toUpperCase().equals("P")) {
                    isProbs = true;
                    while (true) {
                        System.out.print("Use summation cost function (S) or based on Pfam statistics (P)? (S/P): ");
                        String lineSP = reader.readLine();
                        if (lineSP.toUpperCase().equals("S")) {
                            isSum = true;
                            break;
                        } else if (lineSP.toUpperCase().equals("P")) {
                            isSum = false;
                            break;
                        } else {
                            System.out.println("Wrong letter. Use S or P. Try again.");
                        }
                    }
                    break;
                } else if (linePV.toUpperCase().equals("V")) {
                    isProbs = false;
                    break;
                } else {
                    System.out.println("Wrong letter. Use P or V. Try again.");
                }
            }

            //choose gap penalties or use default
            while (true) {
                //choose gap penalties or use default
                System.out.print("Choose gap penalties (Y) or run with the default (D)? (Y/D): ");
                String lineYD = reader.readLine();
                if (lineYD.toUpperCase().equals("D")) {
                    if (isProbs) {
                        if (isSum){
                            gap.setgOP(-12f);
                            gap.setgOpMembr(-30f);
                            gap.setgExt(-4f);
                            gap.setgExtMembr(-3f);
                        }
                        else {
                            gap.setgOP(-30f);
                            gap.setgOpMembr(-18f);
                            gap.setgExt(-5f);
                            gap.setgExtMembr(-1f);
                        }
                    }
                    else {
                        gap.setgOP(-12f);
                        gap.setgOpMembr(-24f);
                        gap.setgExt(-4f);
                        gap.setgExtMembr(-3f);
                    }
                    break;
                } else if (lineYD.toUpperCase().equals("Y")) {
                    try {
                        System.out.println("Type all numbers with minus!");
                        System.out.print("Type soluble gap open penalty: ");
                        String lineGO = reader.readLine();
                        gap.setgOP(Float.parseFloat(lineGO));

                        System.out.print("Type soluble gap extension penalty: ");
                        String lineGext = reader.readLine();
                        gap.setgExt(Float.parseFloat(lineGext));

                        System.out.print("Type membrane gap open penalty: ");
                        String lineGM = reader.readLine();
                        gap.setgOpMembr(Float.parseFloat(lineGM));

                        System.out.print("Type membrane gap extension penalty: ");
                        String lineGMext = reader.readLine();
                        gap.setgExtMembr(Float.parseFloat(lineGMext));

                    } catch (Exception g) {
                        System.out.println("Type a number. You provided the wrong type of data");
                        System.exit(1);
                    }
                    break;
                } else {
                    System.out.println("Wrong letter. Use Y or D. Rerun the program.");
                }
            }

            //use refinement or not?
            while (true) {
                System.out.print("Run the refinement procedure (Y) or not (N)? (Y/N): ");
                String lineR = reader.readLine();
                if (lineR.toUpperCase().equals("Y")) {
                    isRef = true;
                    break;
                } else if (lineR.toUpperCase().equals("N")) {
                    isRef = false;
                    break;
                } else {
                    System.out.println("Wrong letter. Use Y or N. Try again.");
                }
            }

            //Now run the program using probabilities
            if (isProbs) {

                //#1 - CALCULATE PROBABILITIES
                HashMap<String, double[][]> probs = TransProbs.calculateProbs(profile);

                //#2 - CALCULATE PAIRWISE ALIGNMENTS
                ArrayList<ProbNeedlemanWunsch> nw = PerformPairwiseAlignmentProbs.performPairwiseAlignment(profile, probs, gap, isSum);

                //#3 - CALCULATE THE GUIDE TREE
                UPGMA guideTree = PerformUPGMAforProbs.calculateGuideTree(ids, sequences, nw);

                //#4 - PERFORM PROGRESSIVE ALIGNMENT BY TREE
                AlignmentByGuideTreeForProbs alignmentByGuideTreeForProbs = new AlignmentByGuideTreeForProbs(guideTree.getRoot(), probs, gap, isSum);
                ProbMultipleSequenceAlignment multipleSequenceAlignment = alignmentByGuideTreeForProbs.getMultipleAlignment();

                if (isRef) {
                    //#5 - PERFORM REFINEMENT PROCEDURE
                    System.out.println("PERFORM REFINEMENT");
                    ProbMultipleSequenceAlignment refinedAlignment = ProbRefinement.performRefinement(multipleSequenceAlignment, gap, probs, isSum);

                    String[] string = input.split("[.]");
                    string[string.length - 2] = string[string.length - 2].concat("_REFINED");

                    //new file name with REFINED word - denoting that here we will put refined alignment
                    StringBuilder builder = new StringBuilder();
                    for(int b = 0; b < string.length-1; b++) {
                        builder.append(string[b]);
                    }
                    builder.append("." + string[string.length-1]);
                    String fileout2 = builder.toString();

                    //for writing to the file refined version
                    FileWriter fw2 = new FileWriter(fileout2);
                    BufferedWriter bw2 = new BufferedWriter(fw2);

                    HashMap<String, String> msa2 = refinedAlignment.getProfile().getProfile();
                    for(Map.Entry<String, String> entry : msa2.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        bw2.write(key + "\n" + String.valueOf(value) + "\n");
                    }
                    bw2.close();
                }

                //for writing to the file first version
                FileWriter fw = new FileWriter(fileout);
                BufferedWriter bw = new BufferedWriter(fw);

                HashMap<String, String> msa = multipleSequenceAlignment.getProfile().getProfile();
                for (Map.Entry<String, String> entry : msa.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    bw.write(key + "\n" + String.valueOf(value) + "\n");
                }
                bw.close();

                System.out.println("DONE!");

            }
            //else - Viterbi
            else {
                //#1 - CALCULATE PATHS
                HashMap<String, char[]> paths = PerformViterbi.findPaths(profile);

                //#2 - CALCULATE ALL PAIRWISE ALIGNMENTS
                ArrayList<ViterbiNeedlemanWunsch> nw = PerformPairwiseAlignmentViterbi.performPairwiseAlignment(profile, paths, gap);

                //#3 - CALCULATE THE GUIDE TREE
                UPGMA guideTree = PerformUPGMAforViterbi.calculateGuideTree(ids, sequences, nw);

                //#4 - PERFORM PROGRESSIVE ALIGNMENT BY TREE
                AlignmentByGuideTreeForViterbi alignmentByGuideTree = new AlignmentByGuideTreeForViterbi(guideTree.getRoot(), paths, gap);
                ViterbiMultipleSequenceAlignment multipleSequenceAlignment = alignmentByGuideTree.getMultipleAlignment();

                if (isRef) {
                    //#5 - PERFORM THE REFINEMENT PROCEDURE
                    System.out.println("PERFORM REFINEMENT");
                    ViterbiMultipleSequenceAlignment refinedAlignment = ViterbiRefinement.performRefinement(multipleSequenceAlignment, gap, paths);

                    String[] string = input.split("[.]");
                    string[string.length - 2] = string[string.length - 2].concat("_REFINED");

                    //new file name with REFINED word - denoting that here we will put refined alignment
                    StringBuilder builder = new StringBuilder();
                    for(int b = 0; b < string.length-1; b++) {
                        builder.append(string[b]);
                    }
                    builder.append("." + string[string.length-1]);
                    String fileout2 = builder.toString();

                    //for writing to the file refined version
                    FileWriter fw2 = new FileWriter(fileout2);
                    BufferedWriter bw2 = new BufferedWriter(fw2);

                    HashMap<String, String> msa2 = refinedAlignment.getProfile().getProfile();
                    for(Map.Entry<String, String> entry : msa2.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        bw2.write(key + "\n" + String.valueOf(value) + "\n");
                    }
                    bw2.close();
                }

                //for writing to the file first version
                FileWriter fw = new FileWriter(fileout);
                BufferedWriter bw = new BufferedWriter(fw);

                HashMap<String, String> msa = multipleSequenceAlignment.getProfile().getProfile();
                for(Map.Entry<String, String> entry : msa.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    bw.write(key + "\n" + String.valueOf(value) + "\n");
                }
                bw.close();

                System.out.println("DONE!");
            }

        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("No such file. Check the file path");
            System.exit(1);
        }

    }
}
