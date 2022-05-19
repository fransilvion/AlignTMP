package Alignment;

import Help_classes.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fransilvion on 19.09.2016.
 * class for the output of pairwise alignment information
 * . - for not similar residues
 * : - for similar residues
 * | - for identical residues
 */
public class PairwiseAlignmentOutput {

    //groups of similar residues (change?)
    private static final Map<Character, String> similarAminoAcids;
    static
    {
        similarAminoAcids = new HashMap<Character, String>();
        //group ILV
        similarAminoAcids.put('I', "ILV");
        similarAminoAcids.put('L', "ILV");
        similarAminoAcids.put('V', "ILV");
        //group FWY
        similarAminoAcids.put('F', "FWY");
        similarAminoAcids.put('W', "FWY");
        similarAminoAcids.put('Y', "FWY");
        //group KRH
        similarAminoAcids.put('K', "KRH");
        similarAminoAcids.put('R', "KRH");
        similarAminoAcids.put('H', "KRH");
        //group DE
        similarAminoAcids.put('D', "DE");
        similarAminoAcids.put('E', "DE");
        //group GAS
        similarAminoAcids.put('G', "GAS");
        similarAminoAcids.put('A', "GAS");
        similarAminoAcids.put('S', "GAS");
        //group TNQM
        similarAminoAcids.put('T', "TNQM");
        similarAminoAcids.put('N', "TNQM");
        similarAminoAcids.put('Q', "TNQM");
        similarAminoAcids.put('M', "TNQM");

        //residues P and C are not similar to anyone, and should be handled separately.
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //private constructor - no one can create such class
    private PairwiseAlignmentOutput(){

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //main function for building the alignment format presentation
    //only for two sequences
    public static String buildRepresentation(Profile itogProfile, double score) throws Exception {

        //for output file
        StringBuilder finalRepresentation = new StringBuilder();
        StringBuilder stringRepresentation = new StringBuilder();
        String representation;

        //initialize arrays inside profile object
        //itogProfile.getSeqAndIdArray();

        if (itogProfile.getProfileLength() != 2) {
            throw new Exception();
        }

        String profile1 = new String(itogProfile.getSequences()[0]);
        String profile2 = new String(itogProfile.getSequences()[1]);
        String id1 = itogProfile.getIds()[0];
        String id2 = itogProfile.getIds()[1];

        int length_of_alignment = 0;

        double alignmentScore = score;

        int id = 0;
        int sim = 0;
        int gap = 0;
        float identity, similarity, gap_percent;

        if (profile1.length() != profile2.length()){
            throw new Exception("Something wrong, aligned profiles should be of the same length");
        }
        length_of_alignment = profile1.length();

        int i;
        for (i = 0; i < length_of_alignment; i++){

            //check if the postion contains a gap
            if (isGapPosition(profile1.charAt(i), profile2.charAt(i))){
                gap++;
                stringRepresentation.append(' ');
                continue;
            }

            //check if the position is identical in characters
            //sum goes to id and to sim
            if (checkIdentical(profile1.charAt(i), profile2.charAt(i))){
                id++;
                sim++;
                stringRepresentation.append('|');
                continue;
            }

            //check if the positions contains similar characters
            if (checkSimilarity(profile1.charAt(i), profile2.charAt(i))){
                sim++;
                stringRepresentation.append(':');
            }
            else
            {
                stringRepresentation.append('.');
            }
        }

        representation = stringRepresentation.toString();
        identity = id / (float) length_of_alignment;
        similarity = sim / (float) length_of_alignment;
        gap_percent = gap/ (float) length_of_alignment;

        //building the final String;

        finalRepresentation.append("#####################################################################################\n");
        finalRepresentation.append("\n");
        finalRepresentation.append("Sequence №1: " + id1 + "\n");
        finalRepresentation.append("Sequence №2: " + id2 + "\n");
        finalRepresentation.append("\n");
        finalRepresentation.append("identity: " + identity + " (" + id + ")/(" + length_of_alignment +")\n");
        finalRepresentation.append("similarity: " + similarity + " (" + sim + ")/(" + length_of_alignment +")\n");
        finalRepresentation.append("gap_percent: " + gap_percent + " (" + gap + ")/(" + length_of_alignment +")\n");
        finalRepresentation.append("\n");
        finalRepresentation.append("Score: " + alignmentScore + "\n");
        finalRepresentation.append("\n");

        //to build string in format:
        //№1  M
        //     |
        //№2  M
        //60 - is a number of symbols in a line;
        int control = (int)(length_of_alignment / 60);
        int x;
        //for building a line - index
        int num_symbol = 0;
        for (x = 0; x < control; x++){
            finalRepresentation.append("№1  ");
            for (int y = num_symbol; y < num_symbol + 60; y++){
                finalRepresentation.append(profile1.charAt(y));
            }
            finalRepresentation.append("\n");
            finalRepresentation.append("    ");
            for (int y = num_symbol; y < num_symbol + 60; y++){
                finalRepresentation.append(representation.charAt(y));
            }
            finalRepresentation.append("\n");
            finalRepresentation.append("№2  ");
            for (int y = num_symbol; y < num_symbol + 60; y++){
                finalRepresentation.append(profile2.charAt(y));
            }
            finalRepresentation.append("\n");
            num_symbol += 60;
        }

        //to write remaining letters (we wrote only REMAINING OF LENGTH DIVISION BY 60 * 60 letters)
        finalRepresentation.append("№1  ");
        for (int y = num_symbol; y < length_of_alignment; y++){
            finalRepresentation.append(profile1.charAt(y));
        }
        finalRepresentation.append("\n");
        finalRepresentation.append("     ");
        for (int y = num_symbol; y < length_of_alignment; y++){
            finalRepresentation.append(representation.charAt(y));
        }
        finalRepresentation.append("\n");
        finalRepresentation.append("№2  ");
        for (int y = num_symbol; y < length_of_alignment; y++){
            finalRepresentation.append(profile2.charAt(y));
        }
        finalRepresentation.append("\n");
        finalRepresentation.append("#####################################################################################\n");

        String finalResult = finalRepresentation.toString();
        return finalResult;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //function to build the whole string for given Alignments:
    public static String buildTotalString(ArrayList<ProbNeedlemanWunsch> alignments){
        StringBuilder finalString = new StringBuilder();
        for (ProbNeedlemanWunsch al : alignments){
            finalString.append(al.getStringRepresentation());
        }
        return finalString.toString();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //help function to get if residues "a" and "b" are similar or not
    private static boolean checkSimilarity(char a, char b){

        //if P or C then not similar anyway
        if ((a == 'P' || a == 'C') || (b == 'P' || b == 'C')) {
            return false;
        }
        if (similarAminoAcids.get(a).indexOf(b) >= 0){
            return true;
        }
        else{
            return false;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //help function - identical or not?
    private static boolean checkIdentical(char a, char b){

        if (a == b)
        {
            return true;
        }
        else {
            return false;
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //help function - has gap or not
    private static boolean isGapPosition(char a, char b){
        if (a == '-' || b == '-'){
            return true;
        }
        else
        {
            return false;
        }
    }

}
