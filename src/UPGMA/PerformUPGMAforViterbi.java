package UPGMA;

import Alignment.ViterbiNeedlemanWunsch;
import Help_classes.Pair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Fransilvion on 06.10.2016.
 * static class for performing UPGMA stage
 * (calculating the guide tree)
 * (using alignments, got from Viterbi)
 */
public class PerformUPGMAforViterbi {

    //////////////////////////////////////////////////////////////////////////
    //private constructor so no one can create a class
    private PerformUPGMAforViterbi(){

    }

    //////////////////////////////////////////////////////////////////////////
    //main method for building the guide tree
    public static UPGMA calculateGuideTree(String[] ids, char[][] sequences,
                                           ArrayList<ViterbiNeedlemanWunsch> alignemnts) throws FileNotFoundException {
        System.out.println("PERFORMING UPGMA CLUSTERIZATION");
        //step #1 - calculating of distance matrix
        double[][] matrix = buildDistMatrix(ids, alignemnts);

        //step #2 - write matrix into file and get guide tree in newick format
        writeMatrixAndDrawTree(ids, matrix);

        //step #3 - perform
        UPGMA upgma = new UPGMA(matrix, ids, sequences);

        //step #4 - return UPGMA with all relevant links
        return upgma;
    }

    //////////////////////////////////////////////////////////////////////////
    //put the distance matrix it to the file using python script
    private static void writeMatrixAndDrawTree(String[] ids, double[][] matrix) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("distance_matrix.txt");
        int i, j, x;
        StringBuilder s = new StringBuilder();

        for (x = 0; x < ids.length; x++){
            s.append(ids[x] + " ");
        }

        String idsLine = s.toString();
        out.println("order of ids (rows/columns): " + idsLine);

        for (i = 0; i < matrix.length; i++){
            for (j = 0; j < matrix[i].length; j++){
                out.print(matrix[i][j] + " ");
            }
            out.println();
        }
        out.close();

        //code to execute the python code for writing the guide tree into newick file format
        try {
            Process process = Runtime.getRuntime().exec("C:/Users/PC/AppData/Local/Programs/Python/Python35/python.exe C:/Users/PC/Desktop/TM_NewVersion/drawTree.py");
            //process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with drawTree python file: failed to draw the guide tree");
        }
    }

    //////////////////////////////////////////////////////////////////////////
    //build distance matrix and draw the tree using the script
    private static double[][] buildDistMatrix(String[] ids, ArrayList<ViterbiNeedlemanWunsch> alignments){
        double[][] distMatrix = new double[ids.length][];
        HashMap<Pair<String, String>, Double> distances = getDistances(alignments);
        int i, j;
        for (i = 0; i < ids.length; i++){
            distMatrix[i] = new double[i+1];
            for (j = 0; j < i+1; j++){
                String idI = ids[i];
                String idJ = ids[j];
                Pair<String, String> pair = new Pair<>(idI, idJ);

                //fullfill the matrix
                if (i == j){
                    distMatrix[i][j] = 0;
                } else {
                    distMatrix[i][j] = distances.get(pair);
                }
            }
        }
        return distMatrix;
    }

    //////////////////////////////////////////////////////////////////////////
    //get distances from the alignments
    private static HashMap<Pair<String, String>, Double> getDistances(ArrayList<ViterbiNeedlemanWunsch> alignments){
        int i;
        HashMap<Pair<String, String>, Double> distances = new HashMap<>();
        for (i = 0; i < alignments.size(); i++){
            distances.put(alignments.get(i).getPairOfIds(), alignments.get(i).getDistance());
        }
        return distances;
    }
}