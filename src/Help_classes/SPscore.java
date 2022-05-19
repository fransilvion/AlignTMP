package Help_classes;

import Alignment.ProbMultipleSequenceAlignment;

import java.util.HashMap;

/**
 * Created by Fransilvion on 16.04.2017.
 * class for calculating SP-score of the given alignment
 */
public class SPscore {

    private SPscore(){

    }

    //calculate the sp-score of the alignment
    public static double calculateProbsMultipleSPscore(ProbMultipleSequenceAlignment alignment,
                                                       HashMap<String, double[][]> probs){
        double SPscore = 0.0;

        char[][] profileSeqs = alignment.getProfile().getSequences();
        String[] profileSeqIds = alignment.getProfile().getIds();
        int profileSeqLength = profileSeqs.length;
        int profileNumOfColumns = profileSeqs[0].length;
        int[] probsPos = new int[profileSeqLength];
        for (int ind = 0; ind < profileSeqLength; ind++)
            probsPos[ind] = -1;

        int i;
        for (i = 0; i < profileNumOfColumns; i++){
            correctPositions(i, probsPos, profileSeqs);
            SPscore = SPscore + calculateForTheColumn(i, profileSeqs, profileSeqIds, probs, probsPos);
        }

        //double gap = calculateAllGaps(profileSeqs);
        //SPscore = SPscore + gap;

        return SPscore;
    }

    private static void correctPositions(int i, int[] probsPos, char[][] profileSeqs) {
            for (int x = 0; x < probsPos.length; x++) {
                //to correct positions for probs table - it has no gaps!
                if (profileSeqs[x][i] != '-')
                    probsPos[x] = probsPos[x] + 1;
            }
    }

    private static double calculateForTheColumn(int i, char[][] profileSeqs, String[] profileSeqIds,
                                              HashMap<String, double[][]> probs, int[] probsPos){
        int len = profileSeqs.length;
        int g, j;
        double columnSP = 0.0;
        for (g = 0; g < len-1; g++){
            if (profileSeqs[g][i] == '-'){
                continue;
            }
            for (j = g+1; j < len; j++){
                if (profileSeqs[j][i] == '-'){
                    continue;
                }
                columnSP = columnSP + Score.calculateSPscore(i, g, j, profileSeqIds, profileSeqs, probs, probsPos);
            }
        }

        return columnSP;
    }

    //just for experiment
    private static double calculateAllGaps(char[][] profileSeqs){
        double finalGapScore = 0.0;
        int size = profileSeqs.length;
        int i, j;
        boolean isStart, countGap;
        for (i = 0; i < size; i++){
            isStart = false;
            countGap = false;
            int gapNumber = 0;
            int endPos = findEndGapPos(profileSeqs[i]);
            for (j = 0; j < endPos; j++){
                if ((profileSeqs[i][j] == '-') && (!isStart)){
                    continue;
                }
                if ((profileSeqs[i][j] != '-') && (!isStart)){
                    isStart = true;
                    continue;
                }
                if ((profileSeqs[i][j] == '-') && (isStart)){
                    countGap = true;
                    gapNumber++;
                    continue;
                }
                if ((profileSeqs[i][j] == '-') && (countGap)){
                    gapNumber++;
                    continue;
                }
                if ((profileSeqs[i][j] != '-') && (countGap)){
                    finalGapScore = finalGapScore + ((-15)+(-1)*(gapNumber-1));
                    gapNumber = 0;
                    continue;
                }
            }

        }
        return finalGapScore;
    }

    private static int findEndGapPos(char[] seq){
        int finalPos = -1;
        for (int i = seq.length-1; i >= 0; i--){
            if (seq[i] != '-'){
                break;
            }
            if (seq[i] == '-'){
                finalPos = i-1;
            }
        }
        return finalPos;
    }
}
