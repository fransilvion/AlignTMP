package Alignment;

import Help_classes.Pair;
import Help_classes.Profile;
import UPGMA.UPGMA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fransilvion on 19.04.2017.
 */
public abstract class Refinement {
    public static Alignment performRefinement() {
        return null;
    }

    //////////////////////////////////////////////////////////////////////////
    //method for tree bipartition
    static ArrayList<String> treeBipartition(UPGMA tree, int i){
        return tree.getCluster()[i].getProfileIds();
    }

    //////////////////////////////////////////////////////////////////////////
    //method for profile extraction
    static Pair<Profile, Profile> profileExtraction(ArrayList<String> ids, Profile profile){
        return profile.divideProfile(ids);
    }

    //////////////////////////////////////////////////////////////////////////
    //method for deleting columns with only gaps from profile
    static Profile deleteOnlyGapsColumns(Profile profile) throws Exception {
        List<Integer>[] arrayOfIndexes = new List[profile.getProfileLength()];
        String[] sequences = new String[profile.getProfileLength()];
        int i;
        for (i = 0; i < profile.getProfileLength(); i++){
            arrayOfIndexes[i] = findGaps(new String(profile.getSequences()[i]));
        }
        List<Integer> resultList = arrayOfIndexes[0];
        for (i = 0; i < arrayOfIndexes.length; i++){
            resultList.retainAll(arrayOfIndexes[i]);
        }
        for (i = 0; i < profile.getProfileLength(); i++){
            sequences[i] = deleteCharacters(new String(profile.getSequences()[i]), resultList);
        }
        return new Profile(profile.getIds(), sequences, profile.getAllWeights());
    }

    //////////////////////////////////////////////////////////////////////////
    //find indexes of gaps in the position
    static List<Integer> findGaps(String sequence) {
        List<Integer> resultSet = new ArrayList<>();
        int index = sequence.indexOf('-');
        while (index >= 0) {
            resultSet.add(index);
            index = sequence.indexOf('-', index + 1);
        }
        return resultSet;
    }

    //////////////////////////////////////////////////////////////////////////
    //delete chars from string with particular indexes
    static String deleteCharacters(String sequence, List<Integer> indexes){
        StringBuilder sb = new StringBuilder(sequence);
        int k = 0; //to fix the changes in indexes after deleting
        for (int i : indexes){
            sb.deleteCharAt(i-k);
            k++;
        }
        String resultString = sb.toString();
        return resultString;
    }

    static double[][] buildDistMatrixRefinement(char[][] profileSeqs){
        double[][] distMatrix = new double[profileSeqs.length][];
        int i, j;
        for (i = 0; i < profileSeqs.length; i++){
            distMatrix[i] = new double[i+1];
            char[] firstSeq = profileSeqs[i];
            for (j = 0; j < i+1; j++){
                char[] secondSeq = profileSeqs[j];
                //fullfill the matrix
                if (i == j){
                    distMatrix[i][j] = 0;
                } else {
                    char[][] pair = new char[2][];
                    pair[0] = firstSeq;
                    pair[1] = secondSeq;

                    double newDist = calculatePairDistance(pair);
                    distMatrix[i][j] = newDist;
                }
            }
        }
        return distMatrix;
    }

    private static double calculatePairDistance(char[][] profileSeqs){
        int num_ident = 0, num_comp = 0;
        int i;
        double distance;
        int len = profileSeqs[0].length;
        for (i = 0; i < len; i++){
            if (profileSeqs[0][i] == '-' || profileSeqs[1][i] == '-') { continue; }
            if (profileSeqs[0][i] == profileSeqs[1][i]){
                num_ident++;
                num_comp++;
            }
            else {
                num_comp++;
            }
        }

        distance = 1.0 - ((double)(num_ident)/(num_comp));
        return distance;
    }

    //for testing
    static boolean isMiddleGaps(Profile profile){
        char[][] seq = profile.getSequences();
        int len = profile.getProfileLength();
        int firstLetterIndex = 0;
        boolean foundFirst = false;
        int lastLetterIndex = 0;
        boolean foundLast = false;
        //find first letter index
        for (int i = 0; i < seq[0].length; i++){
            for (int j = 0; j < len; j++){
                if (seq[j][i] != '-'){
                    foundFirst = true;
                    break;
                }
            }
            if (foundFirst){
                firstLetterIndex = i;
                break;
            }
        }
        //find last letter index
        for (int i = seq[0].length - 1; i >= 0 ; i--){
            for (int j = 0; j < len; j++){
                if (seq[j][i] != '-'){
                    foundLast = true;
                    break;
                }
            }
            if (foundLast){
                lastLetterIndex = i;
                break;
            }
        }

        List<Integer>[] arrayOfIndexes = new List[profile.getProfileLength()];
        int ni;
        for (ni = 0; ni < profile.getProfileLength(); ni++){
            arrayOfIndexes[ni] = findGaps(new String(profile.getSequences()[ni]));
        }
        List<Integer> resultList = arrayOfIndexes[0];
        int nj;
        for (nj = 0; nj < arrayOfIndexes.length; nj++){
            resultList.retainAll(arrayOfIndexes[nj]);
        }

        boolean isMiddle = false;
        int nx;
        for (nx = 0; nx < resultList.size(); nx++){
            if (resultList.get(nx) > firstLetterIndex && resultList.get(nx) < lastLetterIndex){
                isMiddle = true;
                break;
            }
        }

        return isMiddle;
    }


}
