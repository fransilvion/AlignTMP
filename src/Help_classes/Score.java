package Help_classes;


import java.util.HashMap;

/**
 * Created by Fransilvion on 19.09.2016.
 * class for calculation of weight in provided alignment position
 */
public class Score {

    private Score(){

    }

    public static double calculateSPscore(int numcol, int x, int y, String[] profileSeqIds,
                                          char[][] profileSeqs, HashMap<String, double[][]> probs, int[] probsPos){
        double costForResidues = 0.0;
        String id1 = profileSeqIds[x];
        String id2 = profileSeqIds[y];

        //for soluble part (indexes)
        costForResidues = costForResidues + (probs.get(id1)[probsPos[x]][0] * probs.get(id2)[probsPos[y]][0])
                * SubstitutionMatrix.BLOSUM62[SubstitutionMatrix.seqMatrix.indexOf(profileSeqs[x][numcol])][SubstitutionMatrix.seqMatrix.indexOf(profileSeqs[y][numcol])];

        //for transmembrane part (indexes)
        costForResidues = costForResidues + (probs.get(id1)[probsPos[x]][1] * probs.get(id2)[probsPos[y]][1])
                * SubstitutionMatrix.Phat[SubstitutionMatrix.seqMatrix.indexOf(profileSeqs[x][numcol])][SubstitutionMatrix.seqMatrix.indexOf(profileSeqs[y][numcol])];

        //for mix part (mix means that we align soluble with transmembrane part - we have to penalyze it).
        //make matrixPenalty - as a parameter. Right now put it equal to 0
        costForResidues = costForResidues + (probs.get(id1)[probsPos[x]][0] * probs.get(id2)[probsPos[y]][1]) * 0;
        costForResidues = costForResidues + (probs.get(id1)[probsPos[x]][1] * probs.get(id2)[probsPos[y]][0]) * 0;

        //costFunc = costFunc + (weightsA.get(x) * weightsB.get(y)) * costForResidues;
        return costForResidues;
    }

    //to calculate weight in the pairwise alignment
    public static double probsPairwiseScore(char quer, char targ, double[][] profProbsA,
                                        double[][] profProbsB, int i, int j){
        double costFunction = 0;

        //for soluble part;
        costFunction = costFunction + profProbsB[i][0] * profProbsA[j][0] *
                SubstitutionMatrix.BLOSUM62[SubstitutionMatrix.seqMatrix.indexOf(quer)][SubstitutionMatrix.seqMatrix.indexOf(targ)];

        //for transmembrane part;
        costFunction = costFunction + profProbsB[i][1] * profProbsA[j][1] *
                SubstitutionMatrix.Phat[SubstitutionMatrix.seqMatrix.indexOf(quer)][SubstitutionMatrix.seqMatrix.indexOf(targ)];

        //for mixed part;
        //negative score is prohibited
        //double k = 0;
        costFunction = costFunction + profProbsB[i][0] * profProbsA[j][1] * 0;
        costFunction = costFunction + profProbsB[i][1] * profProbsA[j][0] * 0;


        return costFunction;
    }

    //to calculate weight in the pairwise alignment (Viterbi)
    public static double viterbiPairwiseScore(char quer, char targ, char[] pathA,
                                            char[] pathB, int i, int j){
        double costFunction = 0;

        //for soluble part (if one residue is membrane, while another one - soluble, use BLOSUM62);
        if (pathB[i] == 's' && pathA[j] == 's'){
            costFunction = costFunction +
                    SubstitutionMatrix.BLOSUM62[SubstitutionMatrix.seqMatrix.indexOf(quer)][SubstitutionMatrix.seqMatrix.indexOf(targ)];
        }
        //for transmembrane part;
        else if (pathB[i] == 'm' && pathA[j] == 'm'){
            costFunction = costFunction +
                    SubstitutionMatrix.Phat[SubstitutionMatrix.seqMatrix.indexOf(quer)][SubstitutionMatrix.seqMatrix.indexOf(targ)];
        }
        //for mixed part;
        else if (pathB[i] == 's' || pathA[j] == 's'){
            int k = 0;
            costFunction = costFunction + k;
        }

        return costFunction;
    }

    //to calculate weight in multiple alignment (probabilities)
    //than implement usage of different score matrix
    public static double probMultipleScore(int i, int j, int[] probsPosA, int[] probsPosB, char[][] targA,
                                        char[][] queryB, HashMap<Integer, double[][]> profAprobs,
                                        HashMap<Integer, double[][]> profBprobs, HashMap<Integer, Double> weightsA,
                                           HashMap<Integer, Double> weightsB){
        double costFunc = 0;

        for (int x = 0; x < targA.length; x++) {
            if (targA[x][j] == '-') { continue; }
            for (int y = 0; y < queryB.length; y++) {
                if (queryB[y][i] == '-') { continue; }

                double costForResidues = 0;

                //for soluble part (indexes)
                costForResidues = costForResidues + (profAprobs.get(x)[probsPosA[x]][0] * profBprobs.get(y)[probsPosB[y]][0])
                        * SubstitutionMatrix.BLOSUM62[SubstitutionMatrix.seqMatrix.indexOf(targA[x][j])][SubstitutionMatrix.seqMatrix.indexOf(queryB[y][i])];

                //for transmembrane part (indexes)
                costForResidues = costForResidues + (profAprobs.get(x)[probsPosA[x]][1] * profBprobs.get(y)[probsPosB[y]][1])
                        * SubstitutionMatrix.Phat[SubstitutionMatrix.seqMatrix.indexOf(targA[x][j])][SubstitutionMatrix.seqMatrix.indexOf(queryB[y][i])];

                //for mix part (mix means that we align soluble with transmembrane part - we have to penalyze it).
                //make matrixPenalty - as a parameter. Right now put it equal to 0
                costForResidues = costForResidues + (profAprobs.get(x)[probsPosA[x]][0] * profBprobs.get(y)[probsPosB[y]][1]) * -0.5;
                costForResidues = costForResidues + (profAprobs.get(x)[probsPosA[x]][1] * profBprobs.get(y)[probsPosB[y]][0]) * -0.5;

                costFunc = costFunc + (weightsA.get(x) + weightsB.get(y)) * costForResidues;
                //costFunc = costFunc + costForResidues;


            }
        }
        return costFunc;
    }

    //to calculate weight in multiple alignment (Viterbi path)
    //than implement usage of different score matrix
    public static double viterbiMultipleScore(int i, int j, int[] pathPosA, int[] pathPosB, char[][] targA,
                                           char[][] queryB, HashMap<Integer, char[]> profApath,
                                           HashMap<Integer, char[]> profBpath, HashMap<Integer, Double> weightsA,
                                              HashMap<Integer, Double> weightsB){
        double costFunc = 0;

        for (int x = 0; x < targA.length; x++) {
            if (targA[x][j] == '-'){ continue; }
            for (int y = 0; y < queryB.length; y++) {

                if (queryB[y][i] == '-') { continue; }

                //for soluble part (indexes)
                if (profApath.get(x)[pathPosA[x]] == 's' && profBpath.get(y)[pathPosB[y]] == 's'){
                    costFunc = costFunc + (weightsA.get(x) + weightsB.get(y)) *
                            SubstitutionMatrix.BLOSUM62[SubstitutionMatrix.seqMatrix.indexOf(targA[x][j])][SubstitutionMatrix.seqMatrix.indexOf(queryB[y][i])];
                }
                //for transmembrane part
                else if (profApath.get(x)[pathPosA[x]] == 'm' && profBpath.get(y)[pathPosB[y]] == 'm'){
                    costFunc = costFunc + (weightsA.get(x) + weightsB.get(y)) *
                            SubstitutionMatrix.Phat[SubstitutionMatrix.seqMatrix.indexOf(targA[x][j])][SubstitutionMatrix.seqMatrix.indexOf(queryB[y][i])];
                }
                //for mix part (mix means that we align soluble with transmembrane part - we have to penalyze it).
                //make matrixPenalty - as a parameter. Right now put it equal to 0
                else if (profApath.get(x)[pathPosA[x]] == 's' || profBpath.get(y)[pathPosB[y]] == 's'){
                    costFunc = costFunc + (weightsA.get(x) + weightsB.get(y)) * -0.5;
                    //costFunc = costFunc + -0.5;
                }


            }
        }
        return costFunc;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////PFAM STATISTICS SCORE

    //for pairwise alignment
    public static double statPairwiseScore(char quer, char targ, double[][] profProbsA,
                                            double[][] profProbsB, int i, int j){
        double costFunction = 0;

        String key = new StringBuilder().append(quer).append(targ).toString();

        double probsA = profProbsA[j][1];
        double probsB = profProbsB[i][1];
        Pair indexesAB;

        if (!SubstitutionMatrix.statPfamMatrix.containsKey(key)){
            key = new StringBuilder().append(targ).append(quer).toString();
            indexesAB = getIndexes(probsA, probsB);
        }
        else {
            indexesAB = getIndexes(probsB, probsA);
        }

        costFunction = costFunction +
                SubstitutionMatrix.statPfamMatrix.get(key)[(Integer) indexesAB.getLeft()][(Integer) indexesAB.getRight()];
        return costFunction;
    }

    //for multiple alignment
    public static double statMultipleScore(int i, int j, int[] probsPosA, int[] probsPosB, char[][] targA,
                                           char[][] queryB, HashMap<Integer, double[][]> profAprobs,
                                           HashMap<Integer, double[][]> profBprobs, HashMap<Integer, Double> weightsA,
                                           HashMap<Integer, Double> weightsB){
        double costFunc = 0;

        for (int x = 0; x < targA.length; x++) {
            if (targA[x][j] == '-') { continue; }
            for (int y = 0; y < queryB.length; y++) {

                if (queryB[y][i] == '-') { continue; }
                double costForResidues = 0;

                String key = new StringBuilder().append(queryB[y][i]).append(targA[x][j]).toString();

                double probsA = profAprobs.get(x)[probsPosA[x]][1];
                double probsB = profBprobs.get(y)[probsPosB[y]][1];
                Pair indexesAB;

                if (!SubstitutionMatrix.statPfamMatrix.containsKey(key)){
                    key = new StringBuilder().append(targA[x][j]).append(queryB[y][i]).toString();
                    indexesAB = getIndexes(probsA, probsB);
                }
                else {
                    indexesAB = getIndexes(probsB, probsA);
                }

                costForResidues = costForResidues +
                        SubstitutionMatrix.statPfamMatrix.get(key)[(Integer) indexesAB.getLeft()][(Integer) indexesAB.getRight()];

                costFunc = costFunc + (weightsA.get(x) + weightsB.get(y)) * costForResidues;
                //costFunc = costFunc + costForResidues;


            }
        }

        return costFunc;
    }

    //helping function to get indexes for arrays from statPfamMatrix
    private static Pair<Integer, Integer> getIndexes(double indA, double indB){
        Pair<Integer, Integer> pair;
        if (indA < 0.1){
            if (indB < 0.1) {
                pair = new Pair<>(0, 0);
            }
            else if (indB < 0.9) {
                pair = new Pair<>(0, 1);
            }
            else {
                pair = new Pair<>(0, 2);
            }
        }
        else if (indA < 0.9){
            if (indB < 0.1) {
                pair = new Pair<>(1, 0);
            }
            else if (indB < 0.9) {
                pair = new Pair<>(1, 1);
            }
            else {
                pair = new Pair<>(1, 2);
            }
        }
        else {
            if (indB < 0.1) {
                pair = new Pair<>(2, 0);
            }
            else if (indB < 0.9) {
                pair = new Pair<>(2, 1);
            }
            else {
                pair = new Pair<>(2, 2);
            }
        }
        return pair;
    }

}
