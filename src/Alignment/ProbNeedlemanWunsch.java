package Alignment;

import Help_classes.Gap;
import Help_classes.Pair;
import Help_classes.Profile;
import Help_classes.Score;
import java.util.HashMap;

/**
 * Created by Fransilvion on 16.09.2016.
 * class for pairwise alignment - implement Needleman-Wunsch algorithm
 * (semiglobal alignment - change?)
 */
public class ProbNeedlemanWunsch extends ProbAlignment {

    private String stringRepresentation;

    //////////////////////////////////////////////////////////////////////////////////////
    //probability matrix for each sequence
    private double[][] probsA, probsB;

    //distance between two sequences:
    //equals to:
    //distance = 1 - (num. of ident./num. of compared (exc. gaps) (ClustalW 1994)
    double distance;

    //implements the constructor of its abstract class
    public ProbNeedlemanWunsch(Profile targetA, Profile queryB, Gap gapPenalty,
                               HashMap<String, double[][]> profProbs, boolean isSum) throws Exception {
        super(targetA, queryB, gapPenalty, profProbs, isSum);

        //if length of the profile is not equal to one sequence, that it's an error.
        //if (targetA.getProfileLength() == 0 || queryB.getProfileLength() == 0)
        if (targetA.getProfileLength() != 1 || queryB.getProfileLength() != 1) {
            throw new Exception("Profiles are not of one sequence length for pairwise alignment");
        }

        this.probsA = profProbs.get(targetAids[0]);
        this.probsB = profProbs.get(queryBids[0]);

        align();
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    //functioin to fill the matrix with the appropriate scores
    //due to NW algorigthm
    protected void process() {

        double d, h, v;
        char quer, targ;

        //in case of NW targetA and queryB contain only one sequence
        //that is why everywhere [0] should be put
        for (int i = 1; i <= queryB[0].length; i++) {
            for (int j = 1; j <= targetA[0].length; j++) {

                quer = queryB[0][i - 1];
                targ = targetA[0][j - 1];

                //before it was weight(i-1, j-1)

                double scoreDiagF, scoreDiagH, scoreDiagV;
                if (isSum){
                    scoreDiagF = mD[i-1][j-1] + Score.probsPairwiseScore(quer, targ, probsA, probsB, i-1, j-1);
                    scoreDiagH = H[i-1][j-1] + Score.probsPairwiseScore(quer, targ, probsA, probsB, i-1, j-1);
                    scoreDiagV = V[i-1][j-1] + Score.probsPairwiseScore(quer, targ, probsA, probsB, i-1, j-1);
                }
                else {
                    scoreDiagF = mD[i-1][j-1] + Score.statPairwiseScore(quer, targ, probsA, probsB, i-1, j-1);
                    scoreDiagH = H[i-1][j-1] + Score.statPairwiseScore(quer, targ, probsA, probsB, i-1, j-1);
                    scoreDiagV = V[i-1][j-1] + Score.statPairwiseScore(quer, targ, probsA, probsB, i-1, j-1);
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
                if (j < targetA[0].length) {
                    //scoreHF = mD[i-1][j] + gOP;
                    scoreHF = mD[i-1][j] + gapPenalty.probabilityPairwiseGapFunction(j, i, true, probsA, probsB);
                    //scoreHH = H[i-1][j] + gExt;
                    scoreHH = H[i-1][j] + gapPenalty.probabilityPairwiseGapFunction(j, i, false, probsA, probsB);
                    //scoreHV = V[i-1][j] + gOP;
                    scoreHV = V[i-1][j] + gapPenalty.probabilityPairwiseGapFunction(j, i, true, probsA, probsB);
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
                if (i < queryB[0].length) {
                    //scoreVF = mD[i][j-1] + gOP;
                    scoreVF = mD[i][j-1] + gapPenalty.probabilityPairwiseGapFunction(j, i, true, probsA, probsB);
                    //scoreVV = V[i][j-1] + gExt;
                    scoreVV = V[i][j-1] + gapPenalty.probabilityPairwiseGapFunction(j, i, false, probsA, probsB);
                    //scoreVH = H[i][j-1] + gOP;
                    scoreVH = H[i][j-1] + gapPenalty.probabilityPairwiseGapFunction(j, i, true, probsA, probsB);
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


    public void align() {
        //call the align() method from Aligment parent class
        super.align();

        //calculate the overall distance between two sequences
        calculateDistance();

        //for debugging:
        //make a string representation for the particular object of the alignment
        try {
            this.stringRepresentation = PairwiseAlignmentOutput.buildRepresentation(this.itogProfile, this.score);
        } catch (Exception e) {
            System.out.println("itog profile for pairwise alignment must be of size 2 (two sequences)");
            e.printStackTrace();
            System.exit(1);
        }

        //for debugging
        System.out.println("Aligned two sequences: " + this.targetAids[0] + " and " + this.queryBids[0]);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //method to get the String representation
    public String getStringRepresentation(){
        return this.stringRepresentation;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //method to calculate distance between the aligned sequences
    private void calculateDistance(){
        int num_ident = 0, num_comp = 0;
        int i;
        for (i = 0; i < this.resultProfileA[0].length(); i++){
            if (resultProfileA[0].charAt(i) == '-' || resultProfileB[0].charAt(i) == '-') { continue; }
            if (resultProfileA[0].charAt(i) == resultProfileB[0].charAt(i)){
                num_ident++;
                num_comp++;
            }
            else {
                num_comp++;
            }
        }

        distance = 1.0 - ((double)(num_ident)/(num_comp));

        //in case it's Nan
        if (Double.isNaN(distance)) { distance = 1.0; }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //getter for distance
    public double getDistance(){
        return this.distance;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //method to get the String representation
    public Pair<String, String> getPairOfIds(){
        //it's a pairwise alignment so it contains only one element in each id array
        String idI = this.targetAids[0];
        String idJ = this.queryBids[0];
        Pair<String, String> pair = new Pair<>(idI, idJ);
        return pair;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //method for threads
    //@Override
    //public void run(){ align(); }
}
