package Help_classes;

import java.util.HashMap;

/**
 * Created by Fransilvion on 16.09.2016.
 * Special class to handle with the gap penalties
 */
public class Gap {
    //gap penalties
    private float gOP, gExt, gOpMembr, gExtMembr;

    //initialize with default numbers
    //(change later)
    public Gap(){
        this.gOP = -12f;
        this.gExt = -4f;
        this.gOpMembr = -30f;
        this.gExtMembr = -3f;
    }

    //initialize with particular numbers
    public Gap(float gOP, float gExt, float gOpMembr, float gExtMembr){
        this.gOP = gOP;
        this.gExt = gExt;
        this.gOpMembr = gOpMembr;
        this.gExtMembr = gExtMembr;
    }

    /////////////////////////////////////////////////////////////////////
    //GROUPS OF SETTERS
    public void setgOP(float gOP) { this.gOP = gOP; }

    public void setgExt(float gExt) { this.gExt = gExt; }

    public void setgOpMembr(float gOpMembr) { this.gOpMembr = gOpMembr; }

    public void setgExtMembr(float gExtMembr) { this.gExtMembr = gExtMembr; }

    /////////////////////////////////////////////////////////////////////
    //GROUPS OF GETTERS
    public float getgOP() { return this.gOP; }

    public float getgExt() { return this.gExt; }

    public float getgOpMembr() { return this.gOpMembr; }

    public float getgExtMembr() { return this.gExtMembr; }

//////////////////////////GAPS FOR PROBABILITY///////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    //function for pairwise alignment GAP
    //implements ClustalW approach (ClustalW, Gibson, 1994):
    //GOP = (GOP + log(min(N,M)))*(average residue mismatch score)
    //GEP = GEP*(1+|log(N/M)|)
    public double probabilityPairwiseGapFunction(int posA, int posB, boolean gapOpen,
                                        double[][] probsA, double[][] probsB) {
        int lenA = probsA.length;
        int lenB = probsB.length;


        if (gapOpen) {
            if (pairwiseIsMembraneProbs(posB-1, probsB) && pairwiseIsMembraneProbs(posA-1, probsA)){
                    //double normgOpMembr = (-1)*(Math.abs(this.gOpMembr) + Math.log(Math.min(lenA, lenB)))
                            //* AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                    //return normgOpMembr;
                    //return this.gOpMembr * AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                return this.gOpMembr;
            }
            else{
                    //double normgOP = (-1)*(Math.abs(this.gOP) + Math.log(Math.min(lenA, lenB)))
                            //* AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.BLOSUM62);
                    //return normgOP;
                    //return this.gOP * AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.BLOSUM62);
                return this.gOP;
            }
        }
        else {
            if (pairwiseIsMembraneProbs(posB-1, probsB) && pairwiseIsMembraneProbs(posA-1, probsA)){
                    //double normgExtMembr = this.gExtMembr * (1.0 + Math.abs((double) lenA / (double) lenB));
                    //return normgExtMembr;
                return this.gExtMembr;
            }
            else {
                    //double normgExt = this.gExt * (1.0 + Math.abs((double) lenA / (double) lenB));
                    //return normgExt;
                return this.gExt;
            }
        }


    }

    /////////////////////////////////////////////////////////////////////
    //function for multiple alignment GAP
    //implements ClustalW approach:
    //GOP = (GOP + log(min(N,M)))*(average residue mismatch score)
    //GEP = GEP*(1+|log(N/M)|)
    //1) if there is a gap in a position:
    // GOP = GOP * 0.3 * (no_of_seq_without_gap/no_of_seq)
    // GEP = GEP * 0.5
    //2) if there is no gap but 8 residues from existing gap (???):
    // GOP = GOP * [2 + (8 - distance_from_gap)/4] (GEP the same)
    public double probabilityMultipleGapFunction(int posA, int posB, char matrix, boolean gapOpen, int[] probsPosA, int[] probsPosB,
                                      HashMap<Integer, double[][]> profAprobs, HashMap<Integer, double[][]> profBprobs,
                                      char[][] mProfB, char[][] mProfA){
        //variable to track - is that a transmembrane region or not (if 90% or more of residues are membrane - region is tm)
        boolean transmembraneA;
        boolean transmembraneB;

        transmembraneA = isColumnMembraneProbs(posA-1, probsPosA, mProfA, profAprobs);
        transmembraneB = isColumnMembraneProbs(posB-1, probsPosB, mProfB, profBprobs);

        //we implement membrane gap penalties only when both columns are membrane
        boolean transmembrane;
        if (transmembraneA && transmembraneB){
            transmembrane = true;
        }
        else {
            transmembrane = false;
        }

        if (gapOpen){
            return makeProperMultipleGap(posA-1, posB-1, matrix, true, transmembrane, mProfB, mProfA);
        }
        else {
            return makeProperMultipleGap(posA-1, posB-1, matrix, false, transmembrane, mProfB, mProfA);
        }

        //if (matrix == 'V') {
            //check column
            //transmembrane = isColumnMembraneProbs(posB-1, probsPosB, mProfB, profBprobs);
        //}
        //else {
            //check column
            //transmembrane = isColumnMembraneProbs(posA-1, probsPosA, mProfA, profAprobs);
        //}

        //if (gapOpen){
            //return makeProperMultipleGap(posA-1, posB-1, matrix, true, transmembrane, mProfB, mProfA);
        //}
        //else {
            //return makeProperMultipleGap(posA-1, posB-1, matrix, false, transmembrane, mProfB, mProfA);
        //}
    }

/////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////GAPS FOR VITERBI PATH//////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////
    //function for pairwise alignment GAP
    //implements ClustalW approach (ClustalW, Gibson, 1994):
    //GOP = (GOP + log(min(N,M)))*(average residue mismatch score)
    //GEP = GEP*(1+|log(N/M)|)
    public double viterbiPairwiseGapFunction(int posA, int posB, boolean gapOpen,
                                                 char[] pathA, char[] pathB) {
        int lenA = pathA.length;
        int lenB = pathB.length;
        if (gapOpen) {
            if (pairwiseIsMembraneViterbi(posB-1, pathB) && pairwiseIsMembraneViterbi(posA-1, pathA)){
                //double normgOpMembr = (-1) * (Math.abs(this.gOpMembr) + Math.log(Math.min(lenA, lenB))) *
                        //AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                return this.gOpMembr;
            }
            else{
                //double normgOP = (-1) * (Math.abs(this.gOP) + Math.log(Math.min(lenA, lenB))) *
                        //AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.BLOSUM62);
                return this.gOP;
            }
        }
        else {
            if (pairwiseIsMembraneViterbi(posB-1, pathB) && pairwiseIsMembraneViterbi(posA-1, pathA)){
                //double normgExtMembr = this.gExtMembr * (1.0 + Math.abs((double) lenA / (double) lenB));
                return this.gExtMembr;
            }
            else {
                //double normgExt = this.gExt * (1.0 + Math.abs((double) lenA / (double) lenB));
                return this.gExt;
            }
        }

    }

    /////////////////////////////////////////////////////////////////////
    //function for multiple alignment GAP
    //implements ClustalW approach:
    //GOP = (GOP + log(min(N,M)))*(average residue mismatch score)
    //GEP = GEP*(1+|log(N/M)|)
    //1) if there is a gap in a position:
    // GOP = GOP * 0.3 * (no_of_seq_without_gap/no_of_seq)
    // GEP = GEP * 0.5
    //2) if there is no gap but 8 residues from existing gap (???):
    // GOP = GOP * [2 + (8 - distance_from_gap)/4] (GEP the same)
    public double viterbiMultipleGapFunction(int posA, int posB, char matrix, boolean gapOpen, int[] probsPosA, int[] probsPosB,
                                                 HashMap<Integer, char[]> profApath, HashMap<Integer, char[]> profBpath,
                                                 char[][] mProfB, char[][] mProfA){
        //variable to track - is that a transmembrane region or not (if 90% or more of residues are membrane - region is tm)
        boolean transmembrane;
        boolean transmembraneA;
        boolean transmembraneB;

        transmembraneA = isColumnMembraneViterbi(posA-1, probsPosA, mProfA, profApath);
        transmembraneB = isColumnMembraneViterbi(posB-1, probsPosB, mProfB, profBpath);

        //we implement membrane gap penalties only when both columns are membrane
        if (transmembraneA && transmembraneB){
            transmembrane = true;
        }
        else {
            transmembrane = false;
        }

        if (gapOpen){
            return makeProperMultipleGap(posA-1, posB-1, matrix, true, transmembrane, mProfB, mProfA);
        }
        else {
            return makeProperMultipleGap(posA-1, posB-1, matrix, false, transmembrane, mProfB, mProfA);
        }
    }

    /////////////////////////////////////////////////////////////////////
    //help functions for making proper multiple gap
    private double makeProperMultipleGap(int posA, int posB, char matrix, boolean gapOpen, boolean membrane, char[][] mProfB, char[][] mProfA){
        int lenA = mProfA[0].length;
        int lenB = mProfB[0].length;

        //playing with gap
        int sizeA = mProfA.length;
        int sizeB = mProfB.length;


        //float newGOP, newGOpMembr, newGExt, newGMembrExt;
        //if (sizeA == 1 && sizeB == 1){
            //newGOP = this.gOP;
            //newGOpMembr = this.gOpMembr;
            //newGExt = this.gExt;
            //newGMembrExt = this.gExtMembr;
        //}
        //else {
            //newGOP = -19f;
            //newGOpMembr = -17f;
            //newGExt = -1f;
            //newGMembrExt = -4f;
        //}
        //at first, get into account length of profiles and mismatch of matrix;
        //at second, check for gaps and get them into account (return)
        //at third, check for distance from gap.
        if (gapOpen) {
            if (membrane){
                //double normgOpMembr = (-1) * (Math.abs(this.gOpMembr) + Math.log(Math.min(lenA, lenB))) *
                        //AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                //double normgOpMembr = this.gOpMembr * AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                //double normgOpMembr = implementMultipleApproachesForGap(posA, posB, newGOpMembr, matrix, mProfB, mProfA);
                //double normgOpMembr = (-1) * (Math.abs(newGOpMembr) + Math.log(Math.min(lenA, lenB))) *
                        //AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                //normgOpMembr = this.gOpMembr * AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.Phat);
                double normgOpMembr = implementMultipleApproachesForGap(posA, posB, true, this.gOpMembr, matrix, mProfB, mProfA);
                return normgOpMembr;
            }
            else{
                //double normgOP = (-1) * (Math.abs(this.gOP) + Math.log(Math.min(lenA, lenB))) *
                        //AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.BLOSUM62);
                //double normgOP = this.gOP * AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.BLOSUM62);
                //double normgOP = (-1) * (Math.abs(newGOP) + Math.log(Math.min(lenA, lenB))) *
                        //AverageMatrixMismatch.calculateAverage(SubstitutionMatrix.BLOSUM62);
                //double normgOP = implementMultipleApproachesForGap(posA, posB, newGOP, matrix, mProfB, mProfA);
                double normgOP = implementMultipleApproachesForGap(posA, posB, false, this.gOP, matrix, mProfB, mProfA);
                return normgOP;
            }
        }
        else {
            if (membrane){
                //double normgExtMembr = this.gExtMembr * (1.0 + Math.abs((double) lenA / (double) lenB));
                //return normgExtMembr;
                //return gExtMembr;
                return this.gExtMembr;
            }
            else {
                //double normgExt = this.gExt * (1.0 + Math.abs((double) lenA / (double) lenB));
                //return normgExt;
                //return gExt;
                return this.gExt;
            }
        }

    }

    //get right gap:
    private double implementMultipleApproachesForGap(int posA, int posB, boolean membrane, double gap, char matrix, char[][] mProfB, char[][] mProfA){
        if (matrix == 'V'){
            if (membrane){
                if (findGap(posA, mProfA)){
                    //gap = gap * fractionOfNoGapColumns(posB, mProfB);
                    gap = gap * 0.9 * fractionOfNoGapColumns(posA, mProfA);
                    gap = gap * ((double)(8 - distanceFromGap(posB, mProfB))/2);
                    return gap;
                }
                else {
                    gap = gap * ((double)(8 - distanceFromGap(posB, mProfB))/2);
                    return 10 * gap;
                }
            }
            else {
                if (findGap(posA, mProfA)){
                    //gap = gap * 0.3 * fractionOfNoGapColumns(posA, mProfA);
                    gap = gap * 0.9 * fractionOfNoGapColumns(posA, mProfA);
                    gap = gap * ((double)(8 - distanceFromGap(posB, mProfB))/2);
                    return gap;
                }
                else {
                    //what profile???
                    //gap = gap * (2 + (double)(8 - distanceFromGap(posB, mProfB))/4);
                    //gap = gap * (2 + (double)((8 - distanceFromGap(posB, mProfB))*2)/8);
                    gap = gap * ((double)(8 - distanceFromGap(posB, mProfB))/2);
                    return 2 * gap;
                }
            }
        }
        else {
            if (membrane){
                if (findGap(posB, mProfB)){
                    //gap = gap * fractionOfNoGapColumns(posB, mProfB);
                    gap = gap * 0.9 * fractionOfNoGapColumns(posB, mProfB);
                    gap = gap * ((double)(8 - distanceFromGap(posA, mProfA))/2);
                    return gap;
                }
                else {
                    gap = gap * ((double)(8 - distanceFromGap(posA, mProfA))/2);
                    return 10 * gap;

                }
            }
            else {
                if (findGap(posB, mProfB)){
                    //gap = gap * 0.3 * fractionOfNoGapColumns(posB, mProfB);
                    gap = gap * 0.9 * fractionOfNoGapColumns(posB, mProfB);
                    gap = gap * ((double)(8 - distanceFromGap(posA, mProfA))/2);
                    return gap;
                }
                else {
                    //what profile???
                    //gap = gap * (2 + (double)(8 - distanceFromGap(posA, mProfA))/4);
                    gap = gap * ((double)(8 - distanceFromGap(posA, mProfA))/2);
                    return 2 * gap;
                }
            }
        }
    }


    //function to find a gap in the specified column
    private boolean findGap(int i, char[][] profile) {
        boolean hasGap = false;
        for (int x = 0; x < profile.length; x++) {
            if (profile[x][i] == '-') {
                hasGap = true;
                break;
            }
        }

        return hasGap;
    }

    //function to calculate fraction of sequences without a gap in a column
    private double fractionOfNoGapColumns(int i, char[][] profile){
        int numOfSeq = profile.length;
        int numWithNoGaps = 0;
        for (int x = 0; x < profile.length; x++) {
            if (profile[x][i] != '-') {
                numWithNoGaps++;
            }
        }

        return (double) numWithNoGaps / (double) numOfSeq;
    }

    //function to get distance from gap (what specific is here?)
    //for example, if there is a column with only one gap and 20 other residues,
    //should we continue penalize it as an equal gap event??? (ask Spirin or Mironov)
    //and what direction??
    private int distanceFromGap(int i, char[][] profile){
        int numOfSeq = profile.length;

        //check both directions and choose minimal
        int posL = i-1;
        int posR = i+1;
        int distanceRight = 0;
        int distanceLeft = 0;
        //this parametr was chosen from ClustalW program
        int kL = 6;
        int kR = 6;
        while (posL >= 0 && kL > 0){
            if (findGap(posL, profile)){
                break;
            }
            kL--;
            posL--;
            distanceLeft++;
        }

        while (posR < profile[0].length && kR > 0){
            if (findGap(posR, profile)){
                break;
            }
            kR--;
            posR++;
            distanceRight++;
        }

        //to prevent case when we reached the end of profile nearby, but real gap
        //is at the much more distance.
        if (posL == -1){ return distanceRight; }
        if (posR == profile[0].length) { return distanceLeft; }
        return Math.min(distanceLeft, distanceRight);
    }


    //function to reveal, whether the position is within membrane part or not
    private boolean pairwiseIsMembraneProbs(int pos, double[][] probs){

        if (pos - 1 < 0 && pos + 1 == probs.length){
            System.out.println("Error, looks like sequence of only one length");
            System.exit(1);
        }

        if (pos - 1 < 0){
            if (probs[pos][1] > 0.9 && probs[pos+1][1] > 0.9){
                return true;
            }
            else {
                return false;
            }
        }
        else if (pos + 1 == probs.length){
            if (probs[pos - 1][1] > 0.9 && probs[pos][1] > 0.9){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (probs[pos - 1][1] > 0.5 && probs[pos][1] > 0.9 && probs[pos + 1][1] > 0.9){
                return true;
            }
            else {
                return false;
            }
        }
    }

    private boolean pairwiseIsMembraneViterbi(int pos, char[] path){

        if (pos - 1 < 0 && pos + 1 == path.length){
            System.out.println("Error, looks like sequence length is only one residue");
            System.exit(1);
        }

        if (pos - 1 < 0){
            if (path[pos] == 'm' && path[pos+1] == 'm'){
                return true;
            }
            else {
                return false;
            }
        }
        else if (pos + 1 == path.length){
            if (path[pos - 1] == 'm' && path[pos] == 'm'){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (path[pos - 1] == 'm' && path[pos] == 'm' && path[pos + 1] == 'm'){
                return true;
            }
            else {
                return false;
            }
        }
    }

    //for multiple sequence case - check, whether or not column in the profile is membrane
    //(if 90% or more of residues are membrane - region is tm)
    //in multiple case you can not precisely track border conditions
    private boolean isColumnMembraneProbs(int pos, int[] probsPos, char[][] mProf, HashMap<Integer, double[][]> profProbs){
        int i;
        int lenA = probsPos.length;
        int numOfMembraneResidues = 0;
        int totalNumberOfResidues = 0;
        for (i = 0; i < lenA; i++) {
            //in case if it's probsPosA[x] gonna be -1 (for example, when it's two profiles
            //it can be (0,-1) situation (M- (for example))
            if (probsPos[i] >= 0) {
                if (mProf[i][pos] != '-'){
                    if (profProbs.get(i)[probsPos[i]][1] > 0.9) {
                        numOfMembraneResidues++;
                    }
                    totalNumberOfResidues++;
                }
            }
        }
        if (totalNumberOfResidues == 0){
            System.out.println("Column with all gaps - error in gap function");
            System.exit(1);
        }
        if ((double) numOfMembraneResidues/totalNumberOfResidues < 0.5){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isColumnMembraneViterbi(int pos, int[] pathPos, char[][] mProf, HashMap<Integer, char[]> profPath){
        int i;
        int lenA = pathPos.length;
        int numOfMembraneResidues = 0;
        int totalNumberOfResidues = 0;
        for (i = 0; i < lenA; i++) {
            //in case if it's probsPosA[x] gonna be -1 (for example, when it's two profiles
            //it can be (0,-1) situation (M- (for example))
            if (pathPos[i] >= 0) {
                if (mProf[i][pos] != '-'){
                    if (profPath.get(i)[pathPos[i]] == 'm') {
                        numOfMembraneResidues++;
                    }
                    totalNumberOfResidues++;
                }
            }
        }
        if (totalNumberOfResidues == 0){
            System.out.println("Column with all gaps - error in gap function");
            System.exit(1);
        }
        if ((double) numOfMembraneResidues/totalNumberOfResidues < 0.5){
            return false;
        }
        else {
            return true;
        }
    }
}
