package Alignment;

import Help_classes.Gap;
import Help_classes.Profile;
import Help_classes.Score;

import java.util.HashMap;

/**
 * Created by Fransilvion on 25.10.2016.
 * class for multiple alignment - based on Muscle and Mafft approaches
 * (implement threads?)
 */
public class ProbMultipleSequenceAlignment extends ProbAlignment {

    //tables with probabilities for each profile
    private HashMap<Integer, double[][]> profAprobs = new HashMap<>();
    private HashMap<Integer, double[][]> profBprobs = new HashMap<>();

    //dictionaries with weights of sequences in both profiles
    private HashMap<Integer, Double> weightsA = new HashMap<>();
    private HashMap<Integer, Double> weightsB = new HashMap<>();
    private double sumwWeightsA = 0.0;
    private double sumwWeightsB = 0.0;

    //dictionary with all weights of new profile:
    private HashMap<String, Double> weights = new HashMap<>();

    public ProbMultipleSequenceAlignment(Profile targetA, Profile queryB, Gap gapPenalty,
                                         HashMap<String, double[][]> profProbs, boolean isSum) {
        super(targetA, queryB, gapPenalty, profProbs, isSum);

        //each dictionary contains tables of probabilities for every seq in profile
        //key is a index in the corresponding array (targA, targAids; queryB, queryBids)
        int i, j;
        for (i = 0; i < targetAids.length; i++){
            profAprobs.put(i, profProbs.get(targetAids[i]));
            weightsA.put(i, targetA.getAllWeights().get(targetAids[i]));
            sumwWeightsA = sumwWeightsA + targetA.getAllWeights().get(targetAids[i]);
            weights.put(targetAids[i], targetA.getAllWeights().get(targetAids[i]));
        }

        for (j = 0; j < queryBids.length; j++){
            profBprobs.put(j, profProbs.get(queryBids[j]));
            weightsB.put(j, queryB.getAllWeights().get(queryBids[j]));
            sumwWeightsB = sumwWeightsB + queryB.getAllWeights().get(queryBids[j]);
            weights.put(queryBids[j], queryB.getAllWeights().get(queryBids[j]));
        }

        align();
    }

    @Override
    protected void process() {
        double d, h, v;
        //char quer, targ;
        int i, j, xi, xj, yi, yj;

        xj = targetA[0].length;
        xi = queryB[0].length;

        yj = targetA.length;
        yi = queryB.length;

        //to take care of transmembrane positions (-1 - it means that there is a gap at the begining);
        //if it's two residues aligned together - it will be 0,0 (for profile of two sequences) after first iteration
        //otherwise - it's -1,0
        int[] probsPosB = new int[yi];
        for (int ind = 0; ind<yi; ind++)
            probsPosB[ind] = -1;

        for (i = 1; i <= xi; i++) {

            int[] probsPosA = new int[yj];

            for (int ind = 0; ind<yj; ind++)
                probsPosA[ind] = -1;

            correctPositionsB(i-1, probsPosB);

            for (j = 1; j <= xj; j++) {

                //correct positions
                correctPositionsA(j-1, probsPosA);
                //before it was weight(i-1, j-1)
                double scoreDiagF, scoreDiagH, scoreDiagV;
                if (isSum){
                    scoreDiagF = mD[i-1][j-1] + Score.probMultipleScore(i-1, j-1, probsPosA, probsPosB,
                            targetA, queryB, profAprobs, profBprobs, weightsA, weightsB);
                    scoreDiagH = H[i-1][j-1] + Score.probMultipleScore(i-1, j-1, probsPosA, probsPosB,
                            targetA, queryB, profAprobs, profBprobs, weightsA, weightsB);
                    scoreDiagV = V[i-1][j-1] + Score.probMultipleScore(i-1, j-1, probsPosA, probsPosB,
                            targetA, queryB, profAprobs, profBprobs, weightsA, weightsB);
                }
                else {
                    scoreDiagF = mD[i-1][j-1] + Score.statMultipleScore(i-1, j-1, probsPosA, probsPosB,
                            targetA, queryB, profAprobs, profBprobs, weightsA, weightsB);
                    scoreDiagH = H[i-1][j-1] + Score.statMultipleScore(i-1, j-1, probsPosA, probsPosB,
                            targetA, queryB, profAprobs, profBprobs, weightsA, weightsB);
                    scoreDiagV = V[i-1][j-1] + Score.statMultipleScore(i-1, j-1, probsPosA, probsPosB,
                            targetA, queryB, profAprobs, profBprobs, weightsA, weightsB);
                }

                mD[i][j] = Math.max(Math.max(scoreDiagF, scoreDiagH), scoreDiagV);
                d = mD[i][j];

                if (d == scoreDiagF) {
                    //we remember, that we came from mD
                    pointD[i][j] = 'D';
                }
                else if (d == scoreDiagH) {
                    //we remember, that we came from H
                    pointD[i][j] = 'H';
                }
                else {
                    //we remember, that we came from V
                    pointD[i][j] = 'V';
                }

                double scoreHF, scoreHH, scoreHV;
                if (j < xj) {
                    //scoreHF = mD[i-1][j] + yj * gOP;
                    scoreHF = mD[i-1][j] + yj * gapPenalty.probabilityMultipleGapFunction(j, i, 'H', true, probsPosA, probsPosB,
                            profAprobs, profBprobs, queryB, targetA);
                    //scoreHH = H[i-1][j] + yj * gExt;
                    scoreHH = H[i-1][j] + yj * gapPenalty.probabilityMultipleGapFunction(j, i, 'H', false, probsPosA, probsPosB,
                            profAprobs, profBprobs, queryB, targetA);
                    //scoreHV = V[i-1][j] + yj * gOP;
                    scoreHV = V[i-1][j] + yj * gapPenalty.probabilityMultipleGapFunction(j, i, 'H', true, probsPosA, probsPosB,
                            profAprobs, profBprobs, queryB, targetA);
                }
                else {
                    scoreHF = mD[i-1][j] + 0;
                    scoreHH = H[i-1][j] + 0;
                    scoreHV = V[i-1][j] + 0;
                }
                H[i][j] = Math.max(Math.max(scoreHF, scoreHH), scoreHV);
                h = H[i][j];

                if (h == scoreHF) {
                    //we remember, that we came from mD
                    pointH[i][j] = 'D';
                }
                else if (h == scoreHV) {
                    //we remember, that we came from H
                    pointH[i][j] = 'V';
                }
                else {
                    pointH[i][j] = 'H';
                }

                double scoreVF, scoreVV, scoreVH;
                if (i < xi) {
                    //scoreVF = mD[i][j-1] + yi * gOP;
                    scoreVF = mD[i][j-1] + yi * gapPenalty.probabilityMultipleGapFunction(j, i, 'V', true, probsPosA, probsPosB,
                            profAprobs, profBprobs, queryB, targetA);
                    //scoreVV = V[i][j-1] + yi * gExt;
                    scoreVV = V[i][j-1] + yi * gapPenalty.probabilityMultipleGapFunction(j, i, 'V', false, probsPosA, probsPosB,
                            profAprobs, profBprobs, queryB, targetA);
                    //scoreVH = H[i][j-1] + yi * gOP;
                    scoreVH = H[i][j-1] + yi * gapPenalty.probabilityMultipleGapFunction(j, i, 'V', true, probsPosA, probsPosB,
                            profAprobs, profBprobs, queryB, targetA);
                }
                else {
                    scoreVF = mD[i][j-1] + 0;
                    scoreVV = V[i][j-1] + 0;
                    scoreVH = H[i][j-1] + 0;
                }
                V[i][j] = Math.max(Math.max(scoreVF, scoreVV), scoreVH);
                v = V[i][j];

                if (v == scoreVF) {
                    //we remember, that we came from mD
                    pointV[i][j] = 'D';
                }
                else if (v == scoreVH) {
                    //we remember, that we came from V
                    pointV[i][j] = 'H';
                }
                else {
                    pointV[i][j] = 'V';
                }
            }
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //helping functions to deal with positions in probs table - it has no gaps!
    public void correctPositionsA(int j, int[] probsPosA) {
        if (j < this.targetA[0].length) {
            for (int x = 0; x < this.targetA.length; x++) {
                //to correct positions for probs table - it has no gaps!
                if (this.targetA[x][j] != '-')
                    probsPosA[x] = probsPosA[x] + 1;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void correctPositionsB(int i, int[] probsPosB) {
        if (i < this.queryB[0].length) {
            for (int x = 0; x < this.queryB.length; x++) {
                //to correct positions for probs table - it has no gaps!
                if (this.queryB[x][i] != '-')
                    probsPosB[x] = probsPosB[x] + 1;
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //perform the alignment
    public void align(){
        //call the align() method from Aligment parent class
        super.align();
        this.itogProfile.setWeight(weights);
    }

}
